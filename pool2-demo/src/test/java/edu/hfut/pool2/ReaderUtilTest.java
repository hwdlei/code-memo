package edu.hfut.pool2;

import java.io.File;
import java.io.FileReader;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.Assert;
import org.junit.Test;

public class ReaderUtilTest {

	@Test
	public void test() {
		File f = new File("src/test/resources/a.txt");
		try(FileReader reader = new FileReader(f)){
			ReaderUtil readerUtil = new ReaderUtil(new GenericObjectPool<>(new StringBufferFactory()));
			String a = readerUtil.readToString(reader);
			Assert.assertTrue("a".equals(a));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
