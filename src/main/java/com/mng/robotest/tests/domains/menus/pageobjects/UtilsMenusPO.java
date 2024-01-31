package com.mng.robotest.tests.domains.menus.pageobjects;

import java.text.Normalizer;

public class UtilsMenusPO {

	private UtilsMenusPO() {}
	
	public static String getMenuNameForDataTestId(String menuName) {
		String menuWithoutSpaces = menuName.toLowerCase().replace(" ", "_");
		return removeAccents(menuWithoutSpaces);
	}
	
	private static String removeAccents(String value) {
		String normalizedString = Normalizer.normalize(value, Normalizer.Form.NFD);
        return normalizedString.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	}
	


}
