package com.mng.sapfiori.test.testcase.generic.webobject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.test.testcase.generic.webobject.utils.PageObject;
import com.mng.sapfiori.test.testcase.webobject.iconsmenu.OptionMenu;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public abstract class PageFilter extends PageObject {

	public final OptionMenu option;
	
	private final static String XPathIrButton = "//button[@id[contains(.,'btnGo')]]";
	
	protected PageFilter(OptionMenu option, WebDriver driver) {
		super(driver);
		this.option = option;
	}
	
	protected String getXPathTitle() {
		return "//h1[starts-with(text(), '" + option.getTitlePage() + "')]";
	}
	
	public boolean isVisiblePage(int maxSeconds) {
		By byTitle = By.xpath(getXPathTitle());
		return WebdrvWrapp.isElementVisibleUntil(driver, byTitle, maxSeconds);
	}
	
	public void clickIrButton() throws Exception {
		waitForPageFinished();
		clickAndWaitLoad(driver, By.xpath(XPathIrButton));
	}
}
