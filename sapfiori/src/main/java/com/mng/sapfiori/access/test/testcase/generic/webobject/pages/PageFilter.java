package com.mng.sapfiori.access.test.testcase.generic.webobject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.access.test.testcase.generic.webobject.utils.PageObject;
import com.mng.sapfiori.access.test.testcase.webobject.iconsmenu.OptionMenu;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

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
		return (state(Visible, byTitle).wait(maxSeconds).check());
	}
	
	public void clickIrButton() {
		waitForPageFinished();
		click(By.xpath(XPathIrButton)).exec();
	}
}
