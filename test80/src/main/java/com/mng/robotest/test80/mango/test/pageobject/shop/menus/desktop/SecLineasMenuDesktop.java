package com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Sublinea.SublineaNinosType;
import com.mng.robotest.test80.arq.webdriverwrapper.TypeOfClick;
import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap;

public class SecLineasMenuDesktop extends WebdrvWrapp {
	static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
	
    static String TagIdLinea = "@LineaId";
    static String TagIdSublinea = "@SublineaId";
    static String XPathMenuFatherWrapper = "//div[@id='navMain']";
    static String XPathLineasMenuWrapper = "//div[@class='menu-section']";
    static String XPathLinea = "//ul[@class='menu-section-brands']/li[@class[contains(.,'menu-item-brands')]]";
    static String XPathLineaSpecificWithTag = 
    	XPathLinea + 
    	"//self::*[@id='" + TagIdLinea + "' or " +
    			  "@id[contains(.,'sections_" + TagIdLinea + "')] or " +
    			  "@id[contains(.,'sections-" + TagIdLinea + "')]]";
	static String XPathImagesSublineaWithTags = 
		"//div[" + 
			"@class='image-item' and @data-label[contains(.,'" + TagIdLinea + "')] and " +
			"@data-label[contains(.,'" + TagIdSublinea + "')]" +
		"]";
	static String XPathSublineaLinkWithTag = "//div[@class[contains(.,'nav-item')] and @data-brand='" + TagIdSublinea + "']";

    public static String getXPathLinea(LineaType lineaType, AppEcom app) {
        String lineaIddom = SecMenusWrap.getIdLineaEnDOM(lineaType, Channel.desktop, app);
        return (XPathLineaSpecificWithTag.replace(TagIdLinea, lineaIddom));
    }
    
    public static String getXPathLineaSelected(LineaType lineaType, AppEcom app) {
    	String xpathLinea = getXPathLinea(lineaType, app);
        return (xpathLinea + "//self::*[@class[contains(.,'selected')]]");    	
    }
    
    public static String getXPathLineaLink(LineaType lineaType, AppEcom app) {
    	String xpathLinea = getXPathLinea(lineaType, app);
        return (xpathLinea + "/a");
    }

    public static String getXPathImgSublinea(LineaType lineaId, SublineaNinosType sublineaType) {
    	return XPathImagesSublineaWithTags.replace(TagIdLinea, lineaId.name()).replace(TagIdSublinea, sublineaType.toString());
    }
    
    public static String getXPathSublineaLink(SublineaNinosType sublineaType, AppEcom app) {
        String idSublineaEnDom = sublineaType.getId(app);
    	return (XPathSublineaLinkWithTag.replace(TagIdSublinea, idSublineaEnDom));
    }

    public static boolean isPresentLineasMenuWrapp(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathLineasMenuWrapper)));
    }
    
    public static boolean isVisibleMenuSup(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathLineasMenuWrapper)));
    }
    
    public static boolean isVisibleMenuSupUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementVisibleUntil(driver, By.xpath(XPathLineasMenuWrapper), maxSecondsToWait));
    }    
    
    public static boolean isInvisibleMenuSupUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementInvisibleUntil(driver, By.xpath(XPathLineasMenuWrapper), maxSecondsToWait));
    }
    
    public static void bringMenuBackground(AppEcom app, WebDriver driver) throws Exception {
    	String xpathToBringBack = "";
    	switch (app) {
    	case outlet:
    		xpathToBringBack = XPathLineasMenuWrapper;
    		break;
    	case shop:
    	default:
    		xpathToBringBack = XPathMenuFatherWrapper;
    	}
    	
    	WebElement menuWrapp = driver.findElement(By.xpath(xpathToBringBack));
    	((JavascriptExecutor) driver).executeScript("arguments[0].style.position='relative';", menuWrapp);
    	((JavascriptExecutor) driver).executeScript("arguments[0].style.zIndex=1;", menuWrapp);
    	isElementInvisibleUntil(driver, By.xpath(xpathToBringBack), 1);
    }    
    
    public static List<WebElement> getListaLineas(WebDriver driver) {
        return (driver.findElements(By.xpath(XPathLinea)));
    }
    
    public static boolean isLineaPresent(LineaType lineaType, AppEcom app, WebDriver driver) {
        String xpathLinea = getXPathLineaLink(lineaType, app);
        return (isElementPresent(driver, By.xpath(xpathLinea)));
    }
    
    public static boolean isLineaPresentUntil(LineaType lineaType, AppEcom app, int maxSecondsToWait, WebDriver driver) {
        String xpathLinea = getXPathLineaLink(lineaType, app);
        return (isElementPresentUntil(driver, By.xpath(xpathLinea), maxSecondsToWait));
    }    
    
    public static boolean isLineaVisible(LineaType lineaType, AppEcom app, WebDriver driver) {
    	String xpathLinea = getXPathLineaLink(lineaType, app);
    	return (isElementPresent(driver, By.xpath(xpathLinea)));
    }
    
    public static boolean isLineaSelected(LineaType lineaType, AppEcom app, WebDriver driver) {
        String xpathLinea = getXPathLineaSelected(lineaType, app);
        return (isElementPresent(driver, By.xpath(xpathLinea))); 
    }
    
    public static void selecLinea(Pais pais, LineaType lineaType, AppEcom app, WebDriver driver) throws Exception {
        Linea linea = pais.getShoponline().getLinea(lineaType);
        if (isLineActiveToSelect(pais, linea, app)) {
           String XPathLinkLinea = getXPathLineaLink(lineaType, app);
     	   clickAndWaitLoad(driver, By.xpath(XPathLinkLinea), TypeOfClick.javascript);
        }
    }
     
    private static boolean isLineActiveToSelect(Pais pais, Linea linea, AppEcom app) {
    	//En el caso concreto de los países con únicamente la línea She -> Aparecen otras pestañas
    	return (
    		(app==AppEcom.outlet || 
    		(pais.getShoponline().getNumLineasTiendas(app) > 1 || 
    		 !pais.getShoponline().isLineaTienda(linea)))
    	);
    }

    public static boolean isVisibleImgSublineaUntil(LineaType lineaType, SublineaNinosType sublineaType, 
    												int maxSecondsToWait, WebDriver driver) {
    	String xpathImg = getXPathImgSublinea(lineaType, sublineaType);
    	return (isElementVisibleUntil(driver, By.xpath(xpathImg), maxSecondsToWait));
    }
    
    public static void clickImgSublineaIfVisible(LineaType lineaType, SublineaNinosType sublineaType, WebDriver driver) 
    throws Exception {
    	int maxSecondsToWait = 1;
        if (isVisibleImgSublineaUntil(lineaType, sublineaType, maxSecondsToWait, driver)) {
        	String xpathImg = getXPathImgSublinea(lineaType, sublineaType);
        	clickAndWaitLoad(driver, By.xpath(xpathImg));
        	isElementInvisibleUntil(driver, By.xpath(xpathImg), 1/*seconds*/);
        }
    }

    public static void hoverLineaAndWaitForMenus(LineaType lineaType, SublineaNinosType sublineaType, AppEcom app, WebDriver driver) 
    throws Exception {
    	//Existe un problema aleatorio en Firefox que provoca que el Hover sobre la línea (mientras se está cargando la galería) 
    	//ejecute realmente un hover contra la línea de la izquerda
    	boolean isCapaMenusVisible = false;
    	int i=0;
    	do {
	    	hoverLinea(lineaType, sublineaType, app, driver);
	    	int maxSecondsToWait = 2;
	    	isCapaMenusVisible = SecBloquesMenuDesktop.isCapaMenusLineaVisibleUntil(lineaType, maxSecondsToWait, app, driver);
	    	if (!isCapaMenusVisible) {
	    		pLogger.warn("No se hacen visibles los menús después de Hover sobre línea " + lineaType);
	    	}
	    	i+=1;
    	}
    	while (!isCapaMenusVisible && i<2);
    }
    
    public static void hoverLinea(LineaType lineaType, SublineaNinosType sublineaType, AppEcom app, WebDriver driver) throws Exception {
    	if (sublineaType==null) {
    		hoverLinea(lineaType, app, driver);
    	} else {
    		selectSublinea(lineaType, sublineaType, app, driver);
    	}
    }
    
    public static void hoverLinea(LineaType lineaType, AppEcom app, WebDriver driver) throws Exception {
        //Hover sobre la pestaña -> Hacemos visibles los menús/subimágenes
        String xpathLinkLinea = getXPathLineaLink(lineaType, app);
        isElementVisibleUntil(driver, By.xpath(xpathLinkLinea), 1);
        moveToElement(By.xpath(xpathLinkLinea), driver);
    }

    public static void selectSublinea(LineaType lineaType, SublineaNinosType sublineaType, AppEcom app, WebDriver driver) throws Exception {
    	hoverLinea(lineaType, app, driver);
       	clickImgSublineaIfVisible(lineaType, sublineaType, driver);
        String xpathLinkSublinea = getXPathSublineaLink(sublineaType, app);
        
        //Esperamos que esté visible la sublínea y realizamos un Hover
        isElementVisibleUntil(driver, By.xpath(xpathLinkSublinea), 2);
        moveToElement(By.xpath(xpathLinkSublinea), driver);
    }
}
