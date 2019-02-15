package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.yandex;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;


public class PageYandexMoney extends WebdrvWrapp {

    public static String urlAccess = "https://demomoney.yandex.ru/shop.xml?scid=50215";
    static String XPathInputPaymentCode = "//input[@id='dstAccount']";
    static String XPathInputImport = "//input[@id='amount']";
    static String XPathPayButton = "//input[@class[contains(.,'b-form-button__input')]]";

    public static void goToPage(WebDriver driver) {
        driver.get(urlAccess);
    }
    
    public static void goToPageInNewTab(String titleTab, WebDriver driver) throws Exception {
        loadUrlInAnotherTabTitle(urlAccess, titleTab, driver);
    }
    
    public static void closeActualTabByTitle(String titleTab, WebDriver driver) {
        closeTabByTitle(titleTab, driver);
    }
    
    public static void inputPaymentCode(String inputValue, WebDriver driver) {
        sendKeysWithRetry(3, inputValue, By.xpath(XPathInputPaymentCode), driver);
    }
    
    public static void inputImport(String inputValue, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputImport)).clear();
        sendKeysWithRetry(2, inputValue, By.xpath(XPathInputImport), driver);
    }
    
    public static boolean isVisibleInputPaymentCode(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathInputPaymentCode)));
    }
    
    public static boolean isVisibleInputImport(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathInputImport)));
    }
    
    public static void clickPayButton(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathPayButton));
    }
}
