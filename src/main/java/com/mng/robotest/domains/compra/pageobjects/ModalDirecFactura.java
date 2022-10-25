package com.mng.robotest.domains.compra.pageobjects;

import org.openqa.selenium.StaleElementReferenceException;

import com.github.jorge2m.testmaker.conf.Log4jTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalDirecFactura extends ModalDireccion {

	private static final String XPATH_FORM_MODAL = "//form[@class[contains(.,'customFormIdFACT')]]";
	private static final String XPATH_BUTTON_UPDATE = XPATH_FORM_MODAL + "//div[@class[contains(.,'updateButton')]]/input[@type='submit']";
	
	public void sendDataToInputs(DataDireccion dataToSend) throws Exception {
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

	public void clickActualizar() {
		click(XPATH_BUTTON_UPDATE).exec(); 
		try {
			if (isVisibleButtonActualizar()) {
				click(XPATH_BUTTON_UPDATE).exec();
			}
		}
		catch (StaleElementReferenceException e) {
			Log4jTM.getLogger().info(e);
		}
	}
}
