package com.mng.robotest.test.pageobject.manto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;
import com.mng.robotest.domains.transversal.PageBase;


public class PageGestorCheques extends PageBase {
	
	public static final String TITULO = "Gestor de Cheques";
	public static final String TITULO_DETALLES = "DETALLES CHEQUE REGALO";
	private static final String INI_XPATH_TITULO = "//td[@class='txt11B' and text()[contains(.,'";
	private static final String XPATH_DIV_CONTENT = "//div[@id='busquedaRapidaContent']";
	private static final String XPATH_TEXT_AREA = XPATH_DIV_CONTENT + "//textarea";
	private static final String XPATH_CORREO_RECEPTOR_BUTTON = XPATH_DIV_CONTENT + "//input[@value='Correo del receptor']";
	private static final String XPATH_TABLA = "//table[@class[contains(.,'grupotalla-table')]]";
	private static final String XPATH_DETALLES_TABLA_CHEQUE_NUMERO = "//table//td[text()[contains(.,'CHEQUE NUMERO')]]";

	public enum ButtonsCheque implements ElementPage {
		EDITAR("//input[@value='EDITAR']"),
		MODIFICAR("//input[@value='Modificar']"),
		//add("//input[@value='Añadir']"),
		//desactivar("//input[@value='Desactivar']"),
		REENVIAR("//input[@value='Reenviar']"),
		ID_PEDIDO("//input[@value[contains(.,'pedido')]]"),
		NUM_CHEQUE("//input[@value[contains(.,'Número')]]"),
		ID_COMPRA("//input[@value[contains(.,'de compra')]]"),
		CORREO_RECEPTOR("//input[@value[contains(.,'del receptor')]]"),
		CORREO_COMPRADOR("//input[@value[contains(.,'del comprador')]]"),
		CHEQUE_DATA("//a[text()[contains(.,'204028046151')]]"),
		VOLVER_CHEQUES("//a[text()[contains(.,'Volver a cheque')]]");

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
		ACTIVO( "//td[text()[contains(.,'ACTIVO')]]"),
		CHARGE_BACK("//td[text()[contains(.,'CHARGEBACK')]]"),
		DIVISA("//td[text()[contains(.,'DIVISA')]]"),
		VALOR_TOTAL("//td[text()[contains(.,'VALOR')]]"),
		SALDO("//td[text()[contains(.,'SALDO')]]"),
		FECHA_COMPRA("//td[text()[contains(.,'DE COMPRA')]]"),
		VALIDEZ("//td[text()[contains(.,'VALIDEZ')]]"),
		PEDIDOS_REALIZADOS("//td[text()[contains(.,'REALIZADOS')]]"),
		ID_PEDIDOS("//th[text()[contains(.,'Id')]]"),
		FECHA_PEDIDOS("//th[text()[contains(.,'Fecha')]]"),
		TOTAL_PEDIDOS("//th[text()[contains(.,'Total')]]"),
		USUARIO_PEDIDOS("//th[text()[contains(.,'Usuario')]]"),
		ACTIVO_PEDIDOS("//th[text()[contains(.,'Accion')]]"),
		PEDIDOS_ELIMINADOS("//td[text()[contains(.,'ELIMINADOS')]]");

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
		return (XPATH_TABLA + "//tr[" + numFila + "]");
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
		return (INI_XPATH_TITULO + title + "')]]");
	}
	
	private String getXPathDetallesMail(String mail){
		return XPATH_DETALLES_TABLA_CHEQUE_NUMERO + "/ancestor::tr/following::tr//table//td[contains(.,'" + mail + "')]";
	}
	
	private String getXPathDetallesPedido(String pedido){
		return XPATH_DETALLES_TABLA_CHEQUE_NUMERO + "/ancestor::tr/following::tr//table//td[contains(.,'" + pedido + "')]";
	}
	
	public boolean isPage() {
		return (state(Present, By.xpath(getXPathTitulo(TITULO))).check());
	}

	public boolean isPageDetalles() {
		String xpath = getXPathTitulo(TITULO_DETALLES);
		return (state(Present, By.xpath(xpath)).check());
	}

	public void inputMailAndClickCorreoReceptorButton(String mail) {
		inputMail(mail);
		clickCorreoReceptorButtonAndWaitLoad();
	}

	public void inputChequeAndConfirm(String cheque) {
		inputMail(cheque);
		click(ButtonsCheque.NUM_CHEQUE.getBy()).exec();
	}

	private void inputMail(String mail) {
		driver.findElement(By.xpath(XPATH_TEXT_AREA)).click();
		driver.findElement(By.xpath(XPATH_TEXT_AREA)).clear();
		driver.findElement(By.xpath(XPATH_TEXT_AREA)).sendKeys(mail);
	}
	
	private void clickCorreoReceptorButtonAndWaitLoad() {
		click(By.xpath(XPATH_CORREO_RECEPTOR_BUTTON)).exec();
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