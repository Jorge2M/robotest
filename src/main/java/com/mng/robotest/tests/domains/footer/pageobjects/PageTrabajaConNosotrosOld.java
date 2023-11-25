package com.mng.robotest.tests.domains.footer.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageTrabajaConNosotrosOld extends PageBase implements PageFromFooter {
	
	private static final String XP_ID_FRAME = "//iframe[@id='bodyFrame']";
	private static final String XP_FOR_IDPAGE = "//li[@class='first']/a[text()[contains(.,'Nuestro ADN')]]";
	
	@Override
	public String getName() {
		return "Ofertas y envío de currículums";
	}
	
	@Override
	public boolean isPageCorrectUntil(int seconds) {
		driver.switchTo().frame(getElement(XP_ID_FRAME));
		return state(Present, XP_FOR_IDPAGE).wait(seconds).check();
	}
}
