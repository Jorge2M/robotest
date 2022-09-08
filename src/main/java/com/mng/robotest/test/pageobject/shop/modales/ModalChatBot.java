package com.mng.robotest.test.pageobject.shop.modales;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.domains.transversal.PageBase;

public class ModalChatBot extends PageBase {

	private static final String XPATH_ICON = "//div[@id='iris-button']";
	private static final String XPATH_WEBCHAT = "//div[@id='snack-bubble']";
	
	private String getXPathOption(String text) {
		return XPATH_WEBCHAT + "//button[text()='" + text + "']";
	}
	
	private String getXPathResponse(String text) {
		return XPATH_WEBCHAT + "//div[text()[contains(.,'" + text + "')] and @class[contains(.,'bubble')]]";
	}
	
	private String getXPathButton(String text) {
		return XPATH_WEBCHAT + "//button[text()='" + text + "']";
	}
	
	public boolean checkIconVisible() {
		return state(State.Visible, XPATH_ICON).check();
	}
	
	public void clickIcon() {
		click(XPATH_ICON).exec();
	}
	
	public boolean checkWebchatVisible(int maxSeconds) {
		return state(State.Visible, XPATH_WEBCHAT).wait(maxSeconds).check();
	}

	public boolean isOptionVisible(String text, int maxSeconds) {
		String xpath = getXPathOption(text);
		return state(State.Visible, xpath).wait(maxSeconds).check();
	}
	
	public void clickOption(String text) {
		waitMillis(100); //Avoid strange Sonia´s case: capa in vertical unfold process
		click(getXPathOption(text)).exec();
	}
	
	public boolean isResponseVisible(String text, int maxSeconds) {
		String xpath = getXPathResponse(text);
		return state(State.Visible, xpath).wait(maxSeconds).check();
	}
	
	public boolean isButtonVisible(String text, int maxSeconds) {
		String xpath = getXPathButton(text);
		return state(State.Visible, xpath).wait(maxSeconds).check();
	}
}
