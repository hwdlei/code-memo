package zx.soft.utils.guava.hashing;

import java.util.List;

import zx.soft.utils.guava.hashing.HashingDemo.Person;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;

public class BloomFilterDemo {
	public static void main(String[] args) {
		Funnel<Person> personFunnel = new Funnel<Person>() {
			private static final long serialVersionUID = 5834640829134516623L;

			public void funnel(Person from, PrimitiveSink into) {
				// TODO Auto-generated method stub
				into.putInt(from.id).putString(from.firstName, Charsets.UTF_8).putString(from.lastName, Charsets.UTF_8)
						.putInt(from.birthYear);
			}
		};

		List<Person> friendsList = Lists.newArrayList();

		Person person = new Person();
		person.birthYear = 1991;
		person.firstName = "dong";
		person.id = 1000;
		person.lastName = "lei";

		BloomFilter<Person> friends = BloomFilter.create(personFunnel, 500, 0.01);
		for (Person friend : friendsList) {
			friends.put(friend);
		}

		// 很久以后
		if (friends.mightContain(person)) {
			//dude不是朋友还运行到这里的概率为1%
			//在这儿，我们可以在做进一步精确检查的同时触发一些异步加载
		}
	}

}
