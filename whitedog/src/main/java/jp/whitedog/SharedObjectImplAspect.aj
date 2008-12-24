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

import java.lang.reflect.Method;

import jp.whitedog.annotation.Share;

/**
 * An aspect for adding SharedObject implementation to shared object (that
 * has the method annotated by @Share). 
 * @author Takao Nakaguchi
 */
public aspect SharedObjectImplAspect {
	
	declare parents: hasmethod(@Share * *(..))  implements SharedObject;

	public void SharedObject.bindToSession(Session session, String objectId){
		if(this.session != null){
			throw new IllegalStateException("object already binded to "
					+ session.getSessionId() + ":" + this.objectId);
		}
		this.session = session;
		this.objectId = objectId;
	}

	public void SharedObject.unbindFromSession(){
		this.session = null;
		this.objectId = null;
	}

	public String SharedObject.getObjectId(){
		return objectId;
	}

	public Object SharedObject.share(Method method, Object[] args, Proceeder proceeder){
		if(session == null){
			return proceeder.proceed();
		}
		try{
			return session.share(this, method, args, proceeder);
		} catch(WhiteDogException e){
			e.printStackTrace();
			return null;
		}
	}

	private Session SharedObject.session;
	private String SharedObject.objectId;
}
