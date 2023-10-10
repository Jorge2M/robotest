package com.mng.robotest.tests.domains.registro.pageobjects;

import java.util.Map;


import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.Select;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.compra.pageobjects.Page2IdentCheckout;
import com.mng.robotest.tests.domains.registro.beans.DataRegistro;
import com.mng.robotest.tests.domains.registro.beans.ListDataRegistro;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageRegistroDirecOutlet extends PageBase {

	private static final String XPATH_DIV_ERROR = "//div[@class='errorValidation']";
	private static final String XPATH_INPUT_DIREC = "//input[@id[contains(.,':cfDir1')]]";
	private static final String XPATH_SELECT_PAIS = "//select[@id[contains(.,':pais')]]";
	private static final String XPATH_INPUT_COD_POSTAL = "//input[@id[contains(.,':cfCp')]]";
	private static final String XPATH_SELECT_POBLACION = "//select[@id[contains(.,':localidades')]]";
	private static final String XPATH_SELECT_PROVINCIA = "//select[@id[contains(.,':estadosPais')]]";
	private static final String XPATH_FINALIZAR_BUTTON = "//form[@id[contains(.,'Step')]]//input[@type='submit']";
	
	public int getNumberMsgInputInvalid() {
		return getNumElementsVisible(XPATH_DIV_ERROR);
	}
	
	public void sendDataAccordingCountryToInputs(Map<String,String> dataRegistro) {
		dataRegistro.putAll(new Page2IdentCheckout()
				.inputDataPorDefectoSegunPais(dataRegistro.get("cfEmail"), false, false, channel));
	}
	
	public void sendDataToInputs(ListDataRegistro dataToSend, int repeat) {
		for (int i=0; i<repeat; i++) {
			for (DataRegistro dataInput : dataToSend.getDataPageDirec()) {
				switch (dataInput.getDataRegType()) {
				case DIRECCION:
					sendDataToDireccionIfNotExist(dataInput.getData());
					break;			
				case CODPAIS:
					sendDataToPaisIfNotExist(dataInput.getData());
					break;
				case CODPOSTAL:
					sendDataToCodPostalIfNotExist(dataInput.getData());
					break;
				case POBLACION:
					sendDataToPoblacion(dataInput.getData());
					break;
				case PROVINCIA:
					sendDataToProvincia(dataInput.getData());
					break;
				default:
					break;
				}
			}
			waitMillis(1000);
		}
	}
	
	public void sendDataToDireccionIfNotExist(String direccion) {
		sendKeysToInputIfNotExist(direccion, XPATH_INPUT_DIREC);
	}
	
	public void sendDataToPaisIfNotExist(String codigoPais) {
		String xpathSelectedPais = XPATH_SELECT_PAIS + "/option[@selected='selected' and @value='" + codigoPais + "']";
		if (state(Present, xpathSelectedPais).check()) {
			new Select(getElement(XPATH_SELECT_PAIS)).selectByValue(codigoPais);
		}
	}	
	
	public void sendDataToCodPostalIfNotExist(String codPostal) {
		sendKeysToInputIfNotExist(codPostal, XPATH_INPUT_COD_POSTAL);
	}
	
	public void sendDataToPoblacion(String poblacion) {
		new Select(getElement(XPATH_SELECT_POBLACION)).selectByValue(poblacion);
	}
	
	public void sendDataToProvincia(String provincia) {
		String valueProvincia = getElement(XPATH_SELECT_PROVINCIA + "/option[text()='" + provincia + "']").getAttribute("value");
		new Select(getElement(XPATH_SELECT_PROVINCIA)).selectByValue(valueProvincia);
	}
	
	public boolean isVisibleFinalizarButton() {
		return state(Visible, XPATH_FINALIZAR_BUTTON).check();
	}
	
	public void clickFinalizarButton() {
		click(XPATH_FINALIZAR_BUTTON).exec();
		
		//Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no funciona así que ejecutamos un 2o 
		if (isVisibleFinalizarButton()) {
			click(XPATH_FINALIZAR_BUTTON).type(javascript).exec();
		}
	}
	
	private void sendKeysToInputIfNotExist(String dataToSend, String xpathInput) {
		if (getElement(xpathInput).getAttribute("value").compareTo(dataToSend)!=0) {
			getElement(xpathInput).clear();
			getElement(xpathInput).sendKeys(dataToSend);
			getElement(xpathInput).sendKeys(Keys.TAB);
		}
	}
}