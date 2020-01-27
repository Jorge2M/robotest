package com.mng.robotest.test80.mango.test.pageobject.shop.galeria;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public class SecTallasArticuloDesktop extends WebdrvWrapp {
	
	private final AppEcom app;
	private final WebDriver driver;
	private final String xpathArticulo;
	
	public SecTallasArticuloDesktop(AppEcom app, String xpathArticulo, WebDriver driver) {
		this.app = app;
		this.driver = driver;
		this.xpathArticulo = xpathArticulo;
	}

	final String XPathCapaTallasArticuloOutlet = "//div[@class[contains(.,'add-cart')] and @data-stock]";
	final String XPathCapaTallasArticuloShop = "//div[@class[contains(.,'_1BBIV')]]";
	private String getXPathArticleCapaInferiorDesktop(int posArticulo) {
		String xpathArticuloX = "(" + xpathArticulo + ")[" + posArticulo + "]";
		if (app==AppEcom.outlet) {
			return xpathArticuloX + XPathCapaTallasArticuloOutlet;
		}
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

	final String classCapaActiveOutlet = "@class[contains(.,'active')]";
	final String classCapaActiveShop = "@class[contains(.,'_2yZ7h')]";
	private String getClassCapaTallasActive() {
		if (app==AppEcom.outlet) {
			return classCapaActiveOutlet;
		}
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

	private final String XPathTallaAvailableOutlet = "//span[@data-id and not(@class[contains(.,'no-stock')])]";
	private final String XPathTallaAvailableShop = "//button[not(@class[contains(.,'mQ11o')])]";
	private String getXPathTallaAvailable() {
		if (app==AppEcom.outlet) {
			return XPathTallaAvailableOutlet;
		}
		return XPathTallaAvailableShop;
	}
	public String getXPathArticleTallaAvailable(int posArticulo, int posTalla) {
		String xpathCapaTallas = getXPathCapaTallas(posArticulo, true);
		return "(" + xpathCapaTallas + getXPathTallaAvailable() + ")[" + posTalla + "]";
	}

	private final static String XpathTallaNoDisponibleArticuloOutlet = "//span[@data-id and (@class[contains(.,'no-stock')])]";
	private final static String XpathTallaNoDisponibleArticuloShop = "//button[@class[contains(.,'mQ11o')]]";
	public String getXPathArticleTallaNotAvailable() {
		if (app==AppEcom.outlet) {
			return  XpathTallaNoDisponibleArticuloOutlet;
		}
		return XpathTallaNoDisponibleArticuloShop;
	}
	public boolean isVisibleArticleCapaTallasUntil(int posArticulo, int maxSecondsToWait) {
		String xpathCapa = getXPathCapaTallas(posArticulo, true);
		return (isElementVisibleUntil(driver, By.xpath(xpathCapa), maxSecondsToWait));
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
