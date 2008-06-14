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
import java.util.HashSet;
import java.util.Set;

public abstract aspect AutoSessionManagementAspect{
	public AutoSessionManagementAspect(Session session){
		this.session = session;
	}

	after() : staticinitialization(*)
		&& !within(jp.whitedog.*)
		&& !within(jp.whitedog.AutoSessionManagementAspect+)
	{
		Class<?> clazz = (Class<?>)thisJoinPointStaticPart.getSignature().getDeclaringType();
		for(Method m : clazz.getDeclaredMethods()){
			Share s = m.getAnnotation(Share.class);
			if(s == null) continue;
			sharedClasses.add(clazz);
			return;
		}
	}

	after(Object object) :
		execution(*.new(..))
		&& this(object)
		&& !within(jp.whitedog.*)
		&& !within(jp.whitedog.AutoSessionManagementAspect+)
	{
		if(sharedClasses.contains(object.getClass())){
			session.register(object);
		}
	}

	after() : execution(@ConnectAfterHere * *.*(..)){
		try{
			session.connect();
		} catch(WhiteDogException e){
			e.printStackTrace();
		}
	}

	after() : execution(@ConnectAfterHere *.new(..)){
		try{
			session.connect();
		} catch(WhiteDogException e){
			e.printStackTrace();
		}
	}

	private Session session;
	private Set<Class<?>> sharedClasses = new HashSet<Class<?>>();
}
