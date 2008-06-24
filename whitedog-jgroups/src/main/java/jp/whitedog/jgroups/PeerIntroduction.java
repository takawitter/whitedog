package jp.whitedog.jgroups;

import java.io.Serializable;

public class PeerIntroduction
implements Serializable
{
	public PeerIntroduction(String peerId){
		this.peerId = peerId;
	}

	public String getPeerId(){
		return peerId;
	}

	private String peerId;
	private static final long serialVersionUID = 8166783221699559427L;
}
