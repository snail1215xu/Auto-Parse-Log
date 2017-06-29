package com.ben.lib;

public class Log {
	public static void d(String tag, String log) {
//		System.out.println(tag + " d " + log);
	}

	public static void i(String tag, String log) {
//		System.out.println(tag + " i " + log);
	}

	public static void w(String tag, String log) {
//		System.out.println(tag + " w " + log);
		System.out.println(log);
	}

	public static void e(String tag, String log) {
		System.out.println(tag + " e " + log);
	}
}
