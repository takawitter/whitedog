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

import jp.whitedog.MethodExecution;
import jp.whitedog.Session;
import jp.whitedog.WhiteDogException;

import org.jgroups.ChannelClosedException;
import org.jgroups.ChannelException;
import org.jgroups.ChannelNotConnectedException;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;

/**
 * Session implementation using JGroups.
 * @author Takao Nakaguchi
 */
public class JGroupsSession extends Session {
	/**
	 * Constructor.
	 * @param sessionId session ID
	 */
	public JGroupsSession(String sessionId){
		super(sessionId);
	}

	public void doShare(MethodExecution execution)
	throws WhiteDogException
	{
		if(channel == null){
			connect();
		}
		try{
			channel.send(new Message(null, null, execution));
		} catch(ChannelClosedException e){
			throw new WhiteDogException(e);
		} catch(ChannelNotConnectedException e){
			throw new WhiteDogException(e);
		}
	}

	public void connect()
	throws WhiteDogException
	{
		if(channel != null) return;
		try {
			channel = new JChannel();
			channel.setReceiver(new ReceiverAdapter(){
				public void receive(Message arg0) {
					dispatch((MethodExecution)arg0.getObject());
				}
			});
			channel.connect(getId());
		} catch(ChannelException e) {
			throw new WhiteDogException(e);
		}
	}

	public void disconnect() throws WhiteDogException{
		channel.disconnect();
		channel = null;
	}

	private JChannel channel;

	private static final long serialVersionUID = -3548894109396142431L;
}
