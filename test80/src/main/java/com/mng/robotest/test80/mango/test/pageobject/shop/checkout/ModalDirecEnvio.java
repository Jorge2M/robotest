package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;


public class ModalDirecEnvio extends ModalDireccion {

	static String XPathFormModal = "//form[@class[contains(.,'customFormIdENVIO')]]";
	static String XPathButtonUpdate = XPathFormModal + "//div[@class[contains(.,'updateButton')]]/*[@class[contains(.,'modalConfirmar')]]";

	public static void sendDataToInputsNTimesAndWait(DataDireccion dataToSend, int nTimes, WebDriver driver) throws Exception {
		sendDataToInputsNTimes(dataToSend, nTimes, XPathFormModal, driver);
		waitForPageLoaded(driver);
	}

	public static void sendDataToInputs(DataDireccion dataToSend, WebDriver driver) throws Exception {
		sendDataToInputs(dataToSend, XPathFormModal, driver);
	}

	public static void selectPoblacion(String poblacion, WebDriver driver) throws Exception {
		selectPoblacion(poblacion, XPathFormModal, driver);
	}

	public static void selectProvincia(String provincia, WebDriver driver) {
		selectProvincia(provincia, XPathFormModal, driver);
	} 

	public static boolean isVisibleFormUntil(int maxSeconds, WebDriver driver) {
		return (state(Visible, By.xpath(XPathFormModal), driver)
				.wait(maxSeconds).check());
	}

//	public static boolean isInvisibleFormUntil(int maxSeconds, WebDriver driver) {
//		return (state(Invisible, By.xpath(XPathFormModal), driver)
//				.wait(maxSeconds).check());
//	}

	public static boolean isVisibleButtonActualizar(WebDriver driver) {
		return (state(Visible, By.xpath(XPathButtonUpdate), driver).check());
	}

	public static void moveToAndDoubleClickActualizar(WebDriver driver) {
		moveToElement(By.xpath(XPathButtonUpdate), driver);
		waitForPageLoaded(driver);
		click(By.xpath(XPathButtonUpdate), driver).exec();

		//Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no funciona así que ejecutamos un 2o 
		if (isVisibleButtonActualizar(driver)) {
			click(By.xpath(XPathButtonUpdate), driver).exec();
		}
	}
}
