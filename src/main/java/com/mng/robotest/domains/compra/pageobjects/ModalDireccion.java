package com.mng.robotest.domains.compra.pageobjects;

import java.util.Iterator;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.domains.compra.pageobjects.DataDireccion.DataDirType;
import com.mng.robotest.domains.transversal.PageBase;

public abstract class ModalDireccion extends PageBase {

	private static final String XPATH_INPUT_NIF = "//input[@id[contains(.,'cfDni')]]";
	private static final String XPATH_INPUT_NAME = "//input[@id[contains(.,'cfName')]]";
	private static final String XPATH_INPUT_APELLIDOS = "//input[@id[contains(.,'cfSname')]]";
	private static final String XPATH_INPUT_DIRECCION = "//input[@id[contains(.,'cfDir1')]]";
	private static final String XPATH_INPUT_COD_POSTAL = "//input[@id[contains(.,'cfCp')]]";
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
			return XPATH_INPUT_COD_POSTAL;
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
		for (int i=0; i<nTimes; i++) {
			sendDataToInputs(dataToSend, xpathFormModal);
			waitForPageLoaded(driver);
		}
	}
	
	public void sendDataToInputs(DataDireccion dataToSend, String xpathFormModal) 
			throws Exception {
		try {
			Iterator<Map.Entry<DataDirType,String>> it = dataToSend.getDataDireccion().entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<DataDirType,String> pair = it.next();
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
			 * Es posible que realmente el send se haya ejecutado correctamente
			 */
		}
	}
	
	public void selectPoblacion(String poblacion, String xpathFormModal) throws Exception {
		String xpath = xpathFormModal + XPATH_SELECT_POBLACION;
		state(State.Visible, xpath).wait(2).check();
		waitMillis(1000);
		new Select(getElement(xpath)).selectByValue(poblacion);
	}
	
	public void selectProvincia(String provincia, String xpathFormModal) {
		String xpath = xpathFormModal + XPATH_SELECT_PROVINCIA;
		state(State.Clickable, xpath).wait(2).check();
		new Select(getElement(xpath)).selectByVisibleText(provincia);
	}

	public void selectPais(String codigoPais, String xpathFormModal) {
		String xpathSelectedPais = XPATH_SELECT_PAIS + "/option[@selected='selected' and @value='" + codigoPais + "']";
		if (!state(Present, xpathSelectedPais).check()) {
			String xpath = xpathFormModal + XPATH_SELECT_PAIS;
			state(State.Clickable, xpath).wait(2).check();
			new Select(getElement(xpath)).selectByValue(codigoPais);
		}
	}

	private void sendKeysToInput(DataDirType inputType, String dataToSend, String xpathFormModal) {
		String xpathInput = xpathFormModal + getXPathInput(inputType);
		ifNotValueSetedSendKeysWithRetry(2, dataToSend, By.xpath(xpathInput), driver);
	}
}
