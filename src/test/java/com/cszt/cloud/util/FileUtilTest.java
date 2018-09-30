package com.cszt.cloud.util;

import org.junit.Test;

public class FileUtilTest {

	@Test
	public void testReadFile(){
		String str = FileUtil.readFile("D://工作//响应编码.txt");
		System.out.println(str);
	}
	
	@Test
	public void testWriteFile(){
		FileUtil.writeFile("D://工作//test.txt", "test");
		FileUtil.writeFile("D://工作//test.txt", "test" , true);
	}
	
	@Test
	public void testIsExist(){
		System.out.println(FileUtil.isExistsDirectory("D://工作1"));
		System.out.println(FileUtil.isExist("D://工作//响应编码.txt"));
	}
	
	@Test
	public void testCreateFile(){
		System.out.println(FileUtil.createFiles("D://工作//test//test//test.txt"));
	}
	
	@Test
	public void testCreatePath(){
		FileUtil.createPaths("D://工作//test//test");
	}
	
	@Test
	public void testFileExt(){
		System.out.println(FileUtil.getFileExt("D://工作//响应编码.txt"));
	}
	
	@Test
	public void testFileSize(){
		System.out.println(FileUtil.getFileSize("D://工作//test.txt") / 1024);
	}
}
