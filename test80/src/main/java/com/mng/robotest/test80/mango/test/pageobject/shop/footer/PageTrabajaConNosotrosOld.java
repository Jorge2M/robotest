package com.mng.robotest.test80.mango.test.pageobject.shop.footer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageTrabajaConNosotrosOld extends PageObjTM implements PageFromFooter {
	
	final String XPathIdFrame = "//iframe[@id='bodyFrame']";
	final String XPathForIdPage = "//li[@class='first']/a[text()[contains(.,'Nuestro ADN')]]";
	
	public PageTrabajaConNosotrosOld(WebDriver driver) {
		super(driver);
	}
	
	@Override
	public String getName() {
		return "Ofertas y envío de currículums";
	}
	
	@Override
	public boolean isPageCorrectUntil(int maxSeconds) {
		driver.switchTo().frame(driver.findElement(By.xpath(XPathIdFrame)));
		return (state(Present, By.xpath(XPathForIdPage)).wait(maxSeconds).check());
	}
}
