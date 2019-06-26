package com.mng.robotest.test80.mango.test.pageobject.shop.loyalty;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;

public class PageHomeConseguirPor1200Likes {

	final WebDriver driver;
	final static String XPathButton1200Likes = "//button[text()='Conseguir por 1200 Likes']";
	final static String xpathIconOperationDone = "//span[@class='icon-outline-done']";
	
	private PageHomeConseguirPor1200Likes(WebDriver driver) {
		this.driver = driver;
	}
	
	public static PageHomeConseguirPor1200Likes getNew(WebDriver driver) {
		return (new PageHomeConseguirPor1200Likes(driver));
	}
	
	public boolean isPage() {
		return WebdrvWrapp.isElementVisible(driver, By.xpath(XPathButton1200Likes));
	}
	
	public void selectConseguirButton() throws Exception {
		WebdrvWrapp.clickAndWaitLoad(driver, By.xpath(XPathButton1200Likes));
	}
	
	public boolean isVisibleIconOperationDoneUntil(int maxSecondsWait) {
		return WebdrvWrapp.isElementVisibleUntil(driver, By.xpath(xpathIconOperationDone), maxSecondsWait);
	}
}
