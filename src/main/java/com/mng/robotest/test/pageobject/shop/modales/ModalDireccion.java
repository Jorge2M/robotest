package com.mng.robotest.test.pageobject.shop.modales;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.Select;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.domains.compra.pageobjects.DataDireccion;
import com.mng.robotest.domains.compra.pageobjects.DataDireccion.DataDirType;
import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public abstract class ModalDireccion extends PageBase {

	private static final String XPATH_INPUT_NIF = "//input[@id[contains(.,'cfDni')]]";
	private static final String XPATH_INPUT_NAME = "//input[@id[contains(.,'cfName')]]";
	private static final String XPATH_INPUT_APELLIDOS = "//input[@id[contains(.,'cfSname')]]";
	private static final String XPATH_INPUT_DIRECCION = "//input[@id[contains(.,'cfDir1')]]";
	private static final String XPATH_INPUT_CODPOSTAL = "//input[@id[contains(.,'cfCp')]]";
	private static final String XPATH_INPUT_EMAIL = "//input[@id[contains(.,'cfEmail')]]";
	private static final String XPATH_INPUT_TELEFONO = "//input[@id[contains(.,'cfTelf')]]";
	private static final String XPATH_SELECT_POBLACION = "//select[@id[contains(.,':localidades')]]";
	private static final String XPATH_SELECT_PROVINCIA = "//select[@id[contains(.,':estadosPais')]]";
	private static final String XPATH_SELECT_PAIS = "//select[@id[contains(.,':pais')]]";
	
	private String getXPathInput(DataDirType inputType) {
		switch (inputType) {
		case nif:
			return XPATH_INPUT_NIF;
		case name:
			return XPATH_INPUT_NAME;
		case apellidos:
			return XPATH_INPUT_APELLIDOS;
		case direccion:
			return XPATH_INPUT_DIRECCION;
		case codpostal:
			return XPATH_INPUT_CODPOSTAL;
		case email:
			return XPATH_INPUT_EMAIL;
		case telefono:
			return XPATH_INPUT_TELEFONO;
		default:
			return "";
		}
	}

	public void sendDataToInputsNTimes(DataDireccion dataToSend, int nTimes, String xpathFormModal) 
			throws Exception {
		for (int i=0; i<nTimes; i++)
			sendDataToInputs(dataToSend, xpathFormModal);
	}
	
	public void sendDataToInputs(DataDireccion dataToSend, String xpathFormModal) 
			throws Exception {
		try {
			Iterator<Entry<DataDirType, String>> it = dataToSend.getDataDireccion().entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<DataDirType, String> pair = it.next();
				switch (pair.getKey()) {
				case poblacion:
					selectPoblacion(pair.getValue(), xpathFormModal);
					break;
				case provincia:
					selectProvincia(pair.getValue(), xpathFormModal);
					break;
				case codigoPais:
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
		state(State.Visible, xpathFormModal + XPATH_SELECT_POBLACION).wait(2).check();
		waitMillis(1000);
		new Select(getElement(xpathFormModal + XPATH_SELECT_POBLACION)).selectByValue(poblacion);
	}
	
	public void selectProvincia(String provincia, String xpathFormModal) {
		state(State.Visible, xpathFormModal + XPATH_SELECT_PROVINCIA).wait(2).check();
		new Select(getElement(xpathFormModal + XPATH_SELECT_PROVINCIA)).selectByVisibleText(provincia);
	}	
	
	public void selectPais(String codigoPais, String xpathFormModal) {
		String xpathSelectedPais = XPATH_SELECT_PAIS + "/option[@selected='selected' and @value='" + codigoPais + "']";
		if (!state(Present, xpathSelectedPais).check()) {
			state(State.Visible, xpathFormModal + XPATH_SELECT_PAIS).wait(2).check();
			new Select(getElement(xpathFormModal + XPATH_SELECT_PAIS)).selectByValue(codigoPais);
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
