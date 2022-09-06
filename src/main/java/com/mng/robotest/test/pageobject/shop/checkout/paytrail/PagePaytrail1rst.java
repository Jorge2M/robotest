package com.mng.robotest.test.pageobject.shop.checkout.paytrail;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PagePaytrail1rst extends PageBase {
	
	private static final String XPATH_LIST_OF_PAYMENTS = "//ul[@id='paymentMethods']";
	private static final String XPATH_INPUT_ICONO_PAYTRAIL = "//input[@type='submit' and @name='brandName']";	
	private static final String XPATH_BUTTON_PAGO_DESKTOP = "//input[@class[contains(.,'paySubmit')] and @type='submit']";
	private static final String XPATH_BUTTON_CONTINUE_MOBIL = "//input[@type='submit' and @name[contains(.,'ebanking')]]";
	private static final String XPATH_SELECT_BANCOS = "//select[@id[contains(.,'ebanking')]]";
	
	private String getXPathEntradaPago(String nombrePago) {
		return (XPATH_LIST_OF_PAYMENTS + "//input[@value[contains(.,'" + nombrePago.toLowerCase() + "')] or @value[contains(.,'" + nombrePago + "')]]");
	}
	
	public boolean isPresentEntradaPago(String nombrePago) {
		String xpathPago = getXPathEntradaPago(nombrePago);
		return state(Present, xpathPago).check();
	}
	
	public boolean isPresentButtonPago() {
		return state(Present, XPATH_BUTTON_PAGO_DESKTOP).check();
	}

	public boolean isPresentSelectBancos() {
		return state(Present, XPATH_SELECT_BANCOS).check();
	}
	
	public boolean isVisibleSelectBancosUntil(int maxSeconds) {
		return state(Visible, XPATH_SELECT_BANCOS).wait(maxSeconds).check();
	}	
	
	public void clickButtonContinue() {
		if (channel.isDevice()) {
			click(XPATH_BUTTON_CONTINUE_MOBIL).exec();
		} else {
			click(XPATH_BUTTON_PAGO_DESKTOP).exec();
		}
	}
	
	public void selectBanco(String visibleText) {
		//En el caso de móvil hemos de seleccionar el icono del banco para visualizar el desplegable
		if (channel.isDevice()) {
			if (state(Visible, XPATH_SELECT_BANCOS).check()) {
				clickIconoBanco();
			}
		}
			
		new Select(driver.findElement(By.xpath(XPATH_SELECT_BANCOS))).selectByVisibleText(visibleText);
	}

	public void clickIconoBanco() {
		click(XPATH_INPUT_ICONO_PAYTRAIL).exec();
	}
}