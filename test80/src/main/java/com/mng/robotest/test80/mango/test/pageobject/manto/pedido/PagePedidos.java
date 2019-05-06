package com.mng.robotest.test80.mango.test.pageobject.manto.pedido;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;


public class PagePedidos extends WebdrvWrapp {

    public enum TypeDetalle {bolsa, pedido} 
    public enum Envio {STANDARD, TIENDA, ASM, PICKPOINT}
    
    public enum IdColumn {
    	idpedido ("Id", true),
    	almacen ("almacen", false),
    	estado ("t.e", false),
    	transporte ("transporte", false),
    	votf ("votf", false),
    	vtelf ("vtelf", false),
    	malmacen ("malmacen", false),
    	ew ("ew", false),
    	fecha ("Fecha", false),
    	pais ("Pais", true),
    	nombre ("Nombre", false),
    	apellido ("Apellido", false),
    	apellido2 ("Apellido2", false),
    	email ("Email", false),
    	idregistro ("idRegistro", false),
    	tpv ("Tpv", true),
    	pf ("pf", false),
    	trans ("Trans", false),
    	tarjeta ("Tarjeta", false),
    	totalprendas ("Total Prendas", false),
    	total ("Total", true),
    	tipovale ("Tipo Vale", false),
    	valorvale ("valor vale", false),
    	codigovale ("código vale", false),
    	tipocheque ("Tipo Cheque", false),
    	totaleuros ("Total Euros", false);
    	
    	public String textoColumna;
    	public boolean isLink;
    	private IdColumn(String textoColumna, boolean isLink) {
    		this.textoColumna = textoColumna;
    		this.isLink = isLink;
    	}
    }
    
    private static String tagLinCabecera = "@linCabecera";
    private static String XPathTablaPedidos = "//table//span[text()='Tpv']/../../..";
    private static String XPathCabeceraTablaPedidos = XPathTablaPedidos + "//tr[" + tagLinCabecera + "]";
    private static String XPathLineaPedido = XPathTablaPedidos + "//input[@type='checkbox' and @title='Multi almacén']";
    public static String XPathImporteLineaPedido = "//table//tr/td[22]";
    private static String XPathMainForm = "//form[@action='/pedidos.faces']";
    private static String XPathCapaLoading = "//div[@id[contains(.,'oading')]]";
    private static String iniXPathIdRegistro = "//table//tr[";
    private static String XPathLinkPaginaSiguientePedidos = "//a[text()='>']";
    
    private static String getXPathCabeceraTablePedidos(TypeDetalle typeDetalle) {
    	switch (typeDetalle) {
    	case bolsa:
    		return (XPathCabeceraTablaPedidos.replace(tagLinCabecera, "3"));
    	case pedido:
    	default:
    		return (XPathCabeceraTablaPedidos.replace(tagLinCabecera, "5"));
    	}
    }
    
    private static String getXPathTdCabeceraTablaPedidos(TypeDetalle typeDetalle) {
    	String xpathCabecera = getXPathCabeceraTablePedidos(typeDetalle);
    	return (xpathCabecera + "/td");
    }


    public static int getPosicionColumn(IdColumn idColumn, TypeDetalle typeDetalle, WebDriver driver) {
    	String xpathTdCabeceraPedidos = getXPathTdCabeceraTablaPedidos(typeDetalle); 
    	List<WebElement> listColumns = driver.findElements(By.xpath(xpathTdCabeceraPedidos));
    	int i=0;
    	for (WebElement tdColumn : listColumns) {
    		i+=1;
    		if (isElementPresent(tdColumn, By.xpath("./span")) &&
    			tdColumn.findElement(By.xpath("./span")).getText().compareTo(idColumn.textoColumna)==0) {
    			return i;
    		}
    	}
    	
    	return i;
    }
    
    public static String getXPathCeldaLineaPedido(IdColumn idColumn, TypeDetalle typeDetalle, WebDriver driver) {
    	int posicColumn = getPosicionColumn(idColumn, typeDetalle, driver);
    	String elem = "span";
    	if (idColumn.isLink) {
    		elem = "a";
    	}
    	return ("//table//tr/td[" + posicColumn + "]/" + elem); 
    }
    
    public static String getXPathDataPedidoInLineas(IdColumn idColumn, String data, TypeDetalle typeDetalle, WebDriver driver) {
    	String xpathCelda = getXPathCeldaLineaPedido(idColumn, typeDetalle, driver);
    	return (
    	    xpathCelda 
    	    	+ "[text()[contains(.,'" + data + "')] or "
    	    	+ "text()[contains(.,'" + data.toLowerCase() + "')] or "
    	    	+ "text()[contains(.,'" + data.toUpperCase() + "')]]");
    }
    
