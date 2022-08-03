package com.mng.robotest.test.pageobject.manto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecFiltros extends PageObjTM {

	private static final String XPATH_FILTRO_COD_PEDIDO = "//input[@id[contains(.,':id')]]";
	private static final String XPATH_FILTRO_COD_PAIS = "//input[@id[contains(.,'pais')]]";
	private static final String XPATH_FILTRO_FDESDE = "//input[@id[contains(.,'desde')]]";
	private static final String XPATH_FILTRO_FHASTA = "//input[@id[contains(.,'hasta')]]";
	private static final String XPATH_FILTRO_IMPORTE_TOTAL = "//input[@id[contains(.,':total')]]";
	private static final String XPATH_BUTTON_BUSCAR = "//input[@value='Buscar']";
	
	private static final DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	public SecFiltros(WebDriver driver) {
		super(driver);
	}

	public void setFiltroCodPedido(String codigoPedidoManto) {
		driver.findElement(By.xpath(XPATH_FILTRO_COD_PEDIDO)).clear();
		driver.findElement(By.xpath(XPATH_FILTRO_COD_PEDIDO)).sendKeys(codigoPedidoManto);
	}
	
	public void setFiltroFDesde(LocalDate fechaDesde) {
		String fechaDesdeInput = fechaDesde.format(formatDate);
		driver.findElement(By.xpath(XPATH_FILTRO_FDESDE)).clear();
		driver.findElement(By.xpath(XPATH_FILTRO_FDESDE)).sendKeys(fechaDesdeInput);
	}
	
	public void setFiltroImporteTotal(String importeTotal) {
		driver.findElement(By.xpath(XPATH_FILTRO_IMPORTE_TOTAL)).clear();
		driver.findElement(By.xpath(XPATH_FILTRO_IMPORTE_TOTAL)).sendKeys(importeTotal);
	}
	
	public void setFiltroFHasta(String fechaHasta) {
		driver.findElement(By.xpath(XPATH_FILTRO_FHASTA)).clear();
		driver.findElement(By.xpath(XPATH_FILTRO_FHASTA)).sendKeys(fechaHasta);
	}

	public void setFiltroCodPaisIfExists(String codigoPais) {
		if (state(Present, By.xpath(XPATH_FILTRO_COD_PAIS), driver).check()) {
			driver.findElement(By.xpath(XPATH_FILTRO_COD_PAIS)).clear();
			driver.findElement(By.xpath(XPATH_FILTRO_COD_PAIS)).sendKeys(codigoPais);
		}
	}

	public String getFechaDesdeValue() {
		return (driver.findElement(By.xpath(XPATH_FILTRO_FDESDE)).getAttribute("value"));
	}

	public LocalDate getFechaHastaValue() {
		String fechaHastaScreen = driver.findElement(By.xpath(XPATH_FILTRO_FHASTA)).getAttribute("value");
		return LocalDate.parse(fechaHastaScreen, formatDate);
	}

	public void clickButtonBuscar() {
		click(By.xpath(XPATH_BUTTON_BUSCAR)).exec();
	}
}
