package com.mng.robotest.tests.domains.registro.pageobjects;

import java.util.List;

import com.mng.robotest.tests.domains.menus.beans.Linea;
import com.mng.robotest.tests.domains.menus.pageobjects.LineaWeb.LineaType;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageRegistroPersonalizacionShopGenesis extends PageRegistroPersonalizacionShop {

	private static final String XP_INPUT_NOMBRE = "//*[@data-testid='personalization.nameInput.text']";
	private static final String XP_INPUT_POSTALCODE = "//*[@data-testid='personalization.postalCodeInput.text']";
	private static final String XP_INPUT_DATE_BIRTH = "//*[@data-testid='personalization.birthDateInput.date']";
	private static final String XP_SAVE_BUTTON = "//*[@data-testid='personalization.personalizationButton.personalization']";
	private static final String XP_MESSAGE_ERROR_CODPOSTAL = "//p[@id[contains(.,'postal-code-input-error')]]";
	private static final String TAG_GENDER = "@TagGender";
	private static final String XP_CHECKBOX_GENEDER_WITH_TAG = "//label[@for='gender-" + TAG_GENDER + "']";
	private static final String TAG_LINEA = "@TagLinea";
	private static final String XP_CHECKBOX_LINEA_WITH_TAG = "//input[@id='" + TAG_LINEA + "']";
	
	private String getXPathCheckboxGender(GenderOption gender) {
		return XP_CHECKBOX_GENEDER_WITH_TAG.replace(TAG_GENDER, String.valueOf(gender.getCode()));
	}
	private String getXPathCheckboxLinea(LineaType linea) {
		return XP_CHECKBOX_LINEA_WITH_TAG.replace(TAG_LINEA, linea.name().toUpperCase());
	}
	
	@Override
	public boolean isPage() {
		return isPage(0);
	}
	@Override
	public boolean isPage(int seconds) {
		return state(PRESENT, XP_INPUT_NOMBRE).wait(seconds).check(); 
	}
	@Override
	public boolean isNotPage(int seconds) {
		return state(INVISIBLE, XP_INPUT_NOMBRE).wait(seconds).check();
	}
	@Override
	public boolean isPostalCodeVisible() {
		return state(PRESENT, XP_INPUT_POSTALCODE).check();
	}
	@Override
	public void inputName(String name) {
		sendKeysWithRetry(2, getElement(XP_INPUT_NOMBRE), name);
	}
	@Override
	public void inputPostalCode(String postalCode) {
		state(PRESENT, XP_INPUT_POSTALCODE).wait(2).check();
		sendKeysWithRetry(2, getElement(XP_INPUT_POSTALCODE), postalCode);
	}
	@Override
	public void inputDateOfBirth(String date) {
		getElement(XP_INPUT_DATE_BIRTH).sendKeys(date);
	}
	@Override
	public void selectGender(GenderOption gender) {
		var xpGender = getXPathCheckboxGender(gender);
		state(VISIBLE, xpGender).wait(2).check();
		click(xpGender).exec();
	}
	@Override
	public void selectLineas(List<LineaType> lineasToSelect) {
		//By default all lines are selected
		getAllLineasCountry().stream()
			.filter(s -> !lineasToSelect.contains(s))
			.forEach(this::unselectLinea);
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
	
	@Override
	public void clickGuardar() {
		click(XP_SAVE_BUTTON).exec();
	}
	
	@Override
	public boolean checkMessageErrorMovil(int seconds) {
		return state(VISIBLE, XP_MESSAGE_ERROR_CODPOSTAL).wait(seconds).check();
	}
	
}
