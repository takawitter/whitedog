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
 * Handles hooked event caused by hook aspects.
 * @author Takao Nakaguchi
 */
public interface HookHandler {
	/**
	 * Handles hooked event.
	 * @param target target of hooked method
	 * @param method hooked method signature
	 * @param args argumens passed to hooked method
	 * @param proceeder proceeder that proceeds original implementation of hooked method
	 * @return object to be returned to caller
	 */
	Object handle(Object target, Method method, Object[] args, Proceeder proceeder);
}
