package com.mng.robotest.test.pageobject.shop.footer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.domains.transversal.PageBase;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageFranquicias extends PageBase implements PageFromFooter {
	
	//final String XPathIdFrame = "//iframe[@id='bodyFrame']";
	final String XPathForIdPage = "//*[text()[contains(.,'Forma parte de nuestra historia')]]";
	
	public PageFranquicias(WebDriver driver) {
		super(driver);
	}
	
	@Override
	public String getName() {
		return "Franquicias en el mundo";
	}
	
	@Override
	public boolean isPageCorrectUntil(int maxSeconds) {
		//driver.switchTo().frame(driver.findElement(By.xpath(XPathIdFrame)));
		return (state(Present, By.xpath(XPathForIdPage)).wait(maxSeconds).check());
	}
}
