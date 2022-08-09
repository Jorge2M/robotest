package com.mng.robotest.test.pageobject.shop.footer;

import org.openqa.selenium.By;
import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageTrabajaConNosotrosOld extends PageBase implements PageFromFooter {
	
	private static final String XPATH_ID_FRAME = "//iframe[@id='bodyFrame']";
	private static final String XPATH_FOR_IDPAGE = "//li[@class='first']/a[text()[contains(.,'Nuestro ADN')]]";
	
	@Override
	public String getName() {
		return "Ofertas y envío de currículums";
	}
	
	@Override
	public boolean isPageCorrectUntil(int maxSeconds) {
		driver.switchTo().frame(driver.findElement(By.xpath(XPATH_ID_FRAME)));
		return (state(Present, By.xpath(XPATH_FOR_IDPAGE)).wait(maxSeconds).check());
	}
}
