package com.mng.robotest.test.pageobject.shop.modales;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.base.PageBase;

public class ModalLoyaltyAfterLogin extends PageBase {

	private static final String XPATH_CAPA_CONTAINER = "//div[@class[contains(.,'modal-content')]]";
	private static final String XPATH_IR_DE_SHOPPING_LINK = XPATH_CAPA_CONTAINER + "//a[@class[contains(.,'loyalty-irdeshopping')]]";
	
	public boolean isModalVisibleUntil(int seconds) {
		return state(Visible, XPATH_IR_DE_SHOPPING_LINK).wait(seconds).check();
	}
	
	public void closeModal() {
		click(XPATH_IR_DE_SHOPPING_LINK).exec();
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
