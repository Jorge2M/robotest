package com.mng.robotest.test.pageobject.shop.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;


public class SecDireccionEnvioDesktop extends PageObjTM {

	private static final String XPATH_SECTION = "//micro-frontend[@id='checkoutDeliveryAddressDesktop']";
	private static final String XPATH_EDIT_DIRECCION_BUTTON = XPATH_SECTION + "//button";
	private static final String XPATH_NOMBRE_ENVIO = XPATH_SECTION + "//div[@class[contains(.,'tpavy')]]"; 
	private static final String XPATH_DIRECCION_ENVIO = "//*[@data-testid='checkout.delivery.address']";
	
	public SecDireccionEnvioDesktop(WebDriver driver) {
		super(driver);
	}
	
	public void clickEditDireccion() {
		waitForPageLoaded(driver); //For avoid StaleElementReferenceException
		click(By.xpath(XPATH_EDIT_DIRECCION_BUTTON)).exec();
	}

	public String getTextNombreEnvio() {
		return (driver.findElement(By.xpath(XPATH_NOMBRE_ENVIO)).getText());
	}
	
	public String getTextDireccionEnvio() {
		return (driver.findElement(By.xpath(XPATH_DIRECCION_ENVIO)).getText());
	}
}
