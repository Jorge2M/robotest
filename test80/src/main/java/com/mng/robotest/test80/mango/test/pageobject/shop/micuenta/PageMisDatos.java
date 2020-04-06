package com.mng.robotest.test80.mango.test.pageobject.shop.micuenta;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageMisDatos {
	
    static String XPathIsPage = "//div[@class='myDetails']";
    static String XPathTitleOk = "//h2[text()[contains(.,'Mis datos')]]";
    static String XPathInputEmail = "//input[@id[contains(.,'cfEmail')]]";
    static String XPathInputNombre = "//input[@id[contains(.,'cfName')]]";
    static String XPathInputApellidos = "//input[@id[contains(.,'cfSname')]]";
    static String XPathInputDireccion = "//input[@id[contains(.,'cfDir1')]]";
    static String XPathInputCodPostal = "//input[@id[contains(.,'cfCp')]]";
    static String XPathInputPoblacion = "//input[@id[contains(.,'cfCity')]]";
    static String XPathBotonGuardarCambios = "//div[@class='submitContent']/input[@type='submit']";
    static String XPathPageResOK = "//*[text()[contains(.,'Tus datos han sido modificados en nuestra base de datos')]]";
    static String XPathInputPasswordTypePassword = "//input[@id[contains(.,'cfPass')] and @type='password']";
    static String XPathInputContentVoid = "//div[@class='inputContent']/input[not(@value)]";
    static String XPathSelectPais = "//select[@id[contains(.,':pais')]]";
    static String XPathSelectProvincia = "//select[@id[contains(.,':estadosPais')]]";
    static String XPathOptionPaisSelected = XPathSelectPais + "/option[@selected]";
    static String XPathOptionProvinciaSelected = XPathSelectProvincia + "/option[@selected]";
    
    public static String getText_inputNombre(WebDriver driver) {
        return (driver.findElement(By.xpath(XPathInputNombre)).getAttribute("value"));
    }
    
    public static String getText_inputApellidos(WebDriver driver) {
        return (driver.findElement(By.xpath(XPathInputApellidos)).getAttribute("value"));
    }
    
    public static String getText_inputEmail(WebDriver driver) {
        return (driver.findElement(By.xpath(XPathInputEmail)).getAttribute("value"));
    }

    public static String getText_inputDireccion(WebDriver driver) {
        return (driver.findElement(By.xpath(XPathInputDireccion)).getAttribute("value"));
    }
    
    public static String getText_inputCodPostal(WebDriver driver) {
        return (driver.findElement(By.xpath(XPathInputCodPostal)).getAttribute("value"));
    }
    
    public static String getText_inputPoblacion(WebDriver driver) {
        return (driver.findElement(By.xpath(XPathInputPoblacion)).getAttribute("value"));
    }
    
    public static String getCodPaisSelected(WebDriver driver) {
    	if (state(Present, By.xpath(XPathOptionPaisSelected), driver).check()) {
            return (driver.findElement(By.xpath(XPathOptionPaisSelected)).getAttribute("value"));
        }
        return "";
    }
    
    public static String getProvinciaSelected(WebDriver driver) {
    	if (state(Present, By.xpath(XPathOptionProvinciaSelected), driver).check()) {
            return (driver.findElement(By.xpath(XPathOptionProvinciaSelected)).getText());
        }
        return "";
    }    
    
    public static boolean isVisiblePasswordTypePassword(WebDriver driver) {
    	return (state(Visible, By.xpath(XPathInputPasswordTypePassword), driver).check());
    }
    
    public static int getNumInputContentVoid(WebDriver driver) {
        return (driver.findElements(By.xpath(XPathInputContentVoid)).size());
    }
    
    public static boolean isPage(WebDriver driver) {
    	return (state(Present, By.xpath(XPathIsPage), driver).check());
    }
    
    public static boolean titleOk(WebDriver driver) {
    	return (state(Present, By.xpath(XPathTitleOk), driver).check());
    }
    
    public static boolean emailIsDisabled(WebDriver driver) {
        //Obtenemos el atributo "disabled" del email
        String cfMailStatus = driver.findElement(By.xpath(XPathInputEmail)).getAttribute("disabled");
        return (cfMailStatus!=null && cfMailStatus.compareTo("false")!=0);
    }
    
    public static String getValueEmailInput(WebDriver driver) {
        return (driver.findElement(By.xpath(XPathInputEmail)).getAttribute("value"));
    }
    
    public static String getValueNombreInput(WebDriver driver) {
        return (driver.findElement(By.xpath(XPathInputNombre)).getAttribute("value"));
    }
    
    public static void setNombreInput(WebDriver driver, String nombre) {
        driver.findElement(By.xpath(XPathInputNombre)).clear();
        driver.findElement(By.xpath(XPathInputNombre)).sendKeys(nombre);
    }
    
    public static void clickGuardarCambios(WebDriver driver) {
    	click(By.xpath(XPathBotonGuardarCambios), driver).exec();
    }
    
    public static boolean pageResOK(WebDriver driver) { 
    	return (state(Present, By.xpath(XPathPageResOK), driver).check());
    }
}
