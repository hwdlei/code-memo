package zx.soft.utils.guava.eventbus;

import com.google.common.eventbus.Subscribe;

public class IntegerListener {

	private Integer lastMessage;

	@Subscribe
	public void listen(Integer integer) {
		lastMessage = integer;
		System.out.println("Message:" + lastMessage);
	}

	public Integer getLastMessage() {
		return lastMessage;
	}
}