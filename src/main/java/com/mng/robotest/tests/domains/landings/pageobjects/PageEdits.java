package com.mng.robotest.tests.domains.landings.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageEdits extends PageBase {

	public static final String XP_WRAPPER = "//div[@id='vasava-edit']";
	public static final String XP_PRODUCT = "//a[@class='vsv-product']";
	public static final String XP_IMAGE_PRODUCT = XP_PRODUCT + "//img[@class='vsv-image-component']";
	
	public boolean isPage() {
		return state(VISIBLE, XP_WRAPPER).check();
	}
	
	public boolean isVisibleAnyArticle() {
		boolean isVisible = isVisibleAnyArticleSimple(0);
		if (!isVisible) {
			scrollEjeY(10000);
			return isVisibleAnyArticleSimple(1);
		}
		return false;
	}
	
	private boolean isVisibleAnyArticleSimple(int seconds) {
		return state(VISIBLE, XP_IMAGE_PRODUCT).wait(seconds).check();
	}
	
}
