package com.mng.robotest.domains.compra.pageobject;

import org.openqa.selenium.Keys;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.domains.transversal.PageBase;

public class ModalDirecEnvioNew extends PageBase {

	private static final String XPATH_CHECK_DIRECION_PRINCIPAL = "//*[@for='address.form-isMainAddress']";
	private static final String XPATH_SAVE_BUTTON = "//*[@data-testid[contains(.,'save.button')]]";
	private static final String XPATH_REMOVE_BUTTON = "//*[@data-testid='address.form.delete.button']";
	private static final String XPATH_REMOVE_CONFIRM_BUTTON = "//button[@data-testid='address.form.modal.delete.button']";
	private static final String XPATH_SELECTOR_PROVINCIA = "//select[@id='address.form.provinceId']";

	public enum InputType {
		NOMBRE("address.form.firstName"),
		APELLIDOS("address.form.lastName"),
		DIRECCION("address.form.address"),
		CODIGO_POSTAL("address.form.postalCode"),
		MOVIL("address.form.phoneNumber"),
		CITY("address.form.city"),
		PROVINCIA("address.form.provinceName");

		private String dataTestId;
		private InputType(String dataTestId) {
			this.dataTestId = dataTestId;
		}
		public String getXPath() {
			return String.format("//input[@data-testid='%s']", dataTestId);
		}

	}
	public boolean isVisible(int seconds) {
		return state(State.Present, InputType.NOMBRE.getXPath()).wait(seconds).check();
	}
	
	public void inputData(DirectionData direction) {
		inputData(InputType.NOMBRE, direction.getNombre());
		inputData(InputType.APELLIDOS, direction.getApellidos());
		inputData(InputType.DIRECCION, direction.getDireccion());
		inputData(InputType.CODIGO_POSTAL, direction.getCodPostal());
		inputData(InputType.MOVIL, direction.getMobil());
		inputIfExists(InputType.CITY, "BARCELONA");
		inputIfExists(InputType.PROVINCIA, "BARCELONA");
		setProvinciaIfNotYet();
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
		return state(State.Present, inputType.getXPath()).check();
	}
	
	private void setProvinciaIfNotYet() {
		if (state(State.Visible, XPATH_SELECTOR_PROVINCIA).check()) {
			String valueInicial = getElement(XPATH_SELECTOR_PROVINCIA).getAttribute("value");
			if ("".compareTo(valueInicial)==0) {
				setProvincia();
			}
		}
	}

	private void setProvincia() {
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
		click(XPATH_CHECK_DIRECION_PRINCIPAL).exec();
	}
	
	public void clickSaveButton() {
		click(XPATH_SAVE_BUTTON).exec();
	}
	
	public void clickRemoveButton() {
		click(XPATH_REMOVE_BUTTON).exec();
	}

	public boolean isVisibleModalConfirmacionEliminar(int seconds) {
		return state(State.Visible, XPATH_REMOVE_CONFIRM_BUTTON).wait(seconds).check();
	}
	
	public void clickConfirmEliminarButton() {
		click(XPATH_REMOVE_CONFIRM_BUTTON).exec();
	}
}
