package com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecCarruselDesktop extends PageObjTM {
	
	private final AppEcom app;
	
	private static String TagIdCarrusel = "@CarruselId";
	private static String XPathLinkCarrGenRelToLinea = "//div[@class[contains(.,'carousel-block')]]/a[@data-label]";
    private static String XPathLinkCarruselRelToGenWithTag = "//self::*[@data-label[contains(.,'-" + TagIdCarrusel + "')]]";
    
    private SecCarruselDesktop(AppEcom app, WebDriver driver) {
    	super(driver);
    	this.app = app;
    }
    
    public static SecCarruselDesktop getNew(AppEcom app, WebDriver driver) {
    	return (new SecCarruselDesktop(app, driver));
    }
    
    private String getXPathCarrouselLink(LineaType lineaType) {
    	SecBloquesMenuDesktop secBloquesMenu = SecBloquesMenuDesktop.getNew(app, driver);
        String xpathCapaMenuLinea = secBloquesMenu.getXPathCapaMenusLinea(lineaType);
        return (xpathCapaMenuLinea + XPathLinkCarrGenRelToLinea);
    }
    
    private String getXPathLinkCarruselLinea(LineaType lineaId, String idCarrusel) {
        String xpathCarrGen = getXPathCarrouselLink(lineaId);
        String xpathCarrusel = XPathLinkCarruselRelToGenWithTag.replace(TagIdCarrusel, idCarrusel);
        return (xpathCarrGen + xpathCarrusel);
    }
    
    /**
     * @return indicador de si están visibles todos los bloques que aparecen al realizar 'Hover' sobre una línea
     */
    public boolean isVisibleCarrusels(Linea linea) {
        String[] listCarrusels = linea.getListCarrusels();
        for (int i=0; (i<listCarrusels.length); i++) {
            if (!isVisibleCarrusel(linea, listCarrusels[i])) {
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
        String xpathCarrousel = getXPathLinkCarruselLinea(linea.getType(), idCarrusel);
        return (state(Visible, By.xpath(xpathCarrousel)).check());
    }
    
    public boolean isPresentCarrusel(Linea linea, String idCarrusel) {
        String xpathCarrousel = getXPathLinkCarruselLinea(linea.getType(), idCarrusel);
        return (state(Present, By.xpath(xpathCarrousel)).check());
    }    
    
    /**
     * Retorna el número de bloques nuevo existentes en la página
     */
    public int getNumCarrousels(LineaType lineaNuevoId) {
        String xpathCarrousels = getXPathCarrouselLink(lineaNuevoId);
        return (driver.findElements(By.xpath(xpathCarrousels)).size());
    }    
    
	/**
	 * Se clicka unos de los bloques (carrouseles) que aparecen cuando se realiza un 'Hover' sobre la línea Nuevo en Desktop
	 */
	public void clickCarrousel(Pais pais, LineaType lineaType, String idCarrusel) {
		String xpathCarrousel = getXPathLinkCarruselLinea(lineaType, idCarrusel);
		click(By.xpath(xpathCarrousel)).waitLoadPage(1).exec();
	}

    public boolean isPresentCarrousel(LineaType lineaType) {
        String xpathBanner = getXPathCarrouselLink(lineaType);
        return (state(Present, By.xpath(xpathBanner)).check());
    }
}
