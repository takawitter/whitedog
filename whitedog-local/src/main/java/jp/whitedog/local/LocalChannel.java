package jp.whitedog.local;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import jp.whitedog.MethodExecution;

public class LocalChannel {
	LocalChannel(String id){
		this.id = id;
		Set<LocalChannel> c = channels.get(id);
		if(c == null){
			c = new LinkedHashSet<LocalChannel>();
			channels.put(id, c);
		}
		c.add(this);
	}

	public void close(){
		channels.get(id).remove(this);
	}

	public void send(MethodExecution execution){
		for(LocalChannel c : channels.get(id)){
			c.receiver.receive(execution);
		}
		receiver.receive(execution);
	}

	public void setReceiver(ExecutionReceiver receiver){
		this.receiver = receiver;
	}

	private String id;
	private ExecutionReceiver receiver = new ExecutionReceiver(){
		public void receive(MethodExecution execution) {};
	};

	private static Map<String, Set<LocalChannel>> channels
			= new ConcurrentHashMap<String, Set<LocalChannel>>();
}
