package com.uikoo9.encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EncryptClass {
	
	/**
	 * 加密
	 * @param sDir 源代码目录
	 * @param pDir 加密代码目录
	 * @return
	 */
	public static String encrypt(String sDir, String pDir) {
		String res = "加密失败！";
		String dDir = pDir
				+ (pDir.endsWith(File.separator) ? "" : File.separator)
				+ "encrypt" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		try {
			int errors = copy(sDir, dDir);
			if (errors == 0)
				res = sDir + "\r\n已加密复制到\r\n" + dDir;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	/**
	 * class加密
	 * @param srcDir 源代码目录
	 * @param destDir 加密后目录
	 * @return
	 * @throws IOException
	 */
	public static int copy(String srcDir, String destDir) throws IOException {
		int res = 0;

		File filePath = new File(srcDir);
		if (filePath.isDirectory()) {
			File[] list = filePath.listFiles();
			for (int i = 0; i < list.length; i++) {
				String newPath = srcDir + File.separator + list[i].getName();
				String newCopyPath = destDir + File.separator + list[i].getName();
				File newFile = new File(destDir);
				if (!newFile.exists())
					newFile.mkdir();

				copy(newPath, newCopyPath);
			}
		} else if (filePath.isFile()) {
			if (filePath.toString().endsWith(".class"))
				copyClass(srcDir, destDir);
			else
				copyFile(srcDir, destDir);
		} else {
			res++;
		}

		return res;
	}
	
	/**
	 * 复制普通文件
	 * @param srcDir 源文件目录
	 * @param destDir 复制后文件目录
	 * @throws IOException
	 */
	public static void copyFile(String srcDir, String destDir) throws IOException {
		FileInputStream in = new FileInputStream(srcDir);
		FileOutputStream out = new FileOutputStream(destDir);
		int ch;
		while ((ch = in.read()) != -1) {
			out.write((byte) ch);
		}

		in.close();
		out.close();
	}

	/**
	 * 复制class文件并进行移位
	 * @param srcDir 源代码目录
	 * @param destDir 复制后代码目录
	 * @throws IOException
	 */
	public static void copyClass(String srcDir, String destDir) throws IOException {
		FileInputStream in = new FileInputStream(srcDir);
		FileOutputStream out = new FileOutputStream(destDir);
		
		int ch;
		while ((ch = in.read()) != -1) {
			out.write((byte) (ch + 2));
		}

		in.close();
		out.close();
	}

}