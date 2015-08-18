package zx.soft.utils.guava.base;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

public class OrderingDemo {
	public static void main(String[] args) {
		Ordering<Foo> ordering = Ordering.natural().nullsFirst().onResultOf(new Function<Foo, String>() {
			@Override
			public String apply(Foo foo) {
				return foo.sortedBy;
			}
		});
		List<Foo> foos = Lists.newArrayList();
		foos.add(new Foo());
		foos.add(new Foo("1", 1));
		foos.add(new Foo("12", 12));
		foos.add(new Foo("2", 2));
		System.out.println(foos);
		Collections.sort(foos, ordering);
		System.out.println(foos);

	}
}

class Foo {
	String sortedBy;
	int notSortedBy;

	public Foo() {
		// TODO Auto-generated constructor stub
	}

	public Foo(String soredBy, int notSortedBy) {
		this.sortedBy = soredBy;
		this.notSortedBy = notSortedBy;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(Foo.class).addValue(sortedBy).addValue(notSortedBy).toString();
	}

}