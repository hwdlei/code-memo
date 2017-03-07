package zx.soft.utils.jdk.common;

import java.util.Set;
import java.util.TreeSet;

public class Dize {

	public static void main(String[] args) {

		Set<Case> evens = new TreeSet<Case>();
		Set<Case> odds = new TreeSet<Case>();
		Set<Case> randoms = new TreeSet<Case>();
		for (int i = 1; i <= 6; i++) {
			for (int j = i; j <= 6; j++) {
				if ((i + j) % 2 == 0) {
					evens.add(new Case(i, j));
				} else {
					odds.add(new Case(i, j));
				}
			}
		}
		for (int i = 0; i < 10; i++) {
			int die1 = (int) Math.floor(Math.random() * 6) + 1;
			int die2 = (int) Math.floor(Math.random() * 6) + 1;
			int sm = die1 > die2 ? die2 : die1;
			int bg = die1 > die2 ? die1 : die2;
			randoms.add(new Case(sm, bg));
		}

		Set<Case> cross = new TreeSet<Case>();
		for (Case cs : evens) {
			if (randoms.contains(cs)) {
				cross.add(cs);
			}
		}
		Set<Case> combine = new TreeSet<Case>();
		combine.addAll(odds);
		combine.addAll(randoms);

		System.out.println("A: " + evens);
		System.out.println("B: " + odds);
		System.out.println("C: " + randoms);
		System.out.println("A^B: " + cross);
		System.out.println("AVB: " + combine);

	}

	public static class Case implements Comparable<Case> {

		public final int die1;
		public final int die2;

		public Case(int die1, int die2) {
			this.die1 = die1;
			this.die2 = die2;
		}

		public int compareTo(Case o) {
			if (this.die1 > o.die1) {
				return 1;
			} else if (this.die1 < o.die1) {
				return -1;
			} else {
				if (this.die2 > o.die2) {
					return 1;
				} else if (this.die2 < o.die2) {
					return -1;
				}
			}
			return 0;
		}

		@Override
		public String toString() {
			return die1 + "," + die2;
		}

	}

}
