package com.mng.robotest.test80.mango.test.pageobject.shop.modales;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

public class ModalChatBot extends PageObjTM {

	private final static String XPathRoot = "//div[@id='webchatRoot']";
	private final static String XPathIcon = XPathRoot + "//img";
	private final static String XPathWebchat = XPathRoot + "//div[@id='botonic-webchat']";
	
	public ModalChatBot(WebDriver driver) {
		super(driver);
	}
	
	private String getXPathOption(String text) {
		return XPathWebchat + "//div[text()='" + text + "']";
	}
	
	private String getXPathResponse(String text) {
		return XPathWebchat + "//p[text()[contains(.,'" + text + "')]]";
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
