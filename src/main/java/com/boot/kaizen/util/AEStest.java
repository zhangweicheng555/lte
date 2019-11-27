package com.boot.kaizen.util;

import java.security.SecureRandom;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
/**
 * 加密  方式  
 * String token = AEStest.encrypt(System.currentTimeMillis()+"", "sim*2019")
 * @author weichengz
 * @date 2019年3月27日 下午4:30:47
 */
public class AEStest {

	public static final Random ran = new Random();

	public static String encrypt(String context, String key) throws Exception {
		byte[] byteRe = enCrypt(context, key);
		String result = parseByte2HexStr(byteRe);
		return result;
	}

	public static String dencrypt(String context, String key) throws Exception {
		byte[] encrytByte = parseHexStr2Byte(context);
		String result = deCrypt(encrytByte, key);
		return result;
	}

	public static String toDecode(String context, String key) throws Exception {
		byte[] encrytByte = parseHexStr2Byte(context);
		String result = deCrypt(encrytByte, key);
		return result;
	}

	/**
	 * ������������
	 * 
	 * @param context
	 *            ����ǰJSON�ַ���
	 * @param key
	 *            ��Կ
	 * @return ���ܺ�����
	 * @throws Exception
	 */
	public static String createAsk(String context, String key) throws Exception {
		return (char) (ran.nextInt(6) + 65) + encrypt(context, key);
	}

	/**
	 * ��������н���
	 * 
	 * @param context
	 *            ���ܺ������
	 * @param key
	 *            ��Կ
	 * @return ���ܺ��JSON�ַ���
	 * @throws Exception
	 */
	public static String deAsk(String context, String key) throws Exception {
		context = context.substring(1);
		return dencrypt(context, key);
	}

	/**
	 * ���ܺ���
	 * 
	 * @param content
	 *            ���ܵ�����
	 * @param strKey
	 *            ��Կ
	 * @return ���ض������ַ�����
	 * @throws Exception
	 */
	public static byte[] enCrypt(String content,String strKey) throws Exception {
		KeyGenerator keygen;
		SecretKey desKey;
		Cipher c;
		byte[] cByte;
		String str = content;

		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		random.setSeed(strKey.getBytes());
		keygen = KeyGenerator.getInstance("AES");
		keygen.init(128, random);

		desKey = keygen.generateKey();
		c = Cipher.getInstance("AES");

		c.init(Cipher.ENCRYPT_MODE, desKey);

		cByte = c.doFinal(str.getBytes("UTF-8"));

		return cByte;
	}

	/**
	 * ���ܺ���
	 * 
	 * @param src
	 *            ���ܹ��Ķ������ַ�����
	 * @param strKey
	 *            ��Կ
	 * @return
	 * @throws Exception
	 */
	public static String deCrypt(byte[] src, String strKey) throws Exception {
		KeyGenerator keygen;
		SecretKey desKey;
		Cipher c;
		byte[] cByte;

		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		random.setSeed(strKey.getBytes());

		keygen = KeyGenerator.getInstance("AES");
		keygen.init(128, random);

		desKey = keygen.generateKey();
		c = Cipher.getInstance("AES");

		c.init(Cipher.DECRYPT_MODE, desKey);

		cByte = c.doFinal(src);

		return new String(cByte, "UTF-8");
	}

	/**
	 * 2����ת����16����
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * ��16����ת��Ϊ������
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1) {
			return null;}
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

}
