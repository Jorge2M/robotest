package com.mng.robotest.domains.galeria.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.transversal.PageBase;

public class ModalArticleNotAvailable extends PageBase {

	public enum StateModal { VISIBLE, NOT_VISIBLE }
	
	private static final String XPATH_MODAL = "//div[@data-testid[contains(.,'backInStock.dialog')]]/../..";
	private static final String XPATH_ASPA_FOR_CLOSE = XPATH_MODAL + "//button[@data-testid='modal.close.button']";
	
	public boolean inStateUntil(StateModal stateModal, int seconds) {
		switch (stateModal) {
		case VISIBLE:
			return isVisibleUntil(seconds);
		default:
		case NOT_VISIBLE:
			return isNotVisibleUntil(seconds);
		}
	}
	
	public boolean isVisibleUntil(int seconds) {
		return state(Visible, XPATH_MODAL).wait(seconds).check();
	}
	
	public boolean isNotVisibleUntil(int seconds) {
		return (state(Invisible, XPATH_MODAL).wait(seconds).check());
	}

	public boolean isVisibleRPGD(int seconds) {
		return state(Visible, XPATH_MODAL).wait(seconds).check();
	}

	public void clickAspaForClose() {
		getElement(XPATH_ASPA_FOR_CLOSE).click();
	}
}
