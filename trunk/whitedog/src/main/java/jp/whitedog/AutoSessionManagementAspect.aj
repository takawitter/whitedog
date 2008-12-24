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

import jp.whitedog.annotation.ConnectAfterHere;
import jp.whitedog.annotation.Share;

/**
 * Aspect for automatic session management.
 * This aspect does:
 * <ul>
 * <li>register object that has the method annotated by @Share</li>
 * <li>connect when the execution of the method annotated by @ConnectAfterHere done</li>
 * </ul>
 * @author Takao Nakaguchi
 */
public abstract aspect AutoSessionManagementAspect{
	public AutoSessionManagementAspect(Session session){
		this.session = session;
	}

	pointcut whitedogRelated() :
		within(jp.whitedog.*)
		|| within(jp.whitedog.AutoSessionManagementAspect+)
		;

	after() :
		staticinitialization(*)
		&& !whitedogRelated()
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
		&& !whitedogRelated()
	{
		if(sharedClasses.contains(object.getClass())){
			session.register(object);
		}
	}

	after() :
		execution(@ConnectAfterHere * *.*(..))
		|| execution(@ConnectAfterHere *.new(..))
	{
		if(connected) return;
		try{
			session.connect();
			connected = true;
		} catch(WhiteDogException e){
			e.printStackTrace();
		}
	}

	private Session session;
	private boolean connected;
	private Set<Class<?>> sharedClasses = new HashSet<Class<?>>();
}
