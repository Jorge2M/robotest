package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.yandex;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageYandexMoney {

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
        sendKeysWithRetry(inputValue, By.xpath(XPathInputPaymentCode), 2, driver);
    }
    
    public static void inputImport(String inputValue, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputImport)).clear();
        sendKeysWithRetry(inputValue, By.xpath(XPathInputImport), 2, driver);
    }
    
    public static boolean isVisibleInputPaymentCode(WebDriver driver) {
    	return (state(Visible, By.xpath(XPathInputPaymentCode), driver).check());
    }
    
    public static boolean isVisibleInputImport(WebDriver driver) {
    	return (state(Visible, By.xpath(XPathInputImport), driver).check());
    }

	public static void clickPayButton(WebDriver driver) {
		click(By.xpath(XPathPayButton), driver).exec();
	}
}
