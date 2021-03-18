package com.mng.robotest.test80.mango.test.pageobject.shop.ficha;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecSlidersNew {

    static String XPathCompletaTuLook = "//div[@id='lookTotal']";
    static String XPathElegidoParaTi = "//div[@id='recommendations']";
    static String XPathLoUltimoVisto = "//div[@id='garments']";
    static String relativeXPathArticle = "//div[@class[contains(.,'slider-module-product')] and @data-id]";

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
        String xpathSlider = getXPath(sliderType);
        return (xpathSlider + "/span");
    }
    
    public static String getXPathArticle(Slider sliderType) {
        String xpathSlider = getXPath(sliderType);
        return xpathSlider + relativeXPathArticle;
    }
    
    public static boolean isVisible(Slider sliderType, WebDriver driver) {
        String xpathSlider = getXPath(sliderType);
        return (state(Visible, By.xpath(xpathSlider), driver).check());
    }
    
    public static int getNumVisibleArticles(Slider sliderType, WebDriver driver) {
        String xpathArticle = getXPathArticle(sliderType);
        return (getNumElementsVisible(driver, By.xpath(xpathArticle)));
    }
}
