package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.dotpay;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageDotpayPaymentChannel {

    static String XPathSectionPaymentChannels = "//section[@id='payment-channels']";
    static String XPathBlockInputData = "//div[@id='personal-data-form']";
    static String XPathInputFirstName = XPathBlockInputData + "//input[@name='dp-firstname']";
    static String XPathInputLastName = XPathBlockInputData + "//input[@name='dp-lastname']";
    static String XPathButtonConfirmar = XPathBlockInputData + "//button[@type='submit' and @id='payment-form-submit-dp']";
    
    public static String getXPathPaymentChannelLink(int numPayment) {
        return "//img[@id='channel_image_" + numPayment + "']";
    }
    
    public static boolean isPage(WebDriver driver) {
    	return (state(Present, By.xpath(XPathSectionPaymentChannels), driver).check());
    }

	public static void clickPayment(int numPayment, WebDriver driver) {
		String xpathPayment = getXPathPaymentChannelLink(numPayment);
		click(By.xpath(xpathPayment), driver).exec();
	}

    public static void sendInputNombre(String firstName, String lastName, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputFirstName)).sendKeys(firstName);
        driver.findElement(By.xpath(XPathInputLastName)).sendKeys(lastName);
    }

	public static void clickButtonConfirm(WebDriver driver) {
		click(By.xpath(XPathButtonConfirmar), driver).exec();
	}

    public static boolean isVisibleBlockInputDataUntil(int maxSeconds, WebDriver driver) {
    	return (state(Visible, By.xpath(XPathBlockInputData), driver)
    			.wait(maxSeconds).check());
    }
}
