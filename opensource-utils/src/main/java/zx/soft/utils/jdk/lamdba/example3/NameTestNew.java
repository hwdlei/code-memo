package zx.soft.utils.jdk.lamdba.example3;

import java.util.List;
import java.util.function.Function;

/**
 * @author MikeW
 */
public class NameTestNew {

	public static void main(String[] args) {

		System.out.println("\n==== NameTestNew ===");

		List<Person> list1 = Person.createShortList();

		// Print Custom First Name and e-mail
		System.out.println("===Custom List===");
		for (Person person : list1) {
			System.out.println(person.printCustom(p -> "Name: " + p.getGivenName() + " EMail: " + p.getEmail()));
		}

		// Define Western and Eastern Lambdas

		Function<Person, String> westernStyle = p -> {
			return "\nName: " + p.getGivenName() + " " + p.getSurName() + "\n" + "Age: " + p.getAge() + "  "
					+ "Gender: " + p.getGender() + "\n" + "EMail: " + p.getEmail() + "\n" + "Phone: " + p.getPhone()
					+ "\n" + "Address: " + p.getAddress();
		};

		Function<Person, String> easternStyle = p -> "\nName: " + p.getSurName() + " " + p.getGivenName() + "\n"
				+ "Age: " + p.getAge() + "  " + "Gender: " + p.getGender() + "\n" + "EMail: " + p.getEmail() + "\n"
				+ "Phone: " + p.getPhone() + "\n" + "Address: " + p.getAddress();

		// Print Western List
		System.out.println("\n===Western List===");
		for (Person person : list1) {
			System.out.println(person.printCustom(westernStyle));
		}

		// Print Eastern List
		System.out.println("\n===Eastern List===");
		for (Person person : list1) {
			System.out.println(person.printCustom(easternStyle));
		}

	}
}
