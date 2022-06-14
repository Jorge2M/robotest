package com.mng.robotest.test.pageobject.shop.loyalty;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageHomeConseguirPor1200Likes extends PageObjTM {

	static final String XPathButton1200Likes = "//button[text()='Conseguir por 1200 Likes']";
	static final String xpathIconOperationDone = "//*[@class[contains(.,'icon-outline-done')]]";
	
	private PageHomeConseguirPor1200Likes(WebDriver driver) {
		super(driver);
	}
	
	public static PageHomeConseguirPor1200Likes getNew(WebDriver driver) {
		return (new PageHomeConseguirPor1200Likes(driver));
	}
	
	public boolean isPage(int maxSeconds) {
		return (state(Visible, By.xpath(XPathButton1200Likes)).wait(maxSeconds).check());
	}
	
	public void selectConseguirButton() {
		click(By.xpath(XPathButton1200Likes)).exec();
	}
	
	public boolean isVisibleIconOperationDoneUntil(int maxSeconds) {
		return (state(Visible, By.xpath(xpathIconOperationDone))
				.wait(maxSeconds).check());
	}
}
