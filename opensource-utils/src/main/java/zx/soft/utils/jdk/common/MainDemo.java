package zx.soft.utils.jdk.common;

public class MainDemo {
	public static void main(String[] args) {
		Integer a = Integer.valueOf(127);
		Integer b = Integer.valueOf(127);
		Integer c = new Integer(127);

		System.out.println(a == b); //true
		System.out.println(a == c); // false
	}
}
