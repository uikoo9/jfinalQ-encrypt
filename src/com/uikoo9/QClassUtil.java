package com.uikoo9;

/**
 * 加密解密程序，可以自己定义算法
 * @author qiaowenbin
 */
public class QClassUtil {
	
	/**
	 * 加密程序，可以自己修改
	 * @param ch
	 * @return
	 */
	public static byte encrypt(int ch){
		return (byte) (ch + 2);
	}
	
	/**
	 * 解密程序，可以自己修改
	 * @param ch
	 * @return
	 */
	public static byte decrypt(int ch){
		return (byte) (ch - 2);
	}
	
}
