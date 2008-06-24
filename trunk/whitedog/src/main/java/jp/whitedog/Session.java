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
package jp.whitedog;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

import jp.whitedog.util.StringUtil;

/**
 * Abstract class for the class that implements network session management.
 * @author Takao Nakaguchi
 */
public abstract class Session
implements Serializable
{
	/**
	 * Constructor.
	 * @param sessionId session ID
	 * @param displayName display name of this session
	 */
	public Session(String sessionId){
		this.sessionId = sessionId;
	}

	/**
	 * returns ID of this session.
	 * @return ID of this session
	 */
	public String getSessionId(){
		return sessionId;
	}

	/**
	 * returns self Peer.
	 * @return Peer
	 */
	public Peer getSelfPeer(){
		return selfPeer;
	}

	/**
	 * add Peer event listener.
	 * @param listener Peer event listener to be added
	 */
	public void addPeerListener(PeerListener listener){
		peerListeners.add(listener);
	}

	/**
	 * remove Peer event listener.
	 * @param listener Peer event listener to be removed
	 */
	public void removePeerListener(PeerListener listener){
		peerListeners.remove(listener);
	}

	/**
	 * register shared object.
	 * @param object object to share its method execution
	 */
	public void register(Object object){
		String objectId = object.getClass().getName() + "#" + count;
		register(object, objectId);
		count++;
	}

	/**
	 * register shared object.
	 * @param object object to share its method execution
	 * @param objectId id to object
	 */
	public void register(Object object, String objectId){
		SharedObject so = (SharedObject)object;
		idToObject.put(objectId, object);
		so.bindToSession(Session.this, objectId);
		logger.info("object registered: " + objectId);
	}

	/**
	 * unregister shared object.
	 * @param object object to stop sharing
	 */
	public void unregister(Object object){
		SharedObject so = (SharedObject)object;
		String objectId = so.getObjectId();
		idToObject.remove(objectId);
		so.unbinedFromSession();
	}

	public void connect()
	throws WhiteDogException{
		doConnect();
	}

	public void disconnect()
	throws WhiteDogException{
		doDisconnect();
	}

	/**
	 * share method execution.
	 * @param object shared object
	 * @param method method to share
	 * @param args arguments of method
	 * @param proceeder proceeder of this execution
	 * @throws WhiteDogException
	 */
	public Object share(
			SharedObject object, Method method, Object[] args
			, Proceeder proceeder)
	throws WhiteDogException
	{
		if(receiving.get()){
			return proceeder.proceed();
		}
		String id = object.getObjectId();
		doShare(new MethodExecution(id, method, args));
		return null;
	}

	public void dispatch(MethodExecution execution){
		Object object = idToObject.get(execution.getObjectId());
		if(object == null){
			logger.warning("no object found: " + execution.getObjectId());
			return;
		}
		receiving.set(true);
		try{
			execution.execute(object);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} finally{
			receiving.set(false);
		}
	}

	protected abstract void doConnect()
	throws WhiteDogException;

	protected abstract void doDisconnect()
	throws WhiteDogException;

	protected abstract void doShare(MethodExecution execution)
	throws WhiteDogException;

	protected void peerEntered(Peer peer){
		register(peer, peer.getPeerId());
		peers.add(peer);
		firePeerEntered(peer);
	}

	protected void peerLeaved(Peer peer){
		firePeerLeaved(peer);
		peers.remove(peer);
		unregister(peer);
	}

	private void firePeerEntered(Peer peer){
		for(PeerListener l : peerListeners){
			l.peerEntered(peer);
		}
	}

	private void firePeerLeaved(Peer peer){
		for(PeerListener l : peerListeners){
			l.peerLeaved(peer);
		}
	}

	private String sessionId;
	private Peer selfPeer = new Peer(StringUtil.randomString(16));
	private Set<Peer> peers = new LinkedHashSet<Peer>();
	private Set<PeerListener> peerListeners = new LinkedHashSet<PeerListener>();

	private transient Map<String, Object> idToObject = new HashMap<String, Object>();
	private transient int count;
	private transient ThreadLocal<Boolean> receiving = new ThreadLocal<Boolean>(){
		protected Boolean initialValue() {
			return Boolean.FALSE;
		}
	};
	private static Logger logger = Logger.getLogger(
			Session.class.getPackage().getName());
}
