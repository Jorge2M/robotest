package com.mng.robotest.tests.domains.compra.payments.billpay.pageobjects;

import org.openqa.selenium.support.ui.Select;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecBillpay extends PageBase {
	
	private static final String XP_BLOCK_BILLPAY_DESKTOP = "//div[@class[contains(.,'billpayFormulario')]]";
	private static final String XP_BLOCK_RECHNUNG_MOBIL = "//div[@class[contains(.,'billpayinvoice')] and @class[contains(.,'show')]]";
	private static final String XP_BLOCK_LAST_SCHRIFT_MOBIL = "//div[@class[contains(.,'billpaydirectdebit')] and @class[contains(.,'show')]]";
	private static final String XP_SELECT_BIRTHDAY = "//select[@id[contains(.,'birthDay')]]";
	private static final String XP_SELECT_BIRTH_MONTH = "//select[@id[contains(.,'birthMonth')]]";
	private static final String XP_SELECT_BIRTH_YEAR = "//select[@id[contains(.,'birthYear')]]";
	private static final String XP_INPUT_TITULAR = "//input[@id[contains(.,'accountHolderName')]]";
	private static final String XP_INPUT_IBAN = "//input[@id[contains(.,':iban')] or @id[contains(.,':billpay_iban')]]";
	private static final String XP_INPUT_BIC = "//input[@id[contains(.,':bic')] or @id[contains(.,':billpay_bic')]]";
	private static final String XP_RADIO_ACEPTO_MOBIL = "//div[@class[contains(.,'contenidoTarjetaBillpay')]]//div[@class[contains(.,'custom-check')]]"; 
	private static final String XP_RADIO_ACEPTO_DESKTOP = "//div[@class='legalText']/input[@type='checkbox']";			
   
	public String getXPath_radioAcepto() {
		if (channel.isDevice()) {
			return XP_RADIO_ACEPTO_MOBIL;
		}
		return XP_RADIO_ACEPTO_DESKTOP;
	}

	public boolean isVisibleUntil(int seconds) {
		if (channel.isDevice()) {
			String xpath = XP_BLOCK_RECHNUNG_MOBIL + " | " + XP_BLOCK_LAST_SCHRIFT_MOBIL;
			return state(Visible, xpath).wait(seconds).check();
		}
		return state(Visible, XP_BLOCK_BILLPAY_DESKTOP).wait(seconds).check();
	}

	/**
	 * Informa la fecha de nacimiento en los 3 desplegables de Billpay
	 * @param datNac fecha en formato DD-MM-YYYY
	 */
	public void putBirthday(String datNac) {
		String[] valuesDate = datNac.split("-");
		int dia = Integer.parseInt(valuesDate[0]);
		int mes = Integer.parseInt(valuesDate[1]);
		int any = Integer.parseInt(valuesDate[2]);
		new Select(getElement(XP_SELECT_BIRTHDAY)).selectByValue(String.valueOf(dia));
		new Select(getElement(XP_SELECT_BIRTH_MONTH)).selectByValue(String.valueOf(mes));
		new Select(getElement(XP_SELECT_BIRTH_YEAR)).selectByValue(String.valueOf(any));
	}
	
	/**
	 * Informa la fecha de nacimiento en los 3 desplegables de Billpay
	 * @param datNac fecha en formato DD-MM-YYYY
	 */
	public void clickAcepto() {
		getElement(getXPath_radioAcepto()).click();
	}
	
	public boolean isPresentSelectBirthDay() {
		return state(Present, XP_SELECT_BIRTHDAY).check();
	}
	
	public boolean isPresentSelectBirthMonth() {
		return state(Present, XP_SELECT_BIRTH_MONTH).check();
	}
	
	public boolean isPresentSelectBirthBirthYear() {
		return state(Present, XP_SELECT_BIRTH_YEAR).check();
	}

	public boolean isPresentRadioAcepto() {
		String xpath = getXPath_radioAcepto();
		return state(Present, xpath).check();
	}

	public boolean isPresentInputTitular() {
		return state(Present, XP_INPUT_TITULAR).check();
	}

	public boolean isPresentInputIBAN() {
		return state(Present, XP_INPUT_IBAN).check();
	}

	public boolean isPresentInputBIC() {
		return state(Present, XP_INPUT_BIC).check();
	}

	public void sendDataInputTitular(String titular) {
		getElement(XP_INPUT_TITULAR).sendKeys(titular);	
	}
	
	public void sendDataInputIBAN(String iban) {
		getElement(XP_INPUT_IBAN).sendKeys(iban);
	}
	
	public void sendDataInputBIC(String bic) {
		getElement(XP_INPUT_BIC).sendKeys(bic);
	}
}
