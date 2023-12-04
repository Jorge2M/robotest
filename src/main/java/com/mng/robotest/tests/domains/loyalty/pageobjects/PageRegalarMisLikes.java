package com.mng.robotest.tests.domains.loyalty.pageobjects;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageRegalarMisLikes extends PageBase {
	
	private static final String XP_WRAPPER_PAGE = "//div[@id='loyaltyTransferLikes']";
	private static final String XP_INPUT_MESSAGE = "//input[@name='name']";
	private static final String XP_INPUT_EMAIL_RECEPTOR = "//input[@name='email']";
	private static final String XP_BOTON_CONTINUAR = "//button[@class[contains(.,'step1')]]";
	private static final String XP_BLOCK_CUANTOS_LIKES = "//div[@class='step2']";
	private static final String XP_RADIO_INPUT_NUM_LIKES = "//label[@for='totalLikes']";
	private static final String XP_INPUT_NUM_LIKES = "//input[@name='likesToBeTransferred']";
	private static final String XP_BOTON_ENVIAR_REGALO = "//button[@class[contains(.,'step2-form')]]";
	
	public boolean checkIsPage() {
		return state(VISIBLE, XP_WRAPPER_PAGE).check();
	}
	
	public void inputMensaje(String mensaje) {
		var input = getElement(XP_INPUT_MESSAGE);
		input.clear();
		input.sendKeys(mensaje);
	}
	public void inputEmailReceptor(String emailReceptor) {
		var input = getElement(XP_INPUT_EMAIL_RECEPTOR);
		input.clear();
		input.sendKeys(emailReceptor);
	}
	public void clickContinuar() {
		click(XP_BOTON_CONTINUAR).exec();
	}
	
	public boolean checkIsVisibleBlockCuantosLikes() {
		return state(VISIBLE, XP_BLOCK_CUANTOS_LIKES).check();
	}
	
	public void inputLikesToRegalar(int numLikesToRegalar) {
		click(XP_RADIO_INPUT_NUM_LIKES).type(JAVASCRIPT).exec();
		var input = getElement(XP_INPUT_NUM_LIKES);
		input.clear();
		input.sendKeys(String.valueOf(numLikesToRegalar));
	}
	
	public void clickEnviarRegalo() {
		click(XP_BOTON_ENVIAR_REGALO).exec();
	}
	
}
