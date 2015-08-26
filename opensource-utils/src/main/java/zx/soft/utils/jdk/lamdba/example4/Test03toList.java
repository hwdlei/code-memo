package zx.soft.utils.jdk.lamdba.example4;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author MikeW
 */
public class Test03toList {

	public static void main(String[] args) {

		List<Person> pl = Person.createShortList();

		SearchCriteria search = SearchCriteria.getInstance();

		// Make a new list after filtering.
		List<Person> pilotList = pl.stream().filter(search.getCriteria("allPilots")).collect(Collectors.toList());

		System.out.println("\n=== Western Pilot Phone List ===");
		pilotList.forEach(Person::printWesternName);

	}

}
