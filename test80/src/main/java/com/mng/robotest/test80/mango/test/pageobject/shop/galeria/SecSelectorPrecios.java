package com.mng.robotest.test80.mango.test.pageobject.shop.galeria;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;


/**
 * Page Object correspondiente al selector de precios de Desktop
 * @author jorge.munoz
 *
 */
public class SecSelectorPrecios extends WebdrvWrapp {
	public enum TypeClick {left, right}
	
    static String XPathLineaFiltro = "//div[@id='priceRange']";
    static String XPathImporteMinimo = XPathLineaFiltro + "/a/span[@class[contains(.,'amount-value-min')]]";
    static String XPathImporteMaximo = XPathLineaFiltro + "/a/span[@class[contains(.,'amount-value-max')]]";
    static String XPathFiltroWrapper = "//div[@class='range-slider-wrapper']";
    static String XPathLeftCorner = "//a[@class[contains(.,'ui-corner-all')]]/span[@class[contains(.,'value-min')]]/..";
    static String XPathRightCorner = "//a[@class[contains(.,'ui-corner-all')]]/span[@class[contains(.,'value-max')]]/..";
    
    public static boolean isVisible(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathLineaFiltro)));
    }
    
    public static int getImporteMinimo(WebDriver driver) {
        Integer valueOf = Integer.valueOf(driver.findElement(By.xpath(XPathImporteMinimo)).getText());
        return valueOf.intValue();
    }
    
    public static int getImporteMaximo(WebDriver driver) {
        Integer valueOf = Integer.valueOf(driver.findElement(By.xpath(XPathImporteMaximo)).getText());
        return valueOf.intValue();
    }
    
    /**
     * Seleccionamos un mínimo (click por la izquierda del buscador) y un máximo (click por la derecha del buscador)
     * @param margenPixelsIzquierda indica los píxels desde la izquierda del selector donde ejecutaremos el click para definir un mínimo
     * @param margenPixelsDerecha indica los píxels desde la derecha del selector donde ejecutaremos el click para definir un máximo
     */
    public static void clickMinAndMax(int margenPixelsIzquierda, int margenPixelsDerecha, WebDriver driver) 
    throws Exception {
    	click(TypeClick.right, -30, driver);
    	click(TypeClick.left, 30, driver);
    }
    
    public static void click(TypeClick typeClick, int pixelsFromCorner, WebDriver driver) throws Exception {
        Actions builder = new Actions(driver);
        moveToCornerSelector(TypeClick.right, driver);
        Thread.sleep(2000);
        moveToCornerSelector(typeClick, driver);
        waitForPageLoaded(driver);
        builder.moveByOffset(pixelsFromCorner, 0).click().build().perform();
        waitForPageLoaded(driver);
    }
    
    public static void moveToCornerSelector(TypeClick typeCorner, WebDriver driver) {
        moveToElement(By.xpath(XPathFiltroWrapper), driver);
    	switch (typeCorner) {
    	case left: 
    		moveToElement(By.xpath(XPathLeftCorner), driver);
    		break;
    	case right:
    		moveToElement(By.xpath(XPathRightCorner), driver);
    	}
    }
    
    public static void moveToSelector(WebDriver driver) {
    	moveToElement(By.xpath(XPathLineaFiltro), driver);
    }
}
