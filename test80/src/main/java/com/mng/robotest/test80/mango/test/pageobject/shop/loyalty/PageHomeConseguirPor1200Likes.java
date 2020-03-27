package com.mng.robotest.test80.mango.test.pageobject.shop.loyalty;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageHomeConseguirPor1200Likes extends PageObjTM {

	final static String XPathButton1200Likes = "//button[text()='Conseguir por 1200 Likes']";
	final static String xpathIconOperationDone = "//span[@class='icon-outline-done']";
	
	private PageHomeConseguirPor1200Likes(WebDriver driver) {
		super(driver);
	}
	
	public static PageHomeConseguirPor1200Likes getNew(WebDriver driver) {
		return (new PageHomeConseguirPor1200Likes(driver));
	}
	
	public boolean isPage() {
		return (state(Visible, By.xpath(XPathButton1200Likes)).check());
	}
	
	public void selectConseguirButton() throws Exception {
		clickAndWaitLoad(driver, By.xpath(XPathButton1200Likes));
	}
	
	public boolean isVisibleIconOperationDoneUntil(int maxSeconds) {
		return (state(Visible, By.xpath(xpathIconOperationDone))
				.wait(maxSeconds).check());
	}
}
