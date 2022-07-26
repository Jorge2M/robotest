package com.mng.robotest.domains.ficha.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecTotalLook {

	private static final String XPATH_TOTAL_LOOK = "//div[@id='lookTotal']";
	
	public static boolean isVisible(WebDriver driver) {
		return (state(Visible, By.xpath(XPATH_TOTAL_LOOK), driver).check());
	}
}
