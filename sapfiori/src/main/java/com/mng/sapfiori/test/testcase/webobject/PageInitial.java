package com.mng.sapfiori.test.testcase.webobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public class PageInitial extends WebdrvWrapp {
	
	private final WebDriver driver;
	
	private final static String XPathLabelInitialPageSpanish = "//h1[text()='Página inicial']";
	private final static String TagNameOption = "@TagNameOption";
	private final static String XPathOptionWithTag = "//div[@aria-label[contains(.,'" + TagNameOption + "')]]";
	
	private PageInitial(WebDriver driver) {
		this.driver = driver;
	}
	
	public static PageInitial getNew(WebDriver driver) {
		return new PageInitial(driver);
	}
	
	private String getXPathOption(String title) {
		return XPathOptionWithTag.replace(TagNameOption, title);
	}
	
	public boolean checkIsInitialPageSpanish(int maxSeconds) {
		return (
			WebdrvWrapp.isElementVisibleUntil(driver, By.xpath(XPathLabelInitialPageSpanish), maxSeconds));
	}
	
	public PageSelProdsToReclassify clickClasificarProductos() throws Exception {
		String title = PageSelProdsToReclassify.option.getTitleIcon();
		clickOption(title);
		return PageSelProdsToReclassify.getNew(driver);
	}
	
	private void clickOption(String title) throws Exception {
		String xpath = getXPathOption(title);
		WebdrvWrapp.clickAndWaitLoad(driver, By.xpath(xpath));
	}
}
