package com.mng.robotest.test.pageobject.shop.checkout.klarna;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;


public class ModalUserDataKlarna extends PageObjTM {

	public static enum InputKlarna {
		Email("billing-dialog-email"),
		Codigo_Postal("billing-dialog-postal_code"),
		Person_Number("billing-dialog-national_identification_number"),
		User_Name("billing-dialog-given_name"),
		Apellidos("billing-dialog-family_name"),
		Direccion("billing-dialog-street_address"),
		Ciudad("billing-dialog-city"),
		Phone("billing-dialog-phone");
		
		private String id;
		private InputKlarna(String id) {
			this.id = id;
		}
		public String getXPath() {
			return "//input[@id='" + id + "']";
		}
	}
	
	private final static String XPathButtonContinue = "//button[@id='button-primary']";
	
	public ModalUserDataKlarna(WebDriver driver) {
		super(driver);
	}
	
	public boolean isModal(int maxSeconds) {
		return state(State.Visible, By.xpath(InputKlarna.Email.getXPath())).wait(maxSeconds).check();
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
		if (dataKlarna.getEmail()!=null) {
			if (dataKlarna.getEmail().compareTo(getInputValue(InputKlarna.Email))!=0) {
				clearInput(InputKlarna.Email);
				input(InputKlarna.Email, dataKlarna.getEmail());
			}
		}
		if (dataKlarna.getCodPostal()!=null) {
			if (dataKlarna.getCodPostal().compareTo(getInputValue(InputKlarna.Codigo_Postal))!=0) {
				clearInput(InputKlarna.Codigo_Postal);
				input(InputKlarna.Codigo_Postal, dataKlarna.getCodPostal());
			}
		}
		if (dataKlarna.getPersonnumber()!=null &&
			isVisible(InputKlarna.Person_Number)) {
			if (dataKlarna.getPersonnumber().compareTo(getInputValue(InputKlarna.Person_Number))!=0) {
				clearInput(InputKlarna.Person_Number);
				input(InputKlarna.Person_Number, dataKlarna.getPersonnumber());
			}
		}
		if (dataKlarna.getUserName()!=null) {
			if (dataKlarna.getUserName().compareTo(getInputValue(InputKlarna.User_Name))!=0) {
				input(InputKlarna.User_Name, dataKlarna.getUserName());
			}
		}
		if (dataKlarna.getApellidos()!=null) {
			if (dataKlarna.getApellidos().compareTo(getInputValue(InputKlarna.Apellidos))!=0) {
				clearInput(InputKlarna.Apellidos);
				input(InputKlarna.Apellidos, dataKlarna.getApellidos());
			}
		}
		if (dataKlarna.getDireccion()!=null) {
			waitMillis(500);
			if (dataKlarna.getDireccion().compareTo(getInputValue(InputKlarna.Direccion))!=0) {
				clearInput(InputKlarna.Direccion);
				input(InputKlarna.Direccion, dataKlarna.getDireccion());
			}
		}
//		if (dataKlarna.getCiudad()!=null) {
//			input(InputKlarna.Ciudad, dataKlarna.getCiudad());
//		}
		if (dataKlarna.getPhone()!=null) {
			if (dataKlarna.getPhone().compareTo(getInputValue(InputKlarna.Phone))!=0) {
				clearInput(InputKlarna.Phone);
				input(InputKlarna.Phone, dataKlarna.getPhone());
			}
		}
	}
	
	public void clickButtonContinue() {
		click(By.xpath(XPathButtonContinue)).exec();
		if (!state(State.Invisible, By.xpath(XPathButtonContinue)).wait(2).check()) {
			click(By.xpath(XPathButtonContinue)).exec();
		}
	}
	
	private boolean isVisible(InputKlarna inputKlarna) {
		return state(State.Visible, By.xpath(inputKlarna.getXPath())).check();
	}
	
	private String getInputValue(InputKlarna inputKlarna) {
		return driver.findElement(By.xpath(inputKlarna.getXPath())).getAttribute("value");
	}
	
	private void input(InputKlarna inputKlarna, String text) {
		driver.findElement(By.xpath(inputKlarna.getXPath())).sendKeys(text);
	}
	private void clearInput(InputKlarna inputKlarna) {
		driver.findElement(By.xpath(inputKlarna.getXPath())).clear();
	}
	
}
