package com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.test80.mango.test.beans.Linea;
import com.mng.robotest.test80.mango.test.beans.Pais;
import com.mng.robotest.test80.mango.test.beans.Linea.LineaType;
import com.mng.robotest.test80.mango.test.beans.Sublinea.SublineaType;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap;

public abstract class SecLineasMenuDesktop extends PageObjTM {
	
	public abstract void selectSublinea(LineaType lineaType, SublineaType sublineaType);
	
	protected final AppEcom app;
	
	static String TagIdLinea = "@LineaId";
	static String TagIdSublinea = "@SublineaId";
	static String XPathMenuFatherWrapper = "//div[@id='navMain']";
	static String XPathLineasMenuWrapper = "//div[@class='menu-section']";
	static String XPathLinea = "//ul[@class[contains(.,'menu-section-brands')]]/li[@class[contains(.,'menu-item-brands')]]";
	static String XPathLineaSpecificWithTag = 
		XPathLinea + 
		"//self::*[@id='" + TagIdLinea + "' or " +
				  "@id[contains(.,'sections_" + TagIdLinea + "')] or " +
				  "@id[contains(.,'sections-" + TagIdLinea + "')]]";

	static String XPathSublineaLinkWithTag = 
		"//div[" + 
			"(@class[contains(.,'nav-item')] or " + 
			 "@class[contains(.,'section-detail-list')]) " + //Caso países tipo Colombia con 1 sola sublínea 
			"and @data-brand='" + TagIdSublinea + "']";

	protected SecLineasMenuDesktop(AppEcom app, WebDriver driver) {
		super(driver);
		this.app = app;
	}
	
	public static SecLineasMenuDesktop factory(AppEcom app, WebDriver driver) {
//		if (app==AppEcom.outlet ||
//			//TODO temporalmente, de cara a BF2021 se ha restaurado en pro el menú antiguo
//			//quitar esta línea cuando pase el BF
//			UtilsMangoTest.isEntornoPRO(app, driver)) {
			return new SecLineasMenuDesktopOld(app, driver);
//		}
//		return new SecLineasMenuDesktopNew(app, driver);
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

	public void hoverLineaAndWaitForMenus(LineaType lineaType, SublineaType sublineaType) {
		//Existe un problema aleatorio en Firefox que provoca que el Hover sobre la línea (mientras se está cargando la galería) 
		//ejecute realmente un hover contra la línea de la izquerda
		boolean isCapaMenusVisible = false;
		int i=0;
		do {
			hoverLinea(lineaType, sublineaType);
			int maxSecondsToWait = 2;
			SecBloquesMenuDesktop secBloques = SecBloquesMenuDesktop.factory(app, driver);
			isCapaMenusVisible = secBloques.isCapaMenusLineaVisibleUntil(lineaType, maxSecondsToWait);
			if (!isCapaMenusVisible) {
				Log4jTM.getLogger().warn("No se hacen visibles los menús después de Hover sobre línea " + lineaType);
			}
			i+=1;
		}
		while (!isCapaMenusVisible && i<2);
	}
	
	public void hoverLinea(LineaType lineaType, SublineaType sublineaType) {
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
}
