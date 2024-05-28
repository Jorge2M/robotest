package com.mng.robotest.tests.domains.ficha.pageobjects.nogenesis;


import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class SecFitFinder extends PageBase {
	
	private static final String XP_WRAPPER = "//dialog[@id='size-guide-modal']";
	private static final String XP_ASPA_FOR_CLOSE = XP_WRAPPER + "//*[@data-testid='modal.close.button']";
	
	public boolean isVisibleUntil(int seconds) {
		return state(VISIBLE, XP_WRAPPER).wait(seconds).check();
	}
	
	public boolean isInvisibileUntil(int seconds) {
		return state(INVISIBLE, XP_WRAPPER).wait(seconds).check();
	}
	
	public boolean close() {
		getElement(XP_ASPA_FOR_CLOSE).click();
		return isInvisibileUntil(1);
	}
	
}
