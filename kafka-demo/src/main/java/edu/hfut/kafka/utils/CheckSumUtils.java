package edu.hfut.kafka.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

/**
 * 校验工具类
 *
 * @author wanggang
 *
 */
public class CheckSumUtils {

	public static final int MD5_LENGTH = 16; // bytes

	/**
	 * CRC32校验
	 */
	public static long getCRC32(String str) {
		// get bytes from string
		byte bytes[] = str.getBytes();
		Checksum checksum = new CRC32();
		// update the current checksum with the specified array of bytes
		checksum.update(bytes, 0, bytes.length);
		// get the current checksum value
		return checksum.getValue();
	}

	/**
	 * modified by donglei
	 * MD5加码 生成32位md5码
	 */
	public static String getMD5(String str) {

		byte[] md5Bytes = md5sum(str);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = (md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}

		return hexValue.toString();
	}

	/**
	 * @author donglei
	 * @param str
	 * @return
	 */
	public static byte[] md5sum(String str) {
		MessageDigest d;
		try {
			d = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("MD5 algorithm not available!", e);
		}

		return d.digest(str.getBytes());
	}

}
