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
package jp.whitedog.jgroups;

import java.io.Serializable;

/**
 * Message sent as the response of PeerIntroduction new comer sent.
 * @author Takao Nakaguchi
 */
public class PeerIntroductionResponse
implements Serializable
{
	/**
	 * Constructor.
	 * @param peerId Peer ID
	 */
	public PeerIntroductionResponse(String peerId){
		this.peerId = peerId;
	}

	/**
	 * Returns the Peer ID passed by Constructor.
	 * @return Peer ID
	 */
	public String getPeerId(){
		return peerId;
	}

	private String peerId;
	private static final long serialVersionUID = 7410570777146896004L;
}