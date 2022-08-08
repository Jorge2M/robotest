package com.mng.robotest.test.pageobject.shop.footer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.domains.transversal.PageBase;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageMultimarcasShop extends PageBase implements PageFromFooter {
	
	final String XPathForIdPage = "//title[text()[contains(.,'MANGO')]]";
	
	public PageMultimarcasShop(WebDriver driver) {
		super(driver);
	}
	
	@Override
	public String getName() {
		return "Multimarcas Shop";
	}
	
	@Override
	public boolean isPageCorrectUntil(int maxSeconds) {
		return (state(Present, By.xpath(XPathForIdPage))
				.wait(maxSeconds).check());
	}
}
