package com.mng.robotest.test80.mango.test.pageobject.votf;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;


public class PageAlertaVOTF extends WebdrvWrapp {
    
    private static final String XPathCapaAlerta = "//div[@class='alert']";
    private static final String XPathButtonContinuar = "//div[@class='alert']//span[@class='button']";

    public static boolean isPage(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathCapaAlerta)));
    }
     
    public static void clickButtonContinuar(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonContinuar));
    }
}
