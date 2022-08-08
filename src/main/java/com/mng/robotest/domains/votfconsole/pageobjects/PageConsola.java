package com.mng.robotest.domains.votfconsole.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageConsola extends PageBase {

	public static final String MSG_CONS_TIPOS_ENVIO_OK = "Servicios - Envio a tienda y a domicilio";
	private static final String XPATH_SELECT_TEST_SERV = "//div[@class[contains(.,'serviciosVOTF')]]//select[@name='url']";
	private static final String XPATH_SELECT_CONSOLA_COM = "//div[@class[contains(.,'consolaVOTF')]]//select[@name='urlConsola']";
	private static final String XPATH_INPUT_ARTIC_DISPONIB = "//input[@name='codigoArticulo']";
	private static final String XPATH_INPUT_ARTIC_COMPRA = "//input[@name='codigoArticuloCompra']";
	private static final String XPATH_INPUT_TIENDA = "//input[@name='codigoTienda']";
	private static final String XPATH_INPUT_TIENDA_ENVIO = "//input[@id='codigoTiendaEnvio']";
	private static final String XPATH_INPUT_TIENDA_CONSOLA = "//input[@name='codigoTiendaConsola']";
	private static final String XPATH_BUTTON_CONS_TIPOS_ENVIOS = "//a[@id='envioTipos']";
	private static final String XPATH_BUTTON_DISP_ENVIO_DOMIC = "//a[@id='envioDom']";
	private static final String XPATH_BUTTON_DISP_ENVIO_TIENDA = "//a[@id='envioTienda']";
	private static final String XPATH_SELECT_COD_TRANSPORTE = "//select[@id='idTransporte']";
	private static final String XPATH_IFRAME_RESULT = "//iframe[@id='resultFrame']";
	private static final String XPATH_BUTTON_SOLICITUD_A_TIENDA = "//a[@id='envioTiendaBolsa']";
	private static final String XPATH_BUTTON_SOLICITUD_A_DOMICILIO = "//a[@id='envioDomBolsa']";
	private static final String XPATH_BUTTON_OBTENCION_PEDIDOS = "//a[@id='obtencionPedidos']";
	private static final String XPATH_BUTTON_SELECT_PEDIDO = "//a[@id='seleccionPedidos']";
	private static final String XPATH_BUTTON_PRECONF_PEDIDO = "//a[@id='preconfirmarPedidos']";
	private static final String XPATH_BUTTON_CONF_PEDIDO = "//a[@id='confirmarPedidos']";
	
	private String getXPathOptionSelectTestServ(String textOption) {
		return (XPATH_SELECT_TEST_SERV + "/option[text()[contains(.,'" + textOption + "')]]");
	}
	
	private String getXPathOptionSelectConsolaCom(String textOption) {
		return (XPATH_SELECT_TEST_SERV + "/option[text()[contains(.,'" + textOption + "')]]");
	}
	
	public void switchToResultIFrame() {
		driver.switchTo().frame(driver.findElement(By.xpath(XPATH_IFRAME_RESULT)));
	}
	
	public boolean existTestServVOTF() {
		String xpath = "//span[text()[contains(.,'Test servicios VOTF')]]";
		return (state(Present, By.xpath(xpath)).check());
	}
	
	public boolean existConsolaComVOTF() {
		String xpath = "//span[text()[contains(.,'Consola comandos VOTF')]]";
		return (state(Present, By.xpath(xpath)).check());
	}
	
	public String getTextOptionSelectTestServ(String textOption) {
		String xpathOption = getXPathOptionSelectTestServ(textOption);
		return (driver.findElement(By.xpath(xpathOption)).getText());
	}
	
	public String getTextOptionSelectConsolaCom(String textOption) {
		String xpathOption = getXPathOptionSelectConsolaCom(textOption);
		return (driver.findElement(By.xpath(xpathOption)).getText());
	}	
	
	public void selectEntornoTestServ(String entorno) {
		String optionTestPRE = getTextOptionSelectTestServ(entorno);
		new Select(driver.findElement(By.xpath(XPATH_SELECT_TEST_SERV))).selectByVisibleText(optionTestPRE);
	}
	
	public void selectEntornoConsolaCom(String entorno) {
		String optionTestPRE = getTextOptionSelectConsolaCom(entorno);
		new Select(driver.findElement(By.xpath(XPATH_SELECT_CONSOLA_COM))).selectByVisibleText(optionTestPRE);
	}	
	
	public void inputArticDisponib(String articulo) {
		String valueInput = driver.findElement(By.xpath(XPATH_INPUT_ARTIC_DISPONIB)).getAttribute("value");
		if (valueInput.compareTo(articulo)!=0) {
			driver.findElement(By.xpath(XPATH_INPUT_ARTIC_DISPONIB)).clear();
			driver.findElement(By.xpath(XPATH_INPUT_ARTIC_DISPONIB)).sendKeys(articulo);		
		}
	}
	
	public void inputArticCompra(String articulo) {
		String valueInput = driver.findElement(By.xpath(XPATH_INPUT_ARTIC_COMPRA)).getAttribute("value");
		if (valueInput.compareTo(articulo)!=0) {
			driver.findElement(By.xpath(XPATH_INPUT_ARTIC_COMPRA)).clear();
			driver.findElement(By.xpath(XPATH_INPUT_ARTIC_COMPRA)).sendKeys(articulo);		
		}
	}	
	
	public void inputArticDispYCompra(String articulo) {
		inputArticDisponib(articulo);
		inputArticCompra(articulo);
	}
	
	public void inputTiendas(String tienda) {
		driver.findElement(By.xpath(XPATH_INPUT_TIENDA)).clear();
		driver.findElement(By.xpath(XPATH_INPUT_TIENDA)).sendKeys(tienda);
		driver.findElement(By.xpath(XPATH_INPUT_TIENDA_ENVIO)).clear();
		driver.findElement(By.xpath(XPATH_INPUT_TIENDA_ENVIO)).sendKeys(tienda);
		driver.findElement(By.xpath(XPATH_INPUT_TIENDA_CONSOLA)).clear();
		driver.findElement(By.xpath(XPATH_INPUT_TIENDA_CONSOLA)).sendKeys(tienda);		
	}
	
	public void clickButtonConsTiposEnvios() {
		click(By.xpath(XPATH_BUTTON_CONS_TIPOS_ENVIOS)).exec();
	}
	
	public void clickButtonConsultarDispEnvioDomicilio() {
		click(By.xpath(XPATH_BUTTON_DISP_ENVIO_DOMIC)).exec();
	}
	
	public boolean isDataSelectCodigoTransporte(int maxSeconds) {
		String xpath = XPATH_SELECT_COD_TRANSPORTE + "/option";
		return (state(Present, By.xpath(xpath)).wait(maxSeconds).check());
	}
		
	public String getCodigoTransporte() { 
		return driver.findElement(By.xpath(XPATH_SELECT_COD_TRANSPORTE)).getText();
	}
	
	public void consDispEnvioTienda() {
		click(By.xpath(XPATH_BUTTON_DISP_ENVIO_TIENDA)).exec();
	}
	
	public void clickButtonSolATienda() {
		click(By.xpath(XPATH_BUTTON_SOLICITUD_A_TIENDA)).exec();
	}
	
	public void clickButtonSolADomicilio() {
		click(By.xpath(XPATH_BUTTON_SOLICITUD_A_DOMICILIO)).exec();
	}
	
	public void clickButtonObtenerPedidos() {
		click(By.xpath(XPATH_BUTTON_OBTENCION_PEDIDOS), driver).exec();
	}
	
	public void clickButtonSelectPedido() {
		click(By.xpath(XPATH_BUTTON_SELECT_PEDIDO)).exec();
	}
	
	public void clickButtonPreconfPedido() {
		click(By.xpath(XPATH_BUTTON_PRECONF_PEDIDO)).exec();
	}
	
	public void clickButtonConfPedido() {
		click(By.xpath(XPATH_BUTTON_CONF_PEDIDO)).exec();
	}
	
	public void selectPedido(String codigoPedidoFull) {
		new Select(driver.findElement(By.id("pedidoConsola"))).selectByVisibleText(codigoPedidoFull);
	}
}