package edu.hfut.dese.msgpack;

import static org.msgpack.template.Templates.TString;
import static org.msgpack.template.Templates.tList;
import static org.msgpack.template.Templates.tMap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.msgpack.MessagePack;
import org.msgpack.packer.Packer;
import org.msgpack.template.Template;
import org.msgpack.unpacker.Unpacker;

public class Main4 {
	public static void main(String[] args) throws Exception {
		MessagePack msgpack = new MessagePack();

		// Create templates for serializing/deserializing List and Map objects
		Template<List<String>> listTmpl = tList(TString);
		Template<Map<String, String>> mapTmpl = tMap(TString, TString);

		//
		// Serialization
		//

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Packer packer = msgpack.createPacker(out);

		// Serialize List object
		List<String> list = new ArrayList<String>();
		list.add("msgpack");
		list.add("for");
		list.add("java");
		packer.write(list); // List object

		// Serialize Map object
		Map<String, String> map = new HashMap<String, String>();
		map.put("sadayuki", "furuhashi");
		map.put("muga", "nishizawa");
		packer.write(map); // Map object

		//
		// Deserialization
		//

		byte[] bytes = out.toByteArray();
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		Unpacker unpacker = msgpack.createUnpacker(in);

		// to List object
		List<String> dstList = unpacker.read(listTmpl);

		// to Map object
		Map<String, String> dstMap = unpacker.read(mapTmpl);
	}
}