package com.mng.robotest.tests.domains.compra.payments.paytrail.pageobjects;

import org.openqa.selenium.support.ui.Select;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PagePaytrail1rst extends PageBase {
	
	private static final String XP_LIST_OF_PAYMENTS = "//ul[@id='paymentMethods']";
	private static final String XP_INPUT_ICONO_PAYTRAIL = "//input[@type='submit' and @name='brandName']";	
	private static final String XP_BUTTON_PAGO_DESKTOP = "//input[@class[contains(.,'paySubmit')] and @type='submit']";
	private static final String XP_BUTTON_CONTINUE_MOBIL = "//input[@type='submit' and @name[contains(.,'ebanking')]]";
	private static final String XP_SELECT_BANCOS = "//select[@id[contains(.,'ebanking')]]";
	
	private String getXPathEntradaPago(String nombrePago) {
		return (XP_LIST_OF_PAYMENTS + "//input[@value[contains(.,'" + nombrePago.toLowerCase() + "')] or @value[contains(.,'" + nombrePago + "')]]");
	}
	
	public boolean isPresentEntradaPago(String nombrePago) {
		String xpathPago = getXPathEntradaPago(nombrePago);
		return state(PRESENT, xpathPago).check();
	}
	
	public boolean isPresentButtonPago() {
		return state(PRESENT, XP_BUTTON_PAGO_DESKTOP).check();
	}

	public boolean isPresentSelectBancos() {
		return state(PRESENT, XP_SELECT_BANCOS).check();
	}
	
	public boolean isVisibleSelectBancosUntil(int seconds) {
		return state(VISIBLE, XP_SELECT_BANCOS).wait(seconds).check();
	}	
	
	public void clickButtonContinue() {
		if (channel.isDevice()) {
			click(XP_BUTTON_CONTINUE_MOBIL).exec();
		} else {
			click(XP_BUTTON_PAGO_DESKTOP).exec();
		}
	}
	
	public void selectBanco(String visibleText) {
		//En el caso de móvil hemos de seleccionar el icono del banco para visualizar el desplegable
		if (channel.isDevice() &&
			state(VISIBLE, XP_SELECT_BANCOS).check()) {
			clickIconoBanco();
		}
			
		new Select(getElement(XP_SELECT_BANCOS)).selectByVisibleText(visibleText);
	}

	public void clickIconoBanco() {
		click(XP_INPUT_ICONO_PAYTRAIL).exec();
	}
}