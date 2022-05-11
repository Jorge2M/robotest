package com.mng.robotest.test.pageobject.manto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageGestionarClientes {

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
	
	
	public static String titulo = "Gestionar Clientes";
	static String XPathTitulo = "//td[@class='txt11B' and text()[contains(.,'" + titulo + "')]]";
	static String XPathFormBuscarClientes = "//form[@id='formSelect']";
	static String XPathFormTratarClientes = "//form[@id='formBlock']";
	static String XPathInputDNI = XPathFormBuscarClientes + "//span[text()='DNI']/ancestor::td/following::td//input";
	static String XPathBuscarButton = XPathFormBuscarClientes + "//input[@value='Buscar']";
	static String XPathFormTabla = "//form[@id='formTabla']";
	static String XPathFormTablaDetallesButton = XPathFormTabla + "//input[@value='Detalles']";
	static String XPathSpanMensaje = "//span[text()[contains(.,'";
	
	public static String getXPathIdClienteFromXPathDni(String dni){
		String XPathDni = getXPathDniTabla(dni);
		return XPathDni + "/ancestor::tr/td[1]";
	}
	
	public static String getXPathThirdButton(TypeThirdButton typeButton) {
		return (XPathFormTabla + "//input[@value='" + typeButton + "']");
	}
	
	public static String getXPathDniTabla(String dni){
		return XPathFormTabla + "//td[@title='" + dni + "']";
	}
	
	private static String getXPathSpanMensajeThirdButton(String mensaje) {
		return XPathSpanMensaje + mensaje + "')]]";
	}
	
	private static String getXPathDetallesClienteIdCliente(String idCliente) {
		return "//td[@class='txt8'][text()[contains(.,'" + idCliente + "')]]";
	}
	
	private static String getXPathDetallesClienteDni(String dni) {
		return "//span[text()='" + dni + "']";
	}

	public static boolean isPage(WebDriver driver) {
		return (state(Visible, By.xpath(XPathTitulo), driver).check());
	}

	public static boolean isVisibleFormBuscarClientes(WebDriver driver) {
		return (state(Visible, By.xpath(XPathFormBuscarClientes), driver).check());
	}

	public static boolean isVisibleFormTratarClientes(WebDriver driver) {
		return (state(Visible, By.xpath(XPathFormTratarClientes), driver).check());
	}

	public static void inputDniAndClickBuscarButton(String dni, int waitSeconds, WebDriver driver) throws Exception{
		inputDni(dni, driver);
		clickBuscarButtonAndWaitSeconds(waitSeconds, driver);
	}

	public static void inputDni(String dni, WebDriver driver) throws Exception {
		driver.findElement(By.xpath(XPathInputDNI)).sendKeys(dni);
	}
	
	public static void clickBuscarButtonAndWaitSeconds(int waitSeconds, WebDriver driver) throws Exception {
		driver.findElement(By.xpath(XPathBuscarButton)).click();
	}

	public static boolean isVisibleTablaInformacion(WebDriver driver) {
		return (state(Visible, By.xpath(XPathFormTabla), driver)
				.wait(20).check());
	}

	public static boolean getDniTabla(String dni, WebDriver driver) {
		return (state(Visible, By.xpath(getXPathDniTabla(dni)), driver)
				.wait(20).check());
	}

	public static TypeThirdButton getTypeThirdButton(WebDriver driver) {
		if (isVisibleThirdButtonUntil(TypeThirdButton.Alta, 0, driver)) {
			return (TypeThirdButton.Alta);
		}
		return (TypeThirdButton.Baja); 
	}
	
	public static String getIdClienteTablaFromDni(String dni, WebDriver driver) {
		return driver.findElement(By.xpath(getXPathIdClienteFromXPathDni(dni))).getText();
	}
	
	public static boolean isVisibleThirdButtonUntil(TypeThirdButton typeButton, int maxSeconds, WebDriver driver) {
		String xpathButton = getXPathThirdButton(typeButton); 
		return (state(Visible, By.xpath(xpathButton), driver)
				.wait(maxSeconds).check());
	}
	
	public static void clickThirdButtonAndWaitSeconds(TypeThirdButton typeButton, int maxSeconds, WebDriver driver) {
		By byElem = By.xpath(getXPathThirdButton(typeButton));
		click(byElem, driver).waitLoadPage(maxSeconds).exec();
	}
	
	public static void clickDetallesButtonAndWaitSeconds(int maxSeconds, WebDriver driver) {
		By byElem = By.xpath(XPathFormTablaDetallesButton);
		click(byElem, driver).waitLoadPage(maxSeconds).exec();
	}
	
	public static boolean isVisibleMensajeClickThirdButton(TypeThirdButton typeButton, WebDriver driver) {
		String mensaje = typeButton.getMensaje();
		return (state(Visible, By.xpath(getXPathSpanMensajeThirdButton(mensaje)), driver).check());
	}

	public static boolean isVisibleIdClienteClickDetallesButton(String idCliente, WebDriver driver) {
		String xpath = getXPathDetallesClienteIdCliente(idCliente);
		return (state(Visible, By.xpath(xpath), driver).check());
	}

	public static boolean isVisibleDniClickDetallesButton(String dni, WebDriver driver) {
		String xpath = getXPathDetallesClienteDni(dni);
		return (state(Visible, By.xpath(xpath), driver).check());
	}

}