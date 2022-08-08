package com.mng.robotest.test.pageobject.shop.checkout;

import java.util.Iterator;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.pageobject.shop.checkout.DataDireccion.DataDirType;


public abstract class ModalDireccion extends PageBase {

	private static final String XPathInputNif = "//input[@id[contains(.,'cfDni')]]";
	private static final String XPathInputName = "//input[@id[contains(.,'cfName')]]";
	private static final String XPathInputApellidos = "//input[@id[contains(.,'cfSname')]]";
	private static final String XPathInputDireccion = "//input[@id[contains(.,'cfDir1')]]";
	private static final String XPathInputCodpostal = "//input[@id[contains(.,'cfCp')]]";
	private static final String XPathInputEmail = "//input[@id[contains(.,'cfEmail')]]";
	private static final String XPathInputTelefono = "//input[@id[contains(.,'cfTelf')]]";
	private static final String XPathSelectPoblacion = "//select[@id[contains(.,':localidades')]]";
	private static final String XPathSelectProvincia = "//select[@id[contains(.,':estadosPais')]]";
	private static final String XPathSelectPais = "//select[@id[contains(.,':pais')]]";
	
	public ModalDireccion(WebDriver driver) {
		super(driver);
	}
	
	private String getXPathInput(DataDirType inputType) {
		switch (inputType) {
		case nif:
			return XPathInputNif;
		case name:
			return XPathInputName;
		case apellidos:
			return XPathInputApellidos;
		case direccion:
			return XPathInputDireccion;
		case codpostal:
			return XPathInputCodpostal;
		case email:
			return XPathInputEmail;
		case telefono:
			return XPathInputTelefono;
		default:
			return "";
		}
	}

	public void sendDataToInputsNTimes(DataDireccion dataToSend, int nTimes, String XPathFormModal) throws Exception {
		for (int i=0; i<nTimes; i++) {
			sendDataToInputs(dataToSend, XPathFormModal);
			waitForPageLoaded(driver);
		}
	}
	
	public void sendDataToInputs(DataDireccion dataToSend, String XPathFormModal) throws Exception {
		try {
			Iterator<Map.Entry<DataDirType,String>> it = dataToSend.getDataDireccion().entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<DataDirType,String> pair = it.next();
				switch (pair.getKey()) {
				case poblacion:
					selectPoblacion(pair.getValue(), XPathFormModal);
					break;
				case provincia:
					selectProvincia(pair.getValue(), XPathFormModal);
					break;
				case codigoPais:
					selectPais(pair.getValue(), XPathFormModal);
					break;
				default:
					sendKeysToInput(pair.getKey(), pair.getValue(), XPathFormModal);				
				}
			}
		}
		catch (Exception e) {
			/*
			 * Es posible que realmente el send se haya ejecutado correctamente
			 */
		}
	}
	
	public void selectPoblacion(String poblacion, String XPathFormModal) throws Exception {
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XPathFormModal + XPathSelectPoblacion)));
		Thread.sleep(1000);
		new Select(driver.findElement(By.xpath(XPathFormModal + XPathSelectPoblacion))).selectByValue(poblacion);
	}
	
	public void selectProvincia(String provincia, String XPathFormModal) {
		new WebDriverWait(driver, 2).until(ExpectedConditions.elementToBeClickable(By.xpath(XPathFormModal + XPathSelectProvincia)));
		new Select(driver.findElement(By.xpath(XPathFormModal + XPathSelectProvincia))).selectByVisibleText(provincia);
	}

	public void selectPais(String codigoPais, String XPathFormModal) {
		String xpathSelectedPais = XPathSelectPais + "/option[@selected='selected' and @value='" + codigoPais + "']";
		if (!state(Present, By.xpath(xpathSelectedPais), driver).check()) {
			new WebDriverWait(driver, 2).until(ExpectedConditions.elementToBeClickable(By.xpath(XPathFormModal + XPathSelectPais)));
			new Select(driver.findElement(By.xpath(XPathFormModal + XPathSelectPais))).selectByValue(codigoPais);
		}
	}

	private void sendKeysToInput(DataDirType inputType, String dataToSend, String XPathFormModal) {
		String xpathInput = XPathFormModal + getXPathInput(inputType);
//		WebElement input = driver.findElement(By.xpath(xpathInput));
//		input.clear();
//		input.sendKeys(dataToSend);
		//Hay un problema en Chrome que provoca que aleatoriamente se corten los strings enviados mediante SendKeys. Así que debemos reintentarlo si no ha funcionado correctamente
		//https://github.com/angular/protractor/issues/2019
		ifNotValueSetedSendKeysWithRetry(2/*numRetry*/, dataToSend, By.xpath(xpathInput), driver);
	}
}
