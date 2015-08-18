package zx.soft.utils.jdk.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import zx.soft.utils.json.JsonUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.primitives.Ints;

public class MapSortDemo {

	public static void main(String[] args) throws JsonProcessingException {
		Map<String, Integer> maps = new HashMap<String, Integer>();
		List<Entry<String, Integer>> entrys = new ArrayList<Map.Entry<String, Integer>>();
		for (int i = 0; i < 100; i++) {
			int a = new Random(1000).nextInt();
			maps.put(i + "a", i);
		}
		for (Entry<String, Integer> entry : maps.entrySet()) {
			entrys.add(entry);
		}
		Collections.sort(entrys, new Comparator<Entry<String, Integer>>() {

			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return Ints.compare(o1.getValue(), o2.getValue());
			}
		});
		System.out.println(JsonUtils.getString(entrys));

		List<String> strs = new ArrayList<String>();
		strs.add(0, "1");
		System.out.println(strs);
	}

}
