package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.tmango;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;


public class PageAmexInputTarjeta extends WebdrvWrapp {

    static String XPathIconoBancoSabadell = "//div[@class='logoEntidad']/img[@src[contains(.,'entidad/81.png')]]";
    static String XPathInputNumTarj = "//input[@id[contains(.,'inputCard')]]";
    static String XPathInputMesCad = "//input[@id[contains(.,'cad1')] and @maxlength=2]";
    static String XPathInputAnyCad = "//input[@id[contains(.,'cad2')] and @maxlength=2]";
    static String XPathInputCvc = "//input[@id[contains(.,'codseg')] and @maxlength=4]";
    static String XPathPagarButton = "//button[@id[contains(.,'divImgAceptar')]]";
    
    public static boolean isPasarelaBancoSabadellUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementPresentUntil(driver, By.xpath(XPathIconoBancoSabadell), maxSecondsToWait));
    }
    
    public static void inputDataTarjeta(String numTarj, String mesCad, String anyCad, String Cvc, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputNumTarj)).sendKeys(numTarj);
        driver.findElement(By.xpath(XPathInputMesCad)).sendKeys(mesCad);
        driver.findElement(By.xpath(XPathInputAnyCad)).sendKeys(anyCad);
        driver.findElement(By.xpath(XPathInputCvc)).sendKeys(Cvc);
    }

    public static boolean isPresentNumTarj(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathInputNumTarj)));
    }
    
    public static boolean isPresentInputMesCad(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathInputMesCad)));
    }
    
    public static boolean isPresentInputAnyCad(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathInputAnyCad)));
    }
    
    public static boolean isPresentInputCvc(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathInputCvc)));
    }

    public static boolean isPresentPagarButton(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathPagarButton)));
    }
    
    public static void clickPagarButton(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathPagarButton));
    }
}
