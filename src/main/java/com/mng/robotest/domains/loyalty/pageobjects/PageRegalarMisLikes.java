package com.mng.robotest.domains.loyalty.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageRegalarMisLikes extends PageObjTM {
	
	private static final String XPATH_WRAPPER_PAGE = "//div[@id='loyaltyTransferLikes']";
	private static final String XPATH_INPUT_MESSAGE = "//input[@name='name']";
	private static final String XPATH_INPUT_EMAIL_RECEPTOR = "//input[@name='email']";
	private static final String XPATH_BOTON_CONTINUAR = "//button[@class[contains(.,'step1')]]";
	private static final String XPATH_BLOCK_CUANTOS_LIKES = "//div[@class='step2']";
	private static final String XPATH_RADIO_INPUT_NUM_LIKES = "//label[@for='totalLikes']";
	private static final String XPATH_INPUT_NUM_LIKES = "//input[@name='likesToBeTransferred']";
	private static final String XPATH_BOTON_ENVIAR_REGALO = "//button[@class[contains(.,'step2-form')]]";
	
	public PageRegalarMisLikes(WebDriver driver) {
		super(driver);
	}
	
	public boolean checkIsPage() {
		return (state(Visible, By.xpath(XPATH_WRAPPER_PAGE)).check());
	}
	
	public void inputMensaje(String mensaje) {
		WebElement input = driver.findElement(By.xpath(XPATH_INPUT_MESSAGE));
		input.clear();
		input.sendKeys(mensaje);
	}
	public void inputEmailReceptor(String emailReceptor) {
		WebElement input = driver.findElement(By.xpath(XPATH_INPUT_EMAIL_RECEPTOR));
		input.clear();
		input.sendKeys(emailReceptor);
	}
	public void clickContinuar() {
		click(By.xpath(XPATH_BOTON_CONTINUAR)).exec();
	}
	
	public boolean checkIsVisibleBlockCuantosLikes() {
		return (state(Visible, By.xpath(XPATH_BLOCK_CUANTOS_LIKES)).check());
	}
	
	public void inputLikesToRegalar(int numLikesToRegalar) {
		click(By.xpath(XPATH_RADIO_INPUT_NUM_LIKES)).type(javascript).exec();
		WebElement input = driver.findElement(By.xpath(XPATH_INPUT_NUM_LIKES));
		input.clear();
		input.sendKeys(String.valueOf(numLikesToRegalar));
	}
	
	public void clickEnviarRegalo() {
		click(By.xpath(XPATH_BOTON_ENVIAR_REGALO)).exec();
	}
}
