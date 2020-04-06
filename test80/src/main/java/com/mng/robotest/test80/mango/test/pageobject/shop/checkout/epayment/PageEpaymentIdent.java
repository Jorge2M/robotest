package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.epayment;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageEpaymentIdent extends PageObjTM {

    static String XPathInputUser = "//input[@name[contains(.,'USERID')] and @type='password']";
    static String XPathInputCode = "//input[@name[contains(.,'IDNBR')] and @type='password']";
    
    public PageEpaymentIdent(WebDriver driver) {
    	super(driver);
    }
    
    public boolean isPage() {
        return (driver.getTitle().contains("E-payment"));
    }
    
    public boolean isPresentInputUserTypePassword() {
    	return (state(Present, By.xpath(XPathInputUser), driver).check());
    }
    
    public boolean isPresentCodeUserTypePassword() {
    	return (state(Present, By.xpath(XPathInputCode), driver).check());
    }
}
