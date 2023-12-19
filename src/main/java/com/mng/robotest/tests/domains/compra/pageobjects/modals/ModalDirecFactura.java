package com.mng.robotest.tests.domains.compra.pageobjects.modals;

import org.openqa.selenium.StaleElementReferenceException;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.tests.domains.compra.pageobjects.beans.DataDireccion;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalDirecFactura extends ModalDireccion {

	private static final String XP_FORM_MODAL = "//form[@class[contains(.,'customFormIdFACT')]]";
	private static final String XP_BUTTON_UPDATE = XP_FORM_MODAL + "//div[@class[contains(.,'updateButton')]]/input[@type='submit']";
	private static final String XP_ADDRESS = "//div[@class='direccionCliente']";
	
	public ModalDirecFactura() {
		super(XP_FORM_MODAL);
	}
	
	public boolean isVisibleFormUntil(int seconds) {
		return state(VISIBLE, XP_FORM_MODAL).wait(seconds).check();
	}

	public boolean isVisibleButtonActualizar() {
		return state(VISIBLE, XP_BUTTON_UPDATE).check();
	}
	
	public String getAddress() {
		return getElement(XP_ADDRESS).getText();
	}

	public void clickActualizar() {
		click(XP_BUTTON_UPDATE).exec(); 
		try {
			if (isVisibleButtonActualizar()) {
				click(XP_BUTTON_UPDATE).exec();
			}
		}
		catch (StaleElementReferenceException e) {
			Log4jTM.getLogger().info(e);
		}
	}
}
