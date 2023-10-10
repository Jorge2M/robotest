package com.mng.robotest.tests.domains.loyalty.pageobjects;


import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageRegalarMisLikes extends PageBase {
	
	private static final String XPATH_WRAPPER_PAGE = "//div[@id='loyaltyTransferLikes']";
	private static final String XPATH_INPUT_MESSAGE = "//input[@name='name']";
	private static final String XPATH_INPUT_EMAIL_RECEPTOR = "//input[@name='email']";
	private static final String XPATH_BOTON_CONTINUAR = "//button[@class[contains(.,'step1')]]";
	private static final String XPATH_BLOCK_CUANTOS_LIKES = "//div[@class='step2']";
	private static final String XPATH_RADIO_INPUT_NUM_LIKES = "//label[@for='totalLikes']";
	private static final String XPATH_INPUT_NUM_LIKES = "//input[@name='likesToBeTransferred']";
	private static final String XPATH_BOTON_ENVIAR_REGALO = "//button[@class[contains(.,'step2-form')]]";
	
	public boolean checkIsPage() {
		return state(Visible, XPATH_WRAPPER_PAGE).check();
	}
	
	public void inputMensaje(String mensaje) {
		WebElement input = getElement(XPATH_INPUT_MESSAGE);
		input.clear();
		input.sendKeys(mensaje);
	}
	public void inputEmailReceptor(String emailReceptor) {
		WebElement input = getElement(XPATH_INPUT_EMAIL_RECEPTOR);
		input.clear();
		input.sendKeys(emailReceptor);
	}
	public void clickContinuar() {
		click(XPATH_BOTON_CONTINUAR).exec();
	}
	
	public boolean checkIsVisibleBlockCuantosLikes() {
		return state(Visible, XPATH_BLOCK_CUANTOS_LIKES).check();
	}
	
	public void inputLikesToRegalar(int numLikesToRegalar) {
		click(XPATH_RADIO_INPUT_NUM_LIKES).type(javascript).exec();
		WebElement input = getElement(XPATH_INPUT_NUM_LIKES);
		input.clear();
		input.sendKeys(String.valueOf(numLikesToRegalar));
	}
	
	public void clickEnviarRegalo() {
		click(XPATH_BOTON_ENVIAR_REGALO).exec();
	}
}