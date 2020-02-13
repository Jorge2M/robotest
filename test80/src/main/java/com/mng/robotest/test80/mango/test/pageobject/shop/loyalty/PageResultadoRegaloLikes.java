package com.mng.robotest.test80.mango.test.pageobject.shop.loyalty;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public class PageResultadoRegaloLikes extends WebdrvWrapp {
	
	private final WebDriver driver;
	
	private final static String XPathDoneIcon = "//img[@class='done-icon']";
	
	public PageResultadoRegaloLikes(WebDriver driver) {
		this.driver = driver;
	}
	
	public boolean isEnvioOk(int maxSeconds) {
		return isElementVisibleUntil(driver, By.xpath(XPathDoneIcon), maxSeconds);
	}
	
}
