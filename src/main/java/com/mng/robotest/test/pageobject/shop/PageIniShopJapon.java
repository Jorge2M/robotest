package com.mng.robotest.test.pageobject.shop;

import com.mng.robotest.domains.base.PageBase;

public class PageIniShopJapon extends PageBase {

	public static final String URL = "japan.mango.com";
	public static final String TITLE = "MANGO - マンゴ公式オンラインストア";
	
	public boolean isPageUntil(int seconds) {
		return (
			titleContainsUntil(driver, TITLE, seconds) &&
			driver.getCurrentUrl().contains(URL));
	}
}
