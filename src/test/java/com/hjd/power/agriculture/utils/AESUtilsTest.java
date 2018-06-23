package com.hjd.power.agriculture.utils;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

import com.hjd.power.agriculture.Constants;

public class AESUtilsTest {

	@Test
	public void testEncryptStringStringString() {
		String str = "1Qaz2Wsx";
		String encryptStr = AESUtils.encrypt(str, Constants.AES_KEY, Constants.AES_IV);
		System.out.println("encryptStr : " + encryptStr);
		String decryptStr = AESUtils.decrypt(encryptStr, Constants.AES_KEY, Constants.AES_IV);
		System.out.println("decryptStr : " + decryptStr);
		Assert.assertEquals(str, decryptStr);
	}

	@Test
	public void testDecryptStringStringString() {
		fail("Not yet implemented");
	}

}
