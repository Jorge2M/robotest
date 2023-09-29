package com.mng.robotest.testslegacy.data;

import java.sql.Timestamp;

import com.mng.robotest.tests.domains.base.PageBase;


public class DataMango {
	
	private DataMango() {}
	
	public synchronized static String getEmailNonExistentTimestamp() {
		PageBase.waitMillis(1);
		java.util.Date date = new java.util.Date();
		String timestamp =  new Timestamp(date.getTime()).toString().trim()
				.replace(":", "")
				.replace(" ", "")
				.replace("-", ".");
		return "mng_test" + "_" + timestamp + "@mango.com";
	}
}
