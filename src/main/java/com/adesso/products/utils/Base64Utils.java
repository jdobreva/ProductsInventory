package com.adesso.products.utils;

import java.util.Base64;

public class Base64Utils {
	
	private Base64Utils() {}
	
	public static String encode(String string) {
		return new String(Base64.getEncoder().encode(string.getBytes()));
	}
	
	public static String decode(String string) {
		return new String(Base64.getDecoder().decode(string));
	}

}
