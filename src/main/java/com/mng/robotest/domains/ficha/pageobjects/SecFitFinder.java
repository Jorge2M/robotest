package com.mng.robotest.domains.ficha.pageobjects;


import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.transversal.PageBase;


public class SecFitFinder extends PageBase {
	
	private static final String XPATH_WRAPPER = "//div[@id='uclw_wrapper']";
	private static final String XPATH_INPUT_ALTURA = XPATH_WRAPPER + "//div[@data-ref='height']//input";
	private static final String XPATH_INPUT_PESO = XPATH_WRAPPER + "//div[@data-ref='weight']//input";
	private static final String XPATH_ASPA_FOR_CLOSE = XPATH_WRAPPER + "//div[@data-ref='close']";
	
	public boolean isVisibleUntil(int maxSeconds) {
		return state(Visible, XPATH_INPUT_ALTURA).wait(maxSeconds).check();
	}
	
	public boolean isInvisibileUntil(int maxSeconds) {
		return state(Invisible, XPATH_WRAPPER).wait(maxSeconds).check();
	}
	
	public boolean isVisibleInputAltura() {
		return state(Visible, XPATH_INPUT_ALTURA).check();
	}
		
	public boolean isVisibleInputPeso() {
		return state(Visible, XPATH_INPUT_PESO).check();
	}
	
	public boolean clickAspaForCloseAndWait() {
		getElement(XPATH_ASPA_FOR_CLOSE).click();
		return isInvisibileUntil(1);
	}
}
