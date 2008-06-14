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

/**
 * Transferrable method execution data.
 * holds object instance as objectId.
 * @author Takao Nakaguchi
 */
public class MethodExecution implements Serializable{
	/**
	 * Constructor.
	 * @param objectId object ID
	 * @param method method
	 * @param args method arguments
	 */
	public MethodExecution(String objectId, Method method, Object[] args){
		this.objectId = objectId;
		this.name = method.getName();
		this.paramTypes = method.getParameterTypes();
		this.args = args;
	}

	/**
	 * returns object ID.
	 * @return object ID
	 */
	public String getObjectId(){
		return objectId;
	}

	/**
	 * execute method on target object.
	 * @param target instance for method execution
	 * @return object returned by method
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public Object execute(Object target)
	throws NoSuchMethodException, IllegalAccessException
		, InvocationTargetException
	{
		Method m = target.getClass().getMethod(name, (Class[])paramTypes);
		if(!m.isAccessible()){
			m.setAccessible(true);
		}
		return m.invoke(target, args);
	}

	private String objectId;
	private String name;
	private Class<?>[] paramTypes;
	private Object[] args;

	private static final long serialVersionUID = -5319908239072223484L;
}
