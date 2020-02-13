package com.mng.robotest.test80.mango.test.pageobject.shop.loyalty;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.testmaker.service.webdriver.wrapper.TypeOfClick;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public class PageRegalarMisLikes extends WebdrvWrapp {

	private final WebDriver driver;
	
	private final static String XPathWrapperPage = "//div[@id='loyaltyTransferLikes']";
	private final static String XPathInputMensaje = "//input[@name='name']";
	private final static String XPathInputEmailReceptor = "//input[@name='email']";
	private final static String XPathBotonContinuar = "//button[@class[contains(.,'step1')]]";
	private final static String XPathBlockCuantosLikes = "//div[@class='step2']";
	private final static String XPathRadioInputNumLikes = "//label[@for='totalLikes']";
	private final static String XPathInputNumLikes = "//input[@name='likesToBeTransferred']";
	private final static String XPathBotonEnviarRegalo = "//button[@class[contains(.,'step2-form')]]";
	
	public PageRegalarMisLikes(WebDriver driver) {
		this.driver = driver;
	}
	
	public boolean checkIsPage() {
		return isElementVisible(driver, By.xpath(XPathWrapperPage));
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
	public void clickContinuar() throws Exception {
		clickAndWaitLoad(driver, By.xpath(XPathBotonContinuar));
	}
	
	public boolean checkIsVisibleBlockCuantosLikes() {
		return isElementVisible(driver, By.xpath(XPathBlockCuantosLikes));
	}
	
	public void inputLikesToRegalar(int numLikesToRegalar) throws Exception {
		clickAndWaitLoad(driver, By.xpath(XPathRadioInputNumLikes), TypeOfClick.javascript);
		WebElement input = driver.findElement(By.xpath(XPathInputNumLikes));
		input.clear();
		input.sendKeys(String.valueOf(numLikesToRegalar));
	}
	
	public void clickEnviarRegalo() throws Exception {
		clickAndWaitLoad(driver, By.xpath(XPathBotonEnviarRegalo));
	}
}
