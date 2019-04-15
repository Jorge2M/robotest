package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


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
    
    public static boolean isVisibleFormUntil(int maxSecondsToWait, WebDriver driver) {
        return isElementVisibleUntil(driver, By.xpath(XPathFormModal), maxSecondsToWait); 
    }
    
    public static boolean isInvisibleFormUntil(int maxSecondsToWait, WebDriver driver) {
        return isElementInvisibleUntil(driver, By.xpath(XPathFormModal), maxSecondsToWait); 
    }    
    
    public static boolean isVisibleButtonActualizar(WebDriver driver) {
        return isElementVisible(driver, By.xpath(XPathButtonUpdate));
    }
    
    public static void moveToAndDoubleClickActualizar(WebDriver driver) throws Exception {
    	moveToElement(By.xpath(XPathButtonUpdate), driver);
        waitForPageLoaded(driver);
        clickAndWaitLoad(driver, By.xpath(XPathButtonUpdate));
        
        //Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no funciona así que ejecutamos un 2o 
        if (isVisibleButtonActualizar(driver)) {
        	clickAndWaitLoad(driver, By.xpath(XPathButtonUpdate));
        }
    }    
}
