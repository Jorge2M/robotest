package com.mng.robotest.test.pageobject.shop.checkout.assist;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.beans.Pago;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageAssist1rst extends PageBase {

	private static final String XPATH_LOGO_ASSIST_DESKTOP = "//div[@id[contains(.,'AssistLogo')]]";
	private static final String XPATH_LOGO_ASSIST_MOBIL = "//div[@class='Logo']/img";
	private static final String XPATH_INPUT_NUM_TRJ_DESKTOP = "//input[@id='CardNumber']";
	private static final String XPATH_INPUT_MM_CADUC_DESKTOP = "//input[@id='ExpireMonth']";
	private static final String XPATH_INPUT_AA_CADUC_DESKTOP = "//input[@id='ExpireYear']";
	private static final String XPATH_INPUT_NUM_TRJ_MOVIL = "//input[@id='CardNumber']";
	private static final String XPATH_SELECT_MM_CADUC_MOVIL = "//select[@id='ExpireMonth']";
	private static final String XPATH_SELECT_AA_CADUC_MOVIL = "//select[@id='ExpireYear']";
	private static final String XPATH_INPUT_TITULAR = "//input[@id='Cardholder']";
	private static final String XPATH_INPUT_CVC = "//input[@id[contains(.,'CVC2')] or @id[contains(.,'psw_CVC2')]]";
	private static final String XPATH_BOTON_PAGO_DESKTOP_AVAILABLE = "//input[@class[contains(.,'button_pay')] and not(@disabled)]";
	private static final String XPATH_BOTON_PAGO_MOVIL_AVAILABLE = "//input[@type='Submit' and not(@disabled)]";
	private static final String XPATH_BOTON_PAGO_DESKTOP = "//input[@class[contains(.,'button_pay')]]";
	private static final String XPATH_BOTON_PAGO_MOBIL = "//input[@type='Submit' and not(@disabled)]";
	
	private String getXPathLogoAssist() {
		if (channel.isDevice()) {
			return XPATH_LOGO_ASSIST_MOBIL; 
		}
		return XPATH_LOGO_ASSIST_DESKTOP;
	}
	
	private String getXPathButtonPago() {
		if (channel.isDevice()) {
			return XPATH_BOTON_PAGO_MOVIL_AVAILABLE;
		}
		return XPATH_BOTON_PAGO_DESKTOP_AVAILABLE;
	}
	
	public boolean isPresentLogoAssist() {
		String xpathLogo = getXPathLogoAssist();
		return (state(Present, By.xpath(xpathLogo)).check());
	}
	
	public boolean isPresentInputsForTrjData() {
		boolean inputsOk = true;
		if (channel.isDevice()) {
			if (!state(Present, By.xpath(XPATH_INPUT_NUM_TRJ_MOVIL)).check() ||
				!state(Present, By.xpath(XPATH_SELECT_MM_CADUC_MOVIL)).check() ||
				!state(Present, By.xpath(XPATH_SELECT_AA_CADUC_MOVIL)).check()) {
				inputsOk = false;
			}
		} else {
			if (!state(Present, By.xpath(XPATH_INPUT_NUM_TRJ_DESKTOP)).check() ||
				!state(Present, By.xpath(XPATH_INPUT_MM_CADUC_DESKTOP)).check() ||
				!state(Present, By.xpath(XPATH_INPUT_AA_CADUC_DESKTOP)).check()) {
				inputsOk = false;
			}
		}
		
		if (!state(Present, By.xpath(XPATH_INPUT_TITULAR)).check() ||
			!state(Present, By.xpath(XPATH_INPUT_CVC)).check()) {
			inputsOk = false;
		}
		
		return inputsOk;
	}
	
	public void inputDataPagoAndWaitSubmitAvailable(Pago pago) throws Exception {
		//Input data
		if (channel.isDevice()) {
			driver.findElement(By.xpath(XPATH_INPUT_NUM_TRJ_MOVIL)).sendKeys(pago.getNumtarj());
			new Select(driver.findElement(By.xpath(XPATH_SELECT_MM_CADUC_MOVIL))).selectByValue(pago.getMescad());
			new Select(driver.findElement(By.xpath(XPATH_SELECT_AA_CADUC_MOVIL))).selectByValue("20" + pago.getAnycad()); //Atención con el efecto 2100!!!
		} else {
			driver.findElement(By.xpath(XPATH_INPUT_NUM_TRJ_DESKTOP)).sendKeys(pago.getNumtarj());
			waitForPageLoaded(driver);
			driver.findElement(By.xpath(XPATH_INPUT_MM_CADUC_DESKTOP)).sendKeys(pago.getMescad());
			driver.findElement(By.xpath(XPATH_INPUT_AA_CADUC_DESKTOP)).sendKeys(pago.getAnycad());
			waitForPageLoaded(driver);
		}
		
		driver.findElement(By.xpath(XPATH_INPUT_TITULAR)).sendKeys(pago.getTitular());
		getElementVisible(XPATH_INPUT_CVC).sendKeys(pago.getCvc());
		waitForPageLoaded(driver);
		
		//Wait for button available
		waitForBotonAvailable(1);
	}
	
	public void clickBotonPago() {
		if (channel.isDevice()) {
			click(By.xpath(XPATH_BOTON_PAGO_MOBIL)).exec();
		} else {
			click(By.xpath(XPATH_BOTON_PAGO_DESKTOP)).exec();
		}
	}

	public void waitForBotonAvailable(int seconds) {
		String xpathBoton = getXPathButtonPago();
		state(State.Present, xpathBoton).wait(seconds);
	}
	
	public boolean invisibilityBotonPagoUntil(int maxSeconds) {
		String xpathBoton = getXPathButtonPago();
		return (state(Invisible, By.xpath(xpathBoton)).wait(maxSeconds).check());
	 }
}

