package com.ben.lib;

import com.ben.lib.ParseLogInfo;
public class MainLog {
	public static void main(String[] args) {
		if (args.length == 2) {
			ParseLogInfo pli = new ParseLogInfo(args[0], args[1]);
			pli.run();
		}
	}
}
