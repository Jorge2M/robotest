package com.mng.robotest.test.pageobject.shop.checkout.processout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.test.beans.Pago;

public class PageProcessOutInputTrj extends PageObjTM {

	private static final String XPathFormularioTrj = "//div[@class[contains(.,'payment-section')]]";
	private static final String XPathInputNameCard = "//input[@id='card-name']";
	private static final String XPathInputTrj = "//input[@id='card-number']";
	private static final String XPathInputExpiracion = "//input[@id='card-expiry']";
	private static final String XPathInputCVC = "//input[@id='card-cvc']";
	private static final String XPahtButtonPago = "//a[@class[contains(.,'form-submit')]]";
	
	public PageProcessOutInputTrj(WebDriver driver) {
		super(driver);
	}
	
	public boolean checkIsPage() {
		return state(State.Visible, By.xpath(XPathFormularioTrj)).check();
	}
	
	public void inputDataTrj(Pago pago) {
		driver.findElement(By.xpath(XPathInputNameCard)).sendKeys("Jorge");
		driver.findElement(By.xpath(XPathInputTrj)).sendKeys(pago.getNumtarj());
		driver.findElement(By.xpath(XPathInputExpiracion)).sendKeys(pago.getMescad() + pago.getAnycad().substring(2,4));
		driver.findElement(By.xpath(XPathInputCVC)).sendKeys(pago.getCvc());
	}
	
	public boolean isPresentButtonPago() {
		return state(State.Visible, By.xpath(XPahtButtonPago)).check();
	}
	
	public void clickButtonPay() {
		click(By.xpath(XPahtButtonPago)).exec();
	}
}
