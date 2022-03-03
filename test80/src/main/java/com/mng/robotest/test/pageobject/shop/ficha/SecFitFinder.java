package com.mng.robotest.test.pageobject.shop.ficha;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecFitFinder {
	
	static String XPathWrapper = "//div[@id='uclw_wrapper']";
	static String XPathInputAltura = XPathWrapper + "//div[@data-ref='height']//input";
	static String XPathInputPeso = XPathWrapper + "//div[@data-ref='weight']//input";
	static String XPathAspaForClose = XPathWrapper + "//div[@data-ref='close']";
	
	public static boolean isVisibleUntil(int maxSeconds, WebDriver driver) {
		return (state(Visible, By.xpath(XPathInputAltura), driver)
				.wait(maxSeconds).check());
	}
	
	public static boolean isInvisibileUntil(int maxSeconds, WebDriver driver) {
		return (state(Invisible, By.xpath(XPathWrapper), driver)
				.wait(maxSeconds).check());
	}
	
	public static boolean isVisibleInputAltura(WebDriver driver) {
		return (state(Visible, By.xpath(XPathInputAltura), driver).check());
	}
		
	public static boolean isVisibleInputPeso(WebDriver driver) {
		return (state(Visible, By.xpath(XPathInputPeso), driver).check());
	}
	
	public static boolean clickAspaForCloseAndWait(WebDriver driver) {
		driver.findElement(By.xpath(XPathAspaForClose)).click();
		return (isInvisibileUntil(1/*maxSecondsToWait*/, driver));
	}
}
