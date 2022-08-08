package com.mng.robotest.test.pageobject.shop.galeria;

import org.openqa.selenium.By;

import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.conftestmaker.AppEcom;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecTallasArticuloDesktop extends PageBase {
	
	private final AppEcom app;
	
	private final String XPATH_ARTICULO;
	private final String XPATH_CAPA_TALLAS_ARTICULO_SHOP = 
			"//div[@class[contains(.,'sizes-container')] or " + 
				  "@class[contains(.,'_1BBIV')]]"; //TODO eliminar cuando suban los cambios desde maquetación
	
	public SecTallasArticuloDesktop(AppEcom app, String xpathArticulo) {
		this.app = app;
		this.XPATH_ARTICULO = xpathArticulo;
	}
	
	private String getXPathArticleCapaInferiorDesktop(int posArticulo) {
		String xpathArticuloX = "(" + XPATH_ARTICULO + ")[" + posArticulo + "]";
		return xpathArticuloX + XPATH_CAPA_TALLAS_ARTICULO_SHOP;
	}

	public String getXPathFirstCapaAnadirOutlet(int posArticulo, boolean capaVisible) {
		String xpathCapaAdd = getXPathArticleCapaInferiorDesktop(posArticulo);
		String classSegunVisible = "not(@class[contains(.,'active')])";
		if (capaVisible) {
			classSegunVisible = "@class[contains(.,'active')]";
		}
		return (xpathCapaAdd + "//p[@class[contains(.,'first-step')] and " + classSegunVisible + "]");
	}

	private final static String CLASS_CAPA_ACTIVE_SHOP = 
			"@class[contains(.,'active')] or " + 
			"@class[contains(.,'_2yZ7h')]"; //Para Outlet -> Eliminar cuando suban los cambios desde maquetación
	private String getClassCapaTallasActive() {
		return CLASS_CAPA_ACTIVE_SHOP;
	}
	
	public String getXPathCapaTallas(int posArticulo, boolean capaVisible) {
		String xpathCapaAdd = getXPathArticleCapaInferiorDesktop(posArticulo);
		String classCapaActive = getClassCapaTallasActive();
		if (capaVisible) {
			return xpathCapaAdd + "//self::div[" + classCapaActive + "]";
		}
		return xpathCapaAdd + "//self::div[not(" + classCapaActive + ")]"; 
	}

	//TODO React. 15-diciembre-2021: solicitado a pistoleros data-testid vía Teams
	private final String XPathTallaAvailableShop = "//button[not(@class[contains(.,'iMdOl')])]";
	private final String XPathTallaAvailableOutlet = "//button[@class[contains(.,'undefined')]]";
	
	//TODO 5-abril-2022. Cuando suban a PRO los nuevos filtros se podrá eliminar la parte del iMdOl 
	private final String XPathTallaUnavailableShop = 
			"//button[@class[contains(.,'iMdOl')] or @data-testid='no-stock']";
	
	private final String XPathTallaUnavailableOutlet = "//button[not(@class[contains(.,'undefined')])]";
	
	private String getXPathTallaAvailable() {
		if (app==AppEcom.outlet) {
			return XPathTallaAvailableOutlet;
		}
		return XPathTallaAvailableShop;
	}
	
	private String getXPathTallaUnavailable() {
		if (app==AppEcom.outlet) {
			return XPathTallaUnavailableOutlet;
		}
		return XPathTallaUnavailableShop;
	}
	
	public String getXPathArticleTallaAvailable(int posArticulo, int posTalla) {
		String xpathCapaTallas = getXPathCapaTallas(posArticulo, true);
		return "(" + xpathCapaTallas + getXPathTallaAvailable() + ")[" + posTalla + "]";
	}

//	private static final String XpathTallaNoDisponibleArticuloOutletOld = "//span[@data-id and (@class[contains(.,'no-stock')])]";
//	private static final String XpathTallaNoDisponibleArticuloOutletNew = "//button[@class[contains(.,'no-stock')]]";
//	private static final String XpathTallaNoDisponibleArticuloShop = "//button[@class[contains(.,'no-stock')]]";
	public String getXPathArticleTallaNotAvailable() {
////		if (app==AppEcom.outlet) {
////			if (getOutletGalery()==OutletGalery.old) {
////				return  XpathTallaNoDisponibleArticuloOutletOld;
////			}
////			return XpathTallaNoDisponibleArticuloOutletNew;
////		}
		return getXPathTallaUnavailable();
	}
	public boolean isVisibleArticleCapaTallasUntil(int posArticulo, int maxSeconds) {
		String xpathCapa = getXPathCapaTallas(posArticulo, true);
		return (state(Visible, By.xpath(xpathCapa)).wait(maxSeconds).check());
	}
	
//	public void selectLinkAñadirOutlet(int posArticulo) {
//		String xpathCapaAlta = getXPathFirstCapaAñadirOutlet(posArticulo, true);
//		int i=0;
//		while (i<5) {
//			try {
//				driver.findElement(By.xpath(xpathCapaAlta)).click();
//				break;
//			}
//			catch (WebDriverException e) {
//				//Scrollamos un poquito hacia arriba para asegurar
//				((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-100)", "");
//				waitMillis(200);
//				i+=1;
//			}
//		}
//	}
}
