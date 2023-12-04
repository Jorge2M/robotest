package com.mng.robotest.tests.domains.compra.payments.sepa.pageobjects;

import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageSepa1rst extends PageBase {
	
	private static final String XP_LIST_OF_PAYMENTS = "//ul[@id='paymentMethods']";
	private static final String XP_CABECERA_STEP = "//h2[@id[contains(.,'stageheader')]]";
	private static final String XP_BUTTON_PAGO_DESKTOP = "//input[@class[contains(.,'paySubmit')] and @type='submit']";
	private static final String XP_BUTTON_CONTINUE_MOBIL = "//input[@type='submit' and @id='mainSubmit']";
	private static final String XP_INPUT_TITULAR = "//input[@id[contains(.,'ownerName')]]";
	private static final String XP_INPUT_CUENTA = "//input[@id[contains(.,'bankAccountNumber')]]";
	private static final String XP_RADIO_ACEPTO = "//input[@id[contains(.,'acceptDirectDebit')]]";
	private static final String XP_ICONO_SEPA_MOBIL = XP_LIST_OF_PAYMENTS + "//input[@class[contains(.,'sepa')]]"; 
	private static final String XP_ICONO_SEPA_DESKTOP = XP_LIST_OF_PAYMENTS + "/li[@data-variant[contains(.,'sepa')]]";
	
	private String getXPathIconoSepa(Channel channel) {
		if (channel.isDevice()) {
			return XP_ICONO_SEPA_MOBIL;
		}
		return XP_ICONO_SEPA_DESKTOP;
	}
	
	public boolean isPresentIconoSepa(Channel channel) {
		String xpathPago = getXPathIconoSepa(channel);
		return state(PRESENT, xpathPago).check();
	}

	public void clickIconoSepa(Channel channel) {
		click(getXPathIconoSepa(channel)).exec();
	}

	public boolean isPresentCabeceraStep() {
		return state(PRESENT, XP_CABECERA_STEP).check();
	}
	
	public boolean isPresentButtonPagoDesktop() {
		return state(PRESENT, XP_BUTTON_PAGO_DESKTOP).check();
	}

	public void clickButtonContinuePago(Channel channel) {
		if (channel.isDevice()) {
			click(XP_BUTTON_CONTINUE_MOBIL).exec();
		} else {
			click(XP_BUTTON_PAGO_DESKTOP).exec();
		}
	}
	
	public boolean isPresentInputTitular() { 
		return state(PRESENT, XP_INPUT_TITULAR).check();
	}

	public boolean isPresentInputCuenta() { 
		return (state(PRESENT, By.xpath(XP_INPUT_CUENTA)).check());
	}
	
	public void inputTitular(String titular) {
		driver.findElement(By.xpath(XP_INPUT_TITULAR)).sendKeys(titular);
	}
	
	public void inputCuenta(String cuenta) {
		driver.findElement(By.xpath(XP_INPUT_CUENTA)).sendKeys(cuenta);
	}
	
	public void clickAcepto() {
		driver.findElement(By.xpath(XP_RADIO_ACEPTO)).click();
	}
}
