package com.mng.robotest.test80.mango.test.pageobject.shop.favoritos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.mango.test.data.AppEcom;
import com.mng.robotest.test80.mango.test.datastored.DataFavoritos;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.arq.webdriverwrapper.TypeOfClick;
import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.bolsa.SecBolsa;
import com.mng.robotest.test80.mango.test.pageobject.shop.bolsa.SecBolsa.StateBolsa;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap;

/**
 * Clase que define la automatización de las diferentes funcionalidades de la sección de "Wishlist"
 * @author jorge.munoz
 *
 */
public class PageFavoritos extends WebdrvWrapp {
  
    public static ModalFichaFavoritos modalFichaFavoritos;
    
    static String XPathBlockFavoritos = "//div[@data-pais and @class[contains(.,'favorites')]]";
    static String XPathBlockFavWithArt = XPathBlockFavoritos + "//div[@class[contains(.,'content-garments')]]";
    static String XPathArticulo = "//ul[@id='contentDataFavs']/li";
    static String XPathButtonEmpty = "//a[@class='favorites-empty-btn']";
    static String xPathShareModalButton = "//span[@id='shareIcon']";
    static String xPathCloseShareModalButton = "//span[@onclick[contains(.,'showCloseModalShare')]]";
    static String xPathWhatsAppShareButton = "//span[@class='modal-share-whatsapp-icon']";
    static String xPathTelegramShareButton = "//span[@class='modal-share-telegram-icon']";
    static String xPathUrlShareLabel = "//div[@id='linkShareButton']";
    
    public static String getXPathArticle(String refProducto, String codigoColor) {
        //TODO cuando el cambio sobre favoritos suba a PRO se podrá eliminar el xpathOld
        String xpathOld = "@src[contains(.,'" + refProducto + "_" + codigoColor + "')]"; //img
        String xpathNew = "@style[contains(.,'" + refProducto + "_" + codigoColor + "')]"; //div
        return (XPathArticulo + "//*[" + xpathOld + " or " + xpathNew + "]/ancestor::li");  
    }
    
    public static String getXPathButtonAddBolsa(String refProducto, String codigoColor) {
        return (getXPathArticle(refProducto, codigoColor) + "//div[@id[contains(.,'garment-btn')] or @id[contains(.,'garmentBtn')]]");
    }
    
    public static String getXPathImgProducto(String refProducto, String codigoColor) {
        return (getXPathArticle(refProducto, codigoColor) + "//div[@class[contains(.,'garment-image')]]");
    }    
    
    public static String getXPathAspaBorrar(String refProducto, String codigoColor) {
        return (getXPathArticle(refProducto, codigoColor)  + "//span[@class[contains(.,'icofav-eliminar')]]");
    }
    
    public static String getXPathCapaTallas(String refProducto, String codigoColor) {
        return (getXPathArticle(refProducto, codigoColor) + "//div[@id[contains(.,'modalSelectSize')]]");
    }

    public static String getXPathTalla(String refProducto, String codigoColor) {
        return (getXPathCapaTallas(refProducto, codigoColor) + "//li[@onclick[contains(.,'changeSize')]]");
    } 
    
    // Funcionalidad de Share Favorites (pre)
    
    public static void openShareModal(WebDriver driver) {
    	driver.findElement(By.xpath(xPathShareModalButton)).click();
    }
    
    public static void closeShareModal(WebDriver driver) throws Exception {
    	WebdrvWrapp.clickAndWaitLoad(driver, By.xpath(xPathCloseShareModalButton), TypeOfClick.javascript);
    	//driver.findElement(By.xpath(xPathCloseShareModalButton)).click();
    }
    
    public static boolean checkShareModalUntill(int maxSeconds, WebDriver driver) {
    	return (isElementVisibleUntil(driver, By.xpath(xPathCloseShareModalButton), maxSeconds));
    }
    
    public static boolean isShareFavoritesVisible(WebDriver driver) {
    	return (isElementVisible(driver, By.xpath(xPathShareModalButton)));
    }
    
