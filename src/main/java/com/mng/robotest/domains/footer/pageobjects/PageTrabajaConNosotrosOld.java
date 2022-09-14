package com.mng.robotest.domains.footer.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.transversal.PageBase;

public class PageTrabajaConNosotrosOld extends PageBase implements PageFromFooter {
	
	private static final String XPATH_ID_FRAME = "//iframe[@id='bodyFrame']";
	private static final String XPATH_FOR_IDPAGE = "//li[@class='first']/a[text()[contains(.,'Nuestro ADN')]]";
	
	@Override
	public String getName() {
		return "Ofertas y envío de currículums";
	}
	
	@Override
	public boolean isPageCorrectUntil(int seconds) {
		driver.switchTo().frame(getElement(XPATH_ID_FRAME));
		return state(Present, XPATH_FOR_IDPAGE).wait(seconds).check();
	}
}
