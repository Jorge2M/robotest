package com.mng.robotest.domains.compra.payments.klarna.pageobjects;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.domains.transversal.PageBase;

public class ModalUserDataKlarna extends PageBase {

	public static enum InputKlarna {
		EMAIL("billing-dialog-email"),
		CODIGO_POSTAL("billing-dialog-postal_code"),
		PERSON_NUMBER("billing-dialog-national_identification_number"),
		USER_NAME("billing-dialog-given_name"),
		APELLIDOS("billing-dialog-family_name"),
		DIRECCION("billing-dialog-street_address"),
		CIUDAD("billing-dialog-city"),
		PHONE("billing-dialog-phone");
		
		private String id;
		private InputKlarna(String id) {
			this.id = id;
		}
		public String getXPath() {
			return "//input[@id='" + id + "']";
		}
	}
	
	private static final String XPATH_BUTTON_CONTINUE = "//button[@id='button-primary']";
	
	public boolean isModal(int seconds) {
		return state(State.Visible, InputKlarna.EMAIL.getXPath()).wait(seconds).check();
	}
	
	public void inputData(DataKlarna dataKlarna) {
		inputDataWithRetry(dataKlarna, 3);
	}
	
	private void inputDataWithRetry(DataKlarna dataKlarna, int retrys) {
		for (int i=0; i<retrys; i++) {
			inputVoidData(dataKlarna);
			waitMillis(500);
		}
	}
	
	private void inputVoidData(DataKlarna dataKlarna) {
		if (dataKlarna.getEmail()!=null &&
			dataKlarna.getEmail().compareTo(getInputValue(InputKlarna.EMAIL))!=0) {
			clearInput(InputKlarna.EMAIL);
			input(InputKlarna.EMAIL, dataKlarna.getEmail());
		}
		if (dataKlarna.getCodPostal()!=null &&
			dataKlarna.getCodPostal().compareTo(getInputValue(InputKlarna.CODIGO_POSTAL))!=0) {
			clearInput(InputKlarna.CODIGO_POSTAL);
			input(InputKlarna.CODIGO_POSTAL, dataKlarna.getCodPostal());
		}
		if (dataKlarna.getPersonnumber()!=null &&
			isVisible(InputKlarna.PERSON_NUMBER) &&
			dataKlarna.getPersonnumber().compareTo(getInputValue(InputKlarna.PERSON_NUMBER))!=0) {
			clearInput(InputKlarna.PERSON_NUMBER);
			input(InputKlarna.PERSON_NUMBER, dataKlarna.getPersonnumber());
		}
		if (dataKlarna.getUserName()!=null &&
			dataKlarna.getUserName().compareTo(getInputValue(InputKlarna.USER_NAME))!=0) {
			input(InputKlarna.USER_NAME, dataKlarna.getUserName());
		}
		if (dataKlarna.getApellidos()!=null &&
			dataKlarna.getApellidos().compareTo(getInputValue(InputKlarna.APELLIDOS))!=0) {
			clearInput(InputKlarna.APELLIDOS);
			input(InputKlarna.APELLIDOS, dataKlarna.getApellidos());
		}
		if (dataKlarna.getDireccion()!=null) {
			waitMillis(500);
			if (dataKlarna.getDireccion().compareTo(getInputValue(InputKlarna.DIRECCION))!=0) {
				clearInput(InputKlarna.DIRECCION);
				input(InputKlarna.DIRECCION, dataKlarna.getDireccion());
			}
		}
		if (dataKlarna.getPhone()!=null &&
			dataKlarna.getPhone().compareTo(getInputValue(InputKlarna.PHONE))!=0) {
			clearInput(InputKlarna.PHONE);
			input(InputKlarna.PHONE, dataKlarna.getPhone());
		}
	}
	
	public void clickButtonContinue() {
		click(XPATH_BUTTON_CONTINUE).exec();
		if (!state(State.Invisible, XPATH_BUTTON_CONTINUE).wait(2).check()) {
			click(XPATH_BUTTON_CONTINUE).exec();
		}
	}
	
	private boolean isVisible(InputKlarna inputKlarna) {
		return state(State.Visible, inputKlarna.getXPath()).check();
	}
	
	private String getInputValue(InputKlarna inputKlarna) {
		return getElement(inputKlarna.getXPath()).getAttribute("value");
	}
	
	private void input(InputKlarna inputKlarna, String text) {
		getElement(inputKlarna.getXPath()).sendKeys(text);
	}
	private void clearInput(InputKlarna inputKlarna) {
		getElement(inputKlarna.getXPath()).clear();
	}
	
}
