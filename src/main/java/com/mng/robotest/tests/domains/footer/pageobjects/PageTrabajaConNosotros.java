package com.mng.robotest.tests.domains.footer.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageTrabajaConNosotros extends PageBase implements PageFromFooter {
	
	@Override
	public String getName() {
		return "Life at Mango";
	}
	
	@Override
	public boolean isPageCorrectUntil(int seconds) {
		return state(PRESENT, "//title[text()[contains(.,'MANGO Jobs')]]").wait(seconds).check();
	}
}
