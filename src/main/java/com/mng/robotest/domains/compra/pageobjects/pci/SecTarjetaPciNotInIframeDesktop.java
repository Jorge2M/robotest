package com.mng.robotest.domains.compra.pageobjects.pci;

import org.openqa.selenium.By;

import com.mng.robotest.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.SelectElement.TypeSelect.*;

public class SecTarjetaPciNotInIframeDesktop extends PageBase implements SecTarjetaPci {

	private static final String XPATH_BLOCK = "//span[@id[contains(.,'panelTarjetasForm')]]";
	private static final String XPATH_INPUT_NUMBER = XPATH_BLOCK + "//input[@id[contains(.,'cardNumber')] or @id[contains(.,'cardnumber')] or @id[contains(.,'msu_cardpan')]]";
	private static final String XPATH_INPUT_TITULAR = XPATH_BLOCK + "//input[@data-encrypted-name[contains(.,'holderName')] or @class[contains(.,'holdername')] or @class[contains(.,'holderName')] or @class[contains(.,'msu_nameoncard')]]";
	private static final String XPATH_SELECT_MES = XPATH_BLOCK + "//select[@class[contains(.,'selectMonth')] or @class[contains(.,'select gmo_month')] or @class[contains(.,'select msu_month')]]";
	private static final String XPATH_SELECT_ANY = XPATH_BLOCK + "//select[@class[contains(.,'selectYear')] or @class[contains(.,'select gmo_year')] or @class[contains(.,'select msu_year')]]";
	private static final String XPATH_INPUT_CVC = XPATH_BLOCK + "//input[@class[contains(.,'CVCInput')] or @class[contains(.,'CWInput')]]";
	private static final String XPATH_INPUT_DNI = XPATH_BLOCK + "//input[@class[contains(.,'falcon_dni')]]"; //Specific for Codensa (Colombia)
	
	public String getXPathPanelPago(String nombrePago) {
		return (XPATH_BLOCK + "//*[@id[contains(.,'CardName')] and text()='" + nombrePago + "']");
	}
	
	@Override
	public boolean isVisiblePanelPagoUntil(String nombrePago, int seconds) {
		String xpathPanelPago = getXPathPanelPago(nombrePago);
		return state(Visible, xpathPanelPago).wait(seconds).check();
	}
	
	@Override
	public boolean isPresentInputNumberUntil(int seconds) {
		return state(Present, XPATH_INPUT_NUMBER).wait(seconds).check();
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
		select(By.xpath(XPATH_SELECT_MES), mes).type(VisibleText).exec();
	}
	
	@Override
	public void selectAnyByVisibleText(String any) {
		select(By.xpath(XPATH_SELECT_ANY), any).type(VisibleText).exec();
	}
}