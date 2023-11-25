package com.mng.robotest.tests.domains.chatbot.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class ModalChatBot extends PageBase {

	private static final String XP_ICON = "//*[@data-testid='chatbot.button.open']";
	private static final String XP_WEBCHAT = "//div[@id='snack-bubble']";
	
	private String getXPathOption(String text) {
		return XP_WEBCHAT + "//button[text()='" + text + "']";
	}
	
	private String getXPathResponse(String text) {
		return XP_WEBCHAT + "//div[text()[contains(.,'" + text + "')] and @class[contains(.,'bubble')]]";
	}
	
	private String getXPathButton(String text) {
		return XP_WEBCHAT + "//button[text()='" + text + "']";
	}
	
	public boolean checkIconVisible() {
		return state(Visible, XP_ICON).check();
	}
	
	public void clickIcon() {
		click(XP_ICON).exec();
	}
	
	public boolean checkWebchatVisible(int seconds) {
		return state(Visible, XP_WEBCHAT).wait(seconds).check();
	}

	public boolean isOptionVisible(String text, int seconds) {
		return state(Visible, getXPathOption(text)).wait(seconds).check();
	}
	
	public void clickOption(String text) {
		waitMillis(100); //Avoid strange Sonia´s case: capa in vertical unfold process
		click(getXPathOption(text)).exec();
	}
	
	public boolean isResponseVisible(String text, int seconds) {
		return state(Visible, getXPathResponse(text)).wait(seconds).check();
	}
	
	public boolean isButtonVisible(String text, int seconds) {
		return state(Visible, getXPathButton(text)).wait(seconds).check();
	}
}
