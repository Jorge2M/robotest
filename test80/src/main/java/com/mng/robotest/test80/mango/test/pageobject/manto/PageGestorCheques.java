package com.mng.robotest.test80.mango.test.pageobject.manto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.ElementPage;
import com.mng.robotest.test80.mango.test.pageobject.ElementPageFunctions;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

@SuppressWarnings("javadoc")
public class PageGestorCheques extends ElementPageFunctions {

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
		add("//input[@value='Añadir']"),
		desactivar("//input[@value='Desactivar']"),
    	reenviar("//input[@value='Reenviar']"),
		idPedido("//input[@value[contains(.,'pedido')]]"),
		numCheque("//input[@value[contains(.,'Número')]]"),
		idCompra("//input[@value[contains(.,'de compra')]]"),
		correoReceptor("//input[@value[contains(.,'del receptor')]]"),
		correoComprador("//input[@value[contains(.,'del comprador')]]"),

		chequeData("//a[text()[contains(.,'204028046151')]]"),
    	volverCheques("//a[text()[contains(.,'Volver a cheque')]]");

		private String xPath;

		ButtonsCheque(String xPath) {
			this.xPath = xPath;
		}

		public String getXPath() {
			return this.xPath;
		}

		public String getXPath(Channel channel) {
			return this.xPath;
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

		private String xPath;

		TablaCheque(String xPath) {
			this.xPath = xPath;
		}

		public String getXPath() {
			return this.xPath;
		}

		public String getXPath(Channel channel) {
			return this.xPath;
		}
	}
    
    public static String getXPathFila(int numFila) {
        return (XPathTabla + "//tr[" + numFila + "]");
    }
    
    public static String getXPathMailFila(int numFila, String mail) {
        String xpathFila = getXPathFila(numFila);
        return (xpathFila + "//td[contains(.,'" + mail + "')]");
    }
    
    public static String getXPathTdPedido(int numFila, String mail) {
        String xpathLinea = getXPathMailFila(numFila, mail);
        return xpathLinea + "/../td[1]";
    }
    
    public static String getXPathLinkPedido(int numFila, String mail) {
        String xpathLink = getXPathTdPedido(numFila, mail);
        return xpathLink + "//a";
    }    
    
    public static String getTextPedidoFromXPath(int numFila, String mail, WebDriver driver) {
    	String xpathLinea = getXPathLinkPedido(numFila, mail);
        return driver.findElement(By.xpath(xpathLinea)).getText();
    }
    
    public static String getXPathTitulo(String title){
    	return (iniXPathTitulo + title + "')]]");
    }
    
    public static String getXPathDetallesMail(String mail){
    	return XPathDetallesTablaChequeNumero + "/ancestor::tr/following::tr//table//td[contains(.,'" + mail + "')]";
    }
    
    public static String getXPathDetallesPedido(String pedido){
    	return XPathDetallesTablaChequeNumero + "/ancestor::tr/following::tr//table//td[contains(.,'" + pedido + "')]";
    }
    
	public static boolean isPage(WebDriver driver) {
		return (WebdrvWrapp.isElementPresent(driver, By.xpath(getXPathTitulo(titulo))));
	}
	
	public static boolean isPageDetalles(WebDriver driver) {
		return (WebdrvWrapp.isElementPresent(driver, By.xpath(getXPathTitulo(tituloDetalles))));
	}

	public static void inputMailAndClickCorreoReceptorButton(String mail, WebDriver driver) throws Exception {
		inputMail(mail, driver);
		clickCorreoReceptorButtonAndWaitLoad(driver);
	}

	public static void inputChequeAndConfirm(String cheque, WebDriver driver) throws  Exception {
    	inputMail(cheque, driver);
    	clickAndWait(ButtonsCheque.numCheque, driver);
	}

	private static void inputMail(String mail, WebDriver driver) {
		driver.findElement(By.xpath(XPathTextArea)).click();
		driver.findElement(By.xpath(XPathTextArea)).clear();
		driver.findElement(By.xpath(XPathTextArea)).sendKeys(mail);
	}
	
	private static void clickCorreoReceptorButtonAndWaitLoad(WebDriver driver) throws Exception {
		WebdrvWrapp.clickAndWaitLoad(driver, By.xpath(XPathCorreoReceptorButton));
		
	}

	public static boolean comprobarNumeroPedidos(int numPedidosEsther, WebDriver driver) {
		return WebdrvWrapp.isElementVisibleUntil(driver, By.xpath(getXPathFila(numPedidosEsther)), 20);
	}

	public static boolean isMailCorrecto(String mail, WebDriver driver) {
		return WebdrvWrapp.isElementVisibleUntil(driver, By.xpath(getXPathMailFila(1, mail)), 20);
	}

	public static String clickPedido(int numFila, String mail, WebDriver driver) throws Exception {
	    String pedido = getTextPedidoFromXPath(numFila, mail, driver);
		WebdrvWrapp.clickAndWaitLoad(driver, By.xpath(getXPathLinkPedido(numFila, mail)));
	    return pedido; 
	}

	public static boolean comprobarMailDetallesCheque(String mail, WebDriver driver) {
		return (WebdrvWrapp.isElementPresent(driver, By.xpath(getXPathDetallesMail(mail))));
	}

	public static boolean comprobarPedidoDetallesCheque(String pedido, WebDriver driver) {
		return (WebdrvWrapp.isElementPresent(driver, By.xpath(getXPathDetallesPedido(pedido))));
	}

}