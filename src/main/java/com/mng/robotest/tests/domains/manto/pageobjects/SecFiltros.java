package com.mng.robotest.tests.domains.manto.pageobjects;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecFiltros extends PageBase {

	private static final String XPATH_FILTRO_COD_PEDIDO = "//input[@id[contains(.,':id')]]";
	private static final String XPATH_FILTRO_COD_PAIS = "//input[@id[contains(.,'pais')]]";
	private static final String XPATH_FILTRO_FDESDE = "//input[@id[contains(.,'desde')]]";
	private static final String XPATH_FILTRO_FHASTA = "//input[@id[contains(.,'hasta')]]";
	private static final String XPATH_FILTRO_IMPORTE_TOTAL = "//input[@id[contains(.,':total')]]";
	private static final String XPATH_BUTTON_BUSCAR = "//input[@value='Buscar']";
	
	private static final DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	public void setFiltroCodPedido(String codigoPedidoManto) {
		getElement(XPATH_FILTRO_COD_PEDIDO).clear();
		getElement(XPATH_FILTRO_COD_PEDIDO).sendKeys(codigoPedidoManto);
	}
	
	public void setFiltroFDesde(LocalDate fechaDesde) {
		String fechaDesdeInput = fechaDesde.format(formatDate);
		getElement(XPATH_FILTRO_FDESDE).clear();
		getElement(XPATH_FILTRO_FDESDE).sendKeys(fechaDesdeInput);
	}
	
	public void setFiltroImporteTotal(String importeTotal) {
		getElement(XPATH_FILTRO_IMPORTE_TOTAL).clear();
		getElement(XPATH_FILTRO_IMPORTE_TOTAL).sendKeys(importeTotal);
	}
	
	public void setFiltroFHasta(LocalDate fechaHasta) {
		String fechaHastaInput = fechaHasta.format(formatDate);
		getElement(XPATH_FILTRO_FHASTA).clear();
		getElement(XPATH_FILTRO_FHASTA).sendKeys(fechaHastaInput);
	}

	public void setFiltroCodPaisIfExists(String codigoPais) {
		if (state(Present, XPATH_FILTRO_COD_PAIS).check()) {
			getElement(XPATH_FILTRO_COD_PAIS).clear();
			getElement(XPATH_FILTRO_COD_PAIS).sendKeys(codigoPais);
		}
	}

	public String getFechaDesdeValue() {
		return getElement(XPATH_FILTRO_FDESDE).getAttribute("value");
	}

	public LocalDate getFechaHastaValue() {
		String fechaHastaScreen = getElement(XPATH_FILTRO_FHASTA).getAttribute("value");
		return LocalDate.parse(fechaHastaScreen, formatDate);
	}

	public void clickButtonBuscar() {
		click(XPATH_BUTTON_BUSCAR).exec();
	}
}
