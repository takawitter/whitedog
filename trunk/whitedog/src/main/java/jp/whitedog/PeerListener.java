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
 * object that receives Peer event.
 * @author Takao Nakaguchi
 */
public interface PeerListener {
	/**
	 * Peer event that indicates new Peer added to session.
	 * @param peer Peer
	 */
	void peerEntered(Peer peer);

	/**
	 * Peer event that indicates existing Peer leaved from session.
	 * @param peer Peer
	 */
	void peerLeaved(Peer peer);
}
