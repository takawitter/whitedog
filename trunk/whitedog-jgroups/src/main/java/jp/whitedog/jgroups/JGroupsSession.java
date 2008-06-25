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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jp.whitedog.MethodExecution;
import jp.whitedog.Peer;
import jp.whitedog.PeerFactory;
import jp.whitedog.Session;
import jp.whitedog.WhiteDogException;

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
					} else if(o instanceof PeerIntroductionResponse){
						handlePeerIntroductionResponse(src, (PeerIntroductionResponse)o);
					}
					super.receive(msg);
				}
				@Override
				public void viewAccepted(View new_view) {
					handleViewAccepted(new_view);
					super.viewAccepted(new_view);
				}
			});
			channel.connect(getSessionId());
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
		if(!p.getPeerId().equals(getSelfPeer().getPeerId())){
			try{
				channel.send(new Message(
						source, null
						, new PeerIntroductionResponse(getSelfPeer().getPeerId())
						));
			} catch(ChannelNotConnectedException e){
				e.printStackTrace();
			} catch(ChannelClosedException e){
				e.printStackTrace();
			}
		}
	}

	private void handlePeerIntroductionResponse(
			Address src, PeerIntroductionResponse response){
		Peer p = newPeer(response.getPeerId());
		addressToPeer.put(src, p);
		peerToAddress.put(p, src);
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

	private JChannel channel;
	private Set<Address> members = new HashSet<Address>();
	private Map<Address, Peer> addressToPeer = new HashMap<Address, Peer>();
	private Map<Peer, Address> peerToAddress = new HashMap<Peer, Address>();
	private static final long serialVersionUID = -3548894109396142431L;
}
