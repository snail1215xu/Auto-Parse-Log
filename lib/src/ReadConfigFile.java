package com.ben.lib;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.HashMap;
import java.util.ArrayList;

import com.ben.lib.LogFormat;
import com.ben.lib.Log;

public class ReadConfigFile {
	private String mConfigFileName;
	private String TAG = "ReadConfigFile";	

	private final HashMap<String, ArrayList<LogFormat>> mLogFormatbyModule = 
			new HashMap<String, ArrayList<LogFormat>>();


	public ReadConfigFile(String file) {
		mConfigFileName = file;
	}

	public void loading() {
		FileInputStream fstream = null;
		String line;

		try {
			fstream = new FileInputStream(mConfigFileName);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			while(null != (line = br.readLine())) {
//				Log.d(TAG, line);
				LogFormat lf = LogFormat.createLogFormat(line);
				if (lf != null ) {
					ArrayList<LogFormat> loglist = mLogFormatbyModule.get(lf.getLogModule());
					if (loglist == null) {
						loglist = new ArrayList<LogFormat>();
						mLogFormatbyModule.put(lf.getLogModule(), loglist);
					}
					if (!loglist.contains(lf)) {
						loglist.add(lf);
					}
					Log.d(TAG, lf.dump());
				}
			}
		} catch (IOException ex) {
			Log.e(TAG, "Read config file failed.");
		} finally {
			if (fstream != null) {
				try {
					fstream.close();
				} catch (IOException ex) {}
			}
		}
	}

	public HashMap<String, ArrayList<LogFormat>> getLogFormatList() {
		return mLogFormatbyModule;
	}
}
