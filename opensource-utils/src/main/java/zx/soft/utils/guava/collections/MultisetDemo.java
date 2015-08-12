package zx.soft.utils.guava.collections;

import zx.soft.utils.json.JsonUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Splitter;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

public class MultisetDemo {
	public static void main(String[] args) throws JsonProcessingException {
		Multiset multiset = HashMultiset.create();
		String sentences = "this is a story, there is a good girl in the story.";
		Iterable<String> words = Splitter.onPattern("[^a-z]{1,}").omitEmptyStrings().trimResults().split(sentences);
		for (String word : words) {
			multiset.add(word);
		}

		for (Object element : multiset.elementSet()) {
			System.out.println((String) element + ":" + multiset.count(element));
		}

		System.out.println(JsonUtils.getString(multiset));
	}
}
