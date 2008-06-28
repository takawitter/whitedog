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

/**
 * Peer class that represents network peer.
 * You can use your own peer implementation by extending this class
 * and creating peer factory (implements jp.whitedog.PeerFactory)
 * that instantiates it.
 * @author Takao Nakaguchi
 */
public class Peer
implements Serializable
{
	/**
	 * Cunstructor.
	 * @param peerId ID string of this peer.
	 */
	public Peer(String peerId){
		this.peerId = peerId;
	}

	/**
	 * Gets the peer ID.
	 * @return peer ID.
	 */
	public String getPeerId(){
		return peerId;
	}

	/**
	 * Returns the hash code of this Peer.
	 * Hash code is same to peerId.hashCode().
	 */
	public int hashCode(){
		return peerId.hashCode();
	}

	/**
	 * Returns true if this peer and value are same.
	 * value must be an instance of Peer class and
	 * have same peer ID to this peer.
	 */
	public boolean equals(Object value){
		if(!(value instanceof Peer)) return false;
		return peerId.equals(((Peer)value).peerId);
	}

	/**
	 * Returns the string that represents this peer.
	 */
	public String toString(){
		return "Peer#" + peerId;
	}

	private String peerId;
	private static final long serialVersionUID = -665174834545988109L;
}
