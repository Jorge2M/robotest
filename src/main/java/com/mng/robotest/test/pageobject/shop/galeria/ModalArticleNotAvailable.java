package com.mng.robotest.test.pageobject.shop.galeria;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalArticleNotAvailable extends PageBase {

	public enum StateModal {visible, notvisible}	
	private static final String XPATH_MODAL = "//div[@id='bocataAvisame']";
	private static final String XPATH_ASPA_FOR_CLOSE = XPATH_MODAL + "//div[@class[contains(.,'botonCerrarAvisame')]]";
	
	public boolean inStateUntil(StateModal stateModal, int maxSecondsToWait) throws Exception {
		switch (stateModal) {
		case visible:
			return isVisibleUntil(maxSecondsToWait);
		default:
		case notvisible:
			return isNotVisibleUntil(maxSecondsToWait);
		}
	}
	
	public boolean isVisibleUntil(int maxSeconds) {
		return state(Visible, XPATH_MODAL).wait(maxSeconds).check();
	}
	
	public boolean isNotVisibleUntil(int maxSeconds) {
		return (state(Invisible, XPATH_MODAL).wait(maxSeconds).check());
	}

	public boolean isVisibleRPGD(int maxSeconds) {
		return state(Visible, XPATH_MODAL).wait(maxSeconds).check();
	}

	public void clickAspaForClose() {
		getElement(XPATH_ASPA_FOR_CLOSE).click();
	}
}
