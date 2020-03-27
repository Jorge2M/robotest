package com.mng.robotest.test80.mango.test.pageobject.shop.ficha;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecTotalLook {

	private final static String XPathTotalLook = "//div[@id='lookTotal']";
	
	public static boolean isVisible(WebDriver driver) {
		return (state(Visible, By.xpath(XPathTotalLook), driver).check());
	}
}
