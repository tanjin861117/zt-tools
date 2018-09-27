package com.cszt.cloud.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件帮助工具类
 * 
 * @ClassName: FileUtil
 * @Description: 文件帮助工具类
 * @author: tanjin
 * @date:2018年9月25日 下午5:12:56
 */
public class FileUtil {

	private FileUtil() {

	}

	/**
	 * 读取文件
	 * 
	 * @Title: readFile
	 * @Description: 读取文件
	 * @param is
	 * @return
	 */
	public static String readFile(InputStream is) {
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		try {
			br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String readLine = null;
			while ((readLine = br.readLine()) != null) {
				sb.append(readLine);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * 根据文件路径读取文件内容
	 * 
	 * @Title: readFile
	 * @Description: 根据文件路径读取文件内容
	 * @param fileName
	 * @return
	 */
	public static String readFile(String fileName) {
		File file = new File(fileName);
		if (file.exists()) {
			try {
				return readFile(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 根据文件路径读取文件内容
	 * 
	 * @Title: readFile
	 * @Description: 根据文件路径读取文件内容
	 * @param fileName
	 * @return
	 */
	public static String readFile(File file) {
		if (file.exists()) {
			try {
				return readFile(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 将内容写入文件
	 * 
	 * @Title: writeFile
	 * @Description: 将内容写入文件
	 * @param filePath
	 *            文件路径
	 * @param content
	 *            内容
	 * @param isAppend
	 *            是否追加
	 */
	public static void writeFile(String filePath, String content, boolean isAppend) {
		FileOutputStream outputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		BufferedWriter bufferedWriter = null;
		try {
			outputStream = new FileOutputStream(filePath, isAppend);
			outputStreamWriter = new OutputStreamWriter(outputStream);
			bufferedWriter = new BufferedWriter(outputStreamWriter);
			bufferedWriter.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedWriter != null) {
					bufferedWriter.close();
				}
				if (outputStreamWriter != null) {
					outputStreamWriter.close();
				}
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 将内容写入文件(不追加文件末尾)
	 * 
	 * @Title: writeFile
	 * @Description: 将内容写入文件
	 * @param filePath
	 * @param content
	 */
	public static void writeFile(String filePath, String content) {
		writeFile(filePath, content, false);
	}

	/**
	 * 判断目录是否存在
	 * 
	 * @Title: isExistsDirectory
	 * @Description: 判断目录是否存在
	 * @param fileName
	 * @return
	 */
	public static boolean isExistsDirectory(String fileName) {
		File file = new File(fileName);
		return file.exists() && file.isDirectory();
	}

	/**
	 * 判断文件夹是否存在
	 * 
	 * @Title: isExist
	 * @Description: 判断文件夹是否存在
	 * @param fileName
	 * @return
	 */
	public static boolean isExist(String fileName) {
		File file = new File(fileName);
		return file.exists();
	}

	/**
	 * 创建指定的目录。 如果指定的目录的父目录不存在则创建其目录书上所有需要的父目录。
	 * 
	 * @Title: makeDirectory
	 * @Description: 创建指定的目录。 如果指定的目录的父目录不存在则创建其目录书上所有需要的父目录。
	 * @param file
	 * @return
	 */
	public static boolean makeDirectory(File file) {
		File parent = file.getParentFile();
		if (parent != null) {
			return parent.mkdirs();
		}
		return false;
	}

	/**
	 * 创建指定的目录。 如果指定的目录的父目录不存在则创建其目录书上所有需要的父目录。
	 * 
	 * @Title: makeDirectory
	 * @Description: 创建指定的目录。 如果指定的目录的父目录不存在则创建其目录书上所有需要的父目录。
	 * @param filePath
	 * @return
	 */
	public static boolean makeDirectory(String filePath) {
		File file = new File(filePath);
		return makeDirectory(file);
	}
	
	/**
	 * 创建文件支持多级目录
	 * @Title: createFiles   
	 * @Description: 创建文件支持多级目录  
	 * @param filePath
	 * @return
	 */
    public static boolean createFiles(String filePath) {
        File file = new File(filePath);
        File dir = file.getParentFile();
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                try {
                    return file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    
    /**
     * 创建多级目录
     * @Title: createPaths   
     * @Description: 创建多级目录  
     * @param paths
     * @return
     */
    public static boolean createPaths(String paths) {
        File dir = new File(paths);
        return !dir.exists() && dir.mkdir();
    }

	/**
	 * 得到文件后缀名
	 * 
	 * @Title: getFileExt
	 * @Description: 得到文件后缀名
	 * @param fileName
	 * @return
	 */
	public static String getFileExt(String fileName) {
		int point = fileName.lastIndexOf('.');
		int length = fileName.length();
		if (point == -1 || point == length - 1) {
			return "";
		} else {
			return fileName.substring(point + 1, length);
		}
	}

	/**
	 * 文件的大小
	 * 
	 * @Title: getFileSize
	 * @Description: 文件的大小
	 * @param fileName
	 * @return
	 */
	public static int getFileSize(String fileName) {
		File file = new File(fileName);
		FileInputStream fis = null;
		int size = 0;
		try {
			fis = new FileInputStream(file);
			size = fis.available();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}

	/**
	 * 获取指定目录下的所有文件
	 * 
	 * @Title: getAllFiles
	 * @Description: 获取指定目录下的所有文件
	 * @param dir
	 * @return
	 */
	public static List<File> getAllFiles(String dir) {
		return getAllFiles(new File(dir), null);
	}

	/**
	 * 获取指定目录下的所有文件
	 * 
	 * @Title: getAllFiles
	 * @Description: 获取指定目录下的所有文件
	 * @param file
	 * @return
	 */
	public static List<File> getAllFiles(File file) {
		return getAllFiles(file, null);
	}

	/**
	 * 获取指定目录下的符合后缀规则的所有文件
	 * 
	 * @Title: getAllFiles
	 * @Description: 获取指定目录下的符合后缀规则的所有文件
	 * @param file
	 *            源文件夹
	 * @param suffuxs
	 *            后缀规则
	 * @return
	 */
	public static List<File> getAllFiles(String dir, String[] suffuxs) {
		return getAllFiles(new File(dir), suffuxs);
	}

	/**
	 * 
	 * 获取指定目录下的符合后缀规则的所有文件
	 * 
	 * @Title: getAllFiles
	 * @Description: 获取指定目录下的符合后缀规则的所有文件
	 * @param file
	 *            源文件夹
	 * @param suffuxs
	 *            后缀规则
	 * @return
	 */
	public static List<File> getAllFiles(File file, String[] suffuxs) {
		List<File> list = new ArrayList<File>();
		if (file.isDirectory()) {
			// 递归
			cursionDir(list, file, suffuxs);
		} else {
			throw new IllegalArgumentException("这是一个文件，请输入文件夹");
		}
		return list;
	}

	private static void cursionDir(List<File> list, File file, final String[] suffuxs) {
		File[] files = file.listFiles(new FileFilter() {
			// 过滤器
			public boolean accept(File pathname) {
				if (suffuxs == null) {
					return true;
				}
				if (pathname.isDirectory()) {
					return true;
				}
				for (String string : suffuxs) {
					if (pathname.getAbsolutePath().endsWith(string)) {
						return true;
					}
				}
				return false;
			}
		});
		if (files == null) {
			return;
		} else {
			for (File file2 : files) {
				if (file2.isDirectory()) {
					cursionDir(list, file2, suffuxs);
				} else if (file2.isFile()) {
					list.add(file2);
				}
			}
		}
	}

}
