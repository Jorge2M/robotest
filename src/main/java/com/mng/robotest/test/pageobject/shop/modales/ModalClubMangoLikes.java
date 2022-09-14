package com.mng.robotest.test.pageobject.shop.modales;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalClubMangoLikes extends PageBase {

	private static final String XPATH_MODAL = "//div[@class='superfan-modal']/div[@class='modal-container']";
	private static final String XPATH_DESCUBRE_TUS_VENTAJAS = XPATH_MODAL + "//a";
	private static final String XPATH_LINK_FOR_CLOSE = XPATH_MODAL + "//span[@class='modal-close-icon']";
	
	public boolean isVisible() {
		return state(Visible, XPATH_MODAL).check();
	}
	
	public void clickDescubreTusVentajas() {
		click(XPATH_DESCUBRE_TUS_VENTAJAS).exec();
	}
	
	public void closeModalIfVisible() {
		if (isVisible()) {
			closeModal();
		}
	}
	
	private void closeModal() {
		click(XPATH_LINK_FOR_CLOSE).exec();
	}
}
