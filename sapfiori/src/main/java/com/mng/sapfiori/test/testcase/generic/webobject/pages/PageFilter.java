package com.mng.sapfiori.test.testcase.generic.webobject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.test.testcase.webobject.iconsmenu.OptionMenu;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public abstract class PageFilter extends WebdrvWrapp {

	public final WebDriver driver;
	public final OptionMenu option;
	
	private final static String XPathIrButton = "//button[@id[contains(.,'btnGo')]]";
	
	protected PageFilter(OptionMenu option, WebDriver driver) {
		this.option = option;
		this.driver = driver;
	}
	
	protected String getXPathTitle() {
		return "//h1[starts-with(text(), '" + option.getTitlePage() + "')]";
	}
	
	public boolean isVisiblePage(int maxSeconds) {
		By byTitle = By.xpath(getXPathTitle());
		return WebdrvWrapp.isElementVisibleUntil(driver, byTitle, maxSeconds);
	}
	
	public void clickIrButton() throws Exception {
		WebdrvWrapp.waitForPageLoaded(driver);
		clickAndWaitLoad(driver, By.xpath(XPathIrButton));
	}
}
