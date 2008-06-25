package jp.whitedog.sample.chat;

import jp.whitedog.Peer;
import jp.whitedog.Share;

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

	private String displayName;
	private static final long serialVersionUID = -8285756317969103419L;
}
