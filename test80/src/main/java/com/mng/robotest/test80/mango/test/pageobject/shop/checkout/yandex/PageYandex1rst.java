package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.yandex;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;


public class PageYandex1rst extends WebdrvWrapp {

    static String XPathInputEmail = "//input[@name='cps_email']";
    static String XPathButtonContinue = "//div[@class[contains(.,'payment-submit')]]//button";
    static String XPathInputTelefono = "//input[@name[contains(.,'phone')]]";
    static String XPathRetryButton = "//span[@class='button__text' and text()[contains(.,'Повторить попытку')]]";

    public static boolean isPage(WebDriver driver) {
        return (driver.getTitle().toLowerCase().contains("yandex"));
    }

    //Revisar
    public static boolean isValueEmail(String emailUsr, WebDriver driver) {
        String valueEmail = getValueInputEmail(driver);
        return (valueEmail.contains(emailUsr));
    }
    
    public static String getValueInputEmail(WebDriver driver) {
        return (driver.findElement(By.xpath(XPathInputEmail)).getAttribute("value"));
    }
    
    public static void clickContinue(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonContinue));
    }
    
    public static void inputTelefono(String telefono, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputTelefono)).clear();
        sendKeysWithRetry(2, telefono, By.xpath(XPathInputTelefono), driver);
    }

    public static boolean retryButtonExists(WebDriver driver){
        return isElementPresent(driver, By.xpath(XPathRetryButton));
    }

    public static void clickOnRetry(WebDriver driver) throws Exception{
        clickAndWaitLoad(driver, By.xpath(XPathRetryButton), 2);
    }
}
