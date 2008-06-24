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

import jp.whitedog.Share;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;

/**
 * Hooks method that has @Share annotation and invoke handler.handle().
 * @author Takao Nakaguchi
 */
public aspect ShareAspect
{
	/**
	 * hook method execution and call hookHandler.handle()
	 * @return object returned by hookHandler.handle()
	 */
	Object around() : execution(@Share * *.*(..)){
		JoinPoint jp = thisJoinPoint;

		Object target = jp.getThis();
		if(!(target instanceof SharedObject)){
			return proceed();
		}
		SharedObject so = (SharedObject)target;

		Method method = null;
		try{
			CodeSignature sig = (CodeSignature)jp.getSignature();
			method = so.getClass().getMethod(
					sig.getName(), sig.getParameterTypes()
					);
		} catch(NoSuchMethodException e){
			// must not be thrown
			throw new RuntimeException(e);
		}

		return so.share(method, jp.getArgs(), new Proceeder(){
			public Object proceed() {
				return proceed();
			}
		});
	}
}
