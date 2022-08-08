package com.mng.robotest.test.pageobject.otras;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.mng.robotest.domains.transversal.PageBase;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageGoogle extends PageBase {

	private static final String URL_ACCESO = "http://www.google.es";
	private static final String XPATH_INPUT_TEXT = "//input[@type='text']";
	private static final String XPATH_LINK_NO_PUBLI = "//div[@class='g']//a";
	private static final String XPATH_LINK_NO_PUBLI_TEXT = XPATH_LINK_NO_PUBLI + "//h3";
	private static final String XPATH_BUTTON_ACCEPT_MODAL_COOKIE = "//button[@id='L2AGLb']";

	public void accessViaURL() {
		driver.get(URL_ACCESO);
	}

	public String getUrlAcceso() {
		return URL_ACCESO;
	}

	public String getXPath_linkWithText(String textContained) {
		return (XPATH_LINK_NO_PUBLI_TEXT + "[text()[contains(.,'" + textContained + "')]]");	
	}

	public void searchTextAndWait(String textToSearch) throws Exception {
		driver.findElement(By.xpath(XPATH_INPUT_TEXT)).clear(); 
		driver.findElement(By.xpath(XPATH_INPUT_TEXT)).sendKeys(textToSearch); 
		driver.findElement(By.xpath(XPATH_INPUT_TEXT)).sendKeys(Keys.RETURN);
		waitForPageLoaded(driver);
	}
	
	public void acceptModalCookieIfExists() {
		if (isVisibleModalCookie(0)) {
			acceptModalCookie();
			isVisibleModalCookie(2);
		}
	}
	
	private boolean isVisibleModalCookie(int maxSeconds) {
		return state(Visible, By.xpath(XPATH_BUTTON_ACCEPT_MODAL_COOKIE)).wait(maxSeconds).check();
	}
	
	private void acceptModalCookie() {
		click(By.xpath(XPATH_BUTTON_ACCEPT_MODAL_COOKIE)).exec();
	}

	public boolean validaFirstLinkContains(String textToBeContained) {
		WebElement headerText = driver.findElement(By.xpath(XPATH_LINK_NO_PUBLI_TEXT));
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
		click(By.xpath(XPATH_LINK_NO_PUBLI)).exec();
	}

}
