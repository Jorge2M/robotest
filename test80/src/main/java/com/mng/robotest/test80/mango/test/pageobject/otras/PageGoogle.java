package com.mng.robotest.test80.mango.test.pageobject.otras;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageGoogle extends PageObjTM {

	private static final String URLacceso = "http://www.google.es";
	static final String XPath_inputText = "//input[@type='text']";
	static final String XPath_linkNoPubli = "//div[@class='g']//a";
	static final String XPath_LinkNoPubliText = XPath_linkNoPubli + "//h3";
	static final String XPath_ButtonAcceptModalCookie = "//button[@id='L2AGLb']";

	public PageGoogle(WebDriver driver) {
		super(driver);
	}
	
	public void accessViaURL() {
		driver.get(URLacceso);
	}

	public String getUrlAcceso() {
		return URLacceso;
	}

	public String getXPath_linkWithText(String textContained) {
		return (XPath_LinkNoPubliText + "[text()[contains(.,'" + textContained + "')]]");    
	}

	public void searchTextAndWait(String textToSearch) throws Exception {
		driver.findElement(By.xpath(XPath_inputText)).clear(); 
		driver.findElement(By.xpath(XPath_inputText)).sendKeys(textToSearch); 
		driver.findElement(By.xpath(XPath_inputText)).sendKeys(Keys.RETURN);
		waitForPageLoaded(driver);
	}
	
	public void acceptModalCookieIfExists() {
		if (isVisibleModalCookie(0)) {
			acceptModalCookie();
			isVisibleModalCookie(2);
		}
	}
	
    private boolean isVisibleModalCookie(int maxSeconds) {
    	return state(Visible, By.xpath(XPath_ButtonAcceptModalCookie), driver).wait(maxSeconds).check();
    }
    
    private void acceptModalCookie() {
    	click(By.xpath(XPath_ButtonAcceptModalCookie), driver).exec();
    }

	public boolean validaFirstLinkContains(String textToBeContained) {
		WebElement headerText = driver.findElement(By.xpath(XPath_LinkNoPubliText));
		if (headerText!=null) {
			String textHeader = headerText.getText();
			if (textHeader.contains(textToBeContained) || 
				textHeader.contains(textToBeContained.toLowerCase()) ||
				textHeader.contains(textToBeContained.toUpperCase())) {
				return true;
			}
		}
		return false;
	}

	public boolean validaFirstLinkContainsUntil(String textToBeContained, int maxSecondsToWait) {
		for (int i=0; i<maxSecondsToWait; i++) {
			if (validaFirstLinkContains(textToBeContained)) {
				return true;
			}
			waitMillis(1000);
		}
		return false;
	}

	public void clickFirstLinkNoPubli() {
		click(By.xpath(XPath_linkNoPubli), driver).exec();
	}

}
