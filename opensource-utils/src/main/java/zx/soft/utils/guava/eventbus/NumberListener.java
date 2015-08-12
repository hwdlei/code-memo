package zx.soft.utils.guava.eventbus;

import com.google.common.eventbus.Subscribe;

public class NumberListener {

	private Number lastMessage;

	@Subscribe
	public void listen(Number integer) {
		lastMessage = integer;
		System.out.println("Message:" + lastMessage);
	}

	public Number getLastMessage() {
		return lastMessage;
	}
}