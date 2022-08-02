package com.mng.robotest.test.pageobject.shop.checkout.tmango;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageAmexInputTarjeta extends PageObjTM {

	private static final String XPATH_PAGE_REDSYS = "//title[text()='Redsys']";
	private static final String XPATH_INPUT_NUM_TARJ = "//input[@id[contains(.,'inputCard')]]";
	private static final String XPATH_INPUT_MES_CAD = "//input[@id[contains(.,'cad1')] and @maxlength=2]";
	private static final String XPATH_INPUT_ANY_CAD = "//input[@id[contains(.,'cad2')] and @maxlength=2]";
	private static final String XPATH_INPUT_CVC = "//input[@id[contains(.,'codseg')] and @maxlength=4]";
	private static final String XPATH_PAGAR_BUTTON = "//button[@id[contains(.,'divImgAceptar')]]";
	
	public PageAmexInputTarjeta(WebDriver driver) {
		super(driver);
	}
	
	public boolean isPasarelaRedSysUntil(int maxSeconds) {
		return state(Present, By.xpath(XPATH_PAGE_REDSYS)).wait(maxSeconds).check();
	}
	
	public void inputDataTarjeta(String numTarj, String mesCad, String anyCad, String Cvc) {
		driver.findElement(By.xpath(XPATH_INPUT_NUM_TARJ)).sendKeys(numTarj);
		driver.findElement(By.xpath(XPATH_INPUT_MES_CAD)).sendKeys(mesCad);
		driver.findElement(By.xpath(XPATH_INPUT_ANY_CAD)).sendKeys(anyCad);
		driver.findElement(By.xpath(XPATH_INPUT_CVC)).sendKeys(Cvc);
	}

	public boolean isPresentNumTarj() {
		return (state(Present, By.xpath(XPATH_INPUT_NUM_TARJ)).check());
	}
	
	public boolean isPresentInputMesCad() {
		return (state(Present, By.xpath(XPATH_INPUT_MES_CAD)).check());
	}
	
	public boolean isPresentInputAnyCad() {
		return (state(Present, By.xpath(XPATH_INPUT_ANY_CAD)).check());
	}
	
	public boolean isPresentInputCvc() {
		return (state(Present, By.xpath(XPATH_INPUT_CVC)).check());
	}

	public boolean isPresentPagarButton() {
		return (state(Present, By.xpath(XPATH_PAGAR_BUTTON)).check());
	}

	public void clickPagarButton() {
		click(By.xpath(XPATH_PAGAR_BUTTON)).exec();
	}
}
