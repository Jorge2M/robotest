package com.mng.robotest.tests.domains.footer.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageTrabajaConNosotros extends PageBase implements PageFromFooter {
	
	@Override
	public String getName() {
		return "Work Your Passion";
	}
	
	@Override
	public boolean isPageCorrectUntil(int seconds) {
		return state(PRESENT, "//title[contains(.,'" + getName() + "')]").wait(seconds).check();
	}
}
