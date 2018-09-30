package com.cszt.cloud.util;

import org.junit.Test;

public class DesUtilTest {

	@Test
	public void testEncoder(){
		try {
			System.out.println(DesUtil.encoder("des-test", DesUtil.key));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDecoder(){
		try {
			System.out.println(DesUtil.decoder("6b4668275e2b2c3ec0158e64d4ca03aa", DesUtil.key));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
