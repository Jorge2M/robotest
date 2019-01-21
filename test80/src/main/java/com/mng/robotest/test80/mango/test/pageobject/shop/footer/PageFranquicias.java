package com.mng.robotest.test80.mango.test.pageobject.shop.footer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

public class PageFranquicias extends WebdrvWrapp implements PageFromFooter {
	
	final String XPathIdFrame = "//iframe[@id='bodyFrame']";
	final String XPathForIdPage = "//div[@class='titulosubmenu']/a[text()[contains(.,'MANGO en el mundo')]]";
	
	@Override
	public String getName() {
		return "Franquicias en el mundo";
	}
	
	@Override
	public boolean isPageCorrect(WebDriver driver) {
		driver.switchTo().frame(driver.findElement(By.xpath(XPathIdFrame)));
		return (isElementPresent(driver, By.xpath(XPathForIdPage)));
	}
}
