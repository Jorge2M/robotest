package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;
import com.mng.testmaker.utils.conf.Log4jConfig;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.envio.DataDeliveryPoint;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.envio.DataSearchDeliveryPoint;


public class SecSelectDPoint extends WebdrvWrapp {
    
    public static enum TypeDeliveryPoint {tienda, droppoint, any}
    
    private static String XPathSearchBox = "//div[@class[contains(.,'searchBox')]]";
    private static String XPathInputProvincia = XPathSearchBox + "//input[@class[contains(.,'searchValue')]]";
    private static String XPathSelectProvincia = XPathSearchBox + "//select[@class[contains(.,'id_city')]]";
    private static String XPathContainerList = "//div[@class='list__container' or @class[contains(.,'list-container')]]"; //Desktop or Movil
    private static String XPathDeliveryPoint = XPathContainerList + "//div[@data-shopid]";
    private static String XPathDeliveryPointSelected = XPathDeliveryPoint.replace("@data-shopid", "@data-shopid and @class[contains(.,'selected')]");
    private static String XPathNameDPoint = "//p[@class='dp__title' or @class='dp-title']";   
    private static String XPathAddressDPoint = "//p[@class='dp__address' or @class='dp-address']";
    private static String XPathPostalcodeDPoint = "//p[@class='dp__postalcode' or @class='dp-postal-code']";
    private static String XPathColeccionesDPoint = "//p[@class='dp__brands' or @class='dp-brands']";
    private static String XPathTelefonoDPoint = "//p[@class='dp__phone' or @class='dp-phone']";  
    private static String XPathSeleccionarButton = XPathContainerList + "//span[@id[contains(.,'selectButton')]]";
    
    public static void sendProvincia(String provincia, WebDriver driver) {
    	if (isInputProvinciaVisible(driver)) {
    		sendProvinciaToInput(provincia, driver);
    	} else {
    		sendProvinciaToSelect(provincia, driver);
    	}
    }
    
    private static boolean isInputProvinciaVisible(WebDriver driver) {
    	return (WebdrvWrapp.isElementVisible(driver, By.xpath(XPathInputProvincia)));
    }
 
    private static void sendProvinciaToInput(String provincia, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputProvincia)).clear();
        driver.findElement(By.xpath(XPathInputProvincia)).sendKeys(provincia);
        driver.findElement(By.xpath(XPathInputProvincia)).sendKeys(Keys.RETURN);
    }
    
    private static void sendProvinciaToSelect(String provincia, WebDriver driver) {
    	new Select(driver.findElement(By.xpath(XPathSelectProvincia))).selectByVisibleText(provincia);
    }
    
    public static List<WebElement> getListDeliveryPoints(WebDriver driver) {
        return (driver.findElements(By.xpath(XPathDeliveryPoint)));
    }
    
    public static WebElement getDeliveryPointSelected(WebDriver driver) {
        return (driver.findElement(By.xpath(XPathDeliveryPointSelected)));
    }
    
    public static boolean isDroppointSelected(int position, WebDriver driver) {
        List<WebElement> listDp = getListDeliveryPoints(driver);
        WebElement dpElem = listDp.get(position-1);
        return (dpElem.getAttribute("class")!=null && dpElem.getAttribute("class").contains("selected"));
    }
    
    public static boolean isDroppointVisibleUntil(int position, int maxSecondsToWait, WebDriver driver) {
        String xpathDeliveryPoint = "(" + XPathDeliveryPoint + ")[" + position + "]";  
        return (isElementVisibleUntil(driver, By.xpath(xpathDeliveryPoint), maxSecondsToWait));  
    }
    
    public static TypeDeliveryPoint getTypeDeliveryPoint(int position, WebDriver driver) {
        List<WebElement> listDp = getListDeliveryPoints(driver);
        WebElement dpElem = listDp.get(position-1);
        return getTypeDeliveryPoint(dpElem);
    }
    
    public static TypeDeliveryPoint getTypeDeliveryPoint(WebElement dpElement) {
    	WebElement dpTitle = getElementVisible(dpElement, By.xpath(XPathNameDPoint));
    	if (dpTitle!=null && !dpTitle.getText().toLowerCase().contains("mango")) {
            return TypeDeliveryPoint.droppoint;
    	}
        return TypeDeliveryPoint.tienda;        
    }
    
    public static DataDeliveryPoint clickDeliveryPointAndGetData(int position, WebDriver driver) throws Exception {
        List<WebElement> listDp = getListDeliveryPoints(driver);
        listDp.get(position-1).click();
        return getDataDeliveryPointSelected(driver);
    }
    
    public static DataDeliveryPoint getDataDeliveryPointSelected(WebDriver driver) throws Exception {
        DataDeliveryPoint dataDp = new DataDeliveryPoint();
        waitForPageLoaded(driver); //For avoid StaleElementReferenceException
        WebElement dpSelected = getDeliveryPointSelected(driver);
        dataDp.setTypeDeliveryPoint(getTypeDeliveryPoint(dpSelected));
        dataDp.setCodigo(dpSelected.getAttribute("data-shopid"));
        
        //Recuperamos los datos del DeliveryPoint mirando de evitar excepciones de "StaleElement"
        if (isElementPresent(driver, By.xpath(XPathDeliveryPointSelected + XPathNameDPoint))) {
            dataDp.setName(driver.findElement(By.xpath(XPathDeliveryPointSelected + XPathNameDPoint)).getText());
        }
        if (isElementPresent(driver, By.xpath(XPathDeliveryPointSelected + XPathColeccionesDPoint))) {
            dataDp.setColecciones(driver.findElement(By.xpath(XPathDeliveryPointSelected + XPathColeccionesDPoint)).getText());
        }
        if (isElementPresent(driver, By.xpath(XPathDeliveryPointSelected + XPathAddressDPoint))) {
            dataDp.setDireccion(driver.findElement(By.xpath(XPathDeliveryPointSelected + XPathAddressDPoint)).getText());
        }
        if (isElementPresent(driver, By.xpath(XPathDeliveryPointSelected + XPathPostalcodeDPoint))) {
            dataDp.setCodPostal(driver.findElement(By.xpath(XPathDeliveryPointSelected + XPathPostalcodeDPoint)).getText());
        }
        if (isElementPresent(driver, By.xpath(XPathTelefonoDPoint))) {
            dataDp.setTelefono(driver.findElement(By.xpath(XPathTelefonoDPoint)).getText());
        }
        return dataDp;
    }
    
    public static void clickSelectButtonAndWait(int maxSecondsToWait, WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathSeleccionarButton), maxSecondsToWait);
    }
    
    public static boolean deliveryPointSelectedContainsPoblacionUntil(DataSearchDeliveryPoint dataSearchDp, int maxSecondsToWait, WebDriver driver) 
    throws Exception {
        for (int i=0; i<maxSecondsToWait; i++) {
            try {
            	DataDeliveryPoint dataDp = getDataDeliveryPointSelected(driver);
            	switch (dataSearchDp.typeData) {
            	case Provincia:
	                if (dataDp.getCPandPoblacion().contains(dataSearchDp.data)) {
	                    return true;
	                }
	                break;
            	case CodigoPostal:
	                if (dataDp.getCodPostal().contains(dataSearchDp.data)) {
	                    return true;
	                }
	                break;
            	}
            }
            catch (Exception e) {
                LogManager.getLogger(Log4jConfig.log4jLogger).warn("Exception selecting Provincia in droppoints", e);
            }
            
            Thread.sleep(1000);
        }
        
        return false;
    }
}
