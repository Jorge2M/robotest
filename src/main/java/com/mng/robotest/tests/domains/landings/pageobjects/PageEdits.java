package com.mng.robotest.tests.domains.landings.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageEdits extends PageBase {

	public static final String XP_WRAPPER = "//div[@id='vasava-edit']";
	public static final String XP_PRODUCT = "//*[@class[contains(.,'vsv-product')]]";
	public static final String XP_IMAGE_PRODUCT = XP_PRODUCT + "//*[@class='vsv-image-component' or @class='vsv-background-image-component']";
	
	public boolean isPage() {
		return state(VISIBLE, XP_WRAPPER).check();
	}
	
	public boolean isVisibleAnyArticle() {
		boolean isVisible = isVisibleAnyArticleSimple();
		if (!isVisible) {
			scrollEjeY(10000);
			return isVisibleAnyArticleSimple();
		}
		return true;
	}
	
	private boolean isVisibleAnyArticleSimple() {
		return state(VISIBLE, XP_IMAGE_PRODUCT).check();
	}
	
}
