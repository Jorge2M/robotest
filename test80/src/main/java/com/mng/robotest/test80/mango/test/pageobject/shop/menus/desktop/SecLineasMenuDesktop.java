package com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Sublinea.SublineaNinosType;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap;

public class SecLineasMenuDesktop extends PageObjTM {
	
	private final AppEcom app;
	
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
		super(driver);
		this.app = app;
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
    	return (state(Present, By.xpath(XPathLineasMenuWrapper)).check());
    }
    
    public boolean isVisibleMenuSup() {
    	return (state(Present, By.xpath(XPathLineasMenuWrapper)).check());
    }
    
    public boolean isVisibleMenuSupUntil(int maxSeconds) {
    	return (state(Visible, By.xpath(XPathLineasMenuWrapper)).wait(maxSeconds).check());
    }    
    
    public boolean isInvisibleMenuSupUntil(int maxSeconds) {
    	return (state(Invisible, By.xpath(XPathLineasMenuWrapper)).wait(maxSeconds).check());
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
    	state(Invisible, By.xpath(xpathToBringBack)).wait(1).check();
    }    
    
    public List<WebElement> getListaLineas() {
        return (driver.findElements(By.xpath(XPathLinea)));
    }
    
    public boolean isLineaPresent(LineaType lineaType) {
        String xpathLinea = getXPathLineaLink(lineaType);
        return (state(Present, By.xpath(xpathLinea)).check());
    }
    
    public boolean isLineaPresentUntil(LineaType lineaType, int maxSeconds) {
        String xpathLinea = getXPathLineaLink(lineaType);
        return (state(Present, By.xpath(xpathLinea)).wait(maxSeconds).check());
    }    
    
    public boolean isLineaVisible(LineaType lineaType) {
    	String xpathLinea = getXPathLineaLink(lineaType);
    	return (state(Present, By.xpath(xpathLinea)).check());
    }
    
    public boolean isLineaSelected(LineaType lineaType) {
        String xpathLinea = getXPathLineaSelected(lineaType);
        return (state(Present, By.xpath(xpathLinea)).check()); 
    }

	public void selecLinea(Pais pais, LineaType lineaType) {
		Linea linea = pais.getShoponline().getLinea(lineaType);
		if (isLineActiveToSelect(pais, linea)) {
			String XPathLinkLinea = getXPathLineaLink(lineaType);
			click(By.xpath(XPathLinkLinea)).type(javascript).exec();
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

    public boolean isVisibleImgSublineaUntil(LineaType lineaType, SublineaNinosType sublineaType, int maxSeconds) {
    	String xpathImg = getXPathImgSublinea(lineaType, sublineaType);
    	return (state(Visible, By.xpath(xpathImg)).wait(maxSeconds).check());
    }

	public void clickImgSublineaIfVisible(LineaType lineaType, SublineaNinosType sublineaType) {
		int maxSecondsToWait = 1;
		if (isVisibleImgSublineaUntil(lineaType, sublineaType, maxSecondsToWait)) {
			String xpathImg = getXPathImgSublinea(lineaType, sublineaType);
			click(By.xpath(xpathImg)).exec();
			state(Invisible, By.xpath(xpathImg)).wait(1).check();
		}
	}

    public void hoverLineaAndWaitForMenus(LineaType lineaType, SublineaNinosType sublineaType) {
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
	    		Log4jTM.getLogger().warn("No se hacen visibles los menús después de Hover sobre línea " + lineaType);
	    	}
	    	i+=1;
    	}
    	while (!isCapaMenusVisible && i<2);
    }
    
    public void hoverLinea(LineaType lineaType, SublineaNinosType sublineaType) {
    	if (sublineaType==null) {
    		hoverLinea(lineaType);
    	} else {
    		selectSublinea(lineaType, sublineaType);
    	}
    }
    
    public void hoverLinea(LineaType lineaType) {
        //Hover sobre la pestaña -> Hacemos visibles los menús/subimágenes
        String xpathLinkLinea = getXPathLineaLink(lineaType);
        state(Visible, By.xpath(xpathLinkLinea)).wait(1).check();
        moveToElement(By.xpath(xpathLinkLinea), driver);
    }

    public void selectSublinea(LineaType lineaType, SublineaNinosType sublineaType) {
    	hoverLinea(lineaType);
       	clickImgSublineaIfVisible(lineaType, sublineaType);
        String xpathLinkSublinea = getXPathSublineaLink(sublineaType);
        
        //Esperamos que esté visible la sublínea y realizamos un Hover
        state(Visible, By.xpath(xpathLinkSublinea)).wait(2).check();
        moveToElement(By.xpath(xpathLinkSublinea), driver);
    }
}
