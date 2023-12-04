package com.mng.robotest.testslegacy.pageobject.shop.modales;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.Keys;
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
	private static final String XP_INPUT_CODPOSTAL = "//input[@id[contains(.,'cfCp')]]";
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
			return XP_INPUT_CODPOSTAL;
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
		}
	}
	
	public void sendDataToInputs(DataDireccion dataToSend, String xpathFormModal) {
		try {
			Iterator<Entry<DataDirType, String>> it = dataToSend.getDataDireccion().entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<DataDirType, String> pair = it.next();
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
			 * En caso de error continuamos
			 */
		}
	}
	
	public void selectPoblacion(String poblacion, String xpathFormModal) {
		state(VISIBLE, xpathFormModal + XP_SELECT_POBLACION).wait(2).check();
		waitMillis(1000);
		new Select(getElement(xpathFormModal + XP_SELECT_POBLACION)).selectByValue(poblacion);
	}
	
	public void selectProvincia(String provincia, String xpathFormModal) {
		state(VISIBLE, xpathFormModal + XP_SELECT_PROVINCIA).wait(2).check();
		new Select(getElement(xpathFormModal + XP_SELECT_PROVINCIA)).selectByVisibleText(provincia);
	}	
	
	public void selectPais(String codigoPais, String xpathFormModal) {
		String xpathSelectedPais = XP_SELECT_PAIS + "/option[@selected='selected' and @value='" + codigoPais + "']";
		if (!state(PRESENT, xpathSelectedPais).check()) {
			state(VISIBLE, xpathFormModal + XP_SELECT_PAIS).wait(2).check();
			new Select(getElement(xpathFormModal + XP_SELECT_PAIS)).selectByValue(codigoPais);
		}
	}	
	
	private void sendKeysToInput(DataDirType inputType, String dataToSend, String xpathFormModal) {
		String xpathInput = xpathFormModal + getXPathInput(inputType);
		String contenido = getElement(xpathInput).getAttribute("value");
		if (contenido.compareTo(dataToSend)!=0) {
			if ("".compareTo(contenido)!=0) {
				getElement(xpathInput).clear();
			}
			getElement(xpathInput).sendKeys(dataToSend);
			getElement(xpathInput).sendKeys(Keys.TAB);
		}
	}
}
