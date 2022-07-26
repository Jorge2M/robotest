package com.mng.robotest.domains.ficha.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecFitFinder {
	
	private static final String XPATH_WRAPPER = "//div[@id='uclw_wrapper']";
	private static final String XPATH_INPUT_ALTURA = XPATH_WRAPPER + "//div[@data-ref='height']//input";
	private static final String XPATH_INPUT_PESO = XPATH_WRAPPER + "//div[@data-ref='weight']//input";
	private static final String XPATH_ASPA_FOR_CLOSE = XPATH_WRAPPER + "//div[@data-ref='close']";
	
	public static boolean isVisibleUntil(int maxSeconds, WebDriver driver) {
		return (state(Visible, By.xpath(XPATH_INPUT_ALTURA), driver)
				.wait(maxSeconds).check());
	}
	
	public static boolean isInvisibileUntil(int maxSeconds, WebDriver driver) {
		return (state(Invisible, By.xpath(XPATH_WRAPPER), driver)
				.wait(maxSeconds).check());
	}
	
	public static boolean isVisibleInputAltura(WebDriver driver) {
		return (state(Visible, By.xpath(XPATH_INPUT_ALTURA), driver).check());
	}
		
	public static boolean isVisibleInputPeso(WebDriver driver) {
		return (state(Visible, By.xpath(XPATH_INPUT_PESO), driver).check());
	}
	
	public static boolean clickAspaForCloseAndWait(WebDriver driver) {
		driver.findElement(By.xpath(XPATH_ASPA_FOR_CLOSE)).click();
		return (isInvisibileUntil(1/*maxSecondsToWait*/, driver));
	}
}
