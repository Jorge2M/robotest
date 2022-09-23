package com.mng.robotest.domains.compra.pageobject;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.domains.transversal.PageBase;

public class ModalDirecEnvioNew extends PageBase {

	public static final String XPATH_CHECK_DIRECION_PRINCIPAL = "//*[@data-testid='form-isMainAddress']";
	public static final String XPATH_SAVE_BUTTON = "//*[@data-testid[contains(.,'save.button')]]";
	public static final String XPATH_REMOVE_BUTTON = "//*[@data-testid='address.form.delete.button']";
	public static final String XPATH_REMOVE_BUTTON2 = "button[@data-testid='address.form.delete.button']";

	
	public enum InputType {
		NOMBRE("address.form.firstName"),
		APELLIDOS("address.form.lastName"),
		DIRECCION("address.form.address"),
		CODIGO_POSTAL("address.form.postalCode"),
		MOVIL("address.form.phoneNumber");
		
		private String dataTestId;
		private InputType(String dataTestId) {
			this.dataTestId = dataTestId;
		}
		public String getXPath() {
			return String.format("//*[@data-testid='%s']", dataTestId);
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
		waitMillis(1000);
	}
	public void inputDataEdit(DirectionData2 direction) {
		inputData(InputType.NOMBRE, direction.getNombre());
		inputData(InputType.APELLIDOS, direction.getApellidos());
		inputData(InputType.DIRECCION, direction.getDireccion());
		waitMillis(1000);
	}

	public void inputData(InputType inputType, String data) {
		getElement(inputType.getXPath()).clear();
		getElement(inputType.getXPath()).sendKeys(data);
	}

	public void clickLabelDirecPrincipal() {
		click(XPATH_CHECK_DIRECION_PRINCIPAL).exec();
	}
	
	public void clickSaveButton() {
		click(XPATH_SAVE_BUTTON).exec();
	}
	
	public void clickRemoveButton() {
		click(XPATH_REMOVE_BUTTON).exec();
		click(XPATH_REMOVE_BUTTON).exec();

	}
	public void clickRemoveButton2() {
		click(XPATH_REMOVE_BUTTON2).exec();

	}
}
