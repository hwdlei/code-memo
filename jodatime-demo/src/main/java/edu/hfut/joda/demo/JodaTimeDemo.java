package edu.hfut.joda.demo;

import org.joda.time.DateTime;

/**
 *
 * @author donglei
 * @date: 2016年3月30日 下午2:37:16
 * http://www.joda.org/joda-time/userguide.html
 */

public class JodaTimeDemo {

	public static void main(String[] args) {
		DateTime dt = new DateTime();
		int month = dt.getMonthOfYear(); // where January is 1 and December is 12
		int year = dt.getYear();
		System.out.println(month);
	}

}
