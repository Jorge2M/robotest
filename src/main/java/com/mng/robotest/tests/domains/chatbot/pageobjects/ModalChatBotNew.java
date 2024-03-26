package com.mng.robotest.tests.domains.chatbot.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalChatBotNew extends ModalChatBot {

	private static final String XP_WEBCHAT = "//div[@data-testid='iris']";
	private static final String XP_BUTTON = "//button[@data-testid='ice-breakers-item-button']";
	private static final String XP_ANSWER = "//*[@data-testid='message-text']";
	private static final String XP_INPUT_QUESTION = "//*[@data-testid='input-text']";
	private static final String XP_INPUT_BUTTON = "//*[@data-testid='input-button']";
	private static final String XP_YES_BUTTON = "//*[@data-testid='survey-yes-button']";
	private static final String XP_ICON_CLOSE = "//*[@data-testid='header-close-button']";
	private static final String XP_BUTTON_LEAVE = "//*[@data-testid='close-modal-leave-button']";
	
	private String getXPathButton(String button) {
		return XP_BUTTON + "/self::*[text()='" + button + "']";
	}
	
	private String getXPathAnswer(String answer) {
		return XP_ANSWER + "/self::*[text()[contains(.,'" + answer + "')]]";
	}
	
	@Override
	public boolean checkVisible(int seconds) {
		return state(VISIBLE, XP_WEBCHAT).wait(seconds).check();
	}
	
	public boolean checkInvisible(int seconds) {
		return state(INVISIBLE, XP_WEBCHAT).wait(seconds).check();
	}	
	
	public boolean isVisibleButton(String button, int seconds) {
		return state(VISIBLE, getXPathButton(button)).wait(seconds).check();
	}
	
	public void selectButton(String button) {
		click(getXPathButton(button)).exec();
	}
	
	public boolean checkResponseVisible(String answerExpected, int seconds) {
		return state(VISIBLE, getXPathAnswer(answerExpected)).wait(seconds).check();
	}
	
	public void inputQuestion(String question) {
		state(VISIBLE, XP_INPUT_QUESTION).wait(1).check();
		getElement(XP_INPUT_QUESTION).sendKeys(question);
		click(XP_INPUT_BUTTON).exec();
	}
	
	public boolean isVisibleYesButton(int seconds) {
		return state(VISIBLE, XP_YES_BUTTON).wait(seconds).check();
	}
	
	public void clickYesButton() {
		click(XP_YES_BUTTON).exec();
	}	
	
	public void close() {
		click(XP_ICON_CLOSE).exec();
		state(VISIBLE, XP_BUTTON_LEAVE).wait(1).check();
		click(XP_BUTTON_LEAVE).exec();
	}

}