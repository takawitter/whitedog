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
import java.util.Map;

import jp.whitedog.Proceeder;

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
	public String getId(){
		return sessionId;
	}

	/**
	 * register shared object.
	 * @param object object to share its method execution
	 */
	public void register(Object object){
		SharedObject so = (SharedObject)object;
		String objectId = getId() + "#" + object.getClass().getName() + "#" + count;
		count++;
		idToObject.put(objectId, object);
		so.bindSession(Session.this, objectId);
		System.out.println("object registered: " + objectId);
	}

	/**
	 * unregister shared object.
	 * @param object object to stop sharing
	 */
	public void unregister(Object object){
		SharedObject so = (SharedObject)object;
		String objectId = so.getObjectIdIn(Session.this);
		idToObject.remove(objectId);
		so.unbinedSession(Session.this);
	}

	/**
	 * share method execution.
	 * @param object shared object
	 * @param method method to share
	 * @param args arguments of method
	 * @param proceeder proceeder of this execution
	 * @throws WhiteDogException
	 */
	public void share(
			SharedObject object, Method method, Object[] args
			, Proceeder proceeder)
	throws WhiteDogException
	{
		if(receiving.get()){
			proceeder.proceed();
			return;
		}
		String id = object.getObjectIdIn(this);
		doShare(new MethodExecution(id, method, args));
	}

	public void dispatch(MethodExecution execution){
		Object object = idToObject.get(execution.getObjectId());
		if(object == null){
			System.out.println("no object found: " + execution.getObjectId());
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

	public abstract void connect()
	throws WhiteDogException;

	public abstract void disconnect()
	throws WhiteDogException;

	public abstract void doShare(MethodExecution execution)
	throws WhiteDogException;

	private String sessionId;
	private transient Map<String, Object> idToObject = new HashMap<String, Object>();
	private transient int count;
	private transient ThreadLocal<Boolean> receiving = new ThreadLocal<Boolean>(){
		protected Boolean initialValue() {
			return Boolean.FALSE;
		}
	};
}
