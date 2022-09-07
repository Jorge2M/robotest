package com.mng.robotest.domains.footer.pageobjects;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageTrabajaConNosotros extends PageBase implements PageFromFooter {
	
	@Override
	public String getName() {
		return "Work Your Passion";
	}
	
	@Override
	public boolean isPageCorrectUntil(int maxSeconds) {
		return state(Present, "//title[contains(.,'" + getName() + "')]").wait(maxSeconds).check();
	}
}
