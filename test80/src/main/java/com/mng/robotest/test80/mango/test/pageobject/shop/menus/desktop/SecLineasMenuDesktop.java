package com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.conf.Log4jConfig;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Sublinea.SublineaNinosType;
import com.mng.testmaker.service.webdriver.wrapper.TypeOfClick;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap;

public class SecLineasMenuDesktop extends WebdrvWrapp {
	
	private final AppEcom app;
	private final WebDriver driver;
	
	static Logger pLogger = LogManager.getLogger(Log4jConfig.log4jLogger);
	
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

	private SecLineasMenuDesktop(AppEcom app, WebDriver driver) {
		this.app = app;
		this.driver = driver;
	}
	
	public static SecLineasMenuDesktop getNew(AppEcom app, WebDriver driver) {
		return (new SecLineasMenuDesktop(app, driver));
	}
	
    public String getXPathLinea(LineaType lineaType) {
        String lineaIddom = SecMenusWrap.getIdLineaEnDOM(Channel.desktop, app, lineaType);
        return (XPathLineaSpecificWithTag.replace(TagIdLinea, lineaIddom));
    }
    
    public String getXPathLineaSelected(LineaType lineaType) {
    	String xpathLinea = getXPathLinea(lineaType);
        return (xpathLinea + "//self::*[@class[contains(.,'selected')]]");    	
    }
    
    public String getXPathLineaLink(LineaType lineaType) {
    	String xpathLinea = getXPathLinea(lineaType);
        return (xpathLinea + "/a");
    }

    public String getXPathImgSublinea(LineaType lineaId, SublineaNinosType sublineaType) {
    	return XPathImagesSublineaWithTags.replace(TagIdLinea, lineaId.name()).replace(TagIdSublinea, sublineaType.toString());
    }
    
    public String getXPathSublineaLink(SublineaNinosType sublineaType) {
        String idSublineaEnDom = sublineaType.getId(app);
    	return (XPathSublineaLinkWithTag.replace(TagIdSublinea, idSublineaEnDom));
    }

    public boolean isPresentLineasMenuWrapp() {
        return (isElementPresent(driver, By.xpath(XPathLineasMenuWrapper)));
    }
    
    public boolean isVisibleMenuSup() {
        return (isElementVisible(driver, By.xpath(XPathLineasMenuWrapper)));
    }
    
    public boolean isVisibleMenuSupUntil(int maxSecondsToWait) {
        return (isElementVisibleUntil(driver, By.xpath(XPathLineasMenuWrapper), maxSecondsToWait));
    }    
    
    public boolean isInvisibleMenuSupUntil(int maxSecondsToWait) {
        return (isElementInvisibleUntil(driver, By.xpath(XPathLineasMenuWrapper), maxSecondsToWait));
    }
    
    public void bringMenuBackground() throws Exception {
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
    
    public List<WebElement> getListaLineas() {
        return (driver.findElements(By.xpath(XPathLinea)));
    }
    
    public boolean isLineaPresent(LineaType lineaType) {
        String xpathLinea = getXPathLineaLink(lineaType);
        return (isElementPresent(driver, By.xpath(xpathLinea)));
    }
    
    public boolean isLineaPresentUntil(LineaType lineaType, int maxSecondsToWait) {
        String xpathLinea = getXPathLineaLink(lineaType);
        return (isElementPresentUntil(driver, By.xpath(xpathLinea), maxSecondsToWait));
    }    
    
    public boolean isLineaVisible(LineaType lineaType) {
    	String xpathLinea = getXPathLineaLink(lineaType);
    	return (isElementPresent(driver, By.xpath(xpathLinea)));
    }
    
    public boolean isLineaSelected(LineaType lineaType) {
        String xpathLinea = getXPathLineaSelected(lineaType);
        return (isElementPresent(driver, By.xpath(xpathLinea))); 
    }
    
    public void selecLinea(Pais pais, LineaType lineaType) throws Exception {
        Linea linea = pais.getShoponline().getLinea(lineaType);
        if (isLineActiveToSelect(pais, linea)) {
           String XPathLinkLinea = getXPathLineaLink(lineaType);
     	   clickAndWaitLoad(driver, By.xpath(XPathLinkLinea), TypeOfClick.javascript);
        }
    }
     
    private boolean isLineActiveToSelect(Pais pais, Linea linea) {
    	//En el caso concreto de los países con únicamente la línea She -> Aparecen otras pestañas
    	return (
    		(app==AppEcom.outlet || 
    		(pais.getShoponline().getNumLineasTiendas(app) > 1 || 
    		 !pais.getShoponline().isLineaTienda(linea)))
    	);
    }

    public boolean isVisibleImgSublineaUntil(LineaType lineaType, SublineaNinosType sublineaType, int maxSecondsWait) {
    	String xpathImg = getXPathImgSublinea(lineaType, sublineaType);
    	return (isElementVisibleUntil(driver, By.xpath(xpathImg), maxSecondsWait));
    }
    
    public void clickImgSublineaIfVisible(LineaType lineaType, SublineaNinosType sublineaType) throws Exception {
    	int maxSecondsToWait = 1;
        if (isVisibleImgSublineaUntil(lineaType, sublineaType, maxSecondsToWait)) {
        	String xpathImg = getXPathImgSublinea(lineaType, sublineaType);
        	clickAndWaitLoad(driver, By.xpath(xpathImg));
        	isElementInvisibleUntil(driver, By.xpath(xpathImg), 1/*seconds*/);
        }
    }

    public void hoverLineaAndWaitForMenus(LineaType lineaType, SublineaNinosType sublineaType) throws Exception {
    	//Existe un problema aleatorio en Firefox que provoca que el Hover sobre la línea (mientras se está cargando la galería) 
    	//ejecute realmente un hover contra la línea de la izquerda
    	boolean isCapaMenusVisible = false;
    	int i=0;
    	do {
	    	hoverLinea(lineaType, sublineaType);
	    	int maxSecondsToWait = 2;
	    	SecBloquesMenuDesktop secBloques = SecBloquesMenuDesktop.getNew(app, driver);
	    	isCapaMenusVisible = secBloques.isCapaMenusLineaVisibleUntil(lineaType, maxSecondsToWait);
	    	if (!isCapaMenusVisible) {
	    		pLogger.warn("No se hacen visibles los menús después de Hover sobre línea " + lineaType);
	    	}
	    	i+=1;
    	}
    	while (!isCapaMenusVisible && i<2);
    }
    
    public void hoverLinea(LineaType lineaType, SublineaNinosType sublineaType) throws Exception {
    	if (sublineaType==null) {
    		hoverLinea(lineaType);
    	} else {
    		selectSublinea(lineaType, sublineaType);
    	}
    }
    
    public void hoverLinea(LineaType lineaType) throws Exception {
        //Hover sobre la pestaña -> Hacemos visibles los menús/subimágenes
        String xpathLinkLinea = getXPathLineaLink(lineaType);
        isElementVisibleUntil(driver, By.xpath(xpathLinkLinea), 1);
        moveToElement(By.xpath(xpathLinkLinea), driver);
    }

    public void selectSublinea(LineaType lineaType, SublineaNinosType sublineaType) throws Exception {
    	hoverLinea(lineaType);
       	clickImgSublineaIfVisible(lineaType, sublineaType);
        String xpathLinkSublinea = getXPathSublineaLink(sublineaType);
        
        //Esperamos que esté visible la sublínea y realizamos un Hover
        isElementVisibleUntil(driver, By.xpath(xpathLinkSublinea), 2);
        moveToElement(By.xpath(xpathLinkSublinea), driver);
    }
}
