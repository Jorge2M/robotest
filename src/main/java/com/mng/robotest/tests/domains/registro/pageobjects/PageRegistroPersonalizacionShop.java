package com.mng.robotest.tests.domains.registro.pageobjects;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.menus.beans.Linea;
import com.mng.robotest.tests.domains.menus.pageobjects.LineaWeb.LineaType;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.tests.domains.menus.pageobjects.LineaWeb.LineaType.*;

public class PageRegistroPersonalizacionShop extends PageBase {

	public enum GenderOption { 
		FEMENINO("FEMALE"), 
		MASCULINO("MALE"), 
		NO_BINARIO("BINARY"), 
		PREFIERO_NO_INCLUIRLO("DONT_ANSWER");
	
		private String code;
		private GenderOption(String code) {
			this.code = code;
		}
	}
	
	private static final List<LineaType> ALL_LINEAS = Arrays.asList(SHE, HE, KIDS, HOME); 
	
	private static final String XP_INPUT_NOMBRE = "//*[@data-testid='personalization.nameInput.text']";
	private static final String XP_INPUT_POSTALCODE = "//*[@data-testid='personalization.postalCodeInput.text']";
	private static final String XP_INPUT_DATE_BIRTH = "//*[@data-testid='personalization.birthDateInput.date']";
	private static final String XP_SAVE_BUTTON = "//*[@data-testid='personalization.personalizationButton.personalization']";
	private static final String XP_MESSAGE_ERROR_CODPOSTAL = "//p[@id='postalCode-error']";
	private static final String TAG_GENDER = "@TagGender";
	private static final String XP_CHECKBOX_GENEDER_WITH_TAG = "//label[@for='gender-" + TAG_GENDER + "']";
	private static final String TAG_LINEA = "@TagLinea";
	private static final String XP_CHECKBOX_LINEA_WITH_TAG = "//input[@id='" + TAG_LINEA + "']";
	
	public String getXPathCheckboxGender(GenderOption gender) {
		return XP_CHECKBOX_GENEDER_WITH_TAG.replace(TAG_GENDER, String.valueOf(gender.code));
	}
	public String getXPathCheckboxLinea(LineaType linea) {
		return XP_CHECKBOX_LINEA_WITH_TAG.replace(TAG_LINEA, linea.name().toUpperCase());
	}
	
	public boolean isPage() {
		return isPage(0);
	}
	public boolean isPage(int seconds) {
		return state(PRESENT, XP_INPUT_NOMBRE).wait(seconds).check(); 
	}
	public boolean isPostalCodeVisible() {
		return state(PRESENT, XP_INPUT_POSTALCODE).check();
	}
	
	public void inputName(String name) {
		getElement(XP_INPUT_NOMBRE).sendKeys(name);
	}
	
	public void inputPostalCode(String postalCode) {
		state(PRESENT, XP_INPUT_POSTALCODE).wait(2).check();
		getElement(XP_INPUT_POSTALCODE).sendKeys(postalCode);
	}
	
	public void inputDateOfBirth(String date) {
		getElement(XP_INPUT_DATE_BIRTH).sendKeys(date);
	}
	
	public void selectGender(GenderOption gender) {
		state(VISIBLE, getXPathCheckboxGender(gender)).wait(2).check();
		click(getXPathCheckboxGender(gender)).exec();
	}
	
	public void selectLineas(List<LineaType> lineasToSelect) {
		//By default all lines are selected
		getAllLineasCountry().stream()
			.filter(s -> !lineasToSelect.contains(s))
			.forEach(this::unselectLinea);
	}
	
	public static List<LineaType> getAllLineas() {
		return ALL_LINEAS;
	}
	
	private List<LineaType> getAllLineasCountry() {
		return dataTest.getPais().getShoponline().getLineasToTest(app).stream()
				.map(Linea::getType)
				.filter(ALL_LINEAS::contains)
				.toList();
	}
	private void unselectLinea(LineaType linea) {
		String xpathCheckbox = getXPathCheckboxLinea(linea);
		click(xpathCheckbox).exec();
	}
	
	public void clickGuardar() {
		click(XP_SAVE_BUTTON).exec();
	}
	
	public boolean checkMessageErrorMovil(int seconds) {
		return state(VISIBLE, XP_MESSAGE_ERROR_CODPOSTAL).wait(seconds).check();
	}
}
