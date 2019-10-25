package com.mng.sapfiori.test.testcase.webobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public class PageReclassifProducts extends WebdrvWrapp {

	private final WebDriver driver;
	
	private final static String XPathPageHeader = "//h1[text()[contains(.,'Reclasificación de productos')]]";
	private final static String XPathInputCodEstadMerc = "//input[@id[contains(.,'inputCommodityCode')]]";
	private final static String XPathGrabarButton = "//button[@id[contains(.,'btnMassSave')]]//bdi";
	
	private PageReclassifProducts(WebDriver driver) {
		this.driver = driver;
	}
	
	public static PageReclassifProducts getNew(WebDriver driver) {
		return new PageReclassifProducts(driver);
	}
	
	public boolean checkIsPageUntil(int maxSeconds) {
		return (isElementVisibleUntil(driver, By.xpath(XPathPageHeader), maxSeconds));
	}
	
	public void writeInputCodEstadMerc(String newCodEstadMerc) {
		driver.findElement(By.xpath(XPathInputCodEstadMerc)).sendKeys(newCodEstadMerc);
	}
	
	public PageSelProdsToReclassify clickGrabarButton() throws Exception {
		//Hay problemas con el click sobre este botón, en ocasiones no tiene efecto
		clickAndWaitLoad(driver, By.xpath(XPathGrabarButton));
		if (!WebdrvWrapp.isElementInvisibleUntil(driver, By.xpath(XPathGrabarButton), 1)) {
			clickAndWaitLoad(driver, By.xpath(XPathGrabarButton));
		}
		return PageSelProdsToReclassify.getNew(driver);
	}
}