    /**
     * @return si se trata de la página de pedidos de manto
     */
    public static boolean isPage(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathMainForm)));
    }
    
    public static boolean isInvisibleCapaLoadingUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementInvisibleUntil(driver, By.xpath(XPathCapaLoading), maxSecondsToWait));
    }
    
    /**
     * @return el número de líneas de pedidos que aparecen en pantalla
     */
    public static int getNumLineas(WebDriver driver) {
        return (driver.findElements(By.xpath(XPathLineaPedido)).size());
    }
    
    public static void clickLinkPedidoInLineas(WebDriver driver, String codigoPedidoManto, TypeDetalle typeDetalle) throws Exception {
    	String xpath = getXPathDataPedidoInLineas(IdColumn.idpedido, codigoPedidoManto, typeDetalle, driver);
        //String xpath = getXpath_linkPedidoInLineas(codigoPedidoManto);
        clickAndWaitLoad(driver, By.xpath(xpath));
    }

    public static boolean isPresentDataInPedido(IdColumn idColumn, String data, TypeDetalle typeDetalle, int maxSecondsToWait, WebDriver driver) {
    	String xpath = getXPathDataPedidoInLineas(idColumn, data, typeDetalle, driver);
    	return (isElementPresentUntil(driver, By.xpath(xpath), maxSecondsToWait));
    }
    
	/**
	 * @param posicionPedidoActual, driver
	 * @return codigo del pedido asociado a una posicion de la lista
	 */
	public static String getCodigoPedidoUsuarioRegistrado(int posicionPedidoActual, WebDriver driver) {
		return driver.findElement(By.xpath(getPedidoForThisIdRegistro(getXPathIdRegistroForLine(posicionPedidoActual)))).getText();
	}    
	
	/**
	 * @param linea
	 * @return Xpath del id registro para cada línea
	 */
	public static String getXPathIdRegistroForLine(int linea){
		String XPathIdRegistro = iniXPathIdRegistro + linea + "]/td[16]/span[1]";
		return XPathIdRegistro;
	}

    private static String getXPathLineaPedidoWithTypeEnvio(Envio envio, WebDriver driver) {
    	String xpathCeldaTransporte = getXPathCeldaLineaPedido(IdColumn.transporte, TypeDetalle.pedido, driver);
    	return (xpathCeldaTransporte + "//self::*[text()[contains(.,'" + envio + "')]]/../..");
    }
	
	/**
	 * @param XPathIdRegistro
	 * @return Xpath del id de pedido para cada línea
	 */
	public static String getPedidoForThisIdRegistro(String XPathIdRegistro){
		String XPathIdPedido = XPathIdRegistro + "/ancestor::tr/td[2]/a[1]";
		return XPathIdPedido;
	}

	/**
	 * @param posicionPedidoActual, driver
	 * @return posición del primer pedido que encuentre hecho por un usuario registrado
	 */
	public static int getPosicionPedidoUsuarioRegistrado(int posicionPedidoActual, WebDriver driver) {
		int iterator = posicionPedidoActual;
		isElementVisibleUntil(driver, By.xpath(getXPathIdRegistroForLine(posicionPedidoActual)), 400);
		while (driver.findElement(By.xpath(getXPathIdRegistroForLine(iterator))).getText().equals("0"))
			iterator++;
		return iterator;
	}

	/**
	 * @param driver
	 * @throws Exception
	 */
	public static void clickPaginaSiguientePedidos(WebDriver driver) throws Exception {
		clickAndWaitLoad(driver, By.xpath(XPathLinkPaginaSiguientePedidos));
	}

	
    public static void clickPedidoWithTypeEnvio(Envio envio, WebDriver driver) {
    	String xpathLineaEnvTienda = getXPathLineaPedidoWithTypeEnvio(envio, driver);
    	int posIdPedido = getPosicionColumn(IdColumn.idpedido, TypeDetalle.pedido, driver);
    	driver.findElement(By.xpath(xpathLineaEnvTienda + "/td[" + posIdPedido + "]/a")).click();
    }
	
	public static String getTiendaFisicaFromListaPedidos(WebDriver driver) throws Exception {
		clickPedidoWithTypeEnvio(Envio.TIENDA, driver);
		return (PageDetallePedido.getTiendaIfExists(driver));
	}
	
	public static void filtrar() {
		
	}
}
