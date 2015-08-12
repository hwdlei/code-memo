package zx.soft.utils.guava.eventbus;

public class Event {
	private final int message;

	public Event(int message) {
		this.message = message;
		System.out.println("event message:" + message);
	}

	public int getMessage() {
		return message;
	}
}