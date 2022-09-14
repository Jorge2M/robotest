package com.mng.robotest.test.pageobject.manto.pedido;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PagePedidos extends PageBase {

	public enum TypeDetalle { BOLSA, PEDIDO } 
	public enum Envio { STANDARD, TIENDA, ASM, PICKPOINT }
	
	public enum IdColumn {
		IDPEDIDO("Id", true),
		ALMACEN("almacen", false),
		ESTADO("t.e", false),
		TRANSPORTE("transporte", false),
		VOTF("votf", false),
		VTELF("vtelf", false),
		MALMACEN("malmacen", false),
		EW("ew", false),
		FECHA("Fecha", false),
		PAIS("Pais", true),
		NOMBRE("Nombre", false),
		APELLIDO("Apellido", false),
		APELLIDO2("Apellido2", false),
		EMAIL("Email", false),
		IDREGISTRO("idRegistro", false),
		TPV("Tpv", true),
		PF("pf", false),
		TRANS("Trans", false),
		TARJETA("Tarjeta", false),
		TOTAL_PRENDAS("Total Prendas", false),
		TOTAL("Total", true),
		TIPO_VALE("Tipo Vale", false),
		VALOR_VALE("valor vale", false),
		CODIGO_VALE("código vale", false),
		TIPO_CHEQUE("Tipo Cheque", false),
		TOTAL_EUROS("Total Euros", false);
		
		public String textoColumna;
		public boolean isLink;
		private IdColumn(String textoColumna, boolean isLink) {
			this.textoColumna = textoColumna;
			this.isLink = isLink;
		}
	}
	
	private static String TAG_LIN_CABECERA = "@linCabecera";
	private static String XPATH_TABLA_PEDIDOS = "//table//span[text()='Tpv']/../../..";
	private static String XPATH_CABECERA_TABLA_PEDIDOS = XPATH_TABLA_PEDIDOS + "//tr[" + TAG_LIN_CABECERA + "]";
	private static String XPATH_LINEA_PEDIDO = XPATH_TABLA_PEDIDOS + "//input[@type='checkbox' and @title='Multi almacén']";
	public static String XPATH_IMPORTE_LINEA_PEDIDO = "//table//tr/td[22]";
	private static String XPATH_MAIN_FORM = "//form[@action='/pedidos.faces']";
	private static String XPATH_CAPA_LOADING = "//div[@id[contains(.,'oading')]]";
	private static String INI_XPATH_ID_REGISTRO = "//table//tr[";
	private static String XPATH_LINK_PAGINA_SIGUIENTE_PEDIDOS = "//a[text()='>']";
	
	private String getXPathCabeceraTablePedidos(TypeDetalle typeDetalle) {
		switch (typeDetalle) {
		case BOLSA:
			return (XPATH_CABECERA_TABLA_PEDIDOS.replace(TAG_LIN_CABECERA, "3"));
		case PEDIDO:
		default:
			return (XPATH_CABECERA_TABLA_PEDIDOS.replace(TAG_LIN_CABECERA, "5"));
		}
	}
	
	private String getXPathTdCabeceraTablaPedidos(TypeDetalle typeDetalle) {
		String xpathCabecera = getXPathCabeceraTablePedidos(typeDetalle);
		return (xpathCabecera + "/td");
	}

	public String getXPathCeldaLineaPedido(IdColumn idColumn, TypeDetalle typeDetalle) {
		int posicColumn = getPosicionColumn(idColumn, typeDetalle);
		String elem = "span";
		if (idColumn.isLink) {
			elem = "a";
		}
		return ("//table//tr/td[" + posicColumn + "]/" + elem); 
	}
	
	private String getXPathDataPedidoInLineas(IdColumn idColumn, String data, TypeDetalle typeDetalle) {
		String xpathCelda = getXPathCeldaLineaPedido(idColumn, typeDetalle);
		return (
			xpathCelda 
				+ "[text()[contains(.,'" + data + "')] or "
				+ "text()[contains(.,'" + data.toLowerCase() + "')] or "
				+ "text()[contains(.,'" + data.toUpperCase() + "')]]");
	}
	
	private String getXPathIdRegistroForLine(int linea){
		return INI_XPATH_ID_REGISTRO + linea + "]/td[16]/span[1]";
	}

	public int getPosicionColumn(IdColumn idColumn, TypeDetalle typeDetalle) {
		String xpathTdCabeceraPedidos = getXPathTdCabeceraTablaPedidos(typeDetalle); 
		List<WebElement> listColumns = driver.findElements(By.xpath(xpathTdCabeceraPedidos));
		int i=0;
		for (WebElement tdColumn : listColumns) {
			i+=1;
			if (state(Present, tdColumn, driver).by(By.xpath("./span")).check() &&
				tdColumn.findElement(By.xpath("./span")).getText().compareTo(idColumn.textoColumna)==0) {
				return i;
			}
		}
		return i;
	}

	public boolean isPage() {
		return (state(Present, By.xpath(XPATH_MAIN_FORM)).check());
	}

	public boolean isInvisibleCapaLoadingUntil(int seconds) {
		return (state(Invisible, By.xpath(XPATH_CAPA_LOADING)).wait(seconds).check());
	}

	public int getNumLineas() {
		return (driver.findElements(By.xpath(XPATH_LINEA_PEDIDO)).size());
	}
	
	public void clickLinkPedidoInLineas(String codigoPedidoManto, TypeDetalle typeDetalle) {
		String xpath = getXPathDataPedidoInLineas(IdColumn.IDPEDIDO, codigoPedidoManto, typeDetalle);
		click(By.xpath(xpath), driver).exec();
	}

	public boolean isPresentDataInPedido(IdColumn idColumn, String data, TypeDetalle typeDetalle, int seconds) {
		String xpath = getXPathDataPedidoInLineas(idColumn, data, typeDetalle);
		return (state(Present, By.xpath(xpath)).wait(seconds).check());
	}

	public String getCodigoPedidoUsuarioRegistrado(int posicionPedidoActual) {
		return driver.findElement(By.xpath(getPedidoForThisIdRegistro(getXPathIdRegistroForLine(posicionPedidoActual)))).getText();
	}	
	
	private String getXPathLineaPedidoWithTypeEnvio(Envio envio) {
		String xpathCeldaTransporte = getXPathCeldaLineaPedido(IdColumn.TRANSPORTE, TypeDetalle.PEDIDO);
		return (xpathCeldaTransporte + "//self::*[text()[contains(.,'" + envio + "')]]/../..");
	}
	
	public static String getPedidoForThisIdRegistro(String xpathIdRegistro){
		return xpathIdRegistro + "/ancestor::tr/td[2]/a[1]";
	}

	public int getPosicionPedidoUsuarioRegistrado(int posicionPedidoActual) {
		int iterator = posicionPedidoActual;
		String xpath = getXPathIdRegistroForLine(posicionPedidoActual);
		state(Visible, By.xpath(xpath), driver).wait(400).check();
		while (driver.findElement(By.xpath(getXPathIdRegistroForLine(iterator))).getText().equals("0")) {
			iterator++;
		}
		return iterator;
	}

	public void clickPaginaSiguientePedidos() {
		click(By.xpath(XPATH_LINK_PAGINA_SIGUIENTE_PEDIDOS)).exec();
	}

	public void clickPedidoWithTypeEnvio(Envio envio) {
		String xpathLineaEnvTienda = getXPathLineaPedidoWithTypeEnvio(envio);
		int posIdPedido = getPosicionColumn(IdColumn.IDPEDIDO, TypeDetalle.PEDIDO);
		driver.findElement(By.xpath(xpathLineaEnvTienda + "/td[" + posIdPedido + "]/a")).click();
	}
	
	public String getTiendaFisicaFromListaPedidos() throws Exception {
		clickPedidoWithTypeEnvio(Envio.TIENDA);
		return (new PageDetallePedido().getTiendaIfExists());
	}
}
