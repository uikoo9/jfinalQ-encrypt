package com.uikoo9.loader;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.catalina.loader.WebappClassLoader;

public class UClassLoader extends WebappClassLoader {
	private String containPackage;
	private String basePath;

	public UClassLoader() {
	}

	public UClassLoader(ClassLoader parent) {
		super(parent);
		readProperties();
	}

	public Class<?> findClass(String name) throws ClassNotFoundException {
		if (name.contains(this.containPackage)) {
			return findClassEncrypt(name);
		}
		return super.findClass(name);
	}

	private Class<?> findClassEncrypt(String name)
			throws ClassNotFoundException {
		byte[] classBytes = (byte[]) null;
		try {
			classBytes = loadClassBytesEncrypt(name);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Class<?> cl = defineClass(name, classBytes, 0, classBytes.length);
		if (cl == null)
			throw new ClassNotFoundException(name);
		return cl;
	}

	private byte[] loadClassBytesEncrypt(String name) throws IOException {
		String cname = this.basePath + name.replace('.', '/') + ".uikoo9";
		FileInputStream in = new FileInputStream(cname);
		try {
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			int ch;
			while ((ch = in.read()) != -1) {
				buffer.write((byte) (ch - 2));
			}
			in.close();
			byte[] arrayOfByte = buffer.toByteArray();
			return arrayOfByte;
		} finally {
			in.close();
		}
	}

	private void readProperties() {
		InputStream in = UClassLoader.class.getResourceAsStream("loader.properties");
		Properties p = new Properties();
		try {
			try {
				p.load(in);
				this.containPackage = p.getProperty("package", "ebeiwai");
				this.basePath = p.getProperty("basepath", "Z:/program/workspaces/_work_03_mui/WebRoot/WEB-INF/classes/");
			} finally {
				in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}