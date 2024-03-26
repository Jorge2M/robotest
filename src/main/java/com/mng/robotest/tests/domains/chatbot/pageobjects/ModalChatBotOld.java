package com.mng.robotest.tests.domains.chatbot.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalChatBotOld extends ModalChatBot {

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
	
	@Override
	public boolean checkVisible(int seconds) {
		return state(VISIBLE, XP_WEBCHAT).wait(seconds).check();
	}

	public boolean isOptionVisible(String text, int seconds) {
		return state(VISIBLE, getXPathOption(text)).wait(seconds).check();
	}
	
	public void clickOption(String text) {
		waitMillis(100); //Avoid strange Sonia´s case: capa in vertical unfold process
		click(getXPathOption(text)).exec();
	}
	
	public boolean isResponseVisible(String text, int seconds) {
		return state(VISIBLE, getXPathResponse(text)).wait(seconds).check();
	}
	
	public boolean isButtonVisible(String text, int seconds) {
		return state(VISIBLE, getXPathButton(text)).wait(seconds).check();
	}
	
}
