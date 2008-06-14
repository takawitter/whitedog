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

/**
 * Exception thrown at exceptional occasion related to whitedog library.
 * @author Takao Nakaguchi
 */
public class WhiteDogException extends Exception{
	public WhiteDogException(String message){
		super(message);
	}

	public WhiteDogException(Throwable cause){
		super(cause);
	}

	public WhiteDogException(String message, Throwable cause){
		super(message, cause);
	}

	private static final long serialVersionUID = 8274560428519632740L;
}
