package com.mng.robotest.test.pageobject.shop.footer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.domains.transversal.PageBase;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageEmpresa extends PageBase implements PageFromFooter {
	
	final String XPathForIdPageNew = "//img[@src[contains(.,'empresa-mango')]]";
	
	public PageEmpresa(WebDriver driver) {
		super(driver);
	}
	
	@Override
	public String getName() {
		return "Datos de empresa";
	}
	
	@Override
	public boolean isPageCorrectUntil(int maxSeconds) {
		return (state(Present, By.xpath(XPathForIdPageNew)).wait(maxSeconds).check());
	}
}
