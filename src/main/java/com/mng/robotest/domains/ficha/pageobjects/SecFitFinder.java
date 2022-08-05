package com.mng.robotest.domains.ficha.pageobjects;

import org.openqa.selenium.By;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecFitFinder extends PageObjTM {
	
	private static final String XPATH_WRAPPER = "//div[@id='uclw_wrapper']";
	private static final String XPATH_INPUT_ALTURA = XPATH_WRAPPER + "//div[@data-ref='height']//input";
	private static final String XPATH_INPUT_PESO = XPATH_WRAPPER + "//div[@data-ref='weight']//input";
	private static final String XPATH_ASPA_FOR_CLOSE = XPATH_WRAPPER + "//div[@data-ref='close']";
	
	public boolean isVisibleUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPATH_INPUT_ALTURA))
				.wait(maxSeconds).check());
	}
	
	public boolean isInvisibileUntil(int maxSeconds) {
		return (state(Invisible, By.xpath(XPATH_WRAPPER))
				.wait(maxSeconds).check());
	}
	
	public boolean isVisibleInputAltura() {
		return (state(Visible, By.xpath(XPATH_INPUT_ALTURA)).check());
	}
		
	public boolean isVisibleInputPeso() {
		return (state(Visible, By.xpath(XPATH_INPUT_PESO)).check());
	}
	
	public boolean clickAspaForCloseAndWait() {
		driver.findElement(By.xpath(XPATH_ASPA_FOR_CLOSE)).click();
		return (isInvisibileUntil(1));
	}
}
