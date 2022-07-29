package com.mng.robotest.domains.registro.pageobjects;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.domains.registro.pageobjects.beans.DataRegistro;
import com.mng.robotest.domains.registro.pageobjects.beans.ListDataRegistro;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.pageobject.shop.checkout.Page2IdentCheckout;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageRegistroDirec extends PageObjTM {

	private final static String XPATH_DIV_ERROR = "//div[@class='errorValidation']";
	private final static String XPATH_INPUT_DIREC = "//input[@id[contains(.,':cfDir1')]]";
	private final static String XPATH_SELECT_PAIS = "//select[@id[contains(.,':pais')]]";
	private final static String XPATH_INPUT_COD_POSTAL = "//input[@id[contains(.,':cfCp')]]";
	private final static String XPATH_SELECT_POBLACION = "//select[@id[contains(.,':localidades')]]";
	private final static String XPATH_SELECT_PROVINCIA = "//select[@id[contains(.,':estadosPais')]]";
	private final static String XPATH_FINALIZAR_BUTTON = "//form[@id[contains(.,'Step')]]//input[@type='submit']";
	
	public PageRegistroDirec(WebDriver driver) {
		super(driver);
	}
	
	public int getNumberMsgInputInvalid() {
		return (getNumElementsVisible(driver, By.xpath(XPATH_DIV_ERROR)));
	}
	
	public void sendDataAccordingCountryToInputs(
			Map<String,String> dataRegistro, Pais pais, Channel channel) throws Exception {
		dataRegistro.putAll(new Page2IdentCheckout(pais, driver)
				.inputDataPorDefectoSegunPais(dataRegistro.get("cfEmail"), false, false, channel));
	}
	
	public void sendDataToInputs(ListDataRegistro dataToSend, int repeat) throws Exception {
		for (int i=0; i<repeat; i++) {
			for (DataRegistro dataInput : dataToSend.getDataPageDirec()) {
				switch (dataInput.getDataRegType()) {
				case direccion:
					sendDataToDireccionIfNotExist(dataInput.getData());
					break;			
				case codpais:
					sendDataToPaisIfNotExist(dataInput.getData());
					break;
				case codpostal:
					sendDataToCodPostalIfNotExist(dataInput.getData());
					break;
				case poblacion:
					sendDataToPoblacion(dataInput.getData());
					break;
				case provincia:
					sendDataToProvincia(dataInput.getData());
					break;
				default:
					break;
				}
			}
			
			Thread.sleep(1000);
		}
	}
	
	public void sendDataToDireccionIfNotExist(String direccion) {
		sendKeysToInputIfNotExist(direccion, XPATH_INPUT_DIREC);
	}
	
	public void sendDataToPaisIfNotExist(String codigoPais) {
		String xpathSelectedPais = XPATH_SELECT_PAIS + "/option[@selected='selected' and @value='" + codigoPais + "']";
		if (state(Present, By.xpath(xpathSelectedPais)).check()) {
			new Select(driver.findElement(By.xpath(XPATH_SELECT_PAIS))).selectByValue(codigoPais);
		}
	}	
	
	public void sendDataToCodPostalIfNotExist(String codPostal) {
		sendKeysToInputIfNotExist(codPostal, XPATH_INPUT_COD_POSTAL);
	}
	
	public void sendDataToPoblacion(String poblacion) {
		new Select(driver.findElement(By.xpath(XPATH_SELECT_POBLACION))).selectByValue(poblacion);
	}
	
	public void sendDataToProvincia(String provincia) {
		String valueProvincia = driver.findElement(By.xpath(XPATH_SELECT_PROVINCIA + "/option[text()='" + provincia + "']")).getAttribute("value");
		new Select(driver.findElement(By.xpath(XPATH_SELECT_PROVINCIA))).selectByValue(valueProvincia);
	}
	
	public boolean isVisibleFinalizarButton() {
		return (state(Visible, By.xpath(XPATH_FINALIZAR_BUTTON)).check());
	}
	
	public void clickFinalizarButton() {
		click(By.xpath(XPATH_FINALIZAR_BUTTON)).exec();
		
		//Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no funciona así que ejecutamos un 2o 
		if (isVisibleFinalizarButton()) {
			click(By.xpath(XPATH_FINALIZAR_BUTTON)).type(javascript).exec();
		}
	}
	
	private void sendKeysToInputIfNotExist(String dataToSend, String xpathInput) {
		if (driver.findElement(By.xpath(xpathInput)).getAttribute("value").compareTo(dataToSend)!=0) {
			driver.findElement(By.xpath(xpathInput)).clear();
			driver.findElement(By.xpath(xpathInput)).sendKeys(dataToSend);
			driver.findElement(By.xpath(xpathInput)).sendKeys(Keys.TAB);
		}
	}
}
