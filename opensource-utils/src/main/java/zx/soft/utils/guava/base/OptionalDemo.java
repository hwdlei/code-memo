package zx.soft.utils.guava.base;

import com.google.common.base.Optional;

public class OptionalDemo {
	public static void main(String[] args) {
		testNull();
		testNullObject();
		int a[] = new int[5];
		for (int i : a) {
			System.out.println(i);
		}

		testOptional();
		testOptional2();
	}

	public static void testNull() {
		int age = 0;
		System.out.println("user age:" + age);

		long money;
		money = 10L;
		System.out.println("user money" + money);

		String name = null;
		System.out.println("user name:" + name);
	}

	public static void testNullObject() {
		if (null instanceof java.lang.Object) {
			System.out.println("null属于java.lang.Object类型");
		} else {
			System.out.println("null不属于java.lang.Object类型");
		}
	}

	public static void testOptional() {
		Optional<Integer> possible = Optional.of(6);
		if (possible.isPresent()) {
			System.out.println("possible isPresent:" + possible.isPresent());
			System.out.println("possible value:" + possible.get());
		}
	}

	public static void testOptional2() {
		Optional<Integer> possible = Optional.of(6);
		Optional<Integer> absentOpt = Optional.absent();
		Optional<Integer> NullableOpt = Optional.fromNullable(null);
		Optional<Integer> NoNullableOpt = Optional.fromNullable(10);
		if (possible.isPresent()) {
			System.out.println("possible isPresent:" + possible.isPresent());
			System.out.println("possible value:" + possible.get());
		}
		if (absentOpt.isPresent()) {
			System.out.println("absentOpt isPresent:" + absentOpt.isPresent());
			;
		}
		if (NullableOpt.isPresent()) {
			System.out.println("fromNullableOpt isPresent:" + NullableOpt.isPresent());
			;
		}
		if (NoNullableOpt.isPresent()) {
			System.out.println("NoNullableOpt isPresent:" + NoNullableOpt.isPresent());
			;
		}
	}
}