package com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

public class SecCarruselDesktop extends WebdrvWrapp {
	
	static String TagIdCarrusel = "@CarruselId";
	static String XPathLinkCarrGenRelToLinea = "//div[@class[contains(.,'carousel-block')]]/a[@data-label]";
    static String XPathLinkCarruselRelToGenWithTag = "//self::*[@data-label[contains(.,'-" + TagIdCarrusel + "')]]";
    
    static String getXPathCarrouselLink(LineaType lineaType, AppEcom app) {
        String xpathCapaMenuLinea = SecBloquesMenuDesktop.getXPathCapaMenusLinea(lineaType, app);
        return (xpathCapaMenuLinea + XPathLinkCarrGenRelToLinea);
    }
    
    static String getXPathLinkCarruselLinea(LineaType lineaId, String idCarrusel, AppEcom app) {
        String xpathCarrGen = getXPathCarrouselLink(lineaId, app);
        String xpathCarrusel = XPathLinkCarruselRelToGenWithTag.replace(TagIdCarrusel, idCarrusel);
        return (xpathCarrGen + xpathCarrusel);
    }
    
    /**
     * @return indicador de si están visibles todos los bloques que aparecen al realizar 'Hover' sobre una línea
     */
    public static boolean isVisibleCarrusels(Linea linea, AppEcom app, WebDriver driver) {
        String[] listCarrusels = linea.getListCarrusels();
        for (int i=0; (i<listCarrusels.length); i++) {
            if (!isVisibleCarrusel(linea, listCarrusels[i], app, driver)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * @param idCarrusel el identificador del carrusel tal como se encuentra definidio en el XML de países
     * @return si es visible o no un carrusel concreto
     */
    public static boolean isVisibleCarrusel(Linea linea, String idCarrusel, AppEcom app, WebDriver driver) {
        String xpathCarrousel = getXPathLinkCarruselLinea(linea.getType(), idCarrusel, app);
        return (isElementVisible(driver, By.xpath(xpathCarrousel)));
    }
    
    public static boolean isPresentCarrusel(Linea linea, String idCarrusel, AppEcom app, WebDriver driver) {
        String xpathCarrousel = getXPathLinkCarruselLinea(linea.getType(), idCarrusel, app);
        return (isElementPresent(driver, By.xpath(xpathCarrousel)));
    }    
    
    /**
     * Retorna el número de bloques nuevo existentes en la página
     */
    public static int getNumCarrousels(LineaType lineaNuevoId, AppEcom app, WebDriver driver) {
        String xpathCarrousels = getXPathCarrouselLink(lineaNuevoId, app);
        return (driver.findElements(By.xpath(xpathCarrousels)).size());
    }    
    
    /**
     * Se clicka unos de los bloques (carrouseles) que aparecen cuando se realiza un 'Hover' sobre la línea Nuevo en Desktop
     */
    public static void clickCarrousel(Pais pais, LineaType lineaType, String idCarrusel, AppEcom app, WebDriver driver) throws Exception {
        String xpathCarrousel = getXPathLinkCarruselLinea(lineaType, idCarrusel, app);
        waitClickAndWaitLoad(driver, 1, By.xpath(xpathCarrousel));
    }
    
    public static boolean isPresentCarrousel(LineaType lineaType, AppEcom app, WebDriver driver) {
        String xpathBanner = getXPathCarrouselLink(lineaType, app);
        return isElementPresent(driver, By.xpath(xpathBanner)); 
    }
}
