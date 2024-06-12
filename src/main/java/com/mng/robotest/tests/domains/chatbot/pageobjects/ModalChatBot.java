package com.mng.robotest.tests.domains.chatbot.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.INVISIBLE;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.VISIBLE;

import com.mng.robotest.tests.domains.base.PageBase;

public abstract class ModalChatBot extends PageBase {
	
	private static final String XP_ICON = "//*[@data-testid='chatbot.button.open']";
	
	protected ModalChatBot() {
		super();
	}
	
	public boolean checkIconVisible() {
		return state(VISIBLE, XP_ICON).check();
	}
	
	public boolean checkIconInvisible() {
		return state(INVISIBLE, XP_ICON).check();
	}	
	
	public boolean isNewChatBot() {
		boolean newChatBot;
		clickIcon();
		var modalChatBotNew = new ModalChatBotNew();
		if (modalChatBotNew.checkVisible(1)) {
			newChatBot=true;
			modalChatBotNew.close();
		} else {
			newChatBot=false;
			new ModalChatBotOld().close();
		}
		return newChatBot;
	}
	
	public void clickIcon() {
		click(XP_ICON).exec();
	}
	
	abstract boolean checkVisible(int seconds);
	
}
