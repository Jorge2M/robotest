package com.mng.robotest.test80.mango.test.pageobject.shop.footer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;

public class PageTrabajaConNosotrosOld extends WebdrvWrapp implements PageFromFooter {
	
	final String XPathIdFrame = "//iframe[@id='bodyFrame']";
	final String XPathForIdPage = "//li[@class='first']/a[text()[contains(.,'Nuestro ADN')]]";
	
	@Override
	public String getName() {
		return "Ofertas y envío de currículums";
	}
	
	@Override
	public boolean isPageCorrectUntil(int maxSecondsWait, WebDriver driver) {
		driver.switchTo().frame(driver.findElement(By.xpath(XPathIdFrame)));
		return (isElementPresentUntil(driver, By.xpath(XPathForIdPage), maxSecondsWait));
	}
}
