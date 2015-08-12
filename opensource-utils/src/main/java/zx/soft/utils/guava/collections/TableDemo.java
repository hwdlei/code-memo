package zx.soft.utils.guava.collections;

import zx.soft.utils.json.JsonUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class TableDemo {
	public static void main(String[] args) throws JsonProcessingException {
		Table<String, Integer, String> aTable = HashBasedTable.create();

		for (char a = 'A'; a <= 'C'; ++a) {
			for (Integer b = 1; b <= 3; ++b) {
				aTable.put(Character.toString(a), b, String.format("%c%d", a, b));
			}
		}

		System.out.println(JsonUtils.getString(aTable));
	}

}
