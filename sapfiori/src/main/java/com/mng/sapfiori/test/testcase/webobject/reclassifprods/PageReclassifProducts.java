package com.mng.sapfiori.test.testcase.webobject.reclassifprods;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.test.testcase.generic.webobject.utils.PageObject;

public class PageReclassifProducts extends PageObject {
	
	private final static String XPathPageHeader = "//h1[text()[contains(.,'Reclasificación de productos')]]";
	private final static String XPathInputCodEstadMerc = "//input[@id[contains(.,'inputCommodityCode')]]";
	private final static String XPathGrabarButton = "//button[@id[contains(.,'btnMassSave')]]//bdi";
	
	private PageReclassifProducts(WebDriver driver) {
		super(driver);
	}
	
	public static PageReclassifProducts getNew(WebDriver driver) {
		return new PageReclassifProducts(driver);
	}
	
	public boolean checkIsPageUntil(int maxSeconds) {
		return (isElementVisibleUntil(driver, By.xpath(XPathPageHeader), maxSeconds));
	}
	
	public void writeInputCodEstadMerc(String newCodEstadMerc) throws Exception {
		waitForPageFinished();
		driver.findElement(By.xpath(XPathInputCodEstadMerc)).sendKeys(newCodEstadMerc);
	}
	
	public PageSelProdsToReclassify clickGrabarButton() throws Exception {
		clickAndWaitLoad(driver, By.xpath(XPathGrabarButton));
		if (!isElementInvisibleUntil(driver, By.xpath(XPathGrabarButton), 3)) {
			waitForPageFinished();
			clickAndWaitLoad(driver, By.xpath(XPathGrabarButton));
		}
		return PageSelProdsToReclassify.getNew(driver);
	}
}
