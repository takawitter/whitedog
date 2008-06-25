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
package jp.whitedog.local;

import jp.whitedog.MethodExecution;
import jp.whitedog.Session;
import jp.whitedog.WhiteDogException;

/**
 * IN-VM(ClassLoader) Session implementation.
 * @author Takao Nakaguchi
 */
public class LocalSession extends Session {
	/**
	 * Constructor.
	 * @param sessionId session ID
	 */
	public LocalSession(String sessionId){
		super(sessionId);
	}

	protected void doConnect()
	throws WhiteDogException
	{
		if(channel != null) return;
		channel = new LocalChannel(getSessionId());
		channel.setReceiver(new ExecutionReceiver(){
			public void receive(MethodExecution e) {
				dispatch(null, e);
			}
		});
	}

	protected void doDisconnect() throws WhiteDogException{
		channel.close();
		channel = null;
	}

	protected void doShare(MethodExecution execution)
	throws WhiteDogException
	{
		if(channel == null){
			channel = new LocalChannel(getSessionId());
		}
		channel.send(execution);
	}

	private LocalChannel channel;

	private static final long serialVersionUID = -3548894109396142431L;
}
