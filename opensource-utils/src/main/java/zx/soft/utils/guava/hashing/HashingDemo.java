package zx.soft.utils.guava.hashing;

import com.google.common.base.Charsets;
import com.google.common.hash.Funnel;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.hash.PrimitiveSink;

public class HashingDemo {
	public static void main(String[] args) {
		testMd5();
	}

	public static void testMd5() {
		/**
		 * MD5的算法在RFC1321 中定义
		 * 在RFC 1321中，给出了Test suite用来检验你的实现是否正确：
		 * MD5 ("") = d41d8cd98f00b204e9800998ecf8427e
		 * MD5 ("a") = 0cc175b9c0f1b6a831c399e269772661
		 * MD5 ("abc") = 900150983cd24fb0d6963f7d28e17f72
		 * MD5 ("message digest") = f96b697d7cb7938d525a2f31aaf161d0
		 * MD5 ("abcdefghijklmnopqrstuvwxyz") = c3fcd3d76192e4007dfb496cca67e13b
		 */
		HashFunction hf = Hashing.md5();
		HashCode hc = hf.newHasher().putString("", Charsets.UTF_8).hash();
		System.out.println(hc.toString());
		HashCode hc1 = hf.newHasher().putString("a", Charsets.UTF_8).hash();
		System.out.println(hc1.toString());
		HashCode hc2 = hf.newHasher().putString("abc", Charsets.UTF_8).hash();
		System.out.println(hc2.toString());
		HashCode hc3 = hf.newHasher().putString("message digest", Charsets.UTF_8).hash();
		System.out.println(hc3.toString());
		HashCode hc4 = hf.newHasher().putString("abcdefghijklmnopqrstuvwxyz", Charsets.UTF_8).hash();
		System.out.println(hc4.toString());

	}

	public static void testCRC32() {
		HashFunction hf = Hashing.crc32();
		HashCode hc = hf.newHasher().putString("123abcfffeettt", Charsets.UTF_8).hash();
		System.out.println(hc.padToLong());
		HashCode hc1 = hf.newHasher().putString("123abcfffeettt", Charsets.UTF_8).hash();
		System.out.println(hc1.padToLong());
		Funnel<Person> personFunnel = new Funnel<Person>() {
			private static final long serialVersionUID = 5834640829134516623L;

			public void funnel(Person from, PrimitiveSink into) {
				// TODO Auto-generated method stub
				into.putInt(from.id).putString(from.firstName, Charsets.UTF_8).putString(from.lastName, Charsets.UTF_8)
						.putInt(from.birthYear);
			}
		};
		Person person = new Person();
		person.birthYear = 1991;
		person.firstName = "donglei";
		person.id = 1000;
		person.lastName = "lei";
		HashCode hc2 = hf.newHasher().putObject(person, personFunnel).hash();
		System.out.println(hc2.padToLong());
	}

	static class Person {
		int id;
		String firstName;
		String lastName;
		int birthYear;
	}

}
