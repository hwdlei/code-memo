package zx.soft.utils.guava.iterators;

import java.util.Iterator;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

public class IteratorsDemo {
	public static void main(String[] args) {
		List<String> list = Lists.newArrayList("Apple", "Pear", "Peach", "Banana");

		Predicate<String> condition = new Predicate<String>() {
			public boolean apply(String input) {
				return input.startsWith("P");
			}
		};
		boolean allIsStartsWithP = Iterators.all(list.iterator(), condition);
		System.out.println("all result == " + allIsStartsWithP);

		Iterator<String> startPElements = Iterators.filter(list.iterator(), new Predicate<String>() {
			public boolean apply(String input) {
				return input.startsWith("P");
			}
		});
		while (startPElements.hasNext()) {
			System.out.println(startPElements.next());
		}

		Iterator<Integer> countIterator = Iterators.transform(list.iterator(), new Function<String, Integer>() {
			public Integer apply(String input) {
				return input.length();
			}
		});
		while (countIterator.hasNext()) {
			System.out.println(countIterator.next());
		}
	}

}
