package com.mng.robotest.test80.mango.test.pageobject.shop.galeria;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;


/**
 * Page Object correspondiente al selector de precios de Desktop
 * @author jorge.munoz
 *
 */
public class SecSelectorPrecios extends WebdrvWrapp {
	public enum TypeClick {left, right}
	
	private final WebDriver driver;
	private final AppEcom app;
//	private OutletGalery outputGalery;
	
//	private static String XPathLineaFiltroOutlet = "//div[@id='priceRange']";
//	private static String XPathImporteMinimoOutlet = XPathLineaFiltroOutlet + "/a/span[@class[contains(.,'amount-value-min')]]";
//	private static String XPathImporteMaximoOutlet = XPathLineaFiltroOutlet + "/a/span[@class[contains(.,'amount-value-max')]]";
//	private static String XPathFiltroWrapperOutlet = "//div[@class='range-slider-wrapper']";
//	private static String XPathLeftCornerOutlet = XPathImporteMinimoOutlet + "/..";
//	private static String XPathRightCornerOutlet = XPathImporteMaximoOutlet + "/..";
	
	private static String XPathLineaFiltroShop = "//div[@class[contains(.,'input-range__track--background')]]"; //
	private static String XPathImporteMinimoShop = "(" + XPathLineaFiltroShop + "//span[@class[contains(.,'label-container')]])[1]"; //
	private static String XPathImporteMaximoShop = "(" + XPathLineaFiltroShop + "//span[@class[contains(.,'label-container')]])[2]"; //
	private static String XPathFiltroWrapperShop = "//div[@class='input-range']"; //
	private static String XPathLeftCornerShop = XPathImporteMinimoShop + "/../..";
	private static String XPathRightCornerShop = XPathImporteMaximoShop + "/../..";
	
	public SecSelectorPrecios(AppEcom app, WebDriver driver) {
		this.app = app;
		this.driver = driver;
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
	
	private String getXPathLineaFiltro() {
		switch (app) {
//		case outlet:
//			if (getOutletGalery()==OutletGalery.old) {
//				return XPathLineaFiltroOutlet;
//			}
		default:
			return XPathLineaFiltroShop;
		}
	}
	private String getXPathImporteMinimo() {
		switch (app) {
//		case outlet:
//			if (getOutletGalery()==OutletGalery.old) {
//				return XPathImporteMinimoOutlet;
//			}
		default:
			return XPathImporteMinimoShop;
		}
	}
	private String getXPathImporteMaximo() {
		switch (app) {
//		case outlet:
//			if (getOutletGalery()==OutletGalery.old) {
//				return XPathImporteMaximoOutlet;
//			}
		default:
			return XPathImporteMaximoShop;
		}
	}
	private String getXPathFiltroWrapper() {
		switch (app) {
//		case outlet:
//			if (getOutletGalery()==OutletGalery.old) {
//				return XPathFiltroWrapperOutlet;
//			}
		default:
			return XPathFiltroWrapperShop;
		}
	}
	private String getXPathLeftCorner() {
		switch (app) {
//		case outlet:
//			if (getOutletGalery()==OutletGalery.old) {
//				return XPathLeftCornerOutlet;
//			}
		default:
			return XPathLeftCornerShop;
		}
	}
	private String getXPathRightCorner() {
		switch (app) {
//		case outlet:
//			if (getOutletGalery()==OutletGalery.old) {
//				return XPathRightCornerOutlet;
//			}
		default:
			return XPathRightCornerShop;
		}
	}

	public boolean isVisible() {
		By byLineaFiltro = By.xpath(getXPathLineaFiltro());
		return (isElementVisible(driver, byLineaFiltro));
	}

	public int getImporteMinimo() {
		By byImporteMinimo = By.xpath(getXPathImporteMinimo());
		Integer valueOf = Integer.valueOf(driver.findElement(byImporteMinimo).getText());
		return valueOf.intValue();
	}

	public int getImporteMaximo() {
		By byImporteMaximo = By.xpath(getXPathImporteMaximo());
		Integer valueOf = Integer.valueOf(driver.findElement(byImporteMaximo).getText());
		return valueOf.intValue();
	}

	/**
	 * Seleccionamos un mínimo (click por la izquierda del buscador) y un máximo (click por la derecha del buscador)
	 * @param margenPixelsIzquierda indica los píxels desde la izquierda del selector donde ejecutaremos el click para definir un mínimo
	 * @param margenPixelsDerecha indica los píxels desde la derecha del selector donde ejecutaremos el click para definir un máximo
	 */
	public void clickMinAndMax(int margenPixelsIzquierda, int margenPixelsDerecha) throws Exception {
		click(TypeClick.right, -30);
		click(TypeClick.left, 30);
	}

	public void click(TypeClick typeClick, int pixelsFromCorner) throws Exception {
		Actions builder = new Actions(driver);
		moveToCornerSelector(TypeClick.right);
		Thread.sleep(2000);
		moveToCornerSelector(typeClick);
		waitForPageLoaded(driver);
		builder.moveByOffset(pixelsFromCorner, 0).click().build().perform();
		waitForPageLoaded(driver);
	}

	public void moveToCornerSelector(TypeClick typeCorner) throws Exception {
		waitForPageLoaded(driver);
		moveToElement(By.xpath(getXPathFiltroWrapper()), driver);
		switch (typeCorner) {
		case left: 
			moveToElement(By.xpath(getXPathLeftCorner()), driver);
			break;
		case right:
			moveToElement(By.xpath(getXPathRightCorner()), driver);
		}
	}

	public void moveToSelector() {
		By byLineaFiltro = By.xpath(getXPathLineaFiltro());
		moveToElement(byLineaFiltro, driver);
	}
}
