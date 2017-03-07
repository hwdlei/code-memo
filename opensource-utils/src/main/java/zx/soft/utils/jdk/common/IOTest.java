package zx.soft.utils.jdk.common;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class IOTest {

	public static void main(String[] args) throws IOException {
		test3();
	}

	// 24329
	public static void test2() throws IOException {
		long a = System.currentTimeMillis();
		String f = "src/main/resources/CentOS.iso";
		DataInputStream input = new DataInputStream(new FileInputStream(f));
		FileOutputStream output = new FileOutputStream("src/main/resources/CentOS_o_s.iso");
		IOBase ioBase = new IOBase(input);
		byte[] tmp = new byte[50];
		int num = ioBase.readBytes2(tmp);
		while (num != -1) {
			output.write(tmp, 0, num);
			num = ioBase.readBytes2(tmp);
		}
		ioBase.close();
		output.close();
		System.out.println(System.currentTimeMillis() - a);
	}

	// 16767
	public static void test3() throws IOException {
		long a = System.currentTimeMillis();
		String f = "src/main/resources/CentOS.iso";
		FileInputStream input = new FileInputStream(f);
		FileOutputStream output = new FileOutputStream("src/main/resources/CentOS_o.iso");
		IOBaseBuffer ioBase = new IOBaseBuffer(input);
		byte[] tmp = new byte[50];
		int num = ioBase.readBytes2(tmp);
		while (num != 0) {
			output.write(tmp, 0, num);
			num = ioBase.readBytes2(tmp);
		}
		ioBase.close();
		output.close();
		System.out.println(System.currentTimeMillis() - a);
	}

	// 563
	public static void readFile1(String file1) throws IOException {
		FileInputStream input = new FileInputStream(file1);
		byte[] tmp = new byte[1024 * 1024];
		int b = 0;
		while ((b = input.read(tmp)) > 0) {
		}
		input.close();
	}

	//121
	public static void readFile2(String file) throws IOException {
		FileChannel inChannel = new FileInputStream(file).getChannel();
		ByteBuffer buf = ByteBuffer.allocate(1024 * 1024);

		int bytesRead = inChannel.read(buf);
		while (bytesRead != -1) {
			buf.clear();
			bytesRead = inChannel.read(buf);
		}
		inChannel.close();
	}

	public static void testReadFile() throws IOException {
		long a = System.currentTimeMillis();
		System.out.println(a);
		String f = "src/main/resources/CentOS.iso";
		readFile2(f);
		System.out.println(System.currentTimeMillis() - a);
	}

}
