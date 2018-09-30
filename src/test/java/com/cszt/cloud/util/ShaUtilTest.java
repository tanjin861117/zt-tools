package com.cszt.cloud.util;

import org.junit.Test;

public class ShaUtilTest {

	@Test
	public void testSha(){
		System.out.println(ShaUtil.getSha("tanjin-test", ShaUtil.SHA));
		System.out.println(ShaUtil.getSha("tanjin-test", ShaUtil.SHA_224));
		System.out.println(ShaUtil.getSha("tanjin-test", ShaUtil.SHA_256));
		System.out.println(ShaUtil.getSha("tanjin-test", ShaUtil.SHA_384));
		System.out.println(ShaUtil.getSha("tanjin-test", ShaUtil.SHA_512));
	}
}
