package com.mng.robotest.test.pageobject.shop.galeria;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.transversal.PageBase;

public class ModalArticleNotAvailable extends PageBase {

	public enum StateModal {visible, notvisible}	
	private static final String XPATH_MODAL = "//div[@id='bocataAvisame']";
	private static final String XPATH_ASPA_FOR_CLOSE = XPATH_MODAL + "//div[@class[contains(.,'botonCerrarAvisame')]]";
	
	public boolean inStateUntil(StateModal stateModal, int secondsToWait) throws Exception {
		switch (stateModal) {
		case visible:
			return isVisibleUntil(secondsToWait);
		default:
		case notvisible:
			return isNotVisibleUntil(secondsToWait);
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