    public static boolean isShareWhatsappFavoritesVisible(WebDriver driver) {
    	return (isElementVisible(driver, By.xpath(xPathWhatsAppShareButton)));
    }
    
    public static boolean isShareTelegramFavoritesVisible(WebDriver driver) {
    	return (isElementVisible(driver, By.xpath(xPathTelegramShareButton)));
    }
    
    public static boolean isShareUrlFavoritesVisible(WebDriver driver) {
    	return (isElementVisible(driver, By.xpath(xPathUrlShareLabel)));
    }
    
    public static boolean checkShareModalInvisible(WebDriver driver, int secondsToWait) {
    	return (isElementInvisibleUntil(driver, By.xpath(xPathCloseShareModalButton), secondsToWait));
    }
    
//    public static String getXPathTalla(String refProducto, int posicionTalla) {
//        return (getXPathCapaTallas(refProducto) + "//li[@onclick[contains(.,'changeSize')]][" + posicionTalla + "]");
//    }
    
    /**
     * @return el XPath de un determinado artículo incluyendo su id de ítem
     */
    public static String getXPathWithIdItem(int numArticulo, WebDriver driver) {
        String idItem = driver.findElement(By.xpath("(" + XPathArticulo + ")[" + numArticulo + "]")).getAttribute("id");
        return (XPathArticulo + "[@id='" + idItem + "']");
    }
  
    public static boolean isSectionVisible(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathBlockFavoritos)));
    }
    
    public static boolean isSectionArticlesVisibleUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementVisibleUntil(driver, By.xpath(XPathBlockFavWithArt), maxSecondsToWait));
    }
    
    public static void clearArticuloAndWait(String refArticulo, String codColorArticulo, WebDriver driver) 
    throws Exception {
        String xpathBorrar = getXPathAspaBorrar(refArticulo, codColorArticulo);
        
        //Ejecutamos el click mediante JavaScript porque en el caso de móvil en ocasiones el aspa de cerrado queda por debajo de la cabecera
        clickAndWaitLoad(driver, By.xpath(xpathBorrar), TypeOfClick.javascript);
        //driver.findElement(By.xpath(xpathBorrar)).click();
    }
    
    public static boolean isInvisibleArticleUntil(String referencia, String codColor, int maxSecondsToWait, WebDriver driver) {
        String xpathArticulo = getXPathArticle(referencia, codColor);
        return (isElementInvisibleUntil(driver, By.xpath(xpathArticulo), maxSecondsToWait));
    }
    
    @SuppressWarnings("static-access")
    public static void clearAllArticulos(Channel channel, AppEcom appE, WebDriver driver) throws Exception {
        //Si la sección no es visible clickamos en favoritos
        if (!isSectionVisible(driver)) {
            SecMenusWrap.secMenusUser.clickFavoritosAndWait(channel, appE, driver);
        }
        int i=0; //Para evitar posibles bucles infinitos
        while (hayArticulos(driver) && i<50) {
            clear1rstArticuloAndWait(driver);
            i+=1;
        }
    }
    
    public static boolean hayArticulos(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathArticulo)));
    }
    
    public static boolean areVisibleArticlesUntil(DataFavoritos dataFavoritos, int maxSecondsToWait, WebDriver driver) {
        if (dataFavoritos.isEmpty()) {
            return (!hayArticulos(driver));
        }
        
        Iterator<ArticuloScreen> itArticulos = dataFavoritos.getListArticulos().iterator();
        while (itArticulos.hasNext()) {
            ArticuloScreen articulo = itArticulos.next();
            if (!isVisibleArticleUntil(articulo.getRefProducto(), articulo.getCodigoColor(), maxSecondsToWait, driver)) {
                return false;
            }
        }
        
        return true;
    }
    
    public static boolean isVisibleArticleUntil(String refArticulo, String codigoColor, int maxSecondsToWait, WebDriver driver) {
        String xpathArt = getXPathArticle(refArticulo, codigoColor);
        return (isElementVisibleUntil(driver, By.xpath(xpathArt), maxSecondsToWait));
    }
    
    /**
     * Borra el 1er artículo y espera a que desaparezca
     */
    public static void clear1rstArticuloAndWait(WebDriver driver) throws Exception {
        if (hayArticulos(driver)) {
            String xpathArtWithIdItem = getXPathWithIdItem(1/*numArticulo*/, driver);
            
            //Ejecutamos el click mediante JavaScript porque en el caso de móvil en ocasiones el aspa de cerrado queda por debajo de la cabecera
            clickAndWaitLoad(driver, By.xpath(xpathArtWithIdItem + "//span[@class[contains(.,'icofav-eliminar')]]"), TypeOfClick.javascript);
            //driver.findElement(By.xpath(xpathArtWithIdItem + "//span[@class[contains(.,'icofav-eliminar')]]")).click();
            
            new WebDriverWait(driver, 3).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpathArtWithIdItem)));
        }
    }
    
    /**
     * @return el literal de la talla seleccionada
     */
    public static String addArticleToBag(String refProducto, String codigoColor, int posicionTalla, WebDriver driver) throws Exception {
        clickButtonAddToBagAndWait(refProducto, codigoColor, driver);
        String tallaSelected = selectTallaAvailableAndWait(refProducto, codigoColor, posicionTalla, driver);
        return tallaSelected;
    }
    
    public static void clickButtonAddToBagAndWait(String refProducto, String codigoColor, WebDriver driver) throws Exception {
        String xpathAdd = getXPathButtonAddBolsa(refProducto, codigoColor);
        driver.findElement(By.xpath(xpathAdd)).click();
        
        //Wait to Div tallas appears
        String xpathCapaTallas = getXPathCapaTallas(refProducto, codigoColor);
        new WebDriverWait(driver, 1).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathCapaTallas)));
    }
    
    public static void clickImgProducto(String refProducto, String codigoColor, WebDriver driver) {
        String xpathImg = getXPathImgProducto(refProducto, codigoColor);
        driver.findElement(By.xpath(xpathImg)).click();
    }
    
    public static List<WebElement> getListaTallas(String refProducto, String codigoColor, WebDriver driver) {
        String xpathTalla = getXPathTalla(refProducto, codigoColor);
        return (driver.findElements(By.xpath(xpathTalla)));
    }
    
    public static String selectTallaAndWait(String refProducto, String codigoColor, int posicionTalla, Channel channel, WebDriver driver) {
        List<WebElement> listaTallas = getListaTallas(refProducto, codigoColor, driver);
        WebElement talla = listaTallas.get(posicionTalla);
        String litTalla = talla.getText();
        talla.click();
        int maxSecondsToWait = 2;
        SecBolsa.isInStateUntil(StateBolsa.Open, channel, maxSecondsToWait, driver);
        return litTalla;
    }
    
    public static String selectTallaAvailableAndWait(String refProducto, String codigoColor, int posicionTalla, WebDriver driver) {
        //Filtramos y nos quedamos sólo con las tallas disponibles
        List<WebElement> listTallas = getListaTallas(refProducto, codigoColor, driver);
        List<WebElement> listTallasAvailable = new ArrayList<>();
        for (WebElement talla : listTallas) {
            if (!isElementPresent(talla, By.xpath("./span"))) {
                listTallasAvailable.add(talla);
            }
        }
       
        WebElement tallaDisponible = listTallasAvailable.get(posicionTalla - 1); 
        String litTalla = tallaDisponible.getText();
        tallaDisponible.click();
        
        //Wait to Div tallas disappears
        String xpathCapaTallas = getXPathCapaTallas(refProducto, codigoColor);
        new WebDriverWait(driver, 1).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpathCapaTallas)));
        
        return litTalla;
    }
    
    public static boolean isVisibleButtonEmpty(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathButtonEmpty)));
    }
}