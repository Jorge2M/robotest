package com.mng.robotest.test.pageobject.shop.checkout.klarna;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.domains.transversal.PageBase;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

public class ModalConfUserDataKlarna extends PageBase {

	private static final String XPathButtonConfirmation = "//div[@id[contains(.,'footer-button-wrapper')]]//button";
	
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
