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

/**
 * The interface should be implemented by the object that has method annotated by @Share.
 * @author Takao Nakaguchi
 */
public interface SharedObject {
	/**
	 * bind Session to this object.
	 * @param session Session to be binded
	 * @param objectId object ID of this object in the session
	 */
	void bindToSession(Session session, String objectId);

	/**
	 * unbined Session from this object.
	 * @param session Session to be unbinded
	 */
	void unbindFromSession();

	/**
	 * Gets the object ID in session.
	 * @return object ID
	 */
	String getObjectId();

	/**
	 * Share method execution. Implementer of this method must
	 * send MethodExecution message to the session it belongs to.
	 * @param method method to share its execution
	 * @param arguments parameter to be passed to the method
	 * @param proceeder proceeder of the method execution
	 * @return return value of this process. usually null.
	 */
	Object share(Method method, Object[] arguments, Proceeder proceeder);
}
