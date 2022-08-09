package com.mng.robotest.test.pageobject.shop.footer;

import org.openqa.selenium.By;
import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageMultimarcasShop extends PageBase implements PageFromFooter {
	
	private static final String XPATH_FOR_IDPAGE = "//title[text()[contains(.,'MANGO')]]";
	
	@Override
	public String getName() {
		return "Multimarcas Shop";
	}
	
	@Override
	public boolean isPageCorrectUntil(int maxSeconds) {
		return (state(Present, By.xpath(XPATH_FOR_IDPAGE))
				.wait(maxSeconds).check());
	}
}
