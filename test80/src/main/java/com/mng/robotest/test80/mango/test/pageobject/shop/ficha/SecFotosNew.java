package com.mng.robotest.test80.mango.test.pageobject.shop.ficha;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

/**
 * SectionObject de la ficha nueva correspondiente a la Foto central y líneas inferiores
 * @author jorge.munoz
 *
 */

public class SecFotosNew {

	private final static String XPathCapa = "//div[@class[contains(.,'product-images')]]";
	private final static String XPathLineFoto = XPathCapa + "//*[@class[contains(.,'columns')]]";
    
    private static String getXPathLineFotos(int line) {
        return (XPathLineFoto + "[" + line + "]");
    }
    
    private static String getXPathFoto(int line) {
        String xpathLine = getXPathLineFotos(line);
        return (xpathLine + "//img[@class[contains(.,'image')]]");
    }
    
    private static String getXPathFoto(int line, int position) {
        String xpathLine = getXPathFoto(line);
        return (xpathLine + "[" + position + "]");
    }
    
    public static DataFoto getDataFoto(int line, int position, WebDriver driver) {
        String xpathFoto = getXPathFoto(line, position);
        List<WebElement> listFotos = driver.findElements(By.xpath(xpathFoto));
        if (listFotos.size() < 1) {
            return null;
        }
        return (new DataFoto(listFotos.get(0).getAttribute("src")));
    }

    public static int getNumLinesFotos(WebDriver driver) {
    	if (!state(Present, By.xpath(XPathLineFoto), driver).check()) {
            return 0;
        }
        return (driver.findElements(By.xpath(XPathLineFoto)).size());
    }
        
    public static int getNumFotosLine(int line, WebDriver driver) {
        String xpathFotoLine = getXPathFoto(line);
        if (!state(Present, By.xpath(xpathFotoLine), driver).check()) {
            return 0;
        }
        return (driver.findElements(By.xpath(xpathFotoLine)).size());
    }
}
