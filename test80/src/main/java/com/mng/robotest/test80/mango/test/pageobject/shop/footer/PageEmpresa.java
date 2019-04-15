package com.mng.robotest.test80.mango.test.pageobject.shop.footer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

public class PageEmpresa extends WebdrvWrapp implements PageFromFooter {
	
	//TODO eliminate references to Old Page when this disappear in production
	final String XPathIdFrameOld = "//iframe[@id='bodyFrame']";
	final String XPathForIdPageNew = "//img[@src[contains(.,'edits_site_empresa')]]";
	final String XPathForIdPageOld = "//a[@class='tituloempresalink' and text()[contains(.,'La Empresa')]]";
	
	@Override
	public String getName() {
		return "Datos de empresa";
	}
	
	@Override
	public boolean isPageCorrect(WebDriver driver) {
		if (isNewPage(driver)) {
			return true;
		}
		driver.switchTo().frame(driver.findElement(By.xpath(XPathIdFrameOld)));
		return (isElementPresent(driver, By.xpath(XPathForIdPageOld)));
	}
	
	public boolean isNewPage(WebDriver driver) {
		return (isElementPresent(driver, By.xpath(XPathForIdPageNew)));
	}
}
