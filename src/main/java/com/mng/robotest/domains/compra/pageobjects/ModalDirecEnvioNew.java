package com.mng.robotest.domains.compra.pageobjects;

import org.openqa.selenium.Keys;
import com.mng.robotest.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalDirecEnvioNew extends PageBase {

	private static final String XPATH_CHECK_DIRECCION_PRINCIPAL = "//*[@for='address.form-isMainAddress']";
	private static final String XPATH_CHECK_DIRECCION_PRINCIPAL_PRE = "//*[@data-testid='addressForm.isMainAddress']";
	
	//TODO dejar sólo una versión cuando la operativa de PRE suba a PRO (28-marzo-2023)
	private static final String XPATH_SAVE_BUTTON = "//*[@data-testid[contains(.,'save.button')]]";
	private static final String XPATH_SAVE_BUTTON_PRE = "//*[@data-testid='deliveryAddress.form.button.submit']";
	
	private static final String XPATH_REMOVE_BUTTON = "//*[@data-testid='address.form.delete.button']";
	private static final String XPATH_REMOVE_BUTTON_PRE = "//*[@data-testid='deliveryAddress.form.button.delete']";
	
	private static final String XPATH_REMOVE_CONFIRM_BUTTON = "//button[@data-testid='address.form.modal.delete.button']";
	private static final String XPATH_SELECTOR_PROVINCIA = "//select[@id='address.form.provinceId']";
	private static final String XPATH_PROVINCIA_OPTIONS_PRE = "//div[@data-testid='addressForm.provinceId']";
	private static final String XPATH_PROVINCIA_SELECTED_PRE = XPATH_PROVINCIA_OPTIONS_PRE + "//div[@aria-selected='true']";
	private static final String XPATH_PROVINCIA_SELECT = "//label[@for='provinceId']";

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
		return "(" + XPATH_CHECK_DIRECCION_PRINCIPAL + " | " + XPATH_CHECK_DIRECCION_PRINCIPAL_PRE + ")";
	}	
	
	private String getXPathSaveButton() {
		return "(" + XPATH_SAVE_BUTTON + " | " + XPATH_SAVE_BUTTON_PRE + ")";
	}
	
	
	private String getXPathRemoveButton() {
		return "(" + XPATH_REMOVE_BUTTON + " | " + XPATH_REMOVE_BUTTON_PRE + ")";
	}
	
	private String getXPathSelectorProvincia() {
		return "(" + XPATH_SELECTOR_PROVINCIA + " | " + XPATH_PROVINCIA_OPTIONS_PRE + ")";
	}
	
	
	public boolean isVisible(int seconds) {
		return state(Present, InputType.NOMBRE.getXPath()).wait(seconds).check();
	}
	
	public void inputData(DirectionData direction) {
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
			getElement(inputType.getXPath()).sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
			getElement(inputType.getXPath()).sendKeys(data);
		}
	}

	private boolean isPresent(InputType inputType) {
		return state(Present, inputType.getXPath()).wait(1).check();
	}
	
	private void setProvinciaIfNotYet() {
		if (state(Present, getXPathSelectorProvincia()).check()) {
			if ("".compareTo(getValueInitialProvincia())==0) {
				setProvincia();
			}
		}
	}

	private String getValueInitialProvincia() {
		if (state(Visible, XPATH_SELECTOR_PROVINCIA).check()) {
		    return getElement(XPATH_SELECTOR_PROVINCIA).getAttribute("value");
		}
		if (state(Visible, XPATH_PROVINCIA_SELECTED_PRE).check()) {
			return getElement(XPATH_PROVINCIA_SELECTED_PRE).getText();
		}
		return "";
	}
	
	private void setProvincia() {
		if (state(Visible, XPATH_SELECTOR_PROVINCIA).check()) {
			setProvinciaPro();
		} else {
			setProvinciaPre();
		}
	}
	
	private void setProvinciaPre() {
		click(XPATH_PROVINCIA_SELECT).exec();
		click(XPATH_PROVINCIA_OPTIONS_PRE + "/div").exec();
	}
	
	private void setProvinciaPro() {
		//workaround because doesn't run the Select sentence (seems a Selenium Bug)
		String allCharacters = "abcdefghijklmnopqrstuvwxyz";
		for (int i=0; i<allCharacters.length(); i++) {
			click(XPATH_SELECTOR_PROVINCIA).exec();
			getElement(XPATH_SELECTOR_PROVINCIA).sendKeys(allCharacters.substring(i,i+1));
			click(XPATH_SELECTOR_PROVINCIA).exec();
			String valueNew = getElement(XPATH_SELECTOR_PROVINCIA).getAttribute("value");
			if ("".compareTo(valueNew)!=0) {
				break;
			}
			waitMillis(1000);
		}
	}

	public void clickLabelDirecPrincipal() {
		click(getXPathCheckDireccionPrincipal()).exec();
	}
	
	public void clickSaveButton() {
		click(getXPathSaveButton()).exec();
	}
	
	public void clickRemoveButton() {
		click(getXPathRemoveButton()).exec();
	}

	public boolean isVisibleModalConfirmacionEliminar(int seconds) {
		return state(Visible, XPATH_REMOVE_CONFIRM_BUTTON).wait(seconds).check();
	}
	
	public void clickConfirmEliminarButton() {
		click(XPATH_REMOVE_CONFIRM_BUTTON).exec();
	}
}
