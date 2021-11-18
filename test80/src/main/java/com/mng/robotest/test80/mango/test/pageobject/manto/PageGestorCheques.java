package com.mng.robotest.test80.mango.test.pageobject.manto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;


public class PageGestorCheques extends PageObjTM {
	
	public static String titulo = "Gestor de Cheques";
	public static String tituloDetalles = "DETALLES CHEQUE REGALO";
	static String iniXPathTitulo = "//td[@class='txt11B' and text()[contains(.,'";
	static String XPathDivContent = "//div[@id='busquedaRapidaContent']";
	static String XPathTextArea = XPathDivContent + "//textarea";
	static String XPathCorreoReceptorButton = XPathDivContent + "//input[@value='Correo del receptor']";
	static String XPathTabla = "//table[@class[contains(.,'grupotalla-table')]]";
	static String XPathDetallesTablaChequeNumero = "//table//td[text()[contains(.,'CHEQUE NUMERO')]]";

	public enum ButtonsCheque implements ElementPage {
		editar("//input[@value='EDITAR']"),
		modificar("//input[@value='Modificar']"),
		//add("//input[@value='Añadir']"),
		//desactivar("//input[@value='Desactivar']"),
		reenviar("//input[@value='Reenviar']"),
		idPedido("//input[@value[contains(.,'pedido')]]"),
		numCheque("//input[@value[contains(.,'Número')]]"),
		idCompra("//input[@value[contains(.,'de compra')]]"),
		correoReceptor("//input[@value[contains(.,'del receptor')]]"),
		correoComprador("//input[@value[contains(.,'del comprador')]]"),
		chequeData("//a[text()[contains(.,'204028046151')]]"),
		volverCheques("//a[text()[contains(.,'Volver a cheque')]]");

		private By by;
		ButtonsCheque(String xPath) {
			by = By.xpath(xPath);
		}

		@Override
		public By getBy() {
			return by;
		}
	}

	public enum TablaCheque implements ElementPage {
		activo( "//td[text()[contains(.,'ACTIVO')]]"),
		chargeBack("//td[text()[contains(.,'CHARGEBACK')]]"),
		divisa("//td[text()[contains(.,'DIVISA')]]"),
		valorTotal("//td[text()[contains(.,'VALOR')]]"),
		saldo("//td[text()[contains(.,'SALDO')]]"),
		fechaCompra("//td[text()[contains(.,'DE COMPRA')]]"),
		validez("//td[text()[contains(.,'VALIDEZ')]]"),
		pedidosRealizados("//td[text()[contains(.,'REALIZADOS')]]"),
		idPedidos("//th[text()[contains(.,'Id')]]"),
		fechaPedidos("//th[text()[contains(.,'Fecha')]]"),
		totalPedidos("//th[text()[contains(.,'Total')]]"),
		usuarioPedidos("//th[text()[contains(.,'Usuario')]]"),
		activoPedidos("//th[text()[contains(.,'Accion')]]"),
		pedidosEliminados("//td[text()[contains(.,'ELIMINADOS')]]");

		private By by;
		TablaCheque(String xPath) {
			by = By.xpath(xPath);
		}

		@Override
		public By getBy() {
			return by;
		}
	}
	
	public PageGestorCheques(WebDriver driver) {
		super(driver);
	}
	
	private String getXPathFila(int numFila) {
		return (XPathTabla + "//tr[" + numFila + "]");
	}
	
	private String getXPathMailFila(int numFila, String mail) {
		String xpathFila = getXPathFila(numFila);
		return (xpathFila + "//td[contains(.,'" + mail + "')]");
	}
	
	private String getXPathTdPedido(int numFila, String mail) {
		String xpathLinea = getXPathMailFila(numFila, mail);
		return xpathLinea + "/../td[1]";
	}
	
	private String getXPathLinkPedido(int numFila, String mail) {
		String xpathLink = getXPathTdPedido(numFila, mail);
		return xpathLink + "//a";
	}	
	
	private String getTextPedidoFromXPath(int numFila, String mail) {
		String xpathLinea = getXPathLinkPedido(numFila, mail);
		return driver.findElement(By.xpath(xpathLinea)).getText();
	}
	
	private String getXPathTitulo(String title){
		return (iniXPathTitulo + title + "')]]");
	}
	
	private String getXPathDetallesMail(String mail){
		return XPathDetallesTablaChequeNumero + "/ancestor::tr/following::tr//table//td[contains(.,'" + mail + "')]";
	}
	
	private String getXPathDetallesPedido(String pedido){
		return XPathDetallesTablaChequeNumero + "/ancestor::tr/following::tr//table//td[contains(.,'" + pedido + "')]";
	}
	
	public boolean isPage() {
		return (state(Present, By.xpath(getXPathTitulo(titulo))).check());
	}

	public boolean isPageDetalles() {
		String xpath = getXPathTitulo(tituloDetalles);
		return (state(Present, By.xpath(xpath)).check());
	}

	public void inputMailAndClickCorreoReceptorButton(String mail) throws Exception {
		inputMail(mail);
		clickCorreoReceptorButtonAndWaitLoad();
	}

	public void inputChequeAndConfirm(String cheque) {
		inputMail(cheque);
		click(ButtonsCheque.numCheque.getBy()).exec();
	}

	private void inputMail(String mail) {
		driver.findElement(By.xpath(XPathTextArea)).click();
		driver.findElement(By.xpath(XPathTextArea)).clear();
		driver.findElement(By.xpath(XPathTextArea)).sendKeys(mail);
	}
	
	private void clickCorreoReceptorButtonAndWaitLoad() {
		click(By.xpath(XPathCorreoReceptorButton)).exec();
	}

	public boolean comprobarNumeroPedidos(int numPedidosEsther) {
		String xpath = getXPathFila(numPedidosEsther);
		return (state(Visible, By.xpath(xpath)).wait(20).check());
	}

	public boolean isMailCorrecto(String mail) {
		String xpath = getXPathMailFila(1, mail);
		return (state(Visible, By.xpath(xpath)).wait(20).check());
	}

	public String clickPedido(int numFila, String mail) {
		String pedido = getTextPedidoFromXPath(numFila, mail);
		By byElem = By.xpath(getXPathLinkPedido(numFila, mail));
		click(byElem).exec();
		return pedido; 
	}

	public boolean comprobarMailDetallesCheque(String mail) {
		String xpath = getXPathDetallesMail(mail);
		return (state(Present, By.xpath(xpath)).check());
	}

	public boolean comprobarPedidoDetallesCheque(String pedido) {
		String xpath = getXPathDetallesPedido(pedido);
		return (state(Present, By.xpath(xpath)).check());
	}

}