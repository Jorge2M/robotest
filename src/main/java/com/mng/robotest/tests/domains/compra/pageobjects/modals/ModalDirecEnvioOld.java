package com.mng.robotest.tests.domains.compra.pageobjects.modals;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.compra.pageobjects.beans.DataDireccion;

public class ModalDirecEnvioOld extends ModalDireccion {

	private static final String XP_FORM_MODAL = "//form[@class[contains(.,'customFormIdENVIO')]]";
	private static final String XP_BUTTON_UPDATE = XP_FORM_MODAL + "//div[@class[contains(.,'updateButton')]]/*[@class[contains(.,'modalConfirmar')]]";
	private static final String XP_DELIVERY_ADDRESS = "//*[@data-testid='checkout.delivery.address']";	

	public ModalDirecEnvioOld() {
		super(XP_FORM_MODAL);
	}
	
	public void sendDataToInputsNTimesAndWait(DataDireccion dataToSend, int nTimes) {
		sendDataToInputsNTimes(dataToSend, nTimes);
		waitLoadPage();
	}

	public boolean isVisibleFormUntil(int seconds) {
		return state(VISIBLE, XP_FORM_MODAL).wait(seconds).check();
	}

	public boolean isVisibleButtonActualizar() {
		return state(VISIBLE, XP_BUTTON_UPDATE).check();
	}

	public void moveToAndDoubleClickActualizar() {
		moveToElement(XP_BUTTON_UPDATE);
		waitLoadPage();
		click(XP_BUTTON_UPDATE).exec();

		//Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no funciona así que ejecutamos un 2o 
		if (isVisibleButtonActualizar()) {
			click(XP_BUTTON_UPDATE).exec();
		}
	}
	
	public String getAddress() {
		return getElement(XP_DELIVERY_ADDRESS).getText();
	}
}
