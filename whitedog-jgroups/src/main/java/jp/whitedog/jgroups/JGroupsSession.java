/* $Id$
 * 
 * Copyright 2008 Takao Nakaguchi.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.whitedog.jgroups;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import jp.whitedog.MethodExecution;
import jp.whitedog.Peer;
import jp.whitedog.PeerFactory;
import jp.whitedog.Session;
import jp.whitedog.WhiteDogException;
import jp.whitedog.util.Pair;

import org.jgroups.Address;
import org.jgroups.ChannelClosedException;
import org.jgroups.ChannelException;
import org.jgroups.ChannelNotConnectedException;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

/**
 * Session implementation using JGroups.
 * @author Takao Nakaguchi
 */
public class JGroupsSession extends Session {
	/**
	 * Constructor.
	 * @param sessionId session ID
	 * @param peerFactory Peer factory
	 */
	public JGroupsSession(String sessionId, PeerFactory peerFactory){
		super(sessionId, peerFactory);
	}

	/**
	 * Constructor.
	 * @param sessionId session ID
	 */
	public JGroupsSession(String sessionId){
		super(sessionId);
	}

	protected void doConnect()
	throws WhiteDogException
	{
		if(channel != null) return;
		try {
			channel = new JChannel();
			channel.setReceiver(new ReceiverAdapter(){
				@Override
				public void receive(Message msg) {
					Address src = msg.getSrc();
					Object o = msg.getObject();
					if(o instanceof MethodExecution){
						handleMethodExecution(src, (MethodExecution)o);
					} else if(o instanceof PeerIntroduction){
						handlePeerIntroduction(src, (PeerIntroduction)o);
					}
					super.receive(msg);
				}
				@Override
				public void viewAccepted(View new_view) {
					logger.info("view accepted");
					handleViewAccepted(new_view);
					super.viewAccepted(new_view);
				}
				@Override
				public byte[] getState() {
					logger.info("get state");
					return handleGetState();
				}
				@Override
				public void setState(byte[] state) {
					logger.info("set state");
					handleSetState(state);
					fireSessionStart();
				}
			});

			channel.connect(getSessionId());
			if(!channel.getState(null, 10000)){
				fireSessionStart();
			}
			channel.send(new Message(
					null, null, new PeerIntroduction(getSelfPeer().getPeerId())
					));
		} catch(ChannelException e) {
			throw new WhiteDogException(e);
		}
	}

	protected void doDisconnect() throws WhiteDogException{
		channel.disconnect();
		channel = null;
	}

	protected void doShare(MethodExecution execution)
	throws WhiteDogException
	{
		if(channel == null){
			connect();
		}
		try{
			channel.send(new Message(null, null, execution));
		} catch(ChannelClosedException e){
			throw new WhiteDogException(e);
		} catch(ChannelNotConnectedException e){
			throw new WhiteDogException(e);
		}
	}

	private void handleMethodExecution(Address source, MethodExecution execution){
		Peer sender = addressToPeer.get(source);
		if(sender == null){
			throw new RuntimeException("peer is null");
		}
		dispatch(sender, execution);
	}

	private void handlePeerIntroduction(Address source, PeerIntroduction intro){
		Peer p = null;
		if(intro.getPeerId().equals(getSelfPeer().getPeerId())){
			p = getSelfPeer();
		} else{
			p = newPeer(intro.getPeerId());
		}
		addressToPeer.put(source, p);
		peerToAddress.put(p, source);
		peerEntered(p);
	}

	private void handleViewAccepted(View new_view){
		Set<Address> removed = new HashSet<Address>(members);
		for(Object a : new_view.getMembers()){
			Address address = (Address)a;
			members.add(address);
			removed.remove(address);
		}
		for(Address a : removed){
			// process peer remove
			members.remove(a);
			Peer p = addressToPeer.get(a);
			if(p != null){
				peerLeaved(p);
				addressToPeer.remove(a);
				peerToAddress.remove(p);
			}
		}
	}

	private byte[] handleGetState(){
		List<Pair<String, Address>> peers = new ArrayList<Pair<String, Address>>();
		for(Peer p : getPeers()){
			peers.add(Pair.create(p.getPeerId(), peerToAddress.get(p)));
		}
		List<Pair<String, Object>> container = new ArrayList<Pair<String,Object>>();
		for(Map.Entry<String, Pair<Object, Field>> entry : getSharedFields()){
			Field field = entry.getValue().getSecond();
			if(!field.isAccessible()){
				field.setAccessible(true);
			}
			Object value = null;
			try {
				value = field.get(entry.getValue().getFirst());
			} catch (IllegalArgumentException e) {
				logger.log(Level.SEVERE, "failed to get value", e);
			} catch (IllegalAccessException e) {
				logger.log(Level.SEVERE, "failed to get value", e);
			}
			String id = entry.getKey();
			container.add(Pair.create(id, value));
		}
		logger.info(container.size() + " objects sent as state");
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		try{
			ObjectOutputStream oos = new ObjectOutputStream(bout);
			oos.writeObject(peers);
			oos.writeObject(container);
			oos.close();
		} catch(IOException e){
			logger.log(Level.SEVERE, "failed to serialize shared object", e);
		}
		return bout.toByteArray();
	}

	@SuppressWarnings("unchecked")
	private void handleSetState(byte[] state){
		ByteArrayInputStream bin = new ByteArrayInputStream(state);
		try{
			ObjectInputStream ois = new ObjectInputStream(bin);
			List<Pair<String, Address>> peers = (List<Pair<String, Address>>)ois.readObject();
			for(Pair<String, Address> p : peers){
				Peer peer = newPeer(p.getFirst());
				peerToAddress.put(peer, p.getSecond());
				addressToPeer.put(p.getSecond(), peer);
				peerEntered(peer);
			}
			List<Pair<String, Object>> container = (List<Pair<String, Object>>)ois.readObject();
			logger.info(container.size() + " objects received as state");
			for(Pair<String, Object> element : container){
				assign(element.getFirst(), element.getSecond());
			}
		} catch(ClassNotFoundException e){
			logger.log(Level.SEVERE, "failed to deserialize shared object", e);
		} catch(IOException e){
			logger.log(Level.SEVERE, "failed to deserialize shared object", e);
		}
	}
	
	private JChannel channel;
	private Set<Address> members = new HashSet<Address>();
	private Map<Address, Peer> addressToPeer = new HashMap<Address, Peer>();
	private Map<Peer, Address> peerToAddress = new HashMap<Peer, Address>();
	private static final Logger logger = Logger.getLogger(
			JGroupsSession.class.getName());
	private static final long serialVersionUID = -3548894109396142431L;
}
