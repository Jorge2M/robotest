package com.mng.robotest.domains.ficha.pageobjects;

import org.openqa.selenium.By;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecTotalLook extends PageObjTM {

	private static final String XPATH_TOTAL_LOOK = "//div[@id='lookTotal']";
	
	public boolean isVisible() {
		return (state(Visible, By.xpath(XPATH_TOTAL_LOOK)).check());
	}
}
