package com.ben.lib;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ben.lib.ReadConfigFile;
import com.ben.lib.LogFormat;
import com.ben.lib.Log;


public class ParseLogInfo {
	private ReadConfigFile mReadConfigFile;
	private HashMap<String, ArrayList<LogFormat>> mLogFormatbyModule;
	private String mLogFile;
	private final String TAG = "ParseLogInfo";

	public ParseLogInfo(String cfg, String logfile) {
		mLogFile = logfile;
		mReadConfigFile = new ReadConfigFile(cfg);
		mReadConfigFile.loading();
	}

	public void run() {
		mLogFormatbyModule = mReadConfigFile.getLogFormatList();
		FileInputStream fstream = null;
		String line;

		try {
			fstream = new FileInputStream(mLogFile);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			while(null != (line = br.readLine())) {
				String module = getLogModule(line);
				if (module == null) {
					continue;
				}

				ArrayList<LogFormat> logFormatList = mLogFormatbyModule.get(module);
				if (logFormatList == null) { 
					continue;
				}
				for (LogFormat lf : logFormatList) {
					if (lf.match(line)) {
						if (lf.getLogTag() != null) {
							Log.w(TAG," ");
							Log.w(TAG, "//" + lf.getLogTag());
						}
						Log.w(TAG, line);
						break;
					}
				}
			}
		} catch (IOException ex) {
			Log.e(TAG, "read log file failed.");
		} finally {
			if (fstream != null) {
				try {
					fstream.close();
				} catch (IOException ex) {}
			}
		}
	}

	private static Pattern mAndroidLogPattern = 
		Pattern.compile("([0-9]{2}-[0-9]{2}) +([0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]+) +" +
				"([ 0-9]{5}) +([ 0-9]{5}) +([IWED]{1}) +([a-zA-Z0-9_]+)");

	private String getLogModule(String log) {
		Matcher match = mAndroidLogPattern.matcher(log);
		if (!match.find()) {
			Log.d(TAG, "match failed: " + log);
			return null;
		}

		String module = match.group(6);
//		Log.d(TAG, "Module: " + module);
		return module;
	}
}
