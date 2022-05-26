package com.mng.robotest.test.pageobject.shop.modales;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

public class ModalChatBot extends PageObjTM {

	private final static String XPathIcon = "//div[@id='iris-button']";
	private final static String XPathWebchat = "//div[@id='snack-bubble']";
	
	public ModalChatBot(WebDriver driver) {
		super(driver);
	}
	
	private String getXPathOption(String text) {
		return XPathWebchat + "//button[text()='" + text + "']";
	}
	
	private String getXPathResponse(String text) {
		return XPathWebchat + "//div[text()[contains(.,'" + text + "')] and @class[contains(.,'bubble')]]";
	}
	
	private String getXPathButton(String text) {
		return XPathWebchat + "//button[text()='" + text + "']";
	}
	
	public boolean checkIconVisible() {
		return state(State.Visible, By.xpath(XPathIcon)).check();
	}
	
	public void clickIcon() {
		click(By.xpath(XPathIcon)).exec();
	}
	
	public boolean checkWebchatVisible(int maxSeconds) {
		return state(State.Visible, By.xpath(XPathWebchat)).wait(maxSeconds).check();
	}

	public boolean isOptionVisible(String text, int maxSeconds) {
		String xpath = getXPathOption(text);
		return state(State.Visible, By.xpath(xpath)).wait(maxSeconds).check();
	}
	
	public void clickOption(String text) {
		String xpath = getXPathOption(text);
		click(By.xpath(xpath)).exec();
	}
	
	public boolean isResponseVisible(String text, int maxSeconds) {
		String xpath = getXPathResponse(text);
		return state(State.Visible, By.xpath(xpath)).wait(maxSeconds).check();
	}
	
	public boolean isButtonVisible(String text, int maxSeconds) {
		String xpath = getXPathButton(text);
		return state(State.Visible, By.xpath(xpath)).wait(maxSeconds).check();
	}
}
