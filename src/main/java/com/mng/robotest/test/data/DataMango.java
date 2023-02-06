package com.mng.robotest.test.data;

import java.sql.Timestamp;


public class DataMango {
	
	private DataMango() {}
	
	public synchronized static String getEmailNonExistentTimestamp() {
		java.util.Date date = new java.util.Date();
		String timestamp =  new Timestamp(date.getTime()).toString().trim()
				.replace(":", "")
				.replace(" ", "")
				.replace("-", ".");
		return "mng_test" + "_" + timestamp + "@mango.com";
	}
}
