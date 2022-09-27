package com.mng.robotest.domains.compra.pageobject;

import com.mng.robotest.test.pageobject.manto.PageOrdenacionDePrendas;
import com.mng.robotest.test.utils.PaisGetter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.domains.transversal.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.SelectElement.TypeSelect.Value;
import static com.mng.robotest.test.data.PaisShop.PORTUGAL;

public class ModalDirecEnvioNew extends PageBase {

	private static final String XPATH_CHECK_DIRECION_PRINCIPAL = "//*[@for='address.form-isMainAddress']";
	private static final String XPATH_SAVE_BUTTON = "//*[@data-testid[contains(.,'save.button')]]";
	private static final String XPATH_REMOVE_BUTTON = "//*[@data-testid='address.form.delete.button']";
	private static final String XPATH_REMOVE_CONFIRM_BUTTON = "//button[@data-testid='address.form.modal.delete.button']";
	private static final String XPATH_SELECTOR_PROVINCIA = "//*[@id='address.form.provinceId']";
	private static final String XPATH_PROVINCIA = "//[@id='address.form.provinceId']/option[2]";

	public enum InputType {
		NOMBRE("address.form.firstName"),
		APELLIDOS("address.form.lastName"),
		DIRECCION("address.form.address"),
		CODIGO_POSTAL("address.form.postalCode"),
		MOVIL("address.form.phoneNumber"),
		CITY("address.form.city");

		private String dataTestId;
		private InputType(String dataTestId) {
			this.dataTestId = dataTestId;
		}
		public String getXPath() {
			return String.format("//input[@data-testid='%s']", dataTestId);
		}
	}
	private String getText(WebElement element) {
		return element.getText();
	}
	
	public boolean isVisible(int seconds) {
		return state(State.Present, InputType.NOMBRE.getXPath()).wait(seconds).check();
	}

	
	public void inputData(DirectionData direction) {
		inputData(InputType.NOMBRE, direction.getNombre());
		inputData(InputType.APELLIDOS, direction.getApellidos());
		inputData(InputType.DIRECCION, direction.getDireccion());
		inputData(InputType.CODIGO_POSTAL, direction.getCodPostal());
		inputData(InputType.CITY, direction.getCity());
		//clickSelectorProvincia();
		inputData(InputType.MOVIL, direction.getMobil());
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

	public void inputData(InputType inputType, String data) {
		//clear doesn't works in that case -> workaround
		//getElement(inputType.getXPath()).clear();
		getElement(inputType.getXPath()).sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
		getElement(inputType.getXPath()).sendKeys(data);
	}

	public void clickSelectorProvincia() {
		select(XPATH_SELECTOR_PROVINCIA, XPATH_PROVINCIA).type(Value).exec();
		waitMillis(5000);
		click(XPATH_PROVINCIA).waitLink(2).exec();


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
