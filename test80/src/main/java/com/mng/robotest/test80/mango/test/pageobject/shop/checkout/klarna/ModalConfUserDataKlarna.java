package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.klarna;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

public class ModalConfUserDataKlarna extends PageObjTM {

	private final static String XPathButtonConfirmation = "//div[@id[contains(.,'footer-button-wrapper')]]//button";
	
	public ModalConfUserDataKlarna(WebDriver driver) {
		super(driver);
	}
	
	public boolean isModal(int maxSeconds) {
		return state(State.Visible, By.xpath(XPathButtonConfirmation)).wait(maxSeconds).check();
	}
	
	public void clickButtonConfirmation() {
		click(By.xpath(XPathButtonConfirmation)).exec();
	}
}
