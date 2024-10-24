package com.mng.robotest.tests.domains.compra.pageobjects.pci;

import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.Select;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecTarjetaPciInIframe extends PageBase implements SecTarjetaPci {
	
	private static final String XP_IFRAME = "//iframe[@title='credit_card_form']";
	private static final String XP_BLOCK = "//div[@id='root']";
	private static final String XP_SAVE_CARD_CHECKBOX_DESKTOP = "//input[@name[contains(.,'guardarDatosIntegrado')]]";
	private static final String XP_SAVE_CARD_CHECKBOX_MOBIL = "//div[@data-custom-checkbox-id='button-check']";
	private static final String XP_INPUT_NUMBER = XP_BLOCK + "//input[@name[contains(.,'cardNumber')]]";
	private static final String XP_INPUT_TITULAR = XP_BLOCK + "//input[@name[contains(.,'cardHolder')]]";
	private static final String XP_SELECT_MES = XP_BLOCK + "//select[@name[contains(.,'expirationMonth')]]";
	private static final String XP_SELECT_ANY = XP_BLOCK + "//select[@name[contains(.,'expirationYear')]]";
	private static final String XP_INPUT_CVC = XP_BLOCK + "//input[@name[contains(.,'cvc')]]";
	private static final String XP_INPUT_DNI = XP_BLOCK + "//input[@name[contains(.,'dni')]]"; //Specific for Codensa (Colombia)
	private static final String XP_CVC_TRJ_GUARDADA = "//div[@class[contains(.,'tarjetasGuardadas')]]//input[@id='cvc']";	

	private String getXPathSaveCardCheckbox() {
		if (isDesktop()) {
			return XP_SAVE_CARD_CHECKBOX_DESKTOP;
		}
		return XP_SAVE_CARD_CHECKBOX_MOBIL;
	}
	
	public boolean goToIframe() {
		if (state(VISIBLE, XP_IFRAME).wait(2).check()) {
			driver.switchTo().frame(getElement(XP_IFRAME));
			return true;
		}
		return false;
	}
	
	public void leaveIframe() {
		driver.switchTo().defaultContent();
	}
	
	@Override
	public boolean isVisiblePanelPagoUntil(String nombrePago, int seconds) {
		return true;
	}
	
	@Override
	public boolean isPresentInputNumberUntil(int seconds) {
		goToIframe();
		boolean present = state(PRESENT, XP_INPUT_NUMBER).wait(seconds).check();
		leaveIframe();
		return present;
	}
	
	@Override
	public boolean isPresentInputTitular() {
		goToIframe();
		boolean present = state(PRESENT, XP_INPUT_TITULAR).check();
		leaveIframe();
		return present;
	}
	
	@Override
	public boolean isPresentSelectMes() {
		goToIframe();
		boolean present = state(PRESENT, XP_SELECT_MES).check();
		leaveIframe();
		return present;
	}
	 
	@Override
	public boolean isPresentSelectAny() {
		goToIframe();
		boolean present = state(PRESENT, XP_SELECT_ANY).check();
		leaveIframe();
		return present;
	}
	
	@Override
	public boolean isPresentInputCvc() {
		goToIframe();
		boolean present = state(PRESENT, XP_INPUT_CVC).check();
		leaveIframe();
		return present;
	}
	
	@Override
	public boolean isPresentInputDni() {
		goToIframe();
		boolean present = state(PRESENT, XP_INPUT_DNI).check();
		leaveIframe();
		return present;
	}	
	
	@Override
	public void selectSaveCard() {
		click(getXPathSaveCardCheckbox()).exec();
	}
	
	@Override
	public void inputNumber(String number) {
		goToIframe();
		var input = getElement(XP_INPUT_NUMBER);
		if (number.compareTo(input.getAttribute("value").replace(" ", ""))!=0) {
			var inputNumber = getElement(XP_INPUT_NUMBER);
			inputNumber.clear();
			if (isTablet()) {
				for(char c : number.toCharArray()) {
					inputNumber.sendKeys(String.valueOf(c));
				}
			} else {
				inputNumber.sendKeys(number);
			}
		}
		leaveIframe();
	}
	
	@Override
	public void inputTitular(String titular) {
		goToIframe();
		getElement(XP_INPUT_TITULAR).clear();
		getElement(XP_INPUT_TITULAR).sendKeys(titular);
		leaveIframe();
	}
	
	@Override
	public void inputCvc(String cvc) {
		goToIframe();
		getElement(XP_INPUT_CVC).clear();
		getElement(XP_INPUT_CVC).sendKeys(cvc);
		leaveIframe();
	}
	
	@Override
	public void inputDni(String dni) {
		goToIframe();
		getElement(XP_INPUT_DNI).clear();
		getElement(XP_INPUT_DNI).sendKeys(dni);
		leaveIframe();
	}	
	
	@Override
	public void selectMesByVisibleText(String mes) {
		goToIframe();
		new Select(getElement(XP_SELECT_MES)).selectByVisibleText(mes);
		new Select(getElement(XP_SELECT_MES)).selectByVisibleText(mes);
		leaveIframe();
	}
	
	@Override
	public void selectAnyByVisibleText(String any) {
		goToIframe();
		new Select(getElement(XP_SELECT_ANY)).selectByVisibleText(any);
		new Select(getElement(XP_SELECT_ANY)).selectByVisibleText(any);
		leaveIframe();
	}
	
	public void inputCvcTrjGuardadaIfVisible(String cvc) {
		if (goToIframe()) {
			if (state(VISIBLE, XP_CVC_TRJ_GUARDADA).wait(1).check()) {
				var input = getElement(XP_CVC_TRJ_GUARDADA);
				input.clear();
				input.sendKeys(cvc);
			}
			leaveIframe();
		}
	}
	
	public void inputCardNumberAndTab(String numTarj) {
		goToIframe();
		getElement(XP_INPUT_NUMBER).sendKeys(numTarj, Keys.TAB);
		leaveIframe();
	}
}