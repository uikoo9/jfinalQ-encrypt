package com.uikoo9.loader;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.catalina.loader.WebappClassLoader;

import com.uikoo9.encrypt.util.QClassUtil;

public class QClassLoader extends WebappClassLoader {
	private String encryptedPackage;
	private String encryptedPath;

	public QClassLoader() {
	}

	public QClassLoader(ClassLoader parent) {
		super(parent);
		readProperties();
	}

	public Class<?> findClass(String name) throws ClassNotFoundException {
		boolean flag = false;
		
		for(String s : encryptedPackage.split(",")){
			if (name.contains(s)) {
				flag = true;
			}
		}
		
		return flag ? findClassEncrypt(name) : super.findClass(name);
	}

	private Class<?> findClassEncrypt(String name) throws ClassNotFoundException {
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
		String cname = this.encryptedPath + name.replace('.', '/') + ".class";
		FileInputStream in = new FileInputStream(cname);
		try {
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			int ch;
			while ((ch = in.read()) != -1) {
				buffer.write(QClassUtil.decrypt(ch));
			}
			in.close();
			
			return buffer.toByteArray();
		} finally {
			in.close();
		}
	}

	private void readProperties() {
		InputStream in = QClassLoader.class.getResourceAsStream("loader.properties");
		Properties p = new Properties();
		try {
			try {
				p.load(in);
				this.encryptedPackage = p.getProperty("encrypted_package", "uikoo9");
				this.encryptedPath = p.getProperty("encrypted_class_path", "Z:/workspaces/jfinalq_01_blog/WebRoot/WEB-INF/classes/");
			} finally {
				in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}