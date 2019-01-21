package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.ideal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

@SuppressWarnings("javadoc")
public class PageIdealSimulador extends WebdrvWrapp {

    static String XPathContinueButton = "//input[@type='submit' and @class='btnLink']";
    
    public static boolean isPage(WebDriver driver) {
        return (isElementVisible(driver, By.xpath("//h3[text()[contains(.,'iDEAL Issuer Simulation')]]")));
    }    
    
    public static void clickButtonContinue(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathContinueButton));
    }
}
