package com.mng.robotest.test80.mango.test.pageobject.shop.registro;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.arq.webdriverwrapper.TypeOfClick;
import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.Page2IdentCheckout;


public class PageRegistroDirec extends WebdrvWrapp {

    private static String XPathDivError = "//div[@class='errorValidation']";
    private static String XPathInputDirec = "//input[@id[contains(.,':cfDir1')]]";
    private static String XPathSelectPais = "//select[@id[contains(.,':pais')]]";
    private static String XPathInputCodPostal = "//input[@id[contains(.,':cfCp')]]";
    private static String XPathSelectPoblacion = "//select[@id[contains(.,':localidades')]]";
    private static String XPathSelectProvincia = "//select[@id[contains(.,':estadosPais')]]";
    private static String XPathFinalizarButton = "//form[@id[contains(.,'Step')]]//input[@type='submit']";
    
    /**
     * @return si es visible o no algún mensaje de error asociado al campo de input
     */
    public static int getNumberMsgInputInvalid(WebDriver driver) {
        return (getNumElementsVisible(driver, By.xpath(XPathDivError)));
    }
    
    public static void sendDataAccordingCountryToInputs(HashMap<String,String> dataRegistro, Pais pais, WebDriver driver) throws Exception {
        dataRegistro.putAll(Page2IdentCheckout.inputDataPorDefectoSegunPais(pais, dataRegistro.get("cfEmail"), false, false, driver));
    }
    
    public static void sendDataToInputs(ListDataRegistro dataToSend, WebDriver driver, int repeat) throws Exception {
        for (int i=0; i<repeat; i++) {
            for (DataRegistro dataInput : dataToSend.getDataPageDirec()) {
                switch (dataInput.getDataRegType()) {
                case direccion:
                    sendDataToDireccionIfNotExist(dataInput.getData(), driver);
                    break;            
                case codpais:
                    sendDataToPaisIfNotExist(dataInput.getData(), driver);
                    break;
                case codpostal:
                    sendDataToCodPostalIfNotExist(dataInput.getData(), driver);
                    break;
                case poblacion:
                    sendDataToPoblacion(dataInput.getData(), driver);
                    break;
                case provincia:
                    sendDataToProvincia(dataInput.getData(), driver);
                    break;
                default:
                    break;
                }
            }
            
            Thread.sleep(1000);
        }
    }
    
    public static void sendDataToDireccionIfNotExist(String direccion, WebDriver driver) {
        sendKeysToInputIfNotExist(direccion, XPathInputDirec, driver);
    }
    
    public static void sendDataToPaisIfNotExist(String codigoPais, WebDriver driver) {
        String xpathSelectedPais = XPathSelectPais + "/option[@selected='selected' and @value='" + codigoPais + "']";
        if (isElementPresent(driver, By.xpath(xpathSelectedPais))) {
            new Select(driver.findElement(By.xpath(XPathSelectPais))).selectByValue(codigoPais);
        }
    }    
    
    public static void sendDataToCodPostalIfNotExist(String codPostal, WebDriver driver) {
        sendKeysToInputIfNotExist(codPostal, XPathInputCodPostal, driver);
    }
    
    public static void sendDataToPoblacion(String poblacion, WebDriver driver) {
        new Select(driver.findElement(By.xpath(XPathSelectPoblacion))).selectByValue(poblacion);
    }
    
    public static void sendDataToProvincia(String provincia, WebDriver driver) {
        String valueProvincia = driver.findElement(By.xpath(XPathSelectProvincia + "/option[text()='" + provincia + "']")).getAttribute("value");
        new Select(driver.findElement(By.xpath(XPathSelectProvincia))).selectByValue(valueProvincia);
    }
    
    public static boolean isVisibleFinalizarButton(WebDriver driver) {
    	return (isElementVisible(driver, By.xpath(XPathFinalizarButton)));
    }
    
    public static void clickFinalizarButton(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathFinalizarButton));
        
        //Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no funciona así que ejecutamos un 2o 
        if (isVisibleFinalizarButton(driver)) {
            clickAndWaitLoad(driver, By.xpath(XPathFinalizarButton), TypeOfClick.javascript);
        }
    }
    
    private static void sendKeysToInputIfNotExist(String dataToSend, String xpathInput, WebDriver driver) {
        if (driver.findElement(By.xpath(xpathInput)).getAttribute("value").compareTo(dataToSend)!=0) {
            driver.findElement(By.xpath(xpathInput)).clear();
            driver.findElement(By.xpath(xpathInput)).sendKeys(dataToSend);
            driver.findElement(By.xpath(xpathInput)).sendKeys(Keys.TAB);
        }
    }
}
