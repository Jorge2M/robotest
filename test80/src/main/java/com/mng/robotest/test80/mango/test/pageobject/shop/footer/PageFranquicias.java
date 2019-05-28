package com.mng.robotest.test80.mango.test.pageobject.shop.footer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;

public class PageFranquicias extends WebdrvWrapp implements PageFromFooter {
	
	final String XPathIdFrame = "//iframe[@id='bodyFrame']";
	final String XPathForIdPage = "//div[@class='titulosubmenu']/a[text()[contains(.,'MANGO en el mundo')]]";
	
	@Override
	public String getName() {
		return "Franquicias en el mundo";
	}
	
	@Override
	public boolean isPageCorrectUntil(int maxSecondsWait, WebDriver driver) {
		driver.switchTo().frame(driver.findElement(By.xpath(XPathIdFrame)));
		return (isElementPresentUntil(driver, By.xpath(XPathForIdPage), maxSecondsWait));
	}
}
