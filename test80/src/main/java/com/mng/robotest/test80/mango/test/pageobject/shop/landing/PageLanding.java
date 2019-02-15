package com.mng.robotest.test80.mango.test.pageobject.shop.landing;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleriaDesktop;


/**
 * Clase que define la automatización de las diferentes funcionalidades de la página HOME o HOMEMARCAS (la página inicial multimarca o la asociada a cada pestaña)
 * @author jorge.munoz
 *
 */
public class PageLanding extends WebdrvWrapp {
    static String XPathMainContentPais = "//div[@class[contains(.,'main-content')] and @data-pais]";
    static String XPathContenido = "//div[@class[contains(.,'container-fluid home')]]";
    static String XPathSlider = "//section[@class='entitieswrapper']//div[@class[contains(.,'vsv-slide')]]";
    static String XPathEditItem = "//div[@class[contains(.,'item-edit')] and @data-id]";
    static String XPathMapT1 = "//map[@name[contains(.,'item_')]]/..";
    static String XPathMapT2 = "//img[@class[contains(.,'responsive')] and @hidefocus='true']";
    
    /**
     * @return si nos encontramos en la página
     */
    public static boolean isPage(WebDriver driver) throws Exception {
        return (isElementPresent(driver, By.xpath(XPathContenido)));
    }
    
    public static String getCodigoPais(WebDriver driver) {
        if (isElementPresent(driver, By.xpath(XPathMainContentPais)))
            return (driver.findElement(By.xpath(XPathMainContentPais)).getAttribute("data-pais"));
        
        return "";
    }
    
    /**
     * Función que indica si hay sliders o no en el contenido de la página
     */
    public static boolean haySliders(WebDriver driver) {
        return isElementVisible(driver, By.xpath(XPathSlider));
    }
    
    /**
     * Función que indica si hay maps o no en el contenido de la página
     */
    public static boolean hayMaps(WebDriver driver) {
        boolean hayMaps = false;
        List<WebElement> listaMaps = getListaMaps(driver);
        if (listaMaps!=null && listaMaps.size()>0)
            hayMaps = true;
        
        return hayMaps;
    }
    
    public static boolean hayItemsEdits(WebDriver driver) {
        List<WebElement> listaItemsEdits = getListaItemsEdit(driver);
        if (listaItemsEdits!=null && listaItemsEdits.size()>0)
            return true;
        
        return false;
    }
    
    /**
     * Obtenemos la lista de banners de la página
     */
    public static List<WebElement> getListaMaps(WebDriver driver) {
        // Seleccionamos cada uno de los banners visibles
        List<WebElement> listMaps;
        listMaps = UtilsMangoTest.findDisplayedElements(driver, By.xpath(XPathMapT1));
        listMaps.addAll(UtilsMangoTest.findDisplayedElements(driver, By.xpath(XPathMapT2)));
        return listMaps;
    }
    
    public static List<WebElement> getListaItemsEdit(WebDriver driver) {
        List<WebElement> listItemsEdits;
        listItemsEdits = UtilsMangoTest.findDisplayedElements(driver, By.xpath(XPathEditItem));
        return listItemsEdits;        
    }
    
    /**
     * Función que indica si hay secciones de vídeo
     */
    public static boolean hayIframes(WebDriver driver) {
        boolean hayIFrames = false;
        List<WebElement> listaIFrames = UtilsMangoTest.findDisplayedElements(driver, By.xpath("//iframe"));
        if (listaIFrames!=null && listaIFrames.size()>0)
            hayIFrames = true;
        
        return hayIFrames;
    }
    
    /**
     * Función que indica si hay imágenes o no en el contenido de la página
     */
    public static boolean hayImgsEnContenido(final WebDriver driver) {
        boolean banners = true;
        String xpathImg = "";
        try {
            if (isElementPresent(driver, By.xpath("//*[@class[contains(.,'bannerHome')]]"))) {
                // new WebDriverWait(dFTest.driver, 15).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class[contains(.,'bannerHome')]]")));
                xpathImg = "//*[@class[contains(.,'bannerHome')]]//img";
            } else {
                if (isElementPresent(driver, By.xpath("//*[@id[contains(.,'homeContent')]]"))) {
                    // new WebDriverWait(dFTest.driver, 15).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id[contains(.,'homeContent')]]")));
                    xpathImg = "//*[@id[contains(.,'homeContent')]]//img";
                } else {
                    if (isElementPresent(driver, By.xpath("//*[@class[contains(.,'contentHolder')]]"))) {
                        // new WebDriverWait(dFTest.driver, 15).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id[contains(.,'homeContent')]]")));
                        xpathImg = "//*[@class[contains(.,'contentHolder')]]//iframe";
                    } else {
                        if (isElementPresent(driver, By.xpath("//*[@id[contains(.,'bodyContent')]]"))) {
                            // new WebDriverWait(dFTest.driver, 15).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id[contains(.,'homeContent')]]")));
                            xpathImg = "//*[@id[contains(.,'bodyContent')]]//img";
                        } else {
                            banners = false;
                        }
                    }
                }
            }

            new WebDriverWait(driver, 15).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathImg)));
        } catch (Exception e) {
            banners = false;
        }

        return banners;
    }

    /**
     * Función que indica si hay secciones, artículos, artículo o banners en la página
     */
    public static boolean haySecc_Art_Banners(AppEcom app, WebDriver driver) throws Exception {
    	PageGaleria pageGaleria = PageGaleria.getInstance(Channel.desktop, app, driver);
        return (((PageGaleriaDesktop)pageGaleria).isVisibleAnyArticle() ||
        		isElementPresent(driver, By.xpath("(//section[@id='section1'] | " + 
                                                              "//div[@class[contains(.,'datos_ficha_producto')]] | " +
                                                              "//*[@class='celda'])")));
    }
}
