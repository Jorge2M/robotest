package com.mng.robotest.test.pageobject.manto;

import org.openqa.selenium.By;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageGestionarClientes extends PageBase {

	public enum TypeThirdButton {
		Alta("alta"), Baja("baja");
		
		public String mensaje;
		private TypeThirdButton(String mensaje) {
			this.mensaje = mensaje;
		}
		
		public String getMensaje() {
			return this.mensaje;
		}
		
		public TypeThirdButton buttonExpectedAfterClick() {
			if (this==TypeThirdButton.Alta) {
				return TypeThirdButton.Baja;
			}
			return TypeThirdButton.Alta;
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
	
	public String getXPathIdClienteFromXPathDni(String dni){
		String XPathDni = getXPathDniTabla(dni);
		return XPathDni + "/ancestor::tr/td[1]";
	}
	
	public String getXPathThirdButton(TypeThirdButton typeButton) {
		return (XPATH_FORM_TABLA + "//input[@value='" + typeButton + "']");
	}
	
	public String getXPathDniTabla(String dni){
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
		return (state(Visible, By.xpath(XPATH_TITULO)).check());
	}

	public boolean isVisibleFormBuscarClientes() {
		return (state(Visible, By.xpath(XPATH_FORM_BUSCAR_CLIENTES)).check());
	}

	public boolean isVisibleFormTratarClientes() {
		return (state(Visible, By.xpath(XPATH_FORM_TRATAR_CLIENTES)).check());
	}

	public void inputDniAndClickBuscarButton(String dni, int waitSeconds) throws Exception{
		inputDni(dni);
		clickBuscarButtonAndWaitSeconds(waitSeconds);
	}

	public void inputDni(String dni) throws Exception {
		driver.findElement(By.xpath(XPATH_INPUT_DNI)).sendKeys(dni);
	}
	
	public void clickBuscarButtonAndWaitSeconds(int waitSeconds) throws Exception {
		driver.findElement(By.xpath(XPATH_BUSCAR_BUTTON)).click();
	}

	public boolean isVisibleTablaInformacion() {
		return (state(Visible, By.xpath(XPATH_FORM_TABLA)).wait(20).check());
	}

	public boolean getDniTabla(String dni) {
		return (state(Visible, By.xpath(getXPathDniTabla(dni))).wait(20).check());
	}

	public TypeThirdButton getTypeThirdButton() {
		if (isVisibleThirdButtonUntil(TypeThirdButton.Alta, 0)) {
			return (TypeThirdButton.Alta);
		}
		return (TypeThirdButton.Baja); 
	}
	
	public String getIdClienteTablaFromDni(String dni) {
		return driver.findElement(By.xpath(getXPathIdClienteFromXPathDni(dni))).getText();
	}
	
	public boolean isVisibleThirdButtonUntil(TypeThirdButton typeButton, int maxSeconds) {
		String xpathButton = getXPathThirdButton(typeButton); 
		return (state(Visible, By.xpath(xpathButton)).wait(maxSeconds).check());
	}
	
	public void clickThirdButtonAndWaitSeconds(TypeThirdButton typeButton, int maxSeconds) {
		By byElem = By.xpath(getXPathThirdButton(typeButton));
		click(byElem).waitLoadPage(maxSeconds).exec();
	}
	
	public void clickDetallesButtonAndWaitSeconds(int maxSeconds) {
		By byElem = By.xpath(XPATH_FORM_TABLA_DETALLES_BUTTON);
		click(byElem).waitLoadPage(maxSeconds).exec();
	}
	
	public boolean isVisibleMensajeClickThirdButton(TypeThirdButton typeButton) {
		String mensaje = typeButton.getMensaje();
		return (state(Visible, By.xpath(getXPathSpanMensajeThirdButton(mensaje))).check());
	}

	public boolean isVisibleIdClienteClickDetallesButton(String idCliente) {
		String xpath = getXPathDetallesClienteIdCliente(idCliente);
		return (state(Visible, By.xpath(xpath)).check());
	}

	public boolean isVisibleDniClickDetallesButton(String dni) {
		String xpath = getXPathDetallesClienteDni(dni);
		return (state(Visible, By.xpath(xpath)).check());
	}

}