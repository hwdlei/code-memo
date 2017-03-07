package zx.soft.utils.jdk.common;

import java.nio.ByteBuffer;

public class Test {

	public static void main(String[] args) {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		System.out.println(buffer.position());
		System.err.println(buffer.limit());
		System.err.println(buffer.capacity());
		System.out.println(buffer.remaining());
		System.out.println(buffer.hasRemaining());

		buffer.limit(0);
		System.out.println(buffer.position());
		System.err.println(buffer.limit());
		System.err.println(buffer.capacity());
		System.out.println(buffer.remaining());
		System.out.println(buffer.hasRemaining());

		buffer.compact();
		System.out.println(buffer.position());
		System.err.println(buffer.limit());
		System.err.println(buffer.capacity());
		System.out.println(buffer.remaining());
		System.out.println(buffer.hasRemaining());

		buffer.put((byte) 0x01);
		System.out.println(buffer.position());
		System.err.println(buffer.limit());
		System.err.println(buffer.capacity());
		System.out.println(buffer.remaining());
		System.out.println(buffer.hasRemaining());

		buffer.compact();
		System.out.println(buffer.position());
		System.err.println(buffer.limit());
		System.err.println(buffer.capacity());
		System.out.println(buffer.remaining());
		System.out.println(buffer.hasRemaining());

		buffer.flip();
		System.out.println(buffer.position());
		System.err.println(buffer.limit());
		System.err.println(buffer.capacity());
		System.out.println(buffer.remaining());
		System.out.println(buffer.remaining());
		System.out.println(buffer.hasRemaining());

	}
}
