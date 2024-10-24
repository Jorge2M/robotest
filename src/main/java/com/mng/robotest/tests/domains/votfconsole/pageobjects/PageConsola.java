package com.mng.robotest.tests.domains.votfconsole.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageConsola extends PageBase {

	public static final String MSG_CONS_TIPOS_ENVIO_OK = "Servicios - Envio a tienda y a domicilio";
	private static final String XP_SELECT_TEST_SERV = "//div[@class[contains(.,'serviciosVOTF')]]//select[@name='url']";
	private static final String XP_SELECT_CONSOLA_COM = "//div[@class[contains(.,'consolaVOTF')]]//select[@name='urlConsola']";
	private static final String XP_INPUT_ARTIC_DISPONIB = "//input[@name='codigoArticulo']";
	private static final String XP_INPUT_ARTIC_COMPRA = "//input[@name='codigoArticuloCompra']";
	private static final String XP_INPUT_TIENDA = "//input[@name='codigoTienda']";
	private static final String XP_INPUT_TIENDA_ENVIO = "//input[@id='codigoTiendaEnvio']";
	private static final String XP_INPUT_TIENDA_CONSOLA = "//input[@name='codigoTiendaConsola']";
	private static final String XP_BUTTON_CONS_TIPOS_ENVIOS = "//a[@id='envioTipos']";
	private static final String XP_BUTTON_DISP_ENVIO_DOMIC = "//a[@id='envioDom']";
	private static final String XP_BUTTON_DISP_ENVIO_TIENDA = "//a[@id='envioTienda']";
	private static final String XP_SELECT_COD_TRANSPORTE = "//select[@id='idTransporte']";
	private static final String XP_IFRAME_RESULT = "//iframe[@id='resultFrame']";
	private static final String XP_BUTTON_SOLICITUD_A_TIENDA = "//a[@id='envioTiendaBolsa']";
	private static final String XP_BUTTON_SOLICITUD_A_DOMICILIO = "//a[@id='envioDomBolsa']";
	private static final String XP_BUTTON_OBTENCION_PEDIDOS = "//a[@id='obtencionPedidos']";
	private static final String XP_BUTTON_SELECT_PEDIDO = "//a[@id='seleccionPedidos']";
	private static final String XP_BUTTON_PRECONF_PEDIDO = "//a[@id='preconfirmarPedidos']";
	private static final String XP_BUTTON_CONF_PEDIDO = "//a[@id='confirmarPedidos']";
	
	private String getXPathOptionSelectTestServ(String textOption) {
		return (XP_SELECT_TEST_SERV + "/option[text()[contains(.,'" + textOption + "')]]");
	}
	
	private String getXPathOptionSelectConsolaCom(String textOption) {
		return (XP_SELECT_TEST_SERV + "/option[text()[contains(.,'" + textOption + "')]]");
	}
	
	public void switchToResultIFrame() {
		driver.switchTo().frame(getElement(XP_IFRAME_RESULT));
	}
	
	public boolean existTestServVOTF() {
		String xpath = "//span[text()[contains(.,'Test servicios VOTF')]]";
		return state(PRESENT, xpath).check();
	}
	
	public boolean existConsolaComVOTF() {
		String xpath = "//span[text()[contains(.,'Consola comandos VOTF')]]";
		return state(PRESENT, xpath).check();
	}
	
	public String getTextOptionSelectTestServ(String textOption) {
		String xpathOption = getXPathOptionSelectTestServ(textOption);
		return getElement(xpathOption).getText();
	}
	
	public String getTextOptionSelectConsolaCom(String textOption) {
		String xpathOption = getXPathOptionSelectConsolaCom(textOption);
		return getElement(xpathOption).getText();
	}	
	
	public void selectEntornoTestServ(String entorno) {
		String optionTestPRE = getTextOptionSelectTestServ(entorno);
		new Select(getElement(XP_SELECT_TEST_SERV)).selectByVisibleText(optionTestPRE);
	}
	
	public void selectEntornoConsolaCom(String entorno) {
		String optionTestPRE = getTextOptionSelectConsolaCom(entorno);
		new Select(getElement(XP_SELECT_CONSOLA_COM)).selectByVisibleText(optionTestPRE);
	}	
	
	public void inputArticDisponib(String articulo) {
		String valueInput = getElement(XP_INPUT_ARTIC_DISPONIB).getAttribute("value");
		if (valueInput.compareTo(articulo)!=0) {
			getElement(XP_INPUT_ARTIC_DISPONIB).clear();
			getElement(XP_INPUT_ARTIC_DISPONIB).sendKeys(articulo);		
		}
	}
	
	public void inputArticCompra(String articulo) {
		String valueInput = getElement(XP_INPUT_ARTIC_COMPRA).getAttribute("value");
		if (valueInput.compareTo(articulo)!=0) {
			getElement(XP_INPUT_ARTIC_COMPRA).clear();
			getElement(XP_INPUT_ARTIC_COMPRA).sendKeys(articulo);		
		}
	}	
	
	public void inputArticDispYCompra(String articulo) {
		inputArticDisponib(articulo);
		inputArticCompra(articulo);
	}
	
	public void inputTiendas(String tienda) {
		getElement(XP_INPUT_TIENDA).clear();
		getElement(XP_INPUT_TIENDA).sendKeys(tienda);
		getElement(XP_INPUT_TIENDA_ENVIO).clear();
		getElement(XP_INPUT_TIENDA_ENVIO).sendKeys(tienda);
		getElement(XP_INPUT_TIENDA_CONSOLA).clear();
		getElement(XP_INPUT_TIENDA_CONSOLA).sendKeys(tienda);		
	}
	
	public void clickButtonConsTiposEnvios() {
		click(XP_BUTTON_CONS_TIPOS_ENVIOS).exec();
	}
	
	public void clickButtonConsultarDispEnvioDomicilio() {
		click(XP_BUTTON_DISP_ENVIO_DOMIC).exec();
	}
	
	public boolean isDataSelectCodigoTransporte(int seconds) {
		String xpath = XP_SELECT_COD_TRANSPORTE + "/option";
		return state(PRESENT, xpath).wait(seconds).check();
	}
		
	public String getCodigoTransporte() { 
		return getElement(XP_SELECT_COD_TRANSPORTE).getText();
	}
	
	public void consDispEnvioTienda() {
		click(XP_BUTTON_DISP_ENVIO_TIENDA).exec();
	}
	
	public void clickButtonSolATienda() {
		click(XP_BUTTON_SOLICITUD_A_TIENDA).exec();
	}
	
	public void clickButtonSolADomicilio() {
		click(XP_BUTTON_SOLICITUD_A_DOMICILIO).exec();
	}
	
	public void clickButtonObtenerPedidos() {
		click(XP_BUTTON_OBTENCION_PEDIDOS).exec();
	}
	
	public void clickButtonSelectPedido() {
		click(XP_BUTTON_SELECT_PEDIDO).exec();
	}
	
	public void clickButtonPreconfPedido() {
		click(XP_BUTTON_PRECONF_PEDIDO).exec();
	}
	
	public void clickButtonConfPedido() {
		click(XP_BUTTON_CONF_PEDIDO).exec();
	}
	
	public void selectPedido(String codigoPedidoFull) {
		new Select(driver.findElement(By.id("pedidoConsola"))).selectByVisibleText(codigoPedidoFull);
	}
}