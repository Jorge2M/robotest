package com.mng.robotest.test80.mango.test.pageobject.shop.galeria;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecTallasArticuloDesktop extends PageObjTM {
	
	//private final AppEcom app;
	private final String xpathArticulo;
//	private OutletGalery outputGalery;
	
	public SecTallasArticuloDesktop(AppEcom app, String xpathArticulo, WebDriver driver) {
		super(driver);
		//this.app = app;
		this.xpathArticulo = xpathArticulo;
	}

//	private OutletGalery getOutletGalery() {
//		if (outputGalery==null) {
//			if (app==AppEcom.outlet) {
//				outputGalery = PageGaleriaDesktop.getOutletVersion(driver);
//			} else {
//				outputGalery = OutletGalery.newwithreact;
//			}
//		}
//		return outputGalery;
//	}
	
	//final String XPathCapaTallasArticuloOutletOld = "//div[@class[contains(.,'add-cart')] and @data-stock]";
	//final String XPathCapaTallasArticuloOutletNew = "//div[@class[contains(.,'sizes__container')]]";
	final String XPathCapaTallasArticuloShop = "//div[@class[contains(.,'sizes-container')]]";
	private String getXPathArticleCapaInferiorDesktop(int posArticulo) {
		String xpathArticuloX = "(" + xpathArticulo + ")[" + posArticulo + "]";
//		if (app==AppEcom.outlet) {
//			if (getOutletGalery()==OutletGalery.old) {
//				return xpathArticuloX + XPathCapaTallasArticuloOutletOld;
//			}
//			return xpathArticuloX + XPathCapaTallasArticuloOutletNew;
//		}
		return xpathArticuloX + XPathCapaTallasArticuloShop;
	}

	public String getXPathFirstCapaAñadirOutlet(int posArticulo, boolean capaVisible) {
		String xpathCapaAdd = getXPathArticleCapaInferiorDesktop(posArticulo);
		String classSegunVisible = "not(@class[contains(.,'active')])";
		if (capaVisible) {
			classSegunVisible = "@class[contains(.,'active')]";
		}
		return (xpathCapaAdd + "//p[@class[contains(.,'first-step')] and " + classSegunVisible + "]");
	}

	//final String classCapaActiveOutlet = "@class[contains(.,'active')]";
	final String classCapaActiveShop = "@class[contains(.,'active')]";
	private String getClassCapaTallasActive() {
//		if (app==AppEcom.outlet) {
//			return classCapaActiveOutlet;
//		}
		return classCapaActiveShop;
	}
	
	public String getXPathCapaTallas(int posArticulo, boolean capaVisible) {
		String xpathCapaAdd = getXPathArticleCapaInferiorDesktop(posArticulo);
		String classCapaActive = getClassCapaTallasActive();
		if (capaVisible) {
			return xpathCapaAdd + "//self::div[" + classCapaActive + "]";
		}
		return xpathCapaAdd + "//self::div[not(" + classCapaActive + ")]"; 
	}

//	private final String XPathTallaAvailableOutletOld = "//span[@data-id and not(@class[contains(.,'no-stock')])]";
//	private final String XPathTallaAvailableOutletNew = "//button[not(@class[contains(.,'no-stock')])]";
	private final String XPathTallaAvailableShop = "//button[@class[contains(.,'si-stock')]]";
	private String getXPathTallaAvailable() {
//		if (app==AppEcom.outlet) {
//			if (getOutletGalery()==OutletGalery.old) {
//				return XPathTallaAvailableOutletOld;
//			}
//			return XPathTallaAvailableOutletNew;
//		}
		return XPathTallaAvailableShop;
	}
	public String getXPathArticleTallaAvailable(int posArticulo, int posTalla) {
		String xpathCapaTallas = getXPathCapaTallas(posArticulo, true);
		return "(" + xpathCapaTallas + getXPathTallaAvailable() + ")[" + posTalla + "]";
	}

//	private final static String XpathTallaNoDisponibleArticuloOutletOld = "//span[@data-id and (@class[contains(.,'no-stock')])]";
//	private final static String XpathTallaNoDisponibleArticuloOutletNew = "//button[@class[contains(.,'no-stock')]]";
	private final static String XpathTallaNoDisponibleArticuloShop = "//button[@class[contains(.,'no-stock')]]";
	public String getXPathArticleTallaNotAvailable() {
//		if (app==AppEcom.outlet) {
//			if (getOutletGalery()==OutletGalery.old) {
//				return  XpathTallaNoDisponibleArticuloOutletOld;
//			}
//			return XpathTallaNoDisponibleArticuloOutletNew;
//		}
		return XpathTallaNoDisponibleArticuloShop;
	}
	public boolean isVisibleArticleCapaTallasUntil(int posArticulo, int maxSeconds) {
		String xpathCapa = getXPathCapaTallas(posArticulo, true);
		return (state(Visible, By.xpath(xpathCapa)).wait(maxSeconds).check());
	}
	
    public void selectLinkAñadirOutlet(int posArticulo) throws Exception {
        String xpathCapaAlta = getXPathFirstCapaAñadirOutlet(posArticulo, true);
        int i=0;
        while (i<5) {
            try {
                driver.findElement(By.xpath(xpathCapaAlta)).click();
                break;
            }
            catch (WebDriverException e) {
                //Scrollamos un poquito hacia arriba para asegurar
                ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-100)", "");
                Thread.sleep(200);
                i+=1;
            }
        }
    }
}
