package com.mng.robotest.domains.registro.pageobjects;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.compra.pageobjects.Page2IdentCheckout;
import com.mng.robotest.domains.registro.beans.DataRegistro;
import com.mng.robotest.domains.registro.beans.InputDataXPath;
import com.mng.robotest.domains.registro.beans.ListDataRegistro;
import com.mng.robotest.domains.registro.beans.ListDataRegistro.DataRegType;
import com.mng.robotest.domains.transversal.PageBase;

public class PageRegistroIniOutlet extends PageBase {
	
//	private static final String XPATH_PESTANYA_REGISTRO = "//*[@class[contains(.,'registerTab')]]";
	private static final String XPATH_HEADER_NEWS = "//div[@class[contains(.,'registerModal')]]//div[@class='info']";
	
	private static final String XPATH_NEWSLETTER_TITLE = XPATH_HEADER_NEWS + "//p[@class[contains(.,'newsletter-register-title')]]";
	
	private static final String XPATH_INPUT_NAME = "//input[@id[contains(.,'cfName')]]";
	private static final String XPATH_INPUT_APELLIDOS = "//input[@id[contains(.,'cfSname')]]";
	private static final String XPATH_INPUT_EMAIL = "//input[@id[contains(.,'cfEmail')]]";
	private static final String XPATH_INPUT_PASSWORD = "//input[@id[contains(.,'cfPassw')]]";
	private static final String XPATH_INPUT_TELEFONO = "//input[@id[contains(.,'cfTelf')]]";
	private static final String XPATH_INPUT_COD_POSTAL = "//input[@id[contains(.,'cfCp')]]";
	private static final String XPATH_SELECT_PAIS = "//select[@id[contains(.,':pais')]]";
	private static final String XPATH_BUTTON_REGISTRATE = "//div[@class[contains(.,'registerModal')]]//div[@class='submitContent']//input[@type='submit']";

	private static final String XPATH_DIV_ERROR_NAME = "//div[@id[contains(.,'cfName')] and @class='errorValidation']";
	private static final String XPATH_DIV_ERROR_APELLIDOS = "//div[@id[contains(.,'cfSname')] and @class='errorValidation']";
	private static final String XPATH_DIV_ERROR_EMAIL = "//div[@id[contains(.,'cfEmail')] and @class='errorValidation']";
	private static final String XPATH_DIV_ERROR_PASSWORD = "//div[@id[contains(.,'cfPassw')] and @class='errorValidation']";
	private static final String XPATH_DIV_ERROR_TELEFONO = "//div[@id[contains(.,'cfTelf')] and @class='errorValidation']";
	private static final String XPATH_DIV_ERROR_COD_POSTAL = "//div[@id[contains(.,'cfCp')] and @class='errorValidation']";
	
	private static final String XPATH_CAPA_LOADING = "//div[@class[contains(.,'container-full-centered-loading')]]";
	
	private static final String XPATH_CHECKBOX_PUBLI = "//input[@id[contains(.,'STEP1_cfPubli')]]";
	private static final String XPATH_TEXT_RGPD = "//p[@class='gdpr-text gdpr-profiling']";
	private static final String XPATH_LEGAL_RGPD = "//p[@class='gdpr-text gdpr-data-protection']";
	private static final String XPATH_TEXT_RGPD_LOYALTY = "//p[@class='gdpr-text-loyalty gdpr-profiling']";
	private static final String XPATH_LEGAL_RGPD_LOYALTY = "//p[@class='gdpr-text-loyalty gdpr-data-protection']";
	
	public static final String XPATH_INPUT_OBLIGATORIO_NO_INFORMADO = "//input[@placeholder[contains(.,'*')] and @value='']";
	
	private static final String MSG_NAME_INVALID = "nombre. Este campo solo acepta letras";
	private static final String MSG_APELLIDOS_INVALID = "apellidos. Este campo solo acepta letras";
	private static final String MSG_EMAIL_INVALID = "Introduce un e-mail correcto";
	private static final String MSG_PASSWORD_INVALID = "Revisa la contraseña";
	private static final String MSG_TELEFONO_INVALID = "Revisa tu móvil";
	private static final String MSG_COD_POSTAL_INVALID = "Revisa este campo";
	
