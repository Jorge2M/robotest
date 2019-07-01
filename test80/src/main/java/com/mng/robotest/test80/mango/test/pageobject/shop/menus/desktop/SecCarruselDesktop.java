package com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;

public class SecCarruselDesktop extends WebdrvWrapp {
	
	private final AppEcom app;
	private final WebDriver driver;
	
	private static String TagIdCarrusel = "@CarruselId";
	private static String XPathLinkCarrGenRelToLinea = "//div[@class[contains(.,'carousel-block')]]/a[@data-label]";
    private static String XPathLinkCarruselRelToGenWithTag = "//self::*[@data-label[contains(.,'-" + TagIdCarrusel + "')]]";
    
    private SecCarruselDesktop(AppEcom app, WebDriver driver) {
    	this.app = app;
    	this.driver = driver;
    }
    
    public static SecCarruselDesktop getNew(AppEcom app, WebDriver driver) {
    	return (new SecCarruselDesktop(app, driver));
    }
    
    private String getXPathCarrouselLink(LineaType lineaType) {
        String xpathCapaMenuLinea = SecBloquesMenuDesktop.getXPathCapaMenusLinea(lineaType, app);
        return (xpathCapaMenuLinea + XPathLinkCarrGenRelToLinea);
    }
    
    private String getXPathLinkCarruselLinea(LineaType lineaId, String idCarrusel) {
        String xpathCarrGen = getXPathCarrouselLink(lineaId, app);
        String xpathCarrusel = XPathLinkCarruselRelToGenWithTag.replace(TagIdCarrusel, idCarrusel);
        return (xpathCarrGen + xpathCarrusel);
    }
    
    /**
     * @return indicador de si están visibles todos los bloques que aparecen al realizar 'Hover' sobre una línea
     */
    public boolean isVisibleCarrusels(Linea linea) {
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
    public boolean isVisibleCarrusel(Linea linea, String idCarrusel) {
        String xpathCarrousel = getXPathLinkCarruselLinea(linea.getType(), idCarrusel, app);
        return (isElementVisible(driver, By.xpath(xpathCarrousel)));
    }
    
    public boolean isPresentCarrusel(Linea linea, String idCarrusel) {
        String xpathCarrousel = getXPathLinkCarruselLinea(linea.getType(), idCarrusel, app);
        return (isElementPresent(driver, By.xpath(xpathCarrousel)));
    }    
    
    /**
     * Retorna el número de bloques nuevo existentes en la página
     */
    public int getNumCarrousels(LineaType lineaNuevoId) {
        String xpathCarrousels = getXPathCarrouselLink(lineaNuevoId, app);
        return (driver.findElements(By.xpath(xpathCarrousels)).size());
    }    
    
    /**
     * Se clicka unos de los bloques (carrouseles) que aparecen cuando se realiza un 'Hover' sobre la línea Nuevo en Desktop
     */
    public void clickCarrousel(Pais pais, LineaType lineaType, String idCarrusel) throws Exception {
        String xpathCarrousel = getXPathLinkCarruselLinea(lineaType, idCarrusel, app);
        waitClickAndWaitLoad(driver, 1, By.xpath(xpathCarrousel));
    }
    
    public boolean isPresentCarrousel(LineaType lineaType) {
        String xpathBanner = getXPathCarrouselLink(lineaType, app);
        return isElementPresent(driver, By.xpath(xpathBanner)); 
    }
}
