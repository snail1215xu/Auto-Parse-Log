package com.ben.lib;

public class LogFormat{
	private String mLogTag;
	private String mLogModule;
	private String[] mLogFlag;

	private LogFormat(int flagLen) {
		mLogFlag = new String[flagLen];
	}

	private boolean setLogFlag(String flag, int index) {
		if (mLogFlag.length < index) {
			return false;
		}

		mLogFlag[index] = flag.substring(6, (flag.length()-1));
		return true;
	}

	private boolean setLogModule(String md) {
		mLogModule = md.substring(6, (md.length()-1));
		return true;
	}

	private boolean setLogTag(String lt) {
		mLogTag = lt.substring(5, (lt.length()-1));
		return true;
	}

	public static LogFormat createLogFormat(String log) {
		log = log.trim();
		if (!log.startsWith("Format")) {
			return null;
		}

		String[] fields = log.split(":");
		if (fields.length != 2) {
			return null;
		}

		String[] format = fields[1].split("&");
		
		if (format.length < 2) {
			return null;
		}
//		if (!format[format.length-1].startsWith("TAG=")) {
//			return null;
//		}
		for(int i=0; i<(format.length-2); i++) {
			if (!format[i].startsWith("FLAG=")) {
				return null;
			}
		}

		boolean haveTag = false;
		if (format[format.length-1].startsWith("TAG=")) {
			haveTag = true;
		} 
		
		if (!haveTag && !format[format.length-1].startsWith("FLAG=")) {
			return null;
		}

		int flagNum;
		if (haveTag) {
			flagNum = format.length-1;
		} else {
			flagNum = format.length;
		}
		LogFormat lf = new LogFormat(flagNum-1);
		lf.setLogModule(format[0]);
		for (int i=1; i<(flagNum); i++) {
			lf.setLogFlag(format[i], i-1);
		}
		if (haveTag) {
			lf.setLogTag(format[format.length-1]);
		}
		return lf;
	}

	public boolean match(String log) {
		int index = log.indexOf(mLogModule);
		int indexBig;
		if (index < 0) {
			return false;
		}

		for (int i=0; i<mLogFlag.length; i++) {
			indexBig = log.indexOf(mLogFlag[i]);
			if (indexBig < 0 || indexBig < index) {
				return false;
			}
			index = indexBig;
		}
		return true;
	}

	public String getLogModule() {
		return mLogModule;
	}

	public String getLogTag() {
		return mLogTag;
	}

	public String dump() {
		String log = "Format:FLAG=" + mLogModule;

//		log.append(mLogModule);
		for(int i=0; i<mLogFlag.length; i++) {
			log = log + "&FLAG=" + mLogFlag[i];
//			log.append(mLogFlag[i]);
		}
		log = log + "&TAG=" + mLogTag;
//		log.append(mLogTag);
		return log;
	}
}
