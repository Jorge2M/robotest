package com.mng.robotest.tests.domains.loyalty.pageobjects.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilsLoyaltyPage {

	private UtilsLoyaltyPage() {}
	
	public static int getPointsFromLiteral(String textPoints) {
		Pattern pattern = Pattern.compile(" [0-9,.]+ ");
		Matcher matcher = pattern.matcher(textPoints);
		if (matcher.find()) {
			return Integer.valueOf(
				matcher.group(0).
				trim().
				replace(",", "").
				replace(".", ""));
		}
		return 0;
	}

}
