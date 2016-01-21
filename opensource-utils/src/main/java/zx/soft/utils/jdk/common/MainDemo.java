package zx.soft.utils.jdk.common;

import java.util.TreeSet;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Sets;

public class MainDemo {
	public static void main(String[] args) {
		Integer a = Integer.valueOf(127);
		Integer b = Integer.valueOf(127);
		Integer c = new Integer(127);

		System.out.println(a == b); //true
		System.out.println(a == c); // false

		TreeSet<SequencePayload>  payloads = Sets.newTreeSet();
		payloads.add(new SequencePayload(1L, "1".getBytes()));
		payloads.add(new SequencePayload(3L, "3".getBytes()));
		payloads.add(new SequencePayload(2L, "2".getBytes()));
		payloads.add(new SequencePayload(2L, "2".getBytes()));
		payloads.add(new SequencePayload(2L, "2".getBytes()));
		for(SequencePayload payload : payloads) {
			System.out.println(payload.seq + new String(payload.payload));
		}
	}

	public static  class SequencePayload implements Comparable<SequencePayload> {
		private Long seq;
		private byte[] payload;

		public SequencePayload(Long seq, byte[] payload) {
			this.seq = seq;
			this.payload = payload;
		}

		@Override
		public int compareTo(SequencePayload o) {
			return ComparisonChain.start().compare(this.seq, o.seq).compare(this.payload.length, o.payload.length)
					.result();
		}

		public boolean linked(SequencePayload o) {
			if (this.seq + this.payload.length == o.seq) {
				return true;
			}
			if (o.seq + o.payload.length == this.seq) {
				return true;
			}
			return false;
		}

		@Override
		public String toString() {
			return Objects.toStringHelper(this.getClass()).add("seq", this.seq).add("len", this.payload.length)
					.toString();
		}
	}
}
