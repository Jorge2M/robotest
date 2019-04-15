package com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

public class SecCarruselDesktop extends WebdrvWrapp {
	
	static String TagIdCarrusel = "@CarruselId";
	static String XPathLinkCarrGenRelToLinea = "//div[@class[contains(.,'carousel-block')]]/a[@data-label]";
    static String XPathLinkCarruselRelToGenWithTag = "//self::*[@data-label[contains(.,'-" + TagIdCarrusel + "')]]";
    
    static String getXPathCarrouselLink(LineaType lineaType) {
        String xpathCapaMenuLinea = SecBloquesMenuDesktop.getXPathCapaMenusLinea(lineaType);
        return (xpathCapaMenuLinea + XPathLinkCarrGenRelToLinea);
    }
    
    static String getXPathLinkCarruselLinea(LineaType lineaId, String idCarrusel) {
        String xpathCarrGen = getXPathCarrouselLink(lineaId);
        String xpathCarrusel = XPathLinkCarruselRelToGenWithTag.replace(TagIdCarrusel, idCarrusel);
        return (xpathCarrGen + xpathCarrusel);
    }
    
    /**
     * @return indicador de si están visibles todos los bloques que aparecen al realizar 'Hover' sobre una línea
     */
    public static boolean isVisibleCarrusels(Linea linea, WebDriver driver) {
        String[] listCarrusels = linea.getListCarrusels();
        for (int i=0; (i<listCarrusels.length); i++) {
            if (!isVisibleCarrusel(linea, listCarrusels[i], driver)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * @param idCarrusel el identificador del carrusel tal como se encuentra definidio en el XML de países
     * @return si es visible o no un carrusel concreto
     */
    public static boolean isVisibleCarrusel(Linea linea, String idCarrusel, WebDriver driver) {
        String xpathCarrousel = getXPathLinkCarruselLinea(linea.getType(), idCarrusel);
        return (isElementVisible(driver, By.xpath(xpathCarrousel)));
    }
    
    public static boolean isPresentCarrusel(Linea linea, String idCarrusel, WebDriver driver) {
        String xpathCarrousel = getXPathLinkCarruselLinea(linea.getType(), idCarrusel);
        return (isElementPresent(driver, By.xpath(xpathCarrousel)));
    }    
    
    /**
     * Retorna el número de bloques nuevo existentes en la página
     */
    public static int getNumCarrousels(LineaType lineaNuevoId, WebDriver driver) {
        String xpathCarrousels = getXPathCarrouselLink(lineaNuevoId);
        return (driver.findElements(By.xpath(xpathCarrousels)).size());
    }    
    
    /**
     * Se clicka unos de los bloques (carrouseles) que aparecen cuando se realiza un 'Hover' sobre la línea Nuevo en Desktop
     */
    public static void clickCarrousel(Pais pais, LineaType lineaType, String idCarrusel, WebDriver driver) throws Exception {
        String xpathCarrousel = getXPathLinkCarruselLinea(lineaType, idCarrusel);
        waitClickAndWaitLoad(driver, 1, By.xpath(xpathCarrousel));
    }
    
    public static boolean isPresentCarrousel(LineaType lineaType, WebDriver driver) {
        String xpathBanner = getXPathCarrouselLink(lineaType);
        return isElementPresent(driver, By.xpath(xpathBanner)); 
    }
}
