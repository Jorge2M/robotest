package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.processout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;

public class PageProcessOutInputTrj extends PageObjTM {

	private final static String XPathFormularioTrj = "//div[@class[contains(.,'payment-section')]]";
	private final static String XPathInputNameCard = "//input[@id='card-name']";
	private final static String XPathInputTrj = "//input[@id='card-number']";
	private final static String XPathInputExpiracion = "//input[@id='card-expiry']";
	private final static String XPathInputCVC = "//input[@id='card-cvc']";
	private final static String XPahtButtonPago = "//a[@class[contains(.,'form-submit')]]";
	
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