package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.klarna;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

public class ModalInputPersonnumberKlarna extends PageObjTM {

	private final static String XPathInputPersonnumber = "//input[@id='invoice_kp-purchase-approval-form-national-identification-number']";
	private final static String XPathInputFechaNacimiento = "//input[@id='invoice_kp-purchase-approval-form-date-of-birth']";
	private final static String XPathButtonConf = "//button[@id='invoice_kp-purchase-approval-form-continue-button']";
	private final static String XpathSecondButtonConf = "//div[@id='identification-dialog__footer-button-wrapper']//button";
	
	private String getXPathInputPersonNumber() {
		return XPathInputPersonnumber + " | " + XPathInputFechaNacimiento;
	}
	
	public ModalInputPersonnumberKlarna(WebDriver driver) {
		super(driver);
	}
	
	public boolean isModal(int maxSeconds) {
		String xpath = getXPathInputPersonNumber();
		return state(State.Visible, By.xpath(xpath)).wait(maxSeconds).check();
	}
	
	public void inputPersonNumber(String personnumber) {
		String xpath = getXPathInputPersonNumber();
		driver.findElement(By.xpath(xpath)).sendKeys(personnumber);
	}
	
	public void clickButtonConf() {
		click(By.xpath(XPathButtonConf)).exec();
		if (state(State.Visible, By.xpath(XpathSecondButtonConf)).wait(2).check()) {
			click(By.xpath(XpathSecondButtonConf)).exec();
		}
	}
}
