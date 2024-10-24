package com.mng.robotest.tests.domains.compra.pageobjects.modals;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.compra.pageobjects.beans.DirectionData;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalDirecEnvioNew extends PageBase {

	private static final String XP_CHECK_DIRECCION_PRINCIPAL = "//*[@for='address.form-isMainAddress']";
	private static final String XP_CHECK_DIRECCION_PRINCIPAL_PRE = "//*[@data-testid='addressForm.isMainAddress']";
	
	//TODO dejar sólo una versión cuando la operativa de PRE suba a PRO (28-marzo-2023)
	private static final String XP_SAVE_BUTTON = "//*[@data-testid[contains(.,'save.button')]]";
	private static final String XP_SAVE_BUTTON_PRE = "//*[@data-testid='deliveryAddress.form.button.submit']";
	
	private static final String XP_REMOVE_BUTTON = "//*[@data-testid='address.form.delete.button']";
	private static final String XP_REMOVE_BUTTON_PRE = "//*[@data-testid='deliveryAddress.form.button.delete']";
	
	private static final String XP_REMOVE_CONFIRM_BUTTON = "//button[@data-testid='address.form.modal.delete.button']";
	private static final String XP_SELECTOR_PROVINCIA = "//select[@id='address.form.provinceId']";
	private static final String XP_PROVINCIA_OPTIONS_PRE = "//div[@data-testid='addressForm.provinceId']";
	private static final String XP_PROVINCIA_SELECTED_PRE = XP_PROVINCIA_OPTIONS_PRE + "//div[@aria-selected='true']";
	private static final String XP_PROVINCIA_SELECT = "//label[@for='provinceId']";

	public enum InputType {
		NOMBRE("address.form.firstName", "addressForm.firstName"),
		APELLIDOS("address.form.lastName", "addressForm.lastName"),
		DIRECCION("address.form.address", "addressForm.address"),
		CODIGO_POSTAL("address.form.postalCode", "addressForm.postalCode"),
		MOVIL("address.form.phoneNumber", "address.form.number"),
		CITY("address.form.city", "addressForm.city"),
		PROVINCIA("address.form.provinceName", "addressForm.provinceName");

		private String dataTestId;
		private String dataTestIdPre;
		private InputType(String dataTestId, String dataTestIdPre) {
			this.dataTestId = dataTestId;
			this.dataTestIdPre = dataTestIdPre;
		}
		public String getXPath() {
			return String.format("//input[@data-testid='%s' or @data-testid='%s']", dataTestId, dataTestIdPre);
		}
	}
	
	private String getXPathCheckDireccionPrincipal() {
		return "(" + XP_CHECK_DIRECCION_PRINCIPAL + " | " + XP_CHECK_DIRECCION_PRINCIPAL_PRE + ")";
	}	
	
	private String getXPathSaveButton() {
		return "(" + XP_SAVE_BUTTON + " | " + XP_SAVE_BUTTON_PRE + ")";
	}
	
	
	private String getXPathRemoveButton() {
		return "(" + XP_REMOVE_BUTTON + " | " + XP_REMOVE_BUTTON_PRE + ")";
	}
	
	private String getXPathSelectorProvincia() {
		return "(" + XP_SELECTOR_PROVINCIA + " | " + XP_PROVINCIA_OPTIONS_PRE + ")";
	}
	
	
	public boolean isVisible(int seconds) {
		return state(PRESENT, InputType.NOMBRE.getXPath()).wait(seconds).check();
	}
	
	public void inputData(DirectionData direction) {
		waitMillis(500);
		inputData(InputType.NOMBRE, direction.getNombre());
		inputData(InputType.APELLIDOS, direction.getApellidos());
		inputData(InputType.DIRECCION, direction.getDireccion());
		inputData(InputType.MOVIL, direction.getMobil());
		inputIfExists(InputType.CITY, "BARCELONA");
		inputIfExists(InputType.PROVINCIA, "BARCELONA");
		setProvinciaIfNotYet();
		inputData(InputType.CODIGO_POSTAL, direction.getCodPostal());
		if (direction.isPrincipal()) {
			clickLabelDirecPrincipal();
		}
		waitMillis(1000);
	}
	public void inputDataEdit(DirectionData direction) {
		inputData(InputType.NOMBRE, direction.getNombre());
		inputData(InputType.APELLIDOS, direction.getApellidos());
		inputData(InputType.DIRECCION, direction.getDireccion());
		waitMillis(1000);
	}

	private void inputIfExists(InputType inputType, String value) {
		if (isPresent(inputType)) {
			String valueInput = getElement(inputType.getXPath()).getAttribute("value");
			if ("".compareTo(valueInput)==0) {
				inputData(inputType, value);
			}
		}
	}
	
	private void inputData(InputType inputType, String data) {
		if (isPresent(inputType)) {
			//clear doesn't works in that case -> workaround
			//getElement(inputType.getXPath()).clear();
			getElement(inputType.getXPath()).sendKeys(KEYS_CLEAR_INPUT);
			getElement(inputType.getXPath()).sendKeys(data);
		}
	}

	private boolean isPresent(InputType inputType) {
		return state(PRESENT, inputType.getXPath()).wait(1).check();
	}
	
	private void setProvinciaIfNotYet() {
		if (state(PRESENT, getXPathSelectorProvincia()).check() && 
		   ("".compareTo(getValueInitialProvincia())==0)) {
			setProvincia();
		}
	}

	private String getValueInitialProvincia() {
		if (state(VISIBLE, XP_SELECTOR_PROVINCIA).check()) {
		    return getElement(XP_SELECTOR_PROVINCIA).getAttribute("value");
		}
		if (state(VISIBLE, XP_PROVINCIA_SELECTED_PRE).check()) {
			return getElement(XP_PROVINCIA_SELECTED_PRE).getText();
		}
		return "";
	}
	
	private void setProvincia() {
		if (state(VISIBLE, XP_SELECTOR_PROVINCIA).check()) {
			setProvinciaPro();
		} else {
			setProvinciaPre();
		}
	}
	
	private void setProvinciaPre() {
		click(XP_PROVINCIA_SELECT).exec();
		click(XP_PROVINCIA_OPTIONS_PRE + "/div").exec();
	}
	
	private void setProvinciaPro() {
		//workaround because doesn't run the Select sentence (seems a Selenium Bug)
		String allCharacters = "abcdefghijklmnopqrstuvwxyz";
		for (int i=0; i<allCharacters.length(); i++) {
			click(XP_SELECTOR_PROVINCIA).exec();
			getElement(XP_SELECTOR_PROVINCIA).sendKeys(allCharacters.substring(i,i+1));
			click(XP_SELECTOR_PROVINCIA).exec();
			String valueNew = getElement(XP_SELECTOR_PROVINCIA).getAttribute("value");
			if ("".compareTo(valueNew)!=0) {
				break;
			}
			waitMillis(1000);
		}
	}

	public void clickLabelDirecPrincipal() {
		click(getXPathCheckDireccionPrincipal()).exec();
		if (!getElement(getXPathCheckDireccionPrincipal()).isSelected()) {
			//En ocasiones falla el 1er click así que ejecutamos un 2o
			click(getXPathCheckDireccionPrincipal()).exec();
		}
	}
	
	public void clickSaveButton() {
		click(getXPathSaveButton()).exec();
	}
	
	public void clickRemoveButton() {
		moveToElement(getXPathRemoveButton());
		click(getXPathRemoveButton()).exec();
	}

	public boolean isVisibleModalConfirmacionEliminar(int seconds) {
		return state(VISIBLE, XP_REMOVE_CONFIRM_BUTTON).wait(seconds).check();
	}
	
	public void clickConfirmEliminarButton() {
		click(XP_REMOVE_CONFIRM_BUTTON).exec();
	}
}
