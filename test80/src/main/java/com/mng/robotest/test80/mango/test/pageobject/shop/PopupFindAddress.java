package com.mng.robotest.test80.mango.test.pageobject.shop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.Log4jConfig;
import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;


/**
 * Implementa la API para el pupup que aparece cuando seleccionamos el botón "Find Address" desde la página de introducción de datos del usuario (actualmente sólo existe en Corea del Sur)
 * @author jorge.munoz
 *
 */
public class PopupFindAddress {
    private final static Logger pLogger = LogManager.getLogger(Log4jConfig.log4jLogger);
    private final static String XPathInputBuscador = "//input[@id='region_name']";
    private final static String XPathButtonLupa = "//button[@class='btn_search']";
    private final static String XPathLinkDirecc = "//button[@class='link_post']";
    
    public static String goToPopupAndWait(String mainWindowHandle, int maxSecondsToWait, WebDriver driver) throws Exception { 
        String popupBuscador = switchToAnotherWindow(driver, mainWindowHandle);
        try {
            isIFrameUntil(maxSecondsToWait, driver);
        }
        catch (Exception e) {
            pLogger.warn("Exception going to Find Address Popup. ", e);
        }
        return popupBuscador;
    }

	public static boolean isIFrameUntil(int maxSeconds, WebDriver driver) {
		return (state(Present, By.xpath("//iframe"), driver)
				.wait(maxSeconds).check());
	}

	public static boolean isBuscadorClickableUntil(int maxSeconds, WebDriver driver) {
		return (state(Clickable, By.xpath(XPathInputBuscador), driver)
				.wait(maxSeconds).check());
	}

    public static void setDataBuscador(WebDriver driver, String data) {
        driver.findElement(By.xpath(XPathInputBuscador)).sendKeys(data);
    }
    
    public static void clickButtonLupa(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonLupa));
    }
    
    public static void clickFirstDirecc(WebDriver driver) {
        driver.findElement(By.xpath(XPathLinkDirecc)).click();
    }
    
    public static void switchToIFrame(WebDriver driver) {
        driver.switchTo().frame(0);
    }
}
