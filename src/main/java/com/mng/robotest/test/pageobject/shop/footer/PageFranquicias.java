package com.mng.robotest.test.pageobject.shop.footer;

import org.openqa.selenium.By;
import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageFranquicias extends PageBase implements PageFromFooter {
	
	private static final String XPATH_FOR_ID_PAGE = "//*[text()[contains(.,'Forma parte de nuestra historia')]]";
	
	@Override
	public String getName() {
		return "Franquicias en el mundo";
	}
	
	@Override
	public boolean isPageCorrectUntil(int maxSeconds) {
		//driver.switchTo().frame(driver.findElement(By.xpath(XPathIdFrame)));
		return (state(Present, By.xpath(XPATH_FOR_ID_PAGE)).wait(maxSeconds).check());
	}
}
