package com.mng.robotest.domains.ficha.pageobjects;


import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.base.PageBase;

public class SecTotalLook extends PageBase {

	private static final String XPATH_TOTAL_LOOK = "//div[@id='lookTotal']";
	
	public boolean isVisible() {
		return state(Visible, XPATH_TOTAL_LOOK).check();
	}
}
