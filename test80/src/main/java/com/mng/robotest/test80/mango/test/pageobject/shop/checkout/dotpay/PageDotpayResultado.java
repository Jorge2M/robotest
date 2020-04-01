package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.dotpay;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.pageobject.PageObjTM;


public class PageDotpayResultado extends PageObjTM {

	private final static String XPathButtonNext = "//input[@type='submit' and @value='Next']";
	
	public PageDotpayResultado(WebDriver driver) {
		super(driver);
	}
    
    public boolean isPageResultadoOk() {
        return (driver.getPageSource().contains("Payment successful"));
    }

	public void clickButtonNext() {
		click(By.xpath(XPathButtonNext)).exec();
	}
}
