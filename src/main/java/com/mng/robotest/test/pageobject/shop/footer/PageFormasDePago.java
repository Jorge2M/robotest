package com.mng.robotest.test.pageobject.shop.footer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.domains.transversal.PageBase;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageFormasDePago extends PageBase implements PageFromFooter {
	
	final String XPathForIdPage = "//*[text()[contains(.,'Métodos de pago')]]";
	
	public PageFormasDePago(WebDriver driver) {
		super(driver);
	}
	
	@Override
	public String getName() {
		return "Formas de pago";
	}
	
	@Override
	public boolean isPageCorrectUntil(int maxSeconds) {
		return (state(Present, By.xpath(XPathForIdPage)).wait(maxSeconds).check());
	}
}