	private static final String MSG_CAMPO_OBLIGATORIO = "Este campo es obligatorio";
	private static final String MSG_USR_DUPLICADO_POST_CLICK = "Email ya registrado";

	
	private String getXPath_mensajeErrorFormulario(String mensajeError) {
		return ("//div[@class='formErrors']//li[text()[contains(.,'" + mensajeError + "')]]");
	}
	
	private InputDataXPath getXPathDataInput(DataRegType inputType) {
		switch (inputType) {
		case NAME:
			return (new InputDataXPath(XPATH_INPUT_NAME, XPATH_DIV_ERROR_NAME, MSG_NAME_INVALID));
		case APELLIDOS:
			return (new InputDataXPath(XPATH_INPUT_APELLIDOS, XPATH_DIV_ERROR_APELLIDOS, MSG_APELLIDOS_INVALID));
		case EMAIL:
			return (new InputDataXPath(XPATH_INPUT_EMAIL, XPATH_DIV_ERROR_EMAIL, MSG_EMAIL_INVALID));
		case PASSWORD:
			return (new InputDataXPath(XPATH_INPUT_PASSWORD, XPATH_DIV_ERROR_PASSWORD, MSG_PASSWORD_INVALID));
		case TELEFONO:
			return (new InputDataXPath(XPATH_INPUT_TELEFONO, XPATH_DIV_ERROR_TELEFONO, MSG_TELEFONO_INVALID));
		case CODPOSTAL:
			return (new InputDataXPath(XPATH_INPUT_COD_POSTAL, XPATH_DIV_ERROR_COD_POSTAL, MSG_COD_POSTAL_INVALID));
		case CODPAIS:
		default:
			return (new InputDataXPath("", "", ""));
		}
	}
	
//	public void clickRegisterTab() {
//		click(XPATH_PESTANYA_REGISTRO).type(TypeClick.javascript).exec();
//	}
	
	public boolean isPageUntil(int seconds) {
		return state(Visible, XPATH_INPUT_NAME).wait(seconds).check();
	}
	
	public boolean isCapaLoadingInvisibleUntil(int seconds) {
		return state(Invisible, XPATH_CAPA_LOADING).wait(seconds).check();
	}
	
	public String getNewsLetterTitleText() {
		try {
			WebElement titleNws = getElement(XPATH_NEWSLETTER_TITLE);
			if (titleNws!=null) {
				return getElement(XPATH_NEWSLETTER_TITLE).getText();
			}
		}
		catch (Exception e) {
			//Retornamos ""
		}
		
		return "";
	}
	
	public boolean newsLetterTitleContains(String literal) {
		return (getNewsLetterTitleText().contains(literal));
	}
			
	public int getNumInputsObligatoriosNoInformados() {
		String xpathInput = XPATH_INPUT_OBLIGATORIO_NO_INFORMADO;
		List<WebElement> inputsObligatorios = getElements(xpathInput);
		return (inputsObligatorios.size());
	}

	private void sendKeysToInput(DataRegType inputType, String dataToSend) {
		for (int i=0; i<2; i++) {
			try {
				String xpathInput = getXPathDataInput(inputType).getXPah();
				sendKeysWithRetry(dataToSend, By.xpath(xpathInput), 2, driver);
				break;
			}
			catch (ElementNotInteractableException e) {
				//Vamos al inicio de la página porque la cabecera puede estar tapando el campo
				new Actions(driver).sendKeys(Keys.PAGE_UP).perform();
			}
		}
	}
	
	public Map<String,String> sendDataAccordingCountryToInputs(
			String emailNonExistent, boolean clickPubli, Channel channel) {
		return new Page2IdentCheckout().inputDataPorDefectoSegunPais(emailNonExistent, false, clickPubli, channel);
	}
	
