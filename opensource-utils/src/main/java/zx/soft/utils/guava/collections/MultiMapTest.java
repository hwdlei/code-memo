package zx.soft.utils.guava.collections;

import java.util.Collection;

import com.google.common.base.Function;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset.Entry;

public class MultiMapTest {
	public static void main(String... args) {
		Multimap<String, String> myMultimap = ArrayListMultimap.create();

		// 添加键值对
		myMultimap.put("Fruits", "Bannana");
		//给Fruits元素添加另一个元素
		myMultimap.put("Fruits", "Apple");
		myMultimap.put("Fruits", "Pear");
		myMultimap.put("Vegetables", "Carrot");

		// 获得multimap的size
		int size = myMultimap.size();
		System.out.println(size); // 4

		// 获得Fruits对应的所有的值
		Collection<String> fruits = myMultimap.get("Fruits");
		System.out.println(fruits); // [Bannana, Apple, Pear]
		fruits.clear();
		System.out.println(fruits); // [Bannana, Apple, Pear]

		Collection<String> vegetables = myMultimap.get("Vegetables");
		System.out.println(vegetables); // [Carrot]

		for (Entry<String> key : myMultimap.keys().entrySet()) {
			System.out.println("key : " + key.getElement() + ", Count : " + key.getCount());
		}

		//遍历Mutlimap
		for (String value : myMultimap.values()) {
			System.out.println(value);
		}

		// Removing a single value
		myMultimap.remove("Fruits", "Pear");
		System.out.println(myMultimap.get("Fruits")); // [Bannana, Pear]

		// Remove all values for a key
		myMultimap.removeAll("Fruits");
		System.out.println(myMultimap.get("Fruits")); // [] (Empty Collection!)

		System.out.println("============================================");

		ImmutableSet<String> digits = ImmutableSet.of("zero", "one", "two", "three", "four", "five", "six", "seven",
				"eight", "nine");
		Function<String, Integer> lengthFunction = new Function<String, Integer>() {
			public Integer apply(String string) {
				return string.length();
			}
		};
		ImmutableListMultimap<Integer, String> digitsByLength = Multimaps.index(digits, lengthFunction);
		/*
		 * digitsByLength maps:
		 *  3 => {"one", "two", "six"}
		 *  4 => {"zero", "four", "five", "nine"}
		 *  5 => {"three", "seven", "eight"}
		 */
		System.out.println(digitsByLength);

	}
}
