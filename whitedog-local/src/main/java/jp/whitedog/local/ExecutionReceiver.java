package jp.whitedog.local;

import jp.whitedog.MethodExecution;

public interface ExecutionReceiver {
	void receive(MethodExecution execution);
}
