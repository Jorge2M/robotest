package com.mng.robotest.test80.mango.test.pageobject.shop.favoritos;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.bolsa.SecBolsa;
import com.mng.robotest.test80.mango.test.pageobject.shop.bolsa.SecBolsa.StateBolsa;


public class ModalFichaFavoritos extends WebdrvWrapp {
    
    static String XPathFichaProducto = "//div[@class='favorites-quickview']";
    static String XPathImgColorSelectedFicha = XPathFichaProducto + "//div[@class[contains(.,'color-item')] and @class[contains(.,'active')]]/img";
    
    public static String getXPathFichaProducto(String refProducto) {
        return (XPathFichaProducto + "//img[@src[contains(.,'" + refProducto + "')]]/ancestor::div[@class='favorites-quickview']");
    }
    
    public static String getXPathButtonAddBolsa(String refProducto) {
        return (getXPathFichaProducto(refProducto) + "//div[@class[contains(.,'add-product')]]");
    }
    
    public static String getXPathCapaTallas(String refProducto) {
        return (getXPathFichaProducto(refProducto) + "//div[@id[contains(.,'modalSelectSize')]]");
    }
    
    public static String getXPathSelectorTalla(String refProducto) {
        return (getXPathFichaProducto(refProducto) + "//div[@id[contains(.,'sizeSelector')]]");
    }
    
    public static String getXPathTalla(String refProducto) {
        return (getXPathCapaTallas(refProducto) + "//li[@onclick[contains(.,'changeSize')]]");
    }
    
    public static String getXPathAspaFichaToClose(String refProducto) {
        return (getXPathFichaProducto(refProducto) + "//span[@id='closeQuickviewModal']");
    }
    
    public static String getNombreColorSelectedFicha(WebDriver driver) {
        if (isElementVisible(driver, By.xpath(XPathImgColorSelectedFicha)))
            return (driver.findElement(By.xpath(XPathImgColorSelectedFicha)).getAttribute("title"));
        
        return "";
    }
    
    public static boolean isVisibleFichaUntil(String refProducto, int maxSecondsToWait, WebDriver driver) {
        String xpathFicha = getXPathFichaProducto(refProducto);
        return (isElementVisibleUntil(driver, By.xpath(xpathFicha), maxSecondsToWait));
    }
    
    public static boolean isInvisibleFichaUntil(String refProducto, int maxSecondsToWait, WebDriver driver) {
        String xpathFicha = getXPathFichaProducto(refProducto);
        return (isElementInvisibleUntil(driver, By.xpath(xpathFicha), maxSecondsToWait));
    }
    
    public static boolean isColorSelectedInFicha(String nombreColor, WebDriver driver) {
        String colorSelected = getNombreColorSelectedFicha(driver);
        return (colorSelected.contains(nombreColor));
    }
    
    /**
     * @return el literal de la talla seleccionada
     */
    public static String addArticleToBag(String refProducto, int posicionTalla, Channel channel, WebDriver driver) throws Exception {
        String tallaSelected = selectTallaAvailable(refProducto, posicionTalla, driver);
        clickButtonAddToBagAndWait(refProducto, channel, driver);
        return tallaSelected;
    }
    
    public static void clickButtonAddToBagAndWait(String refProducto, Channel channel, WebDriver driver) throws Exception {
        String xpathAdd = getXPathButtonAddBolsa(refProducto);
        driver.findElement(By.xpath(xpathAdd)).click();
        int maxSecondsToWait = 2;
        SecBolsa.isInStateUntil(StateBolsa.Open, channel, maxSecondsToWait, driver);
    }
    
    public static String selectTalla(String refProducto, int posicionTalla, WebDriver driver) {
        despliegaTallasAndWait(refProducto, driver);
        WebElement talla = getListaTallas(refProducto, driver).get(posicionTalla);
        String litTalla = talla.getText();
        talla.click();
        return litTalla;
    }
    
    public static String selectTallaAvailable(String refProducto, int posicionTalla, WebDriver driver) {
        despliegaTallasAndWait(refProducto, driver);
        List<WebElement> listTallas = getListaTallas(refProducto, driver);
        
        //Filtramos y nos quedamos sólo con las tallas disponibles
        List<WebElement> listTallasAvailable = new ArrayList<>();
        for (WebElement talla : listTallas) {
            if (!isElementPresent(talla, By.xpath("./span")))
                listTallasAvailable.add(talla);
        }
        
        WebElement tallaDisponible = listTallasAvailable.get(posicionTalla - 1); 
        String litTalla = tallaDisponible.getText();
        tallaDisponible.click();
        return litTalla;
    }    
    
    public static List<WebElement> getListaTallas(String refProducto, WebDriver driver) {
        String xpathTalla = getXPathTalla(refProducto);
        return (driver.findElements(By.xpath(xpathTalla)));
    }
    
    public static void despliegaTallasAndWait(String refProducto, WebDriver driver) {
        String xpathSelector = getXPathSelectorTalla(refProducto);
        driver.findElement(By.xpath(xpathSelector)).click();
        String xpathCapaTallas = getXPathCapaTallas(refProducto);
        new WebDriverWait(driver, 1).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathCapaTallas)));
    }
    
    public static void closeFicha(String refProducto, WebDriver driver) {
        String xpathAspa = getXPathAspaFichaToClose(refProducto);
        driver.findElement(By.xpath(xpathAspa)).click();
    }
}
