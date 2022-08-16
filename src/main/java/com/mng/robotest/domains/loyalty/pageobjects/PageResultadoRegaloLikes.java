package com.mng.robotest.domains.loyalty.pageobjects;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageResultadoRegaloLikes extends PageBase {
	
	private static final String XPATH_DONE_ICON = "//img[@class='done-icon']";
	
	public boolean isEnvioOk(int maxSeconds) {
		return state(Visible, XPATH_DONE_ICON).wait(maxSeconds).check();
	}
	
}
