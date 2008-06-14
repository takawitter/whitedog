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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public aspect SharedObjectImplAspect {
	declare parents: hasmethod(@Share * *(..))  implements SharedObject;

public void SharedObject.bindSession(Session session, String objectId){
		sessionIdToObjectIdMap.put(session.getId(), objectId);
		sessions.add(session);
	}

	public void SharedObject.unbinedSession(Session session){
		sessionIdToObjectIdMap.remove(session.getId());
		sessions.remove(session);
	}

	public String SharedObject.getObjectIdIn(Session session){
		return sessionIdToObjectIdMap.get(session.getId());
	}

	public boolean SharedObject.share(Method method, Object[] args, Proceeder proceeder){
		boolean shared = false;
		for(Session session : sessions){
			shared = true;
			try{
				session.share(this, method, args, proceeder);
			} catch(WhiteDogException e) {
				e.printStackTrace();
			}
		}
		return shared;
	}

	private Map<String, String> SharedObject.sessionIdToObjectIdMap = new HashMap<String, String>();
	private Set<Session> SharedObject.sessions = new HashSet<Session>();
}
