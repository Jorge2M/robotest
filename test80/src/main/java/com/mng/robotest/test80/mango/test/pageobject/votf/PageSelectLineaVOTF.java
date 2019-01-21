package com.mng.robotest.test80.mango.test.pageobject.votf;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

@SuppressWarnings("javadoc")
public class PageSelectLineaVOTF extends WebdrvWrapp {

    private static String getXPathLineaSection(LineaType linea) {
        return ("//div[@id='" + linea.name() + "' and @class[contains(.,'section')]]");
    }
    
    private static String getXPathLineaLink(LineaType linea) {
        String xpathBanner = getXPathLineaSection(linea);
        return (xpathBanner + "//div[@class='clickable']");
    }
    
    private static String getXPathMenu(LineaType linea, int numMenu) {
        String xpathMenu = getXPathMenu(linea);
        return ("(" + xpathMenu + ")[" + numMenu + "]");
    }
    
    private static String getXPathMenu(LineaType linea) {
        String xpathBanner = getXPathLineaSection(linea);
        return (xpathBanner + "/div[@class[contains(.,'subsection')]]/span/a");
    }
    
    public static boolean isBannerPresent(LineaType linea, WebDriver driver) {
        String xpathBanner = getXPathLineaSection(linea);
        return isElementPresent(driver, By.xpath(xpathBanner));
    }
    
    public static void clickBanner(LineaType linea, WebDriver driver) throws Exception {
        String xpathLinkBanner = getXPathLineaLink(linea);
        clickAndWaitLoad(driver, By.xpath(xpathLinkBanner)); 
    }
    
    public static void clickMenu(LineaType linea, int numMenu, WebDriver driver) throws Exception {
        String xpathMenu = getXPathMenu(linea, numMenu);
        clickAndWaitLoad(driver, By.xpath(xpathMenu));
    }
}
