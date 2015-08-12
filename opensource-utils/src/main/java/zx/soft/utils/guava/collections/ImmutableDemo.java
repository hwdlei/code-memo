package zx.soft.utils.guava.collections;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

public class ImmutableDemo {
	public static void main(String[] args) {
		Set<String> immutableNamedColors = ImmutableSet.<String> builder()
				.add("red", "green", "black", "white", "grey").build();
		//		immutableNamedColors.add("abc");
		for (String color : immutableNamedColors) {
			System.out.println(color);
		}
	}
}