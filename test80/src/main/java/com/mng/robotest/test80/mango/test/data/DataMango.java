package com.mng.robotest.test80.mango.test.data;

import java.sql.Timestamp;


public class DataMango {
	
	/**
	 * @return un email único gracias a la inclusión de un timestamp
	 */
	public static String getEmailNonExistentTimestamp() {
		java.util.Date date = new java.util.Date();
		String timestamp =  new Timestamp(date.getTime()).toString().trim()
				.replace(":", "")
				.replace(" ", "")
				.replace("-", ".");
		
		String emailUnico = "mng_test" + "_" + timestamp + "@mango.com";
		return emailUnico;
	}
}
