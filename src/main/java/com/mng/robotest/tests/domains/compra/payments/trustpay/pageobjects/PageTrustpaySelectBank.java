package com.mng.robotest.tests.domains.compra.payments.trustpay.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageTrustpaySelectBank extends PageBase {
	
	private static final String XP_LIST_OF_PAYMENTS = "//ul[@id='paymentMethods']";
	private static final String XP_INPUT_ICONO_TRUSTPAY = "//input[@type='submit' and @name='brandName']";
	private static final String XP_BUTTON_PAGO = "//input[@type='submit']";
	private static final String XP_SELECT_BANCOS = "//select[@id='trustPayBankList']";
	private static final String XP_BUTTON_PAY_DESKTOP = "//input[@class[contains(.,'paySubmittrustpay')]]";
	private static final String XP_BUTTON_CONTINUE_MOBIL = "//input[@type='submit' and @value='continue']";
	
	public String getXPathEntradaPago(String nombrePago) {
		if (channel.isDevice()) {
			return (XP_LIST_OF_PAYMENTS + "/li/input[@class[contains(.,'" + nombrePago.toLowerCase() + "')]]");
		}
		return (XP_LIST_OF_PAYMENTS + "/li[@data-variant[contains(.,'" + nombrePago.toLowerCase() + "')]]");
	}
	
	public boolean isPresentEntradaPago(String nombrePago) {
		String xpathPago = getXPathEntradaPago(nombrePago);
		return state(Present, xpathPago).check();
	}
	
	public boolean isPresentCabeceraStep(String nombrePago) {
		String xpathCab = getXPathEntradaPago(nombrePago);
		return state(Present, xpathCab).check();
	}
	
	public boolean isPresentButtonPago() {
		return state(Present, XP_BUTTON_PAGO).check();
	}
	
	public boolean isPresentSelectBancos() {
		return state(Present, XP_SELECT_BANCOS).check();
	}
	
	public void selectBankThatContains(List<String> strContains) {
		//En el caso de móvil para que aparezca el desplegable se ha de seleccionar el icono del banco
		if (channel.isDevice() &&
			!state(Visible, XP_SELECT_BANCOS).check()) {
			clickIconoBanco();
		}
		
	Select selectBank = new Select(getElement(XP_SELECT_BANCOS));
		List<WebElement> elements = selectBank.getOptions();
		WebElement elementFind = null;
		for (WebElement element : elements) {
			if (element.getAttribute("value")!=null &&
				stringContainsAnyValue(element.getText(), strContains)) {
				elementFind = element;
				break;
			}
		}
		
		if (elementFind!=null) {
			selectBank.selectByValue(elementFind.getAttribute("value"));
		}
	}
	
	private boolean stringContainsAnyValue(String str, List<String> listOfValues) {
		for (String strValue : listOfValues) {
			if (str.contains(strValue)) {
				return true;
			}
		}
		return false;
	}

	public void clickIconoBanco() {
		click(XP_INPUT_ICONO_TRUSTPAY).exec();
	}

	public void clickButtonToContinuePay() {
		if (channel.isDevice()) {
			click(XP_BUTTON_CONTINUE_MOBIL).exec();
		} else {
			click(XP_BUTTON_PAY_DESKTOP).exec();
		}
	}
}