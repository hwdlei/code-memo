package edu.hfut.dese.msgpack;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.msgpack.MessagePack;
import org.msgpack.annotation.Message;
import org.msgpack.packer.Packer;
import org.msgpack.unpacker.Unpacker;

public class Main2 {
	@Message
	public static class MyMessage {
		public String name;
		public double version;
	}

	public static void main(String[] args) throws Exception {
		MyMessage src1 = new MyMessage();
		src1.name = "msgpack";
		src1.version = 0.6;
		MyMessage src2 = new MyMessage();
		src2.name = "muga";
		src2.version = 10.0;
		MyMessage src3 = new MyMessage();
		src3.name = "frsyukik";
		src3.version = 1.0;

		MessagePack msgpack = new MessagePack();
		//
		// Serialize
		//
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Packer packer = msgpack.createPacker(out);
		packer.write(src1);
		packer.write(src2);
		packer.write(src3);
		byte[] bytes = out.toByteArray();

		//
		// Deserialize
		//
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		Unpacker unpacker = msgpack.createUnpacker(in);
		MyMessage dst1 = unpacker.read(MyMessage.class);
		MyMessage dst2 = unpacker.read(MyMessage.class);
		MyMessage dst3 = unpacker.read(MyMessage.class);
	}
}