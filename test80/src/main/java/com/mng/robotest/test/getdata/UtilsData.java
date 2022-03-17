package com.mng.robotest.test.getdata;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.access.InputParamsMango;

public class UtilsData {

	public static String getNameCloudTest() {
		try {
			String initialURL = ((InputParamsMango)TestMaker.getInputParamsSuite()).getUrlBase();
			return getNameCloudTest(initialURL);
		}
		catch (Exception e) {
			//
		}
		return "";
	}
	
	public static String getNameCloudTest(String initialURL) {
		Pattern pattern = Pattern.compile(".*://.*name=(.*)");
		Matcher match = pattern.matcher(initialURL);
		if (match.find()) {
			String url = match.group(1);
			if (url.indexOf("&")!=0) {
				return url.substring(0, url.indexOf("&"));
			}
		}
		return "";
	}
}
