package com.mng.robotest.testslegacy.pageobject.shop.modales;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalNewsLetterAfterAccess extends PageBase {

	private static final String XP_CAPA_GLOBAL = "//div[@id='listenerModal']"; 
	private static final String XP_CAPA_CONTAINER = XP_CAPA_GLOBAL + "//div[@id='modalNewsletterSubscription']";
	private static final String XP_ASPA_FOR_CLOSE = XP_CAPA_CONTAINER + "//button[@id='modalNewsletterSubscriptionClose']";
	
	public boolean isModalVisibleUntil(int seconds) {
		return state(Visible, XP_CAPA_CONTAINER).wait(seconds).check();
	}
	
	public void closeModal() {
		click(XP_ASPA_FOR_CLOSE).type(javascript).exec();
	}
	
	public void closeModalIfVisible() {
		closeModalIfVisibleUntil(0);
	}
	
	public void closeModalIfVisibleUntil(int seconds) {
		if (isModalVisibleUntil(seconds)) {
			closeModal();
		}
	}
}
