package com.mng.robotest.test80.mango.test.pageobject.votf;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageSelectLineaVOTF {

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
        return (state(Present, By.xpath(xpathBanner), driver).check());
    }
    
    public static void clickBanner(LineaType linea, WebDriver driver) {
        String xpathLinkBanner = getXPathLineaLink(linea);
        click(By.xpath(xpathLinkBanner), driver).exec();
    }
    
    public static void clickMenu(LineaType linea, int numMenu, WebDriver driver) {
        String xpathMenu = getXPathMenu(linea, numMenu);
        click(By.xpath(xpathMenu), driver).exec();
    }
}