	public void sendDataToInputs(ListDataRegistro dataToSend) {
		//clickRegisterTab();
		for (DataRegistro dataInput : dataToSend.getDataPageInicial()) {
			if (dataInput.dataRegType!=DataRegType.CODPAIS) {
				sendKeysToInput(dataInput.dataRegType, dataInput.data);
			} else {
				selectPais(dataInput.data);
			}
		}
		
		getElement("//body").sendKeys(Keys.TAB);
	}
	
	public void selectPais(String codPais) {
		new Select(getElement(XPATH_SELECT_PAIS)).selectByValue(codPais);
	}
	
	public boolean isVisibleMsgInputInvalid(DataRegType inputType) {
		String xpath = getXPathDataInput(inputType).getXPathDivError();
		return state(Present, xpath).check();
	}
	
	public int getNumberMsgInputInvalid(DataRegType inputType) {
		String xpathError = getXPathDataInput(inputType).getXPathDivError() + "//span";
		if (state(Present, xpathError).check()) {
			return (getNumElementsVisible(xpathError));
		}
		return 0;
	}
	
	public boolean isVisibleAnyInputErrorMessage() {
		return state(Visible, XPATH_DIV_ERROR_NAME).check();
	}

	public boolean isButtonRegistrateVisible() {
		return state(Visible, XPATH_BUTTON_REGISTRATE).check();
	}
	
	public void clickButtonRegistrate() {
		click(XPATH_BUTTON_REGISTRATE).exec();
		
		//Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no funciona así que ejecutamos un 2o 
		if (isButtonRegistrateVisible()) {
			try {
				click(XPATH_BUTTON_REGISTRATE).type(javascript).exec();
			}
			catch (Exception e) {
				Log4jTM.getLogger().info("Problem in second click to Registrate Button", e);
			}
		}
	}
	
	public boolean isVisibleErrorUsrDuplicadoUntil(int seconds) {
		String xpathError = getXPath_mensajeErrorFormulario(MSG_USR_DUPLICADO_POST_CLICK);
		return state(Present, xpathError).wait(seconds).check();
	}	
	
	public boolean isVisibleErrorEmailIncorrecto(int seconds) {
		return getNumberMsgInputInvalid(DataRegType.EMAIL)==1;
	}	
	
	public int getNumberMsgCampoObligatorio() {
		String xpathError = "//div[@class='errorValidation']/span[text()[contains(.,'" + MSG_CAMPO_OBLIGATORIO + "')]]";
		return getNumElementsVisible(xpathError);
	}	
	
	public int getNumberInputsTypePassword() {
		return getNumElementsVisible("//input[@type='password']");
	}
	
	public boolean isVisibleSelectPais() {
		return state(Visible, XPATH_SELECT_PAIS).check();
	}
	
	public boolean isSelectedOptionPais(String codigoPais) {
		String xpathOption = XPATH_SELECT_PAIS + "/option[@selected='selected' and @value='" + codigoPais + "']"; 
		return state(Present, xpathOption).check();
	}

	public boolean isTextoRGPDVisible() {
		//TODO dejar sólo la versión de Loyalty cuando esta operativa suba a producción
		WebElement textoElem = getElementWithSizeNot0(driver, By.xpath(XPATH_TEXT_RGPD));
		if (textoElem==null) {
			textoElem = getElementWithSizeNot0(driver, By.xpath(XPATH_TEXT_RGPD_LOYALTY));
		}
		
		return (textoElem!=null);
	}

	public boolean isTextoLegalRGPDVisible() {
		//TODO dejar sólo la versión de Loyalty cuando esta operativa suba a producción
		WebElement textoElem = getElementWithSizeNot0(driver, By.xpath(XPATH_LEGAL_RGPD));
		if (textoElem==null) {
			textoElem = getElementWithSizeNot0(driver, By.xpath(XPATH_LEGAL_RGPD_LOYALTY));
		}
		
		return (textoElem!=null);
	}

	public boolean isCheckboxRecibirInfoPresentUntil(int seconds) {
		return state(Present, XPATH_CHECKBOX_PUBLI).wait(seconds).check();
	}
}
