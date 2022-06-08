package com.mng.robotest.test.pageobject.shop.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Log4jTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class ModalDirecFactura extends ModalDireccion {

	private final static String XPathFormModal = "//form[@class[contains(.,'customFormIdFACT')]]";
	private final static String XPathButtonUpdate = XPathFormModal + "//div[@class[contains(.,'updateButton')]]/input[@type='submit']";
	
	public ModalDirecFactura(WebDriver driver) {
		super(driver);
	}
	
	public void sendDataToInputs(DataDireccion dataToSend) throws Exception {
		sendDataToInputs(dataToSend, XPathFormModal);
	}
	
	public void selectPoblacion(String poblacion) throws Exception {
		selectPoblacion(poblacion, XPathFormModal);
	}

	public void selectProvincia(String provincia) {
		selectProvincia(provincia, XPathFormModal);
	} 

	public boolean isVisibleFormUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPathFormModal)).wait(maxSeconds).check());
	}

	public boolean isVisibleButtonActualizar() {
		return (state(Visible, By.xpath(XPathButtonUpdate)).check());
	}

	public void clickActualizar() {
		click(By.xpath(XPathButtonUpdate)).exec(); 
		try {
			if (isVisibleButtonActualizar()) {
				click(By.xpath(XPathButtonUpdate)).exec();
			}
		}
		catch (StaleElementReferenceException e) {
			Log4jTM.getLogger().info(e);
		}
	}
}