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
package jp.whitedog.util;

import java.io.Serializable;

/**
 * Pair class.
 * @author Takao Nakaguchi
 * @param <T> class of first value
 * @param <U> class of second value
 */
public class Pair<T, U>
implements Serializable
{
	public Pair(T first, U second){
		this.first = first;
		this.second = second;
	}

	public boolean equls(Object value){
		if(!(value instanceof Pair)) return false;
		Pair<?, ?> v = (Pair<?, ?>)value;
		if(!first.equals(v.first)) return false;
		if(!second.equals(v.second)) return false;
		return true;
	}

	public int hashCode(){
		return first.hashCode() * 31 + second.hashCode(); 
	}

	public String toString(){
		StringBuilder b = new StringBuilder();
		b.append(getClass().getName());
		b.append("[first:");
		b.append(first.toString());
		b.append(", second:");
		b.append(second.toString());
		b.append("]");
		return b.toString();
	}

	public T getFirst(){
		return first;
	}

	public U getSecond(){
		return second;
	}

	public static <V, W> Pair<V, W> create(V first, W second){
		return new Pair<V, W>(first, second);
	}

	private T first;
	private U second;
	private static final long serialVersionUID = 4955622148057290132L;
}
