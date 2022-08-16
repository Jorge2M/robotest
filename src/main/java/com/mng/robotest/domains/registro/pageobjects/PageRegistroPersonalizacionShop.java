package com.mng.robotest.domains.registro.pageobjects;

import java.util.Arrays;
import java.util.List;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.beans.Linea.LineaType;

public class PageRegistroPersonalizacionShop extends PageBase {

	public enum GenderOption { 
		FEMENINO('M'), 
		MASCULINO('H'), 
		NO_BINARIO('X'), 
		PREFIERO_NO_INCLUIRLO('N');
	
		public char code;
		private GenderOption(char code) {
			this.code = code;
		}
	}
	
	public static final List<LineaType> ALL_LINEAS = Arrays.asList(
			LineaType.she, LineaType.he, LineaType.kids, LineaType.home); 
	
	private static final String XPATH_MODAL_CONTENT = "//div[@id[contains(.,'registerModal')]]";
	private static final String XPATH_INPUT_NOMBRE = "//input[@id='name']";
	private static final String XPATH_INPUT_POSTALCODE = "//input[@id='postalCode']";
	private static final String XPATH_INPUT_DATE_BIRTH = "//input[@id='birthDate']";
	private static final String XPATH_SAVE_BUTTON = XPATH_MODAL_CONTENT + "//button[@type='button']";
	
	private static final String TAG_GENDER = "@TagGender";
	private static final String XPATH_CHECKBOX_GENEDER_WITH_TAG = "//label[@for='gender-" + TAG_GENDER + "']";
	
	private static final String TAG_LINEA = "@TagLinea";
	private static final String XPATH_CHECKBOX_LINEA_WITH_TAG = "//input[@id='" + TAG_LINEA + "']";
	
	public String getXPathCheckboxGender(GenderOption gender) {
		return XPATH_CHECKBOX_GENEDER_WITH_TAG.replace(TAG_GENDER, String.valueOf(gender.code));
	}
	public String getXPathCheckboxLinea(LineaType linea) {
		return XPATH_CHECKBOX_LINEA_WITH_TAG.replace(TAG_LINEA, linea.name().toUpperCase());
	}
	
	public boolean isPage() {
		return isPageUntil(0);
	}
	public boolean isPageUntil(int seconds) {
		return state(State.Present, XPATH_INPUT_NOMBRE).wait(seconds).check(); 
	}
	
	public void inputName(String name) {
		getElement(XPATH_INPUT_NOMBRE).sendKeys(name);
	}
	
	public void inputPostalCode(String postalCode) {
		getElement(XPATH_INPUT_POSTALCODE).sendKeys(postalCode);
	}
	
	public void inputDateOfBirth(String date) {
		getElement(XPATH_INPUT_DATE_BIRTH).sendKeys(date);
	}
	
	public void selectGender(GenderOption gender) {
		String xpathCheckbox = getXPathCheckboxGender(gender);
		click(xpathCheckbox).waitLink(1).exec();
	}
	
	public void selectLineas(List<LineaType> lineasToSelect) {
		//By default all lines are selected
		ALL_LINEAS.stream()
			.filter(s -> !lineasToSelect.contains(s))
			.forEach(s -> unselectLinea(s));
	}
	private void unselectLinea(LineaType linea) {
		String xpathCheckbox = getXPathCheckboxLinea(linea);
		click(xpathCheckbox).exec();
	}
	
	public void clickGuardar() {
		click(XPATH_SAVE_BUTTON).exec();
	}
}
