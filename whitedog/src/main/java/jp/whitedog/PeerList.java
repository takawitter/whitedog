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

import java.util.HashSet;
import java.util.Set;

/**
 * PeerList used for share peer added/removed event.
 * @author Takao Nakaguchi
 */
public class PeerList {
	@Share
	public void addPeer(Peer peer){
		peers.add(peer);
		peerAdded(peer);
	}

	@Share
	public void removePeer(Peer peer){
		peerRemoved(peer);
		peers.remove(peer);
	}

	protected void peerAdded(Peer peer){}
	protected void peerRemoved(Peer peer){}

	private Set<Peer> peers = new HashSet<Peer>();
}
