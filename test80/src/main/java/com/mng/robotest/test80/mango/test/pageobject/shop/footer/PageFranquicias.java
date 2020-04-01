package com.mng.robotest.test80.mango.test.pageobject.shop.footer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageFranquicias extends PageObjTM implements PageFromFooter {
	
	final String XPathIdFrame = "//iframe[@id='bodyFrame']";
	final String XPathForIdPage = "//div[@class='titulosubmenu']/a[text()[contains(.,'MANGO en el mundo')]]";
	
	public PageFranquicias(WebDriver driver) {
		super(driver);
	}
	
	@Override
	public String getName() {
		return "Franquicias en el mundo";
	}
	
	@Override
	public boolean isPageCorrectUntil(int maxSeconds) {
		driver.switchTo().frame(driver.findElement(By.xpath(XPathIdFrame)));
		return (state(Present, By.xpath(XPathForIdPage)).wait(maxSeconds).check());
	}
}
