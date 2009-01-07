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
package jp.whitedog.sample.chat;

import jp.whitedog.Peer;
import jp.whitedog.annotation.Share;

public class ChatPeer extends Peer{
	public ChatPeer(String peerId){
		super(peerId);
	}

	public String getDisplayName(){
		return displayName;
	}

	@Share
	public void setDisplayName(String displayName){
		this.displayName = displayName;
		displayNameChanged();
	}

	protected void displayNameChanged(){
	}

	@Share
	private String displayName;
	private static final long serialVersionUID = -8285756317969103419L;
}
