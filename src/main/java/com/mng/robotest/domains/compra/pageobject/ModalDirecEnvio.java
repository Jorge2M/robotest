package com.mng.robotest.domains.compra.pageobject;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalDirecEnvio extends ModalDireccion {

	private static final String XPATH_FORM_MODAL = "//form[@class[contains(.,'customFormIdENVIO')]]";
	private static final String XPATH_BUTTON_UPDATE = XPATH_FORM_MODAL + "//div[@class[contains(.,'updateButton')]]/*[@class[contains(.,'modalConfirmar')]]";
	private static final String XPATH_BUTTON_ELIMINAR = "//*[@data-testid='address.form.delete.button']";

	public void sendDataToInputsNTimesAndWait(DataDireccion dataToSend, int nTimes) throws Exception {
		sendDataToInputsNTimes(dataToSend, nTimes, XPATH_FORM_MODAL);
		waitLoadPage();
	}

	public void sendDataToInputs(DataDireccion dataToSend) throws Exception {
		sendDataToInputs(dataToSend, XPATH_FORM_MODAL);
	}

	public void selectPoblacion(String poblacion) throws Exception {
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
	
	public void clickEliminarButton() {
		click(XPATH_BUTTON_ELIMINAR).exec();
	}
}
