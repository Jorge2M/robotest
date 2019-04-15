package com.mng.robotest.test80.mango.test.pageobject.shop.micuenta;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;


public class PageMisDatos extends WebdrvWrapp {
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
        if (isElementPresent(driver, By.xpath(XPathOptionPaisSelected))) {
            return (driver.findElement(By.xpath(XPathOptionPaisSelected)).getAttribute("value"));
        }
        return "";
    }
    
    public static String getProvinciaSelected(WebDriver driver) {
        if (isElementPresent(driver, By.xpath(XPathOptionProvinciaSelected))) {
            return (driver.findElement(By.xpath(XPathOptionProvinciaSelected)).getText());
        }
        return "";
    }    
    
    public static boolean isVisiblePasswordTypePassword(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathInputPasswordTypePassword)));
    }
    
    public static int getNumInputContentVoid(WebDriver driver) {
        return (driver.findElements(By.xpath(XPathInputContentVoid)).size());
    }
    
    public static boolean isPage(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathIsPage)));
    }
    
    public static boolean titleOk(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathTitleOk)));
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
    
    public static void clickGuardarCambios(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathBotonGuardarCambios));
    }
    
    public static boolean pageResOK(WebDriver driver) { 
        return (isElementPresent(driver, By.xpath(XPathPageResOK)));
    }
}
