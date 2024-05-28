package com.mng.robotest.testslegacy.pageobject.shop.modales.buscarentienda;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalBuscarEnTiendaGenesis extends ModalBuscarEnTienda {

	private static final String XP_CONTAINER = "//dialog[@id='store-finder-modal']";
	private static final String XP_ASPA_FOR_CLOSE = XP_CONTAINER + "//*[@data-testid='modal.close.button']";
	private static final String XP_TIENDA = "//*[@data-testid='checkout.delivery.storeList']//input[@id[contains(.,'store')]]/..";
	
	@Override
	public boolean isVisible(int seconds) {
		return state(VISIBLE, XP_CONTAINER).wait(seconds).check();
	}
	
	@Override
	public boolean isInvisible(int seconds) {
		return state(INVISIBLE, XP_CONTAINER).wait(seconds).check();
	}
	
	@Override
	public boolean isPresentAnyTiendaUntil(int seconds) {
		return state(VISIBLE, XP_TIENDA).wait(seconds).check();
	}
	
	@Override
	public void close() {
		click(XP_ASPA_FOR_CLOSE).exec();
	}
	
}
