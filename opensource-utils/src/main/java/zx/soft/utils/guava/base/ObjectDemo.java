package zx.soft.utils.guava.base;

import java.util.Comparator;

import org.junit.Test;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

public class ObjectDemo {

	@Test
	public void StudentTest() {

		Student student = new Student("peida", 23, 80);
		Student student1 = new Student("aida", 23, 36);
		Student student2 = new Student("jerry", 24, 90);
		Student student3 = new Student("peida", 23, 80);

		System.out.println("==========equals===========");
		System.out.println(student.equals(student2));
		System.out.println(student.equals(student1));
		System.out.println(student.equals(student3));

		System.out.println("==========hashCode===========");
		System.out.println(student.hashCode());
		System.out.println(student1.hashCode());
		System.out.println(student3.hashCode());
		System.out.println(student2.hashCode());

		System.out.println("==========toString===========");
		System.out.println(student.toString());
		System.out.println(student1.toString());
		System.out.println(student2.toString());
		System.out.println(student3.toString());

		System.out.println("==========compareTo===========");
		System.out.println(student.compareTo(student1));
		System.out.println(student.compareTo(student2));
		System.out.println(student2.compareTo(student1));
		System.out.println(student2.compareTo(student));


		Student students = new Student("peida", 23, 80);
		Student students2 = new Student("peida", 23, 80);
		System.out.println(Objects.hashCode(students));
		System.out.println(Objects.hashCode(students2));

	}

}

class Student implements Comparable<Student> {
	public String name;
	public int age;
	public int score;

	Student(String name, int age, int score) {
		this.name = name;
		this.age = age;
		this.score = score;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.name, this.age);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Student) {
			Student that = (Student) obj;
			return Objects.equal(this.name, that.name) && Objects.equal(this.age, that.age) && Objects.equal(this.score, that.score);
		}
		return false;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).addValue(this.name).addValue(this.age).addValue(this.score).toString();
	}

	@Override
	public int compareTo(Student other) {
		return ComparisonChain.start().compare(this.name, other.name).compare(this.age, other.age)
				.compare(this.score, other.score, Ordering.natural().nullsLast()).result();
	}
}



class StudentComparator implements Comparator<Student> {
	@Override
	public int compare(Student s1, Student s2) {
		return ComparisonChain.start().compare(s1.name, s2.name).compare(s1.age, s2.age).compare(s1.score, s2.score)
				.result();
	}
}
