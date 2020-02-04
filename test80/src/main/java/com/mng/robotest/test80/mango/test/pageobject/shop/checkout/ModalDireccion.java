package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import java.util.Iterator;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.DataDireccion.DataDirType;


public abstract class ModalDireccion extends WebdrvWrapp {

    static String XPathInputNif = "//input[@id[contains(.,'cfDni')]]";
    static String XPathInputName = "//input[@id[contains(.,'cfName')]]";
    static String XPathInputApellidos = "//input[@id[contains(.,'cfSname')]]";
    static String XPathInputDireccion = "//input[@id[contains(.,'cfDir1')]]";
    static String XPathInputCodpostal = "//input[@id[contains(.,'cfCp')]]";
    static String XPathInputEmail = "//input[@id[contains(.,'cfEmail')]]";
    static String XPathInputTelefono = "//input[@id[contains(.,'cfTelf')]]";
    static String XPathSelectPoblacion = "//select[@id[contains(.,':localidades')]]";
    static String XPathSelectProvincia = "//select[@id[contains(.,':estadosPais')]]";
    static String XPathSelectPais = "//select[@id[contains(.,':pais')]]";
    
    private static String getXPathInput(DataDirType inputType) {
        switch (inputType) {
        case nif:
            return XPathInputNif;
        case name:
            return XPathInputName;
        case apellidos:
            return XPathInputApellidos;
        case direccion:
            return XPathInputDireccion;
        case codpostal:
            return XPathInputCodpostal;
        case email:
            return XPathInputEmail;
        case telefono:
            return XPathInputTelefono;
        default:
            return "";
        }
    }

    public static void sendDataToInputsNTimes(DataDireccion dataToSend, int nTimes, String XPathFormModal, WebDriver driver) throws Exception {
        for (int i=0; i<nTimes; i++) {
            sendDataToInputs(dataToSend, XPathFormModal, driver);
            waitForPageLoaded(driver);
        }
    }
    
    public static void sendDataToInputs(DataDireccion dataToSend, String XPathFormModal, WebDriver driver) throws Exception {
        try {
            Iterator<Map.Entry<DataDirType,String>> it = dataToSend.getDataDireccion().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<DataDirType,String> pair = it.next();
                switch (pair.getKey()) {
                case poblacion:
                    selectPoblacion(pair.getValue(), XPathFormModal, driver);
                    break;
                case provincia:
                    selectProvincia(pair.getValue(), XPathFormModal, driver);
                    break;
                case codigoPais:
                    selectPais(pair.getValue(), XPathFormModal, driver);
                    break;
                default:
                    sendKeysToInput(pair.getKey(), pair.getValue(), XPathFormModal, driver);                
                }
            }
        }
        catch (Exception e) {
            /*
             * Es posible que realmente el send se haya ejecutado correctamente
             */
        }
    }
    
    public static void selectPoblacion(String poblacion, String XPathFormModal, WebDriver driver) throws Exception {
        new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XPathFormModal + XPathSelectPoblacion)));
        Thread.sleep(1000);
        new Select(driver.findElement(By.xpath(XPathFormModal + XPathSelectPoblacion))).selectByValue(poblacion);
    }
    
    public static void selectProvincia(String provincia, String XPathFormModal, WebDriver driver) {
        new WebDriverWait(driver, 2).until(ExpectedConditions.elementToBeClickable(By.xpath(XPathFormModal + XPathSelectProvincia)));
        new Select(driver.findElement(By.xpath(XPathFormModal + XPathSelectProvincia))).selectByVisibleText(provincia);
    }    
    
    public static void selectPais(String codigoPais, String XPathFormModal, WebDriver driver) {
        String xpathSelectedPais = XPathSelectPais + "/option[@selected='selected' and @value='" + codigoPais + "']";
        if (!isElementPresent(driver, By.xpath(xpathSelectedPais))) {
            new WebDriverWait(driver, 2).until(ExpectedConditions.elementToBeClickable(By.xpath(XPathFormModal + XPathSelectPais)));
            new Select(driver.findElement(By.xpath(XPathFormModal + XPathSelectPais))).selectByValue(codigoPais);
        }
    }    

	private static void sendKeysToInput(DataDirType inputType, String dataToSend, String XPathFormModal, WebDriver driver) {
		String xpathInput = XPathFormModal + getXPathInput(inputType);
//		WebElement input = driver.findElement(By.xpath(xpathInput));
//		input.clear();
//		input.sendKeys(dataToSend);
		//Hay un problema en Chrome que provoca que aleatoriamente se corten los strings enviados mediante SendKeys. Así que debemos reintentarlo si no ha funcionado correctamente
		//https://github.com/angular/protractor/issues/2019
		ifNotValueSetedSendKeysWithRetry(2/*numRetry*/, dataToSend, By.xpath(xpathInput), driver);
	}
}
