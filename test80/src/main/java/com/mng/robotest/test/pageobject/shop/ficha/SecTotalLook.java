package com.mng.robotest.test.pageobject.shop.ficha;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecTotalLook {

	private final static String XPathTotalLook = "//div[@id='lookTotal']";
	
	public static boolean isVisible(WebDriver driver) {
		return (state(Visible, By.xpath(XPathTotalLook), driver).check());
	}
}