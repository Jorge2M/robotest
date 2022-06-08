package com.mng.robotest.test.pageobject.shop.footer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageNotasPrensa extends PageObjTM implements PageFromFooter {
	
	final String XPathForIdPage = "//*[text()[contains(.,'NOTAS DE PRENSA')] or text()[contains(.,'Notas de prensa')] or text()[contains(.,'PRESS RELEASES')]]";
	
	public PageNotasPrensa(WebDriver driver) {
		super(driver);
	}
	
	@Override
	public String getName() {
		return "Noticias en prensa";
	}
	
	@Override
	public boolean isPageCorrectUntil(int maxSeconds) {
		return (state(Present, By.xpath(XPathForIdPage))
				.wait(maxSeconds).check());
	}
}