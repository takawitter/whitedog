package jp.whitedog.jgroups;

import java.io.Serializable;

public class PeerIntroductionResponse
implements Serializable
{
	public PeerIntroductionResponse(String peerId){
		this.peerId = peerId;
	}

	public String getPeerId(){
		return peerId;
	}

	private String peerId;
}
