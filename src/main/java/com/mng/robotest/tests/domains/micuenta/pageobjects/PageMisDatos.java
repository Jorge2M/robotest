package com.mng.robotest.tests.domains.micuenta.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.tests.domains.legal.legaltexts.FactoryLegalTexts.PageLegalTexts.MIS_DATOS_LEGAL_TEXTS;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageMisDatos extends PageBase {
	
	private static final String XP_IS_PAGE = "//div[@class='myDetails']";
	private static final String XP_TITLE_OK = "//h2[text()[contains(.,'Mis datos')]]";
	private static final String XP_INPUT_EMAIL = "//input[@id[contains(.,'cfEmail')]]";
	private static final String XP_INPUT_NOMBRE = "//input[@id[contains(.,'cfName')]]";
	private static final String XP_INPUT_APELLIDOS = "//input[@id[contains(.,'cfSname')]]";
	private static final String XP_INPUT_DIRECCION = "//input[@id[contains(.,'cfDir1')]]";
	private static final String XP_INPUT_COD_POSTAL = "//input[@id[contains(.,'cfCp')]]";
	private static final String XP_INPUT_POBLACION = "//input[@id[contains(.,'cfCity')]]";
	private static final String XP_BOTON_GUARDAR_CAMBIOS = "//div[@class='submitContent']/input[@type='submit']";
	private static final String XP_PAGE_RES_OK = "//span[text()[contains(.,'Tus datos han sido modificados en nuestra base de datos.')]]";
	private static final String XP_INPUT_PASSWORD_TYPE_PASSWORD = "//input[@id[contains(.,'cfPass')] and @type='password']";
	private static final String XP_INPUT_CONTENT_VOID = "//div[@class='inputContent']/input[not(@value)]";
	private static final String XP_SELECT_PAIS = "//select[@id[contains(.,':pais')]]";
	private static final String XP_SELECT_PROVINCIA = "//select[@id[contains(.,':estadosPais')]]";
	private static final String XP_OPTION_PAIS_SELECTED = XP_SELECT_PAIS + "/option[@selected]";
	private static final String XP_OPTION_PROVINCIA_SELECTED = XP_SELECT_PROVINCIA + "/option[@selected]";
	private static final String XP_LINK_ELIMINAR_CUENTA = "//a[@href[contains(.,'account/baja')]]";
	private static final String XP_BUTTON_CONFIRM_ELIMINAR_CUENTA = "//input[@value='Eliminar cuenta']";
	private static final String XP_MESSAGE_CUENTA_CANCELADA_OK = "//div[@class='messageConfirmation']/h3[text()='Cuenta eliminada']";
	
	public PageMisDatos() {
		super(MIS_DATOS_LEGAL_TEXTS);
	}
	
	public String getTextInputNombre() {
		return getElement(XP_INPUT_NOMBRE).getAttribute("value");
	}
	
	public String getTextInputApellidos() {
		return getElement(XP_INPUT_APELLIDOS).getAttribute("value");
	}
	
	public String getTextInputEmail() {
		if (state(Present, XP_INPUT_EMAIL).check()) {
			return getElement(XP_INPUT_EMAIL).getAttribute("value");
		}
		return "";
	}

	public String getTextInputDireccion() {
		if (state(Present, XP_INPUT_DIRECCION).check()) {
			return getElement(XP_INPUT_DIRECCION).getAttribute("value");
		}
		return "";
	}
	
	public String getTextInputCodPostal() {
		if (state(Present, XP_INPUT_COD_POSTAL).check()) {
			return getElement(XP_INPUT_COD_POSTAL).getAttribute("value");
		}
		return "";
	}
	
	public String getTextInputPoblacion() {
		if (state(Present, XP_INPUT_POBLACION).check()) {
			return getElement(XP_INPUT_POBLACION).getAttribute("value");
		}
		return "";
	}
	
	public String getCodPaisSelected() {
		if (state(Present, XP_OPTION_PAIS_SELECTED).check()) {
			return getElement(XP_OPTION_PAIS_SELECTED).getAttribute("value");
		}
		return "";
	}
	
	public String getProvinciaSelected() {
		if (state(Present, XP_OPTION_PROVINCIA_SELECTED).check()) {
			return getElement(XP_OPTION_PROVINCIA_SELECTED).getText();
		}
		return "";
	}	
	
	public boolean isVisiblePasswordTypePassword() {
		return state(Visible, XP_INPUT_PASSWORD_TYPE_PASSWORD).check();
	}
	
	public int getNumInputContentVoid() {
		return getElements(XP_INPUT_CONTENT_VOID).size();
	}
	
	public boolean isPage() {
		return isPage(0);
	}
	public boolean isPage(int seconds) {
		return state(Present, XP_IS_PAGE).wait(seconds).check();
	}
	
	public boolean titleOk() {
		return state(Present, XP_TITLE_OK).check();
	}
	
	public String getValueEmailInput() {
		return getElement(XP_INPUT_EMAIL).getAttribute("value");
	}
	
	public String getValueNombreInput() {
		return getElement(XP_INPUT_NOMBRE).getAttribute("value");
	}
	
	public void setNombreInput(String nombre) {
		getElement(XP_INPUT_NOMBRE).clear();
		getElement(XP_INPUT_NOMBRE).sendKeys(nombre);
	}
	
	public void clickGuardarCambios() {
		click(XP_BOTON_GUARDAR_CAMBIOS).exec();
	}
	
	public boolean pageResOK() { 
		return state(Present, XP_PAGE_RES_OK).check();
	}
	
	public void cancelarCuenta() {
		clickEliminarCuenta();
		confirmEliminarCuenta();
	}
	
	private void clickEliminarCuenta() {
		click(XP_LINK_ELIMINAR_CUENTA).exec();
		if (!state(Invisible, XP_LINK_ELIMINAR_CUENTA).wait(1).check()) {
			click(XP_LINK_ELIMINAR_CUENTA).exec();
		}		
	}
	
	public void confirmEliminarCuenta() {
		click(XP_BUTTON_CONFIRM_ELIMINAR_CUENTA).waitLink(3).exec();
	}
	
	public boolean isMessageCuentaCanceladaOkVisible(int seconds) {
		return state(Visible, XP_MESSAGE_CUENTA_CANCELADA_OK).wait(seconds).check();
	}
}
