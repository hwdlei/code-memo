package zx.soft.utils.guava.eventbus;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

public class DeadEventListener {
	boolean notDelivered = false;

	@Subscribe
	public void listen(DeadEvent event) {

		notDelivered = true;
	}

	public boolean isNotDelivered() {
		return notDelivered;
	}
}