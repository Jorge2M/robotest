package com.mng.robotest.test80.mango.test.pageobject.shop.favoritos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mng.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.Talla;
import com.mng.robotest.test80.mango.test.datastored.DataFavoritos;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.testmaker.service.webdriver.wrapper.TypeOfClick;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.bolsa.SecBolsa;
import com.mng.robotest.test80.mango.test.pageobject.shop.bolsa.SecBolsa.StateBolsa;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenusUserWrapper.UserMenu;

/**
 * Clase que define la automatización de las diferentes funcionalidades de la sección de "Wishlist"
 * @author jorge.munoz
 *
 */
public class PageFavoritos extends WebdrvWrapp {
  
    private final WebDriver driver;
	private final ModalFichaFavoritos modalFichaFavoritos;
    
    private final static String XPathBlockFavoritos = "//div[@data-pais and @class[contains(.,'favorites')]]";
    private final static String XPathBlockFavWithArt = XPathBlockFavoritos + "//div[@class[contains(.,'content-garments')]]";
    private final static String XPathArticulo = "//ul[@id='contentDataFavs']/li";
    private final static String XPathButtonEmpty = "//a[@class='favorites-empty-btn']";
    private final static String xPathShareModalButton = "//span[@id='shareIcon']";
    private final static String xPathCloseShareModalButton = "//span[@onclick[contains(.,'showCloseModalShare')]]";
    private final static String xPathWhatsAppShareButton = "//span[@class='modal-share-whatsapp-icon']";
    private final static String xPathTelegramShareButton = "//span[@class='modal-share-telegram-icon']";
    private final static String xPathUrlShareLabel = "//div[@id='linkShareButton']";
    
    private PageFavoritos(WebDriver driver) {
    	this.driver = driver;
    	this.modalFichaFavoritos = ModalFichaFavoritos.getNew(driver);
    }
    
    public static PageFavoritos getNew(WebDriver driver) {
    	return new PageFavoritos(driver);
    }
    
    public ModalFichaFavoritos getModalFichaFavoritos() {
    	return this.modalFichaFavoritos;
    }
    
    private String getXPathArticle(String refProducto, String codigoColor) {
        //Cuando el cambio sobre favoritos suba a PRO se podrá eliminar el xpathOld
        //String xpathOld = "@src[contains(.,'" + refProducto + "_" + codigoColor + "')]"; //img
        String xpathNew = "@style[contains(.,'" + refProducto + "_" + codigoColor + "')]"; //div
        //return (XPathArticulo + "//*[" + xpathOld + " or " + xpathNew + "]/ancestor::li");  
        return (XPathArticulo + "//*[" + xpathNew + "]/ancestor::li");
    }
    
    private String getXPathButtonAddBolsa(String refProducto, String codigoColor) {
        return (getXPathArticle(refProducto, codigoColor) + "//div[@id[contains(.,'garment-btn')] or @id[contains(.,'garmentBtn')]]");
    }
    
    private String getXPathImgProducto(String refProducto, String codigoColor) {
        return (getXPathArticle(refProducto, codigoColor) + "//div[@class[contains(.,'garment-image')]]");
    }    
    
    private String getXPathAspaBorrar(String refProducto, String codigoColor) {
        return (getXPathArticle(refProducto, codigoColor)  + "//span[@class[contains(.,'icofav-eliminar')]]");
    }
    
    private String getXPathCapaTallas(String refProducto, String codigoColor) {
        return (getXPathArticle(refProducto, codigoColor) + "//div[@id[contains(.,'modalSelectSize')]]");
    }

    private String getXPathTalla(String refProducto, String codigoColor) {
        return (getXPathCapaTallas(refProducto, codigoColor) + "//li[@onclick[contains(.,'changeSize')]]");
    } 
    
    // Funcionalidad de Share Favorites (pre)
    
    private String getXPathWithIdItem(int numArticulo) {
        String idItem = driver.findElement(By.xpath("(" + XPathArticulo + ")[" + numArticulo + "]")).getAttribute("id");
        return (XPathArticulo + "[@id='" + idItem + "']");
    }
    
    public void openShareModal() {
    	driver.findElement(By.xpath(xPathShareModalButton)).click();
    }
    
    public void closeShareModal() throws Exception {
    	WebdrvWrapp.clickAndWaitLoad(driver, By.xpath(xPathCloseShareModalButton), TypeOfClick.javascript);
    }
    
    public boolean checkShareModalUntill(int maxSeconds) {
    	return (isElementVisibleUntil(driver, By.xpath(xPathCloseShareModalButton), maxSeconds));
    }
    
    public boolean isShareFavoritesVisible() {
    	return (isElementVisible(driver, By.xpath(xPathShareModalButton)));
    }
    
    public boolean isShareWhatsappFavoritesVisible() {
    	return (isElementVisible(driver, By.xpath(xPathWhatsAppShareButton)));
    }
    
    public boolean isShareTelegramFavoritesVisible() {
    	return (isElementVisible(driver, By.xpath(xPathTelegramShareButton)));
    }
    
    public boolean isShareUrlFavoritesVisible() {
    	return (isElementVisible(driver, By.xpath(xPathUrlShareLabel)));
    }
    
    public boolean checkShareModalInvisible(int secondsToWait) {
    	return (isElementInvisibleUntil(driver, By.xpath(xPathCloseShareModalButton), secondsToWait));
    }

    public boolean isSectionVisible() {
        return (isElementVisible(driver, By.xpath(XPathBlockFavoritos)));
    }
    
    public boolean isSectionArticlesVisibleUntil(int maxSecondsToWait) {
        return (isElementVisibleUntil(driver, By.xpath(XPathBlockFavWithArt), maxSecondsToWait));
    }
    
    public void clearArticuloAndWait(String refArticulo, String codColorArticulo) throws Exception {
        String xpathBorrar = getXPathAspaBorrar(refArticulo, codColorArticulo);
        
        //Ejecutamos el click mediante JavaScript porque en el caso de móvil en ocasiones el aspa de cerrado queda por debajo de la cabecera
        clickAndWaitLoad(driver, By.xpath(xpathBorrar), TypeOfClick.javascript);
    }
    
    public boolean isInvisibleArticleUntil(String referencia, String codColor, int maxSecondsToWait) {
        String xpathArticulo = getXPathArticle(referencia, codColor);
        return (isElementInvisibleUntil(driver, By.xpath(xpathArticulo), maxSecondsToWait));
    }
    
    @SuppressWarnings("static-access")
    public void clearAllArticulos(Channel channel, AppEcom appE) throws Exception {
        //Si la sección no es visible clickamos en favoritos
        if (!isSectionVisible()) {
        	SecMenusWrap secMenus = SecMenusWrap.getNew(channel, appE, driver);
            secMenus.getMenusUser().clickMenuAndWait(UserMenu.favoritos);
        }
        int i=0; //Para evitar posibles bucles infinitos
        while (hayArticulos() && i<50) {
            clear1rstArticuloAndWait();
            i+=1;
        }
    }
    
    public boolean hayArticulos() {
        return (isElementPresent(driver, By.xpath(XPathArticulo)));
    }
    
    public boolean areVisibleArticlesUntil(DataFavoritos dataFavoritos, int maxSecondsToWait) {
        if (dataFavoritos.isEmpty()) {
            return (!hayArticulos());
        }
        
        Iterator<ArticuloScreen> itArticulos = dataFavoritos.getListArticulos().iterator();
        while (itArticulos.hasNext()) {
            ArticuloScreen articulo = itArticulos.next();
            if (!isVisibleArticleUntil(articulo.getRefProducto(), articulo.getCodigoColor(), maxSecondsToWait)) {
                return false;
            }
        }
        
        return true;
    }
    
    public boolean isVisibleArticleUntil(String refArticulo, String codigoColor, int maxSecondsToWait) {
        String xpathArt = getXPathArticle(refArticulo, codigoColor);
        return (isElementVisibleUntil(driver, By.xpath(xpathArt), maxSecondsToWait));
    }
    
    public void clear1rstArticuloAndWait() throws Exception {
        if (hayArticulos()) {
            String xpathArtWithIdItem = getXPathWithIdItem(1);
            
            //Ejecutamos el click mediante JavaScript porque en el caso de móvil en ocasiones el aspa de cerrado queda por debajo de la cabecera
            clickAndWaitLoad(driver, By.xpath(xpathArtWithIdItem + "//span[@class[contains(.,'icofav-eliminar')]]"), TypeOfClick.javascript);
            //driver.findElement(By.xpath(xpathArtWithIdItem + "//span[@class[contains(.,'icofav-eliminar')]]")).click();
            
            new WebDriverWait(driver, 3).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpathArtWithIdItem)));
        }
    }

	public Talla addArticleToBag(String refProducto, String codigoColor, int posicionTalla) throws Exception {
		clickButtonAddToBagAndWait(refProducto, codigoColor);
		Talla tallaSelected = selectTallaAvailableAndWait(refProducto, codigoColor, posicionTalla);
		return tallaSelected;
	}
    
    public void clickButtonAddToBagAndWait(String refProducto, String codigoColor) throws Exception {
        String xpathAdd = getXPathButtonAddBolsa(refProducto, codigoColor);
        driver.findElement(By.xpath(xpathAdd)).click();
        
        //Wait to Div tallas appears
        String xpathCapaTallas = getXPathCapaTallas(refProducto, codigoColor);
        new WebDriverWait(driver, 1).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathCapaTallas)));
    }
    
    public void clickImgProducto(String refProducto, String codigoColor) {
        String xpathImg = getXPathImgProducto(refProducto, codigoColor);
        driver.findElement(By.xpath(xpathImg)).click();
    }
    
    public List<WebElement> getListaTallas(String refProducto, String codigoColor) {
        String xpathTalla = getXPathTalla(refProducto, codigoColor);
        return (driver.findElements(By.xpath(xpathTalla)));
    }
    
    public String selectTallaAndWait(String refProducto, String codigoColor, int posicionTalla, Channel channel) {
        List<WebElement> listaTallas = getListaTallas(refProducto, codigoColor);
        WebElement talla = listaTallas.get(posicionTalla);
        String litTalla = talla.getText();
        talla.click();
        int maxSecondsToWait = 2;
        SecBolsa.isInStateUntil(StateBolsa.Open, channel, maxSecondsToWait, driver);
        return litTalla;
    }
    
    public Talla selectTallaAvailableAndWait(String refProducto, String codigoColor, int posicionTalla) {
        //Filtramos y nos quedamos sólo con las tallas disponibles
        List<WebElement> listTallas = getListaTallas(refProducto, codigoColor);
        List<WebElement> listTallasAvailable = new ArrayList<>();
        for (WebElement talla : listTallas) {
            if (!isElementPresent(talla, By.xpath("./span"))) {
                listTallasAvailable.add(talla);
            }
        }
       
        WebElement tallaDisponible = listTallasAvailable.get(posicionTalla - 1); 
        Talla talla = Talla.from(tallaDisponible.getText());
        tallaDisponible.click();
        
        //Wait to Div tallas disappears
        String xpathCapaTallas = getXPathCapaTallas(refProducto, codigoColor);
        new WebDriverWait(driver, 1).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpathCapaTallas)));
        
        return talla;
    }
    
    public boolean isVisibleButtonEmpty() {
        return (isElementVisible(driver, By.xpath(XPathButtonEmpty)));
    }
}