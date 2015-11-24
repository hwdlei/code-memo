package org.apache.spark.examples.streaming;

import java.util.regex.Pattern;

import com.google.common.collect.Lists;

public class MainTest {

	public static void main(String[] args) {
		Pattern patt = Pattern.compile(" ");
		System.out.println(Lists.newArrayList(patt.split("hello world")));
	}

}
