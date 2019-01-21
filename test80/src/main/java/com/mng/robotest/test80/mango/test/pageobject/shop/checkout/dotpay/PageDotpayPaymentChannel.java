package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.dotpay;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

@SuppressWarnings("javadoc")
public class PageDotpayPaymentChannel extends WebdrvWrapp {

    static String XPathSectionPaymentChannels = "//section[@id='payment-channels']";
    static String XPathBlockInputData = "//div[@id='personal-data-form']";
    static String XPathInputFirstName = XPathBlockInputData + "//input[@name='dp-firstname']";
    static String XPathInputLastName = XPathBlockInputData + "//input[@name='dp-lastname']";
    static String XPathButtonConfirmar = XPathBlockInputData + "//button[@type='submit' and @id='payment-form-submit-dp']";
    
    public static String getXPathPaymentChannelLink(int numPayment) {
        return "//img[@id='channel_image_" + numPayment + "']";
    }
    
    public static boolean isPage(WebDriver driver) {
        return isElementPresent(driver, By.xpath(XPathSectionPaymentChannels));
    }
    
    public static void clickPayment(int numPayment, WebDriver driver) throws Exception {
        String xpathPayment = getXPathPaymentChannelLink(numPayment);
        clickAndWaitLoad(driver, By.xpath(xpathPayment));
    }
    
    public static void sendInputNombre(String firstName, String lastName, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputFirstName)).sendKeys(firstName);
        driver.findElement(By.xpath(XPathInputLastName)).sendKeys(lastName);
    }
    
    public static void clickButtonConfirm(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonConfirmar));
    }
    
    public static boolean isVisibleBlockInputDataUntil(int maxSecondsToWait, WebDriver driver) {
        return isElementVisibleUntil(driver, By.xpath(XPathBlockInputData), maxSecondsToWait);
    }
}
