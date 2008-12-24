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

import java.io.Externalizable;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import jp.whitedog.annotation.Share;
import jp.whitedog.util.Pair;

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
	 * @param factory Peer factory
	 */
	public Session(String sessionId, PeerFactory factory){
		this.sessionId = sessionId;
		this.peerFactory = factory;
		selfPeer = newPeer(UUID.randomUUID().toString());
	}

	/**
	 * Constructor.
	 * @param sessionId session ID
	 */
	public Session(String sessionId){
		this(sessionId, new PeerFactory(){
			public Peer createPeer(String peerId) {
				return new Peer(peerId);
			}
		});
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
	 * returns the current sender peer if exists.
	 * This method returns valid peer only in dispatching context.
	 * @return current sender peer. null if not exists.
	 */
	public Peer getCurrentSender(){
		return currentSender.get();
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

	public void addStateSynchronizationListener(StateSynchronizationListener listener){
		stateSynchronizationListeners.add(listener);
	}

	public void removeStateSynchronizationListener(StateSynchronizationListener listener){
		stateSynchronizationListeners.remove(listener);
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
		Class<?> clazz = object.getClass();
		while(!clazz.equals(Object.class)){
			for(Field f : clazz.getDeclaredFields()){
				Share s = f.getAnnotation(Share.class);
				if(s == null) continue;

				if(!f.isAccessible()){
					f.setAccessible(true);
				}
				Object value = null;
				try{
					value = f.get(object);
				} catch(IllegalAccessException e){
					logger.log(Level.WARNING, "failed to get field.", e);
					continue;
				}
				Class<?> fieldType = f.getType();
				if(!(fieldType.isPrimitive())
						&& !(String.class.isAssignableFrom(fieldType))
						&& !(Serializable.class.isAssignableFrom(fieldType))
						&& !(Externalizable.class.isAssignableFrom(fieldType))
						&& !(value instanceof Serializable)
						&& !(value instanceof Externalizable)
						){
					// TODO more essential code needed
					throw new WhiteDogRuntimeException(
							"fields annotated by jp.whitedog.Share must be" +
							" serializable or externalizable.  field name: "
							+ f.getName() + "  field class: " + f.getType()
							+ "  declaring class: " + object.getClass().getName()
							);
				}

				String fieldId = objectId + "#" + f.getName();
				idToFields.put(fieldId, Pair.create(object, f));
				logger.info("field registered: " + fieldId
						+ "  field name: "
							+ f.getName() + "  field class: " + f.getType()
							+ "  declaring class: " + object.getClass().getName());
			}
			clazz = clazz.getSuperclass();
		}
		idToObject.put(objectId, object);
		so.bindToSession(this, objectId);
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
		so.unbindFromSession();
	}

	/**
	 * Connect to the session.
	 * @throws WhiteDogException failed to connect
	 */
	public void connect()
	throws WhiteDogException{
		doConnect();
	}

	/**
	 * Disconnect from the session.
	 * @throws WhiteDogException some network error occurred
	 */
	public void disconnect()
	throws WhiteDogException{
		doDisconnect();
	}

	/**
	 * Gets the Peer list in the session.
	 * @return Peer list
	 */
	public List<Peer> getPeers(){
		return new ArrayList<Peer>(peers);
	}

	/**
	 * Share method execution. The session encode method execution and
	 * send Peers in the session.
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
		if(dispatching.get()){
			return proceeder.proceed();
		}
		String id = object.getObjectId();
		doShare(new MethodExecution(id, method, args));
		return null;
	}

	protected Collection<Map.Entry<String, Pair<Object, Field>>> getSharedFields(){
		return idToFields.entrySet();
	}

	protected void assign(String fieldId, Object value){
		Pair<Object, Field> of = idToFields.get(fieldId);
		if(of == null){
			logger.warning("unknown field id specified.");
			return;
		}
		Field f = of.getSecond();
		if(!f.isAccessible()){
			f.setAccessible(true);
		}
		try {
			f.set(of.getFirst(), value);
		} catch (IllegalArgumentException e) {
			logger.log(Level.SEVERE, "failed to assign value", e);
		} catch (IllegalAccessException e) {
			logger.log(Level.SEVERE, "failed to assign value", e);
		}
	}

	/**
	 * Dispatche method execution. Actual session class (derived from
	 * the Session class) should call this method when receiving
	 * method execution message from other Peer.
	 * @param sender sender of the message
	 * @param execution MethodExecution object
	 */
	protected void dispatch(Peer sender, MethodExecution execution){
		Object object = idToObject.get(execution.getObjectId());
		if(object == null){
			logger.warning("no object found: " + execution.getObjectId());
			return;
		}
		dispatching.set(true);
		currentSender.set(sender);
		try{
			execution.execute(object);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} finally{
			currentSender.set(null);
			dispatching.set(false);
		}
	}

	protected abstract void doConnect()
	throws WhiteDogException;

	protected abstract void doDisconnect()
	throws WhiteDogException;

	protected abstract void doShare(MethodExecution execution)
	throws WhiteDogException;

	protected Peer newPeer(String peerId){
		return peerFactory.createPeer(peerId);
	}

	protected void peerEntered(Peer peer){
		peers.add(peer);
		firePeerEntered(peer);
	}

	protected void peerLeaved(Peer peer){
		firePeerLeaved(peer);
		peers.remove(peer);
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

	protected void fireSessionStart(){
		for(StateSynchronizationListener l : stateSynchronizationListeners){
			l.stateSynchronized();
		}
	}

	private String sessionId;
	private PeerFactory peerFactory;
	private Peer selfPeer;
	private Set<Peer> peers = new LinkedHashSet<Peer>();
	private Set<PeerListener> peerListeners = new LinkedHashSet<PeerListener>();
	private Set<StateSynchronizationListener> stateSynchronizationListeners
		= new LinkedHashSet<StateSynchronizationListener>();

	private transient Map<String, Object> idToObject = new HashMap<String, Object>();
	private Map<String, Pair<Object, Field>> idToFields
		= new LinkedHashMap<String, Pair<Object, Field>>();
	private transient int count;
	private transient ThreadLocal<Boolean> dispatching = new ThreadLocal<Boolean>(){
		protected Boolean initialValue() {
			return Boolean.FALSE;
		}
	};
	private transient ThreadLocal<Peer> currentSender = new ThreadLocal<Peer>();
	private static Logger logger = Logger.getLogger(
			Session.class.getPackage().getName());
}
