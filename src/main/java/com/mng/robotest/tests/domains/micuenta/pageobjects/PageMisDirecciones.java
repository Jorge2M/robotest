package com.mng.robotest.tests.domains.micuenta.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageMisDirecciones extends PageBase {

	private static final String XP_MICROFRONTEND = "//micro-frontend[@id='myAddresses']"; 
	private static final String XP_LINK_EDITAR = "//*[@data-testid[contains(.,'addressCard')]]//a";
	private static final String XP_INPUT_CODPOSTAL = "//input[@data-testid[contains(.,'.postalCode')]]";
	private static final String XP_POBLACION_DESPLEGABLE_DESKTOP = "//div[@aria-labelledby='city.label']//p";
	private static final String XP_INPUT_POBLACION_DESKTOP = "//input[@data-testid='addressForm.city']";
	private static final String XP_INPUT_POBLACION_DEVICE = "//select[@id='city']";
	private static final String XP_INPUT_DIRECCION = "//input[@data-testid[contains(.,'.address')]]";
	private static final String XP_BOTON_GUARDAR = "//*[@data-testid='deliveryAddress.form.button.submit']";
	
	public boolean isPage(int seconds) {
		return state(Visible, XP_MICROFRONTEND).wait(seconds).check();
	}
	
	public boolean isLinkEditarVisible(int seconds) {
		return state(Visible, XP_LINK_EDITAR).wait(seconds).check(); 
	}
	
	public void clickLinkEditar() {
		click(XP_LINK_EDITAR).waitLink(3).exec();
	}
	public boolean isFormularioUsuario(int seconds) {
		return state(Visible, XP_BOTON_GUARDAR).wait(seconds).check();
	}
	public String getCodigoPostal() {
		state(Visible, XP_INPUT_CODPOSTAL).wait(2).check();
		return getElement(XP_INPUT_CODPOSTAL).getAttribute("value");
	}

	public String getPoblacion() {
		if (channel.isDevice()) {
			return getPoblacionDevice();
		}
		return getPoblacionDesktop();
	}	
	private String getPoblacionDevice() {
		var inputPoblacion = getElement(XP_INPUT_POBLACION_DEVICE);
		return inputPoblacion.getAttribute("value");
	}
	private String getPoblacionDesktop() {
		if (state(Visible, XP_POBLACION_DESPLEGABLE_DESKTOP).check()) {
			var inputPoblacion = getElement(XP_POBLACION_DESPLEGABLE_DESKTOP);
			return inputPoblacion.getText();
		}
		var inputPoblacion = getElement(XP_INPUT_POBLACION_DESKTOP);
		return inputPoblacion.getAttribute("value");
	}
	
	public String getDireccion() {
		return getElement(XP_INPUT_DIRECCION).getAttribute("value");
	}
	public void guardar() {
		click(XP_BOTON_GUARDAR).exec();
	}
	
}
