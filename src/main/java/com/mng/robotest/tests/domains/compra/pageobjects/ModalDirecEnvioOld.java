package com.mng.robotest.tests.domains.compra.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalDirecEnvioOld extends ModalDireccion {

	private static final String XPATH_FORM_MODAL = "//form[@class[contains(.,'customFormIdENVIO')]]";
	private static final String XPATH_BUTTON_UPDATE = XPATH_FORM_MODAL + "//div[@class[contains(.,'updateButton')]]/*[@class[contains(.,'modalConfirmar')]]";

	public void sendDataToInputsNTimesAndWait(DataDireccion dataToSend, int nTimes) {
		sendDataToInputsNTimes(dataToSend, nTimes, XPATH_FORM_MODAL);
		waitLoadPage();
	}

	public void sendDataToInputs(DataDireccion dataToSend) {
		sendDataToInputs(dataToSend, XPATH_FORM_MODAL);
	}

	public void selectPoblacion(String poblacion) {
		selectPoblacion(poblacion, XPATH_FORM_MODAL);
	}

	public void selectProvincia(String provincia) {
		selectProvincia(provincia, XPATH_FORM_MODAL);
	} 

	public boolean isVisibleFormUntil(int seconds) {
		return state(Visible, XPATH_FORM_MODAL).wait(seconds).check();
	}

	public boolean isVisibleButtonActualizar() {
		return state(Visible, XPATH_BUTTON_UPDATE).check();
	}

	public void moveToAndDoubleClickActualizar() {
		moveToElement(XPATH_BUTTON_UPDATE);
		waitLoadPage();
		click(XPATH_BUTTON_UPDATE).exec();

		//Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no funciona así que ejecutamos un 2o 
		if (isVisibleButtonActualizar()) {
			click(XPATH_BUTTON_UPDATE).exec();
		}
	}
}
