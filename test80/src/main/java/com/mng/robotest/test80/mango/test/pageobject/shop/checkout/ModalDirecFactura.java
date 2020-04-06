package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class ModalDirecFactura extends ModalDireccion {

    static String XPathFormModal = "//form[@class[contains(.,'customFormIdFACT')]]";
    static String XPathButtonUpdate = XPathFormModal + "//div[@class[contains(.,'updateButton')]]/input[@type='submit']";
    
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
		return (state(Visible, By.xpath(XPathFormModal), driver).wait(maxSeconds).check());
	}

	public static boolean isVisibleButtonActualizar(WebDriver driver) {
		return (state(Visible, By.xpath(XPathButtonUpdate), driver).check());
	}

	public static void clickActualizar(WebDriver driver) {
		click(By.xpath(XPathButtonUpdate), driver).exec();

		//Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no funciona así que ejecutamos un 2o 
		if (isVisibleButtonActualizar(driver)) {
			click(By.xpath(XPathButtonUpdate), driver).exec();
		}
	}
}
