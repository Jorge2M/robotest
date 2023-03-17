package com.mng.robotest.domains.compra.pageobjects.pci;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecTarjetaPciInIframe extends PageBase implements SecTarjetaPci {
	
	private static final String XPATH_IFRAME = "//iframe[@title='credit_card_form']";
	private static final String XPATH_BLOCK = "//div[@id='root']";
	private static final String XPATH_INPUT_NUMBER = XPATH_BLOCK + "//input[@name[contains(.,'cardNumber')]]";
	private static final String XPATH_INPUT_TITULAR = XPATH_BLOCK + "//input[@name[contains(.,'cardHolder')]]";
	private static final String XPATH_SELECT_MES = XPATH_BLOCK + "//select[@name[contains(.,'expirationMonth')]]";
	private static final String XPATH_SELECT_ANY = XPATH_BLOCK + "//select[@name[contains(.,'expirationYear')]]";
	private static final String XPATH_INPUT_CVC = XPATH_BLOCK + "//input[@name[contains(.,'cvc')]]";
	private static final String XPATH_INPUT_DNI = XPATH_BLOCK + "//input[@name[contains(.,'dni')]]"; //Specific for Codensa (Colombia)
	
	protected void goToIframe() {
		state(State.Visible, XPATH_IFRAME).wait(2).check();
		driver.switchTo().frame(getElement(XPATH_IFRAME));
	}
	
	protected void leaveIframe() {
		driver.switchTo().defaultContent();
	}
	
	@Override
	public boolean isVisiblePanelPagoUntil(String nombrePago, int seconds) {
		return true;
	}
	
	@Override
	public boolean isPresentInputNumberUntil(int seconds) {
		goToIframe();
		boolean present = state(Present, XPATH_INPUT_NUMBER).wait(seconds).check();
		leaveIframe();
		return present;
	}
	
	@Override
	public boolean isPresentInputTitular() {
		goToIframe();
		boolean present = state(Present, XPATH_INPUT_TITULAR).check();
		leaveIframe();
		return present;
	}
	
	@Override
	public boolean isPresentSelectMes() {
		goToIframe();
		boolean present = state(Present, XPATH_SELECT_MES).check();
		leaveIframe();
		return present;
	}
	 
	@Override
	public boolean isPresentSelectAny() {
		goToIframe();
		boolean present = state(Present, XPATH_SELECT_ANY).check();
		leaveIframe();
		return present;
	}
	
	@Override
	public boolean isPresentInputCvc() {
		goToIframe();
		boolean present = state(Present, XPATH_INPUT_CVC).check();
		leaveIframe();
		return present;
	}
	
	@Override
	public boolean isPresentInputDni() {
		goToIframe();
		boolean present = state(Present, XPATH_INPUT_DNI).check();
		leaveIframe();
		return present;
	}	
	
	@Override
	public void inputNumber(String number) {
		goToIframe();
		WebElement input = getElement(XPATH_INPUT_NUMBER);
		if (number.compareTo(input.getAttribute("value").replace(" ", ""))!=0) {
			WebElement inputNumber = getElement(XPATH_INPUT_NUMBER);
			inputNumber.clear();
			if (channel==Channel.tablet) {
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
		getElement(XPATH_INPUT_TITULAR).clear();
		getElement(XPATH_INPUT_TITULAR).sendKeys(titular);
		leaveIframe();
	}
	
	@Override
	public void inputCvc(String cvc) {
		goToIframe();
		getElement(XPATH_INPUT_CVC).clear();
		getElement(XPATH_INPUT_CVC).sendKeys(cvc);
		leaveIframe();
	}
	
	@Override
	public void inputDni(String dni) {
		goToIframe();
		getElement(XPATH_INPUT_DNI).clear();
		getElement(XPATH_INPUT_DNI).sendKeys(dni);
		leaveIframe();
	}	
	
	@Override
	public void selectMesByVisibleText(String mes) {
		goToIframe();
		new Select(getElement(XPATH_SELECT_MES)).selectByVisibleText(mes);
		new Select(getElement(XPATH_SELECT_MES)).selectByVisibleText(mes);
		leaveIframe();
	}
	
	@Override
	public void selectAnyByVisibleText(String any) {
		goToIframe();
		new Select(getElement(XPATH_SELECT_ANY)).selectByVisibleText(any);
		new Select(getElement(XPATH_SELECT_ANY)).selectByVisibleText(any);
		leaveIframe();
	}
	
	public void inputCardNumberAndTab(String numTarj) {
		goToIframe();
		getElement(XPATH_INPUT_NUMBER).sendKeys(numTarj, Keys.TAB);
		leaveIframe();
	}
}