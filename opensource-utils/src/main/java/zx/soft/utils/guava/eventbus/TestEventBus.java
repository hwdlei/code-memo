package zx.soft.utils.guava.eventbus;

import com.google.common.eventbus.EventBus;

public class TestEventBus {
	public static void testReceiveEvent() {

		EventBus eventBus = new EventBus("test");
		EventListener listener = new EventListener();

		eventBus.register(listener);

		eventBus.post(new Event(200));
		eventBus.post(new Event(300));
		eventBus.post(new Event(400));

		System.out.println("LastMessage:" + listener.getLastMessage());
		;
	}

	public static void testMultipleEvents() {

		EventBus eventBus = new EventBus("test");
		MultipleListener multiListener = new MultipleListener();

		eventBus.register(multiListener);

		eventBus.post(new Integer(100));
		eventBus.post(new Integer(200));
		eventBus.post(new Integer(300));
		eventBus.post(new Long(800));
		eventBus.post(new Long(800990));
		eventBus.post(new Long(800882934));

		System.out.println("LastInteger:" + multiListener.getLastInteger());
		System.out.println("LastLong:" + multiListener.getLastLong());
	}

	public static void testDeadEventListeners() {

		EventBus eventBus = new EventBus("test");
		DeadEventListener deadEventListener = new DeadEventListener();
		eventBus.register(deadEventListener);

		eventBus.post(new Event(200));
		eventBus.post(new Event(300));

		System.out.println("deadEvent:" + deadEventListener.isNotDelivered());

	}

	public static void testEventsFromSubclass() {

		EventBus eventBus = new EventBus("test");
		IntegerListener integerListener = new IntegerListener();
		NumberListener numberListener = new NumberListener();
		eventBus.register(integerListener);
		eventBus.register(numberListener);

		eventBus.post(new Integer(100));

		System.out.println("integerListener message:" + integerListener.getLastMessage());
		System.out.println("numberListener message:" + numberListener.getLastMessage());

		eventBus.post(new Long(200L));

		System.out.println("integerListener message:" + integerListener.getLastMessage());
		System.out.println("numberListener message:" + numberListener.getLastMessage());
	}

	public static void main(String[] args) {
		testReceiveEvent();
		System.out.println();
		testMultipleEvents();
		System.out.println();
		testDeadEventListeners();
		System.out.println();
		testEventsFromSubclass();
		System.out.println();
	}
}