package com.mng.robotest.test80.mango.test.pageobject.shop.footer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageTrabajaConNosotros extends PageObjTM implements PageFromFooter {
	
	final String XPathForIdPage = "//div[@id='search-bar']";
	
	public PageTrabajaConNosotros(WebDriver driver) {
		super(driver);
	}
	
	@Override
	public String getName() {
		return "Diseña tu futuro en Mango";
	}
	
	@Override
	public boolean isPageCorrectUntil(int maxSeconds) {
		return (state(Present, By.xpath(XPathForIdPage)).wait(maxSeconds).check());
	}
}
