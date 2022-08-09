package com.mng.robotest.test.pageobject.shop.checkout.dotpay;

import org.openqa.selenium.By;
import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageDotpay1rst extends PageBase {
	
	private static final String XPATH_LIST_OF_PAYMENTS = "//ul[@id='paymentMethods']";
	private static final String XPATH_INPUT_ICONO_DOTPAY = "//input[@type='submit' and @name='brandName']";
	private static final String XPATH_BUTTON_PAGO = "//input[@name='pay' and @type='submit']";
	
	private String getXPathEntradaPago(String nombrePago) {
		if (channel.isDevice()) {
			return (XPATH_LIST_OF_PAYMENTS + "/li/input[@class[contains(.,'" + nombrePago.toLowerCase() + "')]]");
		}
		return (XPATH_LIST_OF_PAYMENTS + "/li[@data-variant[contains(.,'" + nombrePago.toLowerCase() + "')]]");
	}
	
	public boolean isPresentEntradaPago(String nombrePago) {
		String xpathPago = getXPathEntradaPago(nombrePago);
		return (state(Present, By.xpath(xpathPago)).check());
	}
	
	public boolean isPresentCabeceraStep(String nombrePago) {
		String xpathCab = getXPathEntradaPago(nombrePago);
		return state(Present, By.xpath(xpathCab)).check();
	}

	public boolean isPresentButtonPago() {
		return (state(Present, By.xpath(XPATH_BUTTON_PAGO)).check());
	}

	public void clickToPay() {
		if (channel.isDevice()) {
			click(By.xpath(XPATH_INPUT_ICONO_DOTPAY)).exec();
		} else {
			click(By.xpath(XPATH_BUTTON_PAGO)).exec();
		}
	}
}
