package com.mng.robotest.test.pageobject.shop.checkout.pci;

import org.openqa.selenium.support.ui.Select;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecTarjetaPciNotInIframeMobil extends PageBase implements SecTarjetaPci {

	private static final String XPATH_BLOCK = "//div[@data-form-card-content='form']";
	private static final String XPATH_INPUT_NUMBER = XPATH_BLOCK + "//input[@id[contains(.,'number-card')] or @id[contains(.,'card-pci')]]";
	private static final String XPATH_INPUT_TITULAR = XPATH_BLOCK + "//input[@id[contains(.,'card-holder')] or @id[contains(.,'holder-name-pci')]]";
	private static final String XPATH_SELECT_MES = XPATH_BLOCK + "//select[@id[contains(.,'month')]]";
	private static final String XPATH_SELECT_ANY = XPATH_BLOCK + "//select[@id[contains(.,'year')]]";
	private static final String XPATH_INPUT_CVC = XPATH_BLOCK + "//input[@id[contains(.,'cvc')]]";
	private static final String XPATH_INPUT_DNI = XPATH_BLOCK + "//input[@id[contains(.,'dni')]]"; //Specific for Codensa (Colombia)
	
	@Override
	public boolean isVisiblePanelPagoUntil(String nombrePago, int maxSeconds) {
		return true;
	}
	
	@Override
	public boolean isPresentInputNumberUntil(int maxSeconds) {
		return state(Present, XPATH_INPUT_NUMBER).wait(maxSeconds).check();
	}
	
	@Override
	public boolean isPresentInputTitular() {
		return state(Present, XPATH_INPUT_TITULAR).check();
	}
	
	@Override
	public boolean isPresentSelectMes() {
		return state(Present, XPATH_SELECT_MES).check();
	}
	 
	@Override
	public boolean isPresentSelectAny() {
		return state(Present, XPATH_SELECT_ANY).check();
	}
	
	@Override
	public boolean isPresentInputCvc() {
		return state(Present, XPATH_INPUT_CVC).check();
	}
	
	@Override
	public boolean isPresentInputDni() {
		return state(Present, XPATH_INPUT_DNI).check();
	}	
	
	@Override
	public void inputNumber(String number) {
		getElement(XPATH_INPUT_NUMBER).clear();
		getElement(XPATH_INPUT_NUMBER).sendKeys(number);
	}
	
	@Override
	public void inputTitular(String titular) {
		getElement(XPATH_INPUT_TITULAR).clear();
		getElement(XPATH_INPUT_TITULAR).sendKeys(titular);
	}
	
	@Override
	public void inputCvc(String cvc) {
		getElement(XPATH_INPUT_CVC).clear();
		getElement(XPATH_INPUT_CVC).sendKeys(cvc);
	}
	
	@Override
	public void inputDni(String dni) {
		getElement(XPATH_INPUT_DNI).clear();
		getElement(XPATH_INPUT_DNI).sendKeys(dni);
	}	
	
	@Override
	public void selectMesByVisibleText(String mes) {
		new Select(getElement(XPATH_SELECT_MES)).selectByVisibleText(mes);
	}
	
	@Override
	public void selectAnyByVisibleText(String any) {
		new Select(getElement(XPATH_SELECT_ANY)).selectByVisibleText(any);
	}
}