package com.mng.robotest.tests.domains.ficha.pageobjects;


import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;


public class SecFitFinder extends PageBase {
	
	private static final String XP_WRAPPER = "//div[@id='uclw_wrapper']";
	private static final String XP_INPUT_ALTURA = XP_WRAPPER + "//div[@data-ref='height']//input";
	private static final String XP_INPUT_PESO = XP_WRAPPER + "//div[@data-ref='weight']//input";
	private static final String XP_ASPA_FOR_CLOSE = XP_WRAPPER + "//div[@data-ref='close']";
	
	public boolean isVisibleUntil(int seconds) {
		return state(VISIBLE, XP_INPUT_ALTURA).wait(seconds).check();
	}
	
	public boolean isInvisibileUntil(int seconds) {
		return state(INVISIBLE, XP_WRAPPER).wait(seconds).check();
	}
	
	public boolean isVisibleInputAltura() {
		return state(VISIBLE, XP_INPUT_ALTURA).check();
	}
		
	public boolean isVisibleInputPeso() {
		return state(VISIBLE, XP_INPUT_PESO).check();
	}
	
	public boolean clickAspaForCloseAndWait() {
		getElement(XP_ASPA_FOR_CLOSE).click();
		return isInvisibileUntil(1);
	}
}
