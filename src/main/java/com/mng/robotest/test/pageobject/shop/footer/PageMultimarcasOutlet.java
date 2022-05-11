package com.mng.robotest.test.pageobject.shop.footer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageMultimarcasOutlet extends PageObjTM implements PageFromFooter {
	
	final String XPathForIdPage = "//title[text()[contains(.,'OUTLET')] or text()[contains(.,'Outlet')]]";
	
	public PageMultimarcasOutlet(WebDriver driver) {
		super(driver);
	}
	
	@Override
	public String getName() {
		return "Multimarcas Outlet";
	}
	
	@Override
	public boolean isPageCorrectUntil(int maxSeconds) {
		return (state(Present, By.xpath(XPathForIdPage), driver)
				.wait(maxSeconds).check());
	}
}
