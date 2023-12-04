package com.mng.robotest.tests.domains.compra.payments.assist.pageobjects;

import org.openqa.selenium.support.ui.Select;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.beans.Pago;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageAssist1rst extends PageBase {

	private static final String XP_LOGO_ASSIST_DESKTOP = "//div[@id[contains(.,'AssistLogo')]]";
	private static final String XP_LOGO_ASSIST_MOBIL = "//div[@class='Logo']/img";
	private static final String XP_INPUT_NUM_TRJ_DESKTOP = "//input[@id='CardNumber']";
	private static final String XP_INPUT_MM_CADUC_DESKTOP = "//input[@id='ExpireMonth']";
	private static final String XP_INPUT_AA_CADUC_DESKTOP = "//input[@id='ExpireYear']";
	private static final String XP_INPUT_NUM_TRJ_MOVIL = "//input[@id='CardNumber']";
	private static final String XP_SELECT_MM_CADUC_MOVIL = "//select[@id='ExpireMonth']";
	private static final String XP_SELECT_AA_CADUC_MOVIL = "//select[@id='ExpireYear']";
	private static final String XP_INPUT_TITULAR = "//input[@id='Cardholder']";
	private static final String XP_INPUT_CVC = "//input[@id[contains(.,'CVC2')] or @id[contains(.,'psw_CVC2')]]";
	private static final String XP_BOTON_PAGO_DESKTOP_AVAILABLE = "//input[@class[contains(.,'button_pay')] and not(@disabled)]";
	private static final String XP_BOTON_PAGO_MOVIL_AVAILABLE = "//input[@type='Submit' and not(@disabled)]";
	private static final String XP_BOTON_PAGO_DESKTOP = "//input[@class[contains(.,'button_pay')]]";
	private static final String XP_BOTON_PAGO_MOBIL = "//input[@type='Submit' and not(@disabled)]";
	
	private String getXPathLogoAssist() {
		if (channel.isDevice()) {
			return XP_LOGO_ASSIST_MOBIL; 
		}
		return XP_LOGO_ASSIST_DESKTOP;
	}
	
	private String getXPathButtonPago() {
		if (channel.isDevice()) {
			return XP_BOTON_PAGO_MOVIL_AVAILABLE;
		}
		return XP_BOTON_PAGO_DESKTOP_AVAILABLE;
	}
	
	public boolean isPresentLogoAssist() {
		String xpathLogo = getXPathLogoAssist();
		return state(PRESENT, xpathLogo).check();
	}
	
	public boolean isPresentInputsForTrjData() {
		boolean inputsOk = true;
		if (channel.isDevice()) {
			if (!state(PRESENT, XP_INPUT_NUM_TRJ_MOVIL).check() ||
				!state(PRESENT, XP_SELECT_MM_CADUC_MOVIL).check() ||
				!state(PRESENT, XP_SELECT_AA_CADUC_MOVIL).check()) {
				inputsOk = false;
			}
		} else {
			if (!state(PRESENT, XP_INPUT_NUM_TRJ_DESKTOP).check() ||
				!state(PRESENT, XP_INPUT_MM_CADUC_DESKTOP).check() ||
				!state(PRESENT, XP_INPUT_AA_CADUC_DESKTOP).check()) {
				inputsOk = false;
			}
		}
		
		if (!state(PRESENT, XP_INPUT_TITULAR).check() ||
			!state(PRESENT, XP_INPUT_CVC).check()) {
			inputsOk = false;
		}
		
		return inputsOk;
	}
	
	public void inputDataPagoAndWaitSubmitAvailable(Pago pago) {
		//Input data
		if (channel.isDevice()) {
			getElement(XP_INPUT_NUM_TRJ_MOVIL).sendKeys(pago.getNumtarj());
			new Select(getElement(XP_SELECT_MM_CADUC_MOVIL)).selectByValue(pago.getMescad());
			new Select(getElement(XP_SELECT_AA_CADUC_MOVIL)).selectByValue("20" + pago.getAnycad()); //Atención con el efecto 2100!!!
		} else {
			getElement(XP_INPUT_NUM_TRJ_DESKTOP).sendKeys(pago.getNumtarj());
			waitLoadPage();
			getElement(XP_INPUT_MM_CADUC_DESKTOP).sendKeys(pago.getMescad());
			getElement(XP_INPUT_AA_CADUC_DESKTOP).sendKeys(pago.getAnycad());
			waitLoadPage();
		}
		
		getElement(XP_INPUT_TITULAR).sendKeys(pago.getTitular());
		getElementVisible(XP_INPUT_CVC).sendKeys(pago.getCvc());
		waitLoadPage();
		
		//Wait for button available
		waitForBotonAvailable(1);
	}
	
	public void clickBotonPago() {
		if (channel.isDevice()) {
			click(XP_BOTON_PAGO_MOBIL).exec();
		} else {
			click(XP_BOTON_PAGO_DESKTOP).exec();
		}
	}

	public void waitForBotonAvailable(int seconds) {
		state(PRESENT, getXPathButtonPago()).wait(seconds);
	}
	
	public boolean invisibilityBotonPagoUntil(int seconds) {
		return state(INVISIBLE, getXPathButtonPago()).wait(seconds).check();
	 }
}

