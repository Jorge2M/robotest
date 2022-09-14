package com.mng.robotest.domains.footer.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.transversal.PageBase;

public class PageTrabajaConNosotros extends PageBase implements PageFromFooter {
	
	@Override
	public String getName() {
		return "Work Your Passion";
	}
	
	@Override
	public boolean isPageCorrectUntil(int seconds) {
		return state(Present, "//title[contains(.,'" + getName() + "')]").wait(seconds).check();
	}
}
