package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.postfinance;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PagePostfCodSeg extends PageObjTM {

	private final static String XPathAceptarButton = "//form/input[@id='btn_Accept']";
	private final static String XPathInputCodSeg = "//input[@id='postfinanceCardId']";
	private final static String XPathButtonWeiter = "//button[@class='efinance-button' and text()[contains(.,'Weiter')]]";
	
	public PagePostfCodSeg(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * @return si se trata de la pasarela de test
	 * Para CI y PRE la pasarela de pago es diferente a la de PRO
	 *   PRE/CI-Postfinance Card-> https://e-payment.postfinance.ch/ncol/test/orderstandard.asp
	 *   PRO-PostFinance Card ->   https://epayment.postfinance.ch/pfcd/authentication/arh/cardIdForm
	 *   PRE-Postfinance -> https://e-payment.postfinance.ch/ncol/test/orderstandard.asp
	 *   PRO-PostFinance -> https://epayment.postfinance.ch/pfef/authentication/v2
	 */ 
	public boolean isPasarelaTest() {
		return (driver.getCurrentUrl().contains("test"));
	}
	
	/**
	 * @return si se trata de la pasarela de Postfinance correcta de test (hay de 2 tipos "e-finance" y "Card")
	 */
	public boolean isPasarelaPostfinanceTest(String metodoPago) {
		boolean isPasarela = false;
		if (metodoPago.toUpperCase().contains("E-FINANCE") && driver.getTitle().contains("e-finance")) {
			isPasarela = true;
		}
		if (metodoPago.toUpperCase().contains("CARD") && driver.getTitle().contains("Card")) {
			isPasarela = true;
		}
		return isPasarela;
	}
	
	public boolean isPasarelaPostfinanceProUntil(int maxSecondsToWait) {
		return (titleContainsUntil(driver, "E-Payment", maxSecondsToWait));
		//return (driver.getTitle().contains("E-Payment"));
	}
	
	public boolean isPresentButtonAceptar() {
		return (state(Present, By.xpath(XPathAceptarButton)).check());
	}

	public void clickAceptarButton() {
		click(By.xpath(XPathAceptarButton), driver).exec();
	}
	
	public boolean isPresentInputCodSeg() {
		return (state(Present, By.xpath(XPathInputCodSeg)).check());
	}
	
	public boolean isPresentButtonWeiter() {
		return (state(Present, By.xpath(XPathButtonWeiter)).check());
	}
	
	public void inputCodigoSeguridad(String codigoSeg) {
		driver.findElement(By.xpath(XPathInputCodSeg)).sendKeys(codigoSeg);
	}

}