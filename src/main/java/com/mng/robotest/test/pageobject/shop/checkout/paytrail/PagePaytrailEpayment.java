package com.mng.robotest.test.pageobject.shop.checkout.paytrail;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;


public class PagePaytrailEpayment extends PageObjTM {

	private static String XPathFormCodeCard = "//form[@action[contains(.,'AUTH=OLD')]]";
	private static String XPathButtonOkFromCodeCard = XPathFormCodeCard + "//input[@class='button' and @name='Ok']";
	
	public PagePaytrailEpayment(WebDriver driver) {
		super(driver);
	}

	public void clickOkFromCodeCard() {
		click(By.xpath(XPathButtonOkFromCodeCard)).exec();
	}
}
