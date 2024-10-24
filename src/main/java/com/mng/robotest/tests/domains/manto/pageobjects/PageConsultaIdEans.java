package com.mng.robotest.tests.domains.manto.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageConsultaIdEans extends PageBase {
	
	private static final String XP_TITULO_PAGINA = "//form[@id='formTempl']//td[text()[contains(.,'s / EANS')]]";
	private static final String XP_DIV_BUSQUEDA_EXCEL = "//div[@id='ExcelContent']";
	private static final String XP_DIV_BUSQUEDA_RAPIDA = "//div[@id='busquedaRapidaContent']";
	
	private static final String XP_BOTON_BUSCAR_DATOS_CONTACTO = "//input[@value[contains(.,'Buscar datos de contacto')]]";
	private static final String XP_BOTON_BUSCAR_IDENTIFICADORES_PEDIDO = "//input[@value[contains(.,'Buscar identificadores de pedidos')]]";
	private static final String XP_BOTON_BUSCAR_TRACKINGS = "//input[@value[contains(.,'Buscar trackings')]]";
	private static final String XP_BOTON_BUSCAR_DATOS_EAN = "//input[@value[contains(.,'Buscar datos EAN')]]";
	
	private static final String XP_TEXT_AREA_BUSQUEDA_RAPIDA = "//textarea[@name='form:j_id_1u']";
	
	private static final String XP_HEADER_TABLA_ID = "//table/thead/tr[1]/th[text()[contains(.,'ID')]]";
	private static final String XP_LINEA_PEDIDO_TABLA_ID = XP_HEADER_TABLA_ID + "/../../../tbody/tr"; 
	
	
  //######################  PARTE DATOS CONTACTO ###################### 
	
	private String getXPathLineaPedido(String pedido) {
		return XP_LINEA_PEDIDO_TABLA_ID + "/td//self::*[text()[contains(.,'" + pedido + "')]]";
	}

	public boolean isVisibleTituloPagina() {
		return state(VISIBLE, XP_TITULO_PAGINA).check();
	}

	public boolean isVisibleDivBusquedaExcel() {	
		return state(VISIBLE, XP_DIV_BUSQUEDA_EXCEL).check();
	}

	public boolean isVisibleDivBusquedaRapida() {
		return state(VISIBLE, XP_DIV_BUSQUEDA_RAPIDA).check();
	}

	public boolean isVisibleTablaInformacionUntil(int seconds) {
		return state(VISIBLE, XP_HEADER_TABLA_ID).wait(seconds).check();
	}

	public void inputPedidosAndClickBuscarDatos(List<String> pedidosPrueba) {
		inputPedidos(pedidosPrueba);
		clickBuscarDatosContactoButton();
	}
	
	public void inputPedidos(List<String> pedidosPrueba) {
		getElement(XP_TEXT_AREA_BUSQUEDA_RAPIDA).clear();
		for (String pedido : pedidosPrueba) {
			getElement(XP_TEXT_AREA_BUSQUEDA_RAPIDA).sendKeys(pedido);
			getElement(XP_TEXT_AREA_BUSQUEDA_RAPIDA).sendKeys(Keys.ENTER);
		}
	}
	
	public void clickBuscarDatosContactoButton() {
		getElement(XP_BOTON_BUSCAR_DATOS_CONTACTO).click();
	}

	public int getLineasPedido() {
		return getElements(XP_LINEA_PEDIDO_TABLA_ID).size();
	}

	public boolean isPedidosTablaCorrecto(List<String> pedidosPrueba) {
		for (String pedido : pedidosPrueba) {
			String xpathLineaPedido = getXPathLineaPedido(pedido);
			if (!state(VISIBLE, By.xpath(xpathLineaPedido)).check()) {
				return false;
			}
		}
		return true;
	}
	
	
	//######################  PARTE IDENTIFICADORES ###################### 

	public void inputPedidosAndClickBuscarIdentificadores (List<String> pedidosPrueba) {
		inputPedidos(pedidosPrueba);
		clickBuscarIdentificadoresPedidoButton();
	}
	
	private void clickBuscarIdentificadoresPedidoButton() {
		getElement(XP_BOTON_BUSCAR_IDENTIFICADORES_PEDIDO).click();
	}
	

	//######################  PARTE TRACKINGS ###################### 

	public void inputPedidosAndClickBuscarTrackings(List<String> pedidosPrueba) {
		inputPedidos(pedidosPrueba);
		clickBuscarTrackingsButton();
	}
	
	private void clickBuscarTrackingsButton() {
		getElement(XP_BOTON_BUSCAR_TRACKINGS).click();
	}

	
	//######################  PARTE EANS ######################
	
	public void inputArticulosAndClickBuscarDatosEan(List<String> articulosPrueba) {
		inputPedidos(articulosPrueba);
		clickBuscarDatosEanButton();
	}

	private void clickBuscarDatosEanButton() {
		getElement(XP_BOTON_BUSCAR_DATOS_EAN).click();
	}

	public boolean isArticulosTablaCorrecto(List<String> articulosPrueba) {
		for (String articulo : articulosPrueba) {
			if (!state(VISIBLE, getXPathLineaPedido(articulo)).check()) {
				return false;
			}
		}
		return true;
	}
}
