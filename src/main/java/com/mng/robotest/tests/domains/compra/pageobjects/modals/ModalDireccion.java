package com.mng.robotest.tests.domains.compra.pageobjects.modals;

import java.util.Iterator;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.compra.pageobjects.beans.DataDireccion;
import com.mng.robotest.tests.domains.compra.pageobjects.beans.DataDireccion.DataDirType;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public abstract class ModalDireccion extends PageBase {

	private static final String XP_INPUT_NIF = "//input[@id[contains(.,'cfDni')]]";
	private static final String XP_INPUT_NAME = "//input[@id[contains(.,'cfName')]]";
	private static final String XP_INPUT_APELLIDOS = "//input[@id[contains(.,'cfSname')]]";
	private static final String XP_INPUT_DIRECCION = "//input[@id[contains(.,'cfDir1')]]";
	private static final String XP_INPUT_COD_POSTAL = "//input[@id[contains(.,'cfCp')]]";
	private static final String XP_INPUT_EMAIL = "//input[@id[contains(.,'cfEmail')]]";
	private static final String XP_INPUT_TELEFONO = "//input[@id[contains(.,'cfTelf')]]";
	private static final String XP_SELECT_POBLACION = "//select[@id[contains(.,':localidades')]]";
	private static final String XP_SELECT_PROVINCIA = "//select[@id[contains(.,':estadosPais')]]";
	private static final String XP_SELECT_PAIS = "//select[@id[contains(.,':pais')]]";
	
	private String getXPathInput(DataDirType inputType) {
		switch (inputType) {
		case NIF:
			return XP_INPUT_NIF;
		case NAME:
			return XP_INPUT_NAME;
		case APELLIDOS:
			return XP_INPUT_APELLIDOS;
		case DIRECCION:
			return XP_INPUT_DIRECCION;
		case CODPOSTAL:
			return XP_INPUT_COD_POSTAL;
		case EMAIL:
			return XP_INPUT_EMAIL;
		case TELEFONO:
			return XP_INPUT_TELEFONO;
		default:
			return "";
		}
	}

	public void sendDataToInputsNTimes(DataDireccion dataToSend, int nTimes, String xpathFormModal) {
		for (int i=0; i<nTimes; i++) {
			sendDataToInputs(dataToSend, xpathFormModal);
			waitForPageLoaded(driver);
		}
	}
	
	public void sendDataToInputs(DataDireccion dataToSend, String xpathFormModal) {
		try {
			Iterator<Map.Entry<DataDirType,String>> it = dataToSend.getDataDireccion().entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<DataDirType,String> pair = it.next();
				switch (pair.getKey()) {
				case POBLACION:
					selectPoblacion(pair.getValue(), xpathFormModal);
					break;
				case PROVINCIA:
					selectProvincia(pair.getValue(), xpathFormModal);
					break;
				case CODIGOPAIS:
					selectPais(pair.getValue(), xpathFormModal);
					break;
				default:
					sendKeysToInput(pair.getKey(), pair.getValue(), xpathFormModal);				
				}
			}
		}
		catch (Exception e) {
			/*
			 * Es posible que realmente el send se haya ejecutado correctamente
			 */
		}
	}
	
	public void selectPoblacion(String poblacion, String xpathFormModal) {
		String xpath = xpathFormModal + XP_SELECT_POBLACION;
		state(VISIBLE, xpath).wait(2).check();
		waitMillis(1000);
		new Select(getElement(xpath)).selectByValue(poblacion);
	}
	
	public void selectProvincia(String provincia, String xpathFormModal) {
		String xpath = xpathFormModal + XP_SELECT_PROVINCIA;
		state(CLICKABLE, xpath).wait(2).check();
		new Select(getElement(xpath)).selectByVisibleText(provincia);
	}

	public void selectPais(String codigoPais, String xpathFormModal) {
		String xpathSelectedPais = XP_SELECT_PAIS + "/option[@selected='selected' and @value='" + codigoPais + "']";
		if (!state(PRESENT, xpathSelectedPais).check()) {
			String xpath = xpathFormModal + XP_SELECT_PAIS;
			state(CLICKABLE, xpath).wait(2).check();
			new Select(getElement(xpath)).selectByValue(codigoPais);
		}
	}

	private void sendKeysToInput(DataDirType inputType, String dataToSend, String xpathFormModal) {
		String xpathInput = xpathFormModal + getXPathInput(inputType);
		ifNotValueSetedSendKeysWithRetry(2, dataToSend, By.xpath(xpathInput), driver);
	}
}
