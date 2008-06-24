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

import java.util.Random;

/**
 * Utilities related to String.
 * @author Takao Nakaguchi
 */
public class StringUtil {
	/**
	 * generate string consists of random characters.
	 * @param length length of generated string
	 * @return string
	 */
	public static String randomString(int length){
		StringBuilder b = new StringBuilder();
		for(int i = 0; i < length; i++){
			b.append(base.charAt(random.nextInt(base.length())));
		}
		return b.toString();
	}

	private static String base = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabsdefghijklmnopqrstuvwxyz";
	private static Random random = new Random();
}
