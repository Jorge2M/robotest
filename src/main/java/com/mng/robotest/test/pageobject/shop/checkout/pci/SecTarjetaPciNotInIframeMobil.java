package com.mng.robotest.test.pageobject.shop.checkout.pci;

import org.openqa.selenium.support.ui.Select;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecTarjetaPciNotInIframeMobil extends PageBase implements SecTarjetaPci {

	private static final String XPATH_BLOCK = "//div[@data-form-card-content='form']";
	private static final String XPathInputNumber = XPATH_BLOCK + "//input[@id[contains(.,'number-card')] or @id[contains(.,'card-pci')]]";
	private static final String XPathInputTitular = XPATH_BLOCK + "//input[@id[contains(.,'card-holder')] or @id[contains(.,'holder-name-pci')]]";
	private static final String XPathSelectMes = XPATH_BLOCK + "//select[@id[contains(.,'month')]]";
	private static final String XPathSelectAny = XPATH_BLOCK + "//select[@id[contains(.,'year')]]";
	private static final String XPathInputCvc = XPATH_BLOCK + "//input[@id[contains(.,'cvc')]]";
	private static final String XPathInputDni = XPATH_BLOCK + "//input[@id[contains(.,'dni')]]"; //Specific for Codensa (Colombia)
	
	@Override
	public boolean isVisiblePanelPagoUntil(String nombrePago, int maxSeconds) {
		return true;
	}
	
	@Override
	public boolean isPresentInputNumberUntil(int maxSeconds) {
		return state(Present, XPathInputNumber).wait(maxSeconds).check();
	}
	
	@Override
	public boolean isPresentInputTitular() {
		return state(Present, XPathInputTitular).check();
	}
	
	@Override
	public boolean isPresentSelectMes() {
		return state(Present, XPathSelectMes).check();
	}
	 
	@Override
	public boolean isPresentSelectAny() {
		return state(Present, XPathSelectAny).check();
	}
	
	@Override
	public boolean isPresentInputCvc() {
		return state(Present, XPathInputCvc).check();
	}
	
	@Override
	public boolean isPresentInputDni() {
		return state(Present, XPathInputDni).check();
	}	
	
	@Override
	public void inputNumber(String number) {
		getElement(XPathInputNumber).clear();
		getElement(XPathInputNumber).sendKeys(number);
	}
	
	@Override
	public void inputTitular(String titular) {
		getElement(XPathInputTitular).clear();
		getElement(XPathInputTitular).sendKeys(titular);
	}
	
	@Override
	public void inputCvc(String cvc) {
		getElement(XPathInputCvc).clear();
		getElement(XPathInputCvc).sendKeys(cvc);
	}
	
	@Override
	public void inputDni(String dni) {
		getElement(XPathInputDni).clear();
		getElement(XPathInputDni).sendKeys(dni);
	}	
	
	@Override
	public void selectMesByVisibleText(String mes) {
		new Select(getElement(XPathSelectMes)).selectByVisibleText(mes);
	}
	
	@Override
	public void selectAnyByVisibleText(String any) {
		new Select(getElement(XPathSelectAny)).selectByVisibleText(any);
	}
}