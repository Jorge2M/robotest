package com.mng.robotest.test.pageobject.shop.modales;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;

import com.mng.robotest.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalLoyaltyAfterAccess extends PageBase {

	private static final String XPATH_CAPA_GLOBAL = "//div[@id='adhesionModal']"; 
	private static final String XPATH_CAPA_CONTAINER = XPATH_CAPA_GLOBAL + "//div[@class='modal-container']";
	private static final String XPATH_ASPA_FOR_CLOSE = XPATH_CAPA_CONTAINER + "//span[@class='modal-close-icon']";
	
	public boolean isModalVisibleUntil(int seconds) {
		return state(Visible, XPATH_CAPA_CONTAINER).wait(seconds).check();
	}
	
	public void closeModal() {
		click(XPATH_ASPA_FOR_CLOSE).type(javascript).exec();
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
