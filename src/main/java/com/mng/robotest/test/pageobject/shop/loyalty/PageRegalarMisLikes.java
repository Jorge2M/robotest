package com.mng.robotest.test.pageobject.shop.loyalty;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageRegalarMisLikes extends PageObjTM {
	
	private static final String XPathWrapperPage = "//div[@id='loyaltyTransferLikes']";
	private static final String XPathInputMensaje = "//input[@name='name']";
	private static final String XPathInputEmailReceptor = "//input[@name='email']";
	private static final String XPathBotonContinuar = "//button[@class[contains(.,'step1')]]";
	private static final String XPathBlockCuantosLikes = "//div[@class='step2']";
	private static final String XPathRadioInputNumLikes = "//label[@for='totalLikes']";
	private static final String XPathInputNumLikes = "//input[@name='likesToBeTransferred']";
	private static final String XPathBotonEnviarRegalo = "//button[@class[contains(.,'step2-form')]]";
	
	public PageRegalarMisLikes(WebDriver driver) {
		super(driver);
	}
	
	public boolean checkIsPage() {
		return (state(Visible, By.xpath(XPathWrapperPage)).check());
	}
	
	public void inputMensaje(String mensaje) {
		WebElement input = driver.findElement(By.xpath(XPathInputMensaje));
		input.clear();
		input.sendKeys(mensaje);
	}
	public void inputEmailReceptor(String emailReceptor) {
		WebElement input = driver.findElement(By.xpath(XPathInputEmailReceptor));
		input.clear();
		input.sendKeys(emailReceptor);
	}
	public void clickContinuar() {
		click(By.xpath(XPathBotonContinuar)).exec();
	}
	
	public boolean checkIsVisibleBlockCuantosLikes() {
		return (state(Visible, By.xpath(XPathBlockCuantosLikes)).check());
	}
	
	public void inputLikesToRegalar(int numLikesToRegalar) {
		click(By.xpath(XPathRadioInputNumLikes)).type(javascript).exec();
		WebElement input = driver.findElement(By.xpath(XPathInputNumLikes));
		input.clear();
		input.sendKeys(String.valueOf(numLikesToRegalar));
	}
	
	public void clickEnviarRegalo() {
		click(By.xpath(XPathBotonEnviarRegalo)).exec();
	}
}
