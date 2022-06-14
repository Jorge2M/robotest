package com.mng.robotest.test.pageobject.shop.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class ModalDirecEnvio extends ModalDireccion {

	private static final String XPathFormModal = "//form[@class[contains(.,'customFormIdENVIO')]]";
	private static final String XPathButtonUpdate = XPathFormModal + "//div[@class[contains(.,'updateButton')]]/*[@class[contains(.,'modalConfirmar')]]";
	
	public ModalDirecEnvio(WebDriver driver) {
		super(driver);
	}

	public void sendDataToInputsNTimesAndWait(DataDireccion dataToSend, int nTimes) throws Exception {
		sendDataToInputsNTimes(dataToSend, nTimes, XPathFormModal);
		waitForPageLoaded(driver);
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

	public void moveToAndDoubleClickActualizar() {
		moveToElement(By.xpath(XPathButtonUpdate), driver);
		waitForPageLoaded(driver);
		click(By.xpath(XPathButtonUpdate)).exec();

		//Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no funciona así que ejecutamos un 2o 
		if (isVisibleButtonActualizar()) {
			click(By.xpath(XPathButtonUpdate)).exec();
		}
	}
}
