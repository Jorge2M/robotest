package com.mng.robotest.domains.micuenta.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.base.PageBase;

public class PageMisDirecciones extends PageBase {

	private static final String XPATH_MICROFRONTEND = "//micro-frontend[@id='myAddresses']"; 
	private static final String XPATH_LINK_EDITAR = "//*[@data-testid[contains(.,'addressCard')]]//a";
	private static final String XPATH_INPUT_CODPOSTAL = "//input[@data-testid[contains(.,'.postalCode')]]";
	private static final String XPATH_INPUT_POBLACION = "//input[@data-testid[contains(.,'.city')]]";
	private static final String XPATH_INPUT_DIRECCION = "//input[@data-testid[contains(.,'.address')]]";
	private static final String XPATH_BOTON_GUARDAR = "//*[@data-testid='deliveryAddress.form.button.submit']";
	
	public boolean isPage(int seconds) {
		return state(Visible, XPATH_MICROFRONTEND).wait(seconds).check();
	}
	
	public boolean isLinkEditarVisible() {
		return state(Visible, XPATH_LINK_EDITAR).check(); 
	}
	
	public void clickLinkEditar() {
		click(XPATH_LINK_EDITAR).waitLink(3).exec();
	}
	public boolean isFormularioUsuario(int seconds) {
		return state(Visible, XPATH_BOTON_GUARDAR).wait(seconds).check();
	}
	public String getCodigoPostal() {
		state(Visible, XPATH_INPUT_CODPOSTAL).wait(2).check();
		return getElement(XPATH_INPUT_CODPOSTAL).getAttribute("value");
	}
	public String getPoblacion() {
		return getElement(XPATH_INPUT_POBLACION).getAttribute("value");
	}	
	public String getDireccion() {
		return getElement(XPATH_INPUT_DIRECCION).getAttribute("value");
	}
	public void guardar() {
		click(XPATH_BOTON_GUARDAR).exec();
	}
	
}
