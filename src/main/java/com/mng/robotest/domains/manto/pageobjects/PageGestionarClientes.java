package com.mng.robotest.domains.manto.pageobjects;

import com.mng.robotest.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageGestionarClientes extends PageBase {

	public enum TypeThirdButton {
		ALTA("Alta"), 
		BAJA("Baja");
		
		private String literal;
		private TypeThirdButton(String literal) {
			this.literal = literal;
		}
		
		public String literal() {
			return this.literal;
		}
		public String getMensaje() {
			return "El cliente ha sido dado de " + literal.toLowerCase();
		}
		
		public TypeThirdButton buttonExpectedAfterClick() {
			if (this==TypeThirdButton.ALTA) {
				return TypeThirdButton.BAJA;
			}
			return TypeThirdButton.ALTA;
		}
	} 
	
	
	public static final String TITULO = "Gestionar Clientes";
	private static final String XPATH_TITULO = "//td[@class='txt11B' and text()[contains(.,'" + TITULO + "')]]";
	private static final String XPATH_FORM_BUSCAR_CLIENTES = "//form[@id='formSelect']";
	private static final String XPATH_FORM_TRATAR_CLIENTES = "//form[@id='formBlock']";
	private static final String XPATH_INPUT_DNI = XPATH_FORM_BUSCAR_CLIENTES + "//span[text()='DNI']/ancestor::td/following::td//input";
	private static final String XPATH_BUSCAR_BUTTON = XPATH_FORM_BUSCAR_CLIENTES + "//input[@value='Buscar']";
	private static final String XPATH_FORM_TABLA = "//form[@id='formTabla']";
	private static final String XPATH_FORM_TABLA_DETALLES_BUTTON = XPATH_FORM_TABLA + "//input[@value='Detalles']";
	private static final String XPATH_SPAN_MENSAJE = "//span[text()[contains(.,'";
	
	private String getXPathIdClienteFromXPathDni(String dni){
		String xpathDni = getXPathDniTabla(dni);
		return xpathDni + "/ancestor::tr/td[1]";
	}
	
	private String getXPathThirdButton(TypeThirdButton typeButton) {
		return (XPATH_FORM_TABLA + "//input[@value='" + typeButton.literal() + "']");
	}
	
	private String getXPathDniTabla(String dni){
		return XPATH_FORM_TABLA + "//td[@title='" + dni + "']";
	}
	
	private String getXPathSpanMensajeThirdButton(String mensaje) {
		return XPATH_SPAN_MENSAJE + mensaje + "')]]";
	}
	
	private String getXPathDetallesClienteIdCliente(String idCliente) {
		return "//td[@class='txt8'][text()[contains(.,'" + idCliente + "')]]";
	}
	
	private String getXPathDetallesClienteDni(String dni) {
		return "//span[text()='" + dni + "']";
	}

	public boolean isPage() {
		return state(Visible, XPATH_TITULO).check();
	}

	public boolean isVisibleFormBuscarClientes() {
		return state(Visible, XPATH_FORM_BUSCAR_CLIENTES).check();
	}

	public boolean isVisibleFormTratarClientes() {
		return state(Visible, XPATH_FORM_TRATAR_CLIENTES).check();
	}

	public void inputDniAndClickBuscarButton(String dni, int waitSeconds) {
		inputDni(dni);
		clickBuscarButtonAndWaitSeconds(waitSeconds);
	}

	public void inputDni(String dni) {
		getElement(XPATH_INPUT_DNI).sendKeys(dni);
	}
	
	public void clickBuscarButtonAndWaitSeconds(int seconds) {
		click(XPATH_BUSCAR_BUTTON).waitLink(seconds).exec();
	}

	public boolean isVisibleTablaInformacion() {
		return state(Visible, XPATH_FORM_TABLA).wait(20).check();
	}

	public boolean getDniTabla(String dni) {
		return state(Visible, getXPathDniTabla(dni)).wait(20).check();
	}

	public TypeThirdButton getTypeThirdButton() {
		if (isVisibleThirdButton(TypeThirdButton.ALTA, 0)) {
			return (TypeThirdButton.ALTA);
		}
		return (TypeThirdButton.BAJA); 
	}
	
	public String getIdClienteTablaFromDni(String dni) {
		return getElement(getXPathIdClienteFromXPathDni(dni)).getText();
	}
	
	public boolean isVisibleThirdButton(TypeThirdButton typeButton, int seconds) {
		String xpathButton = getXPathThirdButton(typeButton); 
		return state(Visible, xpathButton).wait(seconds).check();
	}
	
	public void clickThirdButtonAndWaitSeconds(TypeThirdButton typeButton, int seconds) {
		click(getXPathThirdButton(typeButton)).waitLoadPage(seconds).exec();
	}
	
	public void clickDetallesButtonAndWaitSeconds(int seconds) {
		click(XPATH_FORM_TABLA_DETALLES_BUTTON).waitLoadPage(seconds).exec();
	}
	
	public boolean isVisibleMensajeClickThirdButton(TypeThirdButton typeButton, int seconds) {
		String mensaje = typeButton.getMensaje();
		return state(Visible, getXPathSpanMensajeThirdButton(mensaje)).wait(seconds).check();
	}

	public boolean isVisibleIdClienteClickDetallesButton(String idCliente) {
		return state(Visible, getXPathDetallesClienteIdCliente(idCliente)).check();
	}

	public boolean isVisibleDniClickDetallesButton(String dni) {
		return state(Visible, getXPathDetallesClienteDni(dni)).check();
	}

}