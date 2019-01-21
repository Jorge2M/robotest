package com.mng.robotest.test80.mango.test.pageobject.shop.identificacion;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.AllPages;

@SuppressWarnings("javadoc")
public class PageRecuperaPasswd extends WebdrvWrapp {
    
    static String xpathInputCorreo = "//input[@type='text' and @id[contains(.,'RPemail')]]";
    static String xpathButtonEnviar = "//input[@type='submit' and @id[contains(.,'ResetPassword')]]";
    static String xpathMsgRevisaTuEmail = "//div[text()[contains(.,'REVISA TU EMAIL')]]";
    static String xpathButtonIrDeShopping = "//div[@id[contains(.,'IrShopping')]]/a";
    
    public static boolean isPageUntil(int maxSecondsToWait, WebDriver driver) {
        return AllPages.isPresentElementWithTextUntil("RECUPERA TU CONTRASEÑA", maxSecondsToWait, driver);
    }
    
    public static boolean isPresentInputCorreo(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(xpathInputCorreo)));
    }
    
    public static void inputEmail(String email, WebDriver driver) {
        driver.findElement(By.xpath(xpathInputCorreo)).clear();
        driver.findElement(By.xpath(xpathInputCorreo)).sendKeys(email);
    }
    
    public static void clickEnviar(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(xpathButtonEnviar));
    }
    
    public static boolean isVisibleRevisaTuEmailUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementVisibleUntil(driver, By.xpath(xpathMsgRevisaTuEmail), maxSecondsToWait));
    }

    public static boolean isVisibleButtonIrDeShopping(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(xpathButtonIrDeShopping)));
    }
        
}
