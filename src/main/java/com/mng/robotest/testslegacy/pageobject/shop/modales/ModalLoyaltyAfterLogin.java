package com.mng.robotest.testslegacy.pageobject.shop.modales;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class ModalLoyaltyAfterLogin extends PageBase {

	private static final String XP_CAPA_CONTAINER = "//div[@class[contains(.,'modal-content')]]";
	private static final String XP_IR_DE_SHOPPING_LINK = XP_CAPA_CONTAINER + "//a[@class[contains(.,'loyalty-irdeshopping')]]";
	
	public boolean isModalVisibleUntil(int seconds) {
		return state(Visible, XP_IR_DE_SHOPPING_LINK).wait(seconds).check();
	}
	
	public void closeModal() {
		click(XP_IR_DE_SHOPPING_LINK).exec();
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
