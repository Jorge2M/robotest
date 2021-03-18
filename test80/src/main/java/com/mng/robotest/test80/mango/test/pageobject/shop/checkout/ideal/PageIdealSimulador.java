package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.ideal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageIdealSimulador {

	static String XPathContinueButton = "//input[@type='submit' and @class='btnLink']";

	public static boolean isPage(WebDriver driver) {
		return (state(Visible, By.xpath("//h3[text()[contains(.,'iDEAL Issuer Simulation')]]"), driver).check());
	}

	public static void clickButtonContinue(WebDriver driver) {
		click(By.xpath(XPathContinueButton), driver).exec();
	}
}
