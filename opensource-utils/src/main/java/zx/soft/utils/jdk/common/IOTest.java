package zx.soft.utils.jdk.common;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

public class IOTest {

    public static void main(String[] args) throws IOException {
//        test2();
//        writeFile();
        testIOBase();

    }

    public static void writeFile() throws IOException {
        String f = "src/main/resources/test.b";
        DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(f));
        int i = 0;
        while (i < 1001) {
            outputStream.write(new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09 });
            i++;
        }
        outputStream.close();
    }

    // 1192
    public static void testIOBase() throws IOException {
        String f = "src/main/resources/test.b";
        DataInputStream input = new DataInputStream(new FileInputStream(f));
        IOBase ioBase = new IOBase(input);
        byte[] tmp = new byte[50];
        while (ioBase.readFully(tmp)) {
            System.out.println(Arrays.toString(tmp));
        }
        ioBase.close();
    }

    // 1192
    public static void test2() throws IOException {
        long a = System.currentTimeMillis();
        String f = "src/main/resources/centos.box";
        DataInputStream input = new DataInputStream(new FileInputStream(f));
        BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream("src/main/resources/CentOS_o_s.iso"));
        IOBase ioBase = new IOBase(input);
        byte[] tmp = new byte[50];
        int num = ioBase.readBytes(tmp);
        while (num != -1) {
            output.write(tmp, 0, num);
            num = ioBase.readBytes(tmp);
        }
        ioBase.close();
        output.close();
        System.out.println(System.currentTimeMillis() - a);
    }

    // 1489
    public static void test3() throws IOException {
        long a = System.currentTimeMillis();
        String f = "src/main/resources/centos.box";
        FileInputStream input = new FileInputStream(f);
        BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream("src/main/resources/CentOS_o.iso"));
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

    // 121
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
