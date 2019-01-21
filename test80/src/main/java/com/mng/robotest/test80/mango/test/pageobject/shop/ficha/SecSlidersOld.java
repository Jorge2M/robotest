package com.mng.robotest.test80.mango.test.pageobject.shop.ficha;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

@SuppressWarnings("javadoc")
public class SecSlidersOld extends WebdrvWrapp {
    static String XPathElegidoParaTi = "//div[@class='recommendations']";
    static String XPathElegidoParaTiCabecera = XPathElegidoParaTi + "/p";
    static String XPathArtElegidoParaTi = XPathElegidoParaTi + "//div[@class[contains(.,'recommendations-product')]]";
    static String XPathCompletaTuLook = "//div[@class='total-look']";
    static String XPathCompletaTuLookCabecera = XPathCompletaTuLook + "/span";
    static String XPathArtCompletaTuLook = XPathCompletaTuLook + "//div[@class[contains(.,'total-look-product')]]";
    static String XPathLoUltimoVisto = "//div[@class='last-viewed']";
    static String XPathLoUltimoVistoCabecera = XPathLoUltimoVisto + "/span";
    static String XPathArtLoUltimoVisto = XPathLoUltimoVisto + "//div[@class[contains(.,'last-viewed-product')]]";

    public static String getXPath(Slider sliderType) {
        switch (sliderType) {
        case CompletaTuLook:
            return XPathCompletaTuLook;
        case ElegidoParaTi:
            return XPathElegidoParaTi;
        case LoUltimoVisto:
        default:
            return XPathLoUltimoVisto;
        }
    }
    
    public static String getXPathCabecera(Slider sliderType) {
        switch (sliderType) {
        case CompletaTuLook:
            return XPathCompletaTuLookCabecera;
        case ElegidoParaTi:
            return XPathElegidoParaTiCabecera;
        case LoUltimoVisto:
        default:
            return XPathLoUltimoVistoCabecera;
        }
    }
    
    public static String getXPathArticle(Slider sliderType) {
        switch (sliderType) {
        case CompletaTuLook:
            return XPathArtCompletaTuLook;
        case ElegidoParaTi:
            return XPathArtElegidoParaTi;
        case LoUltimoVisto:
        default:
            return XPathArtLoUltimoVisto;
        }
    }
    
    public static boolean isVisible(Slider sliderType, WebDriver driver) {
        String xpathSlider = getXPath(sliderType);
        return (isElementVisible(driver, By.xpath(xpathSlider))); 
    }
    
    public static int getNumVisibleArticles(Slider sliderType, WebDriver driver) {
        String xpathArticle = getXPathArticle(sliderType);
        return (getNumElementsVisible(driver, By.xpath(xpathArticle)));
    }
}
