package com.mng.robotest.test.pageobject.shop.checkout.giropay;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageGiropay1rst extends PageObjTM {
	
	private static final String XPATH_LIST_OF_PAYMENTS = "//ul[@id='paymentMethods']";
	private static final String XPATH_CABECERA_STEP = "//h2[@id[contains(.,'stageheader')]]";
	private static final String XPATH_BUTTON_PAGO_DESKTOP = "//input[@class[contains(.,'paySubmit')] and @type='submit']";
	private static final String XPATH_BUTTON_CONTINUE_MOBIL = "//input[@type='submit' and @id='mainSubmit']";
	private static final String XPATH_ICONO_GIROPAY_MOBIL = XPATH_LIST_OF_PAYMENTS + "//input[@class[contains(.,'giropay')]]";
	private static final String XPATH_ICONO_GIROPAY_DESKTOP = XPATH_LIST_OF_PAYMENTS + "/li[@data-variant[contains(.,'giropay')]]";
	
	private final Channel channel;
	
	public PageGiropay1rst(Channel channel) {
		this.channel = channel;
	}
	
	private String getXPathIconoGiropay() {
		if (channel.isDevice()) {
			return XPATH_ICONO_GIROPAY_MOBIL;
		}
		return XPATH_ICONO_GIROPAY_DESKTOP;
	}
	
	private String getXPathRowListWithBank(String bank) {
		return ("//ul[@id='giropaysuggestionlist']/li[@data-bankname[contains(.,'" + bank + "')]]");
	}
	
	public boolean isPresentIconoGiropay() {
		String xpathPago = getXPathIconoGiropay();
		return (state(Present, By.xpath(xpathPago)).check());
	}
	
	public boolean isPresentCabeceraStep() {
		return (state(Present, By.xpath(XPATH_CABECERA_STEP)).check());
	}
	
	public boolean isPresentButtonPagoDesktopUntil(int maxSeconds) {
		return (state(Present, By.xpath(XPATH_BUTTON_PAGO_DESKTOP)).wait(maxSeconds).check());
	}

	public void clickIconoGiropay(WebDriver driver) {
		String xpathPago = getXPathIconoGiropay();
		click(By.xpath(xpathPago)).exec();
	}

	public void clickButtonContinuePay() {
		if (channel.isDevice()) {
			clickButtonContinueMobil();
		} else {
			clickButtonPagoDesktop();
		}
	}

	public void clickButtonPagoDesktop() {
		click(By.xpath(XPATH_BUTTON_PAGO_DESKTOP)).exec();
	}

	public void clickButtonContinueMobil() {
		click(By.xpath(XPATH_BUTTON_CONTINUE_MOBIL)).exec();
	}
	
}
