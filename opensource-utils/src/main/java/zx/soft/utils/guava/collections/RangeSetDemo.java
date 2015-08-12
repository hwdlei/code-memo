package zx.soft.utils.guava.collections;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeMap;
import com.google.common.collect.TreeRangeSet;

public class RangeSetDemo {
	public static void main(String[] args) {
		RangeSet<Integer> rangeSet = TreeRangeSet.create();
		rangeSet.add(Range.closed(1, 10));
		System.out.println("rangeSet:" + rangeSet);
		rangeSet.add(Range.closedOpen(11, 15));
		System.out.println("rangeSet:" + rangeSet);
		rangeSet.add(Range.open(15, 20));
		System.out.println("rangeSet:" + rangeSet);
		rangeSet.add(Range.openClosed(0, 0));
		System.out.println("rangeSet:" + rangeSet);
		rangeSet.remove(Range.open(5, 10));
		System.out.println("rangeSet:" + rangeSet);
		Range<Integer> range = rangeSet.rangeContaining(12);
		System.out.println(range);
		System.out.println(range.contains(14));

		System.out.println("=====================");

		RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
		rangeMap.put(Range.closed(1, 10), "foo");
		System.out.println("rangeMap:" + rangeMap);
		rangeMap.put(Range.open(3, 6), "bar");
		System.out.println("rangeMap:" + rangeMap);
		rangeMap.put(Range.open(10, 20), "foo");
		System.out.println("rangeMap:" + rangeMap);
		rangeMap.remove(Range.closed(5, 11));
		System.out.println("rangeMap:" + rangeMap);

	}


}
