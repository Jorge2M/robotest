package com.mng.robotest.test.pageobject.manto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageGestorConsultaCambioFamilia {

	public static final String TITULO = "Consulta y cambio de familia";
	private static final String INI_XPATH_TITULO = "//td[@class='txt11B' and text()[contains(.,'";
	private static final String XPATH_CONSULTA_TABLE = "//form[@id='formConsulta']/table";
	private static final String XPATH_CAMBIO_FAMILIA_TABLE = "//form[@id='formCambio']/table";
	private static final String INI_XPATH_CONSULTA_BUTTON = "//form[@id='formOptions']//input[@value='Consultar'";
	private static final String XPATH_CAMBIO_FAMILIA_BUTTON = "//form[@id='formOptions']//input[@value='Cambiar']";
	private static final String XPATH_SELECT_FAMILIA_OPTION_ACCESORIOS = "//select[@id='formConsulta:FamiliaConsultar']//option[@value='Accesorios']";
	private static final String XPATH_CONSULTAR_POR_FAMILIA_BUTTON = "//input[@id='formConsulta:consultaPorFamilia']";
	private static final String XPATH_TABLA_PRODUCTOS = "//th[text()='PRODUCTO']/ancestor::table";
	private static final String XPATH_TABLA_PRODUCTOS_FAMILIA_PRINCIPAL_FIRST_ROW = XPATH_TABLA_PRODUCTOS + "//tbody/tr[1]/td[5]";

	public static String getXPathTitulo(String title){
		return (INI_XPATH_TITULO + title + "')]]");
	}  
	
	public static String getXPathConsultaButtonDisabled() {
		return (INI_XPATH_CONSULTA_BUTTON + " and @disabled='disabled']");
	}
	
	public static boolean isPage(WebDriver driver) {
		String xpath = getXPathTitulo(TITULO);
		return (state(Present, By.xpath(xpath), driver).check());
	}

	public static boolean isVisibleConsultaTable(WebDriver driver) {
		return (state(Visible, By.xpath(XPATH_CONSULTA_TABLE), driver).check());
	}

	public static boolean isDisabledConsultaButton(WebDriver driver) {
		String xpath = getXPathConsultaButtonDisabled();
		return (state(Present, By.xpath(xpath), driver).check());
	}

	public static void selectAccesoriosAndClickConsultaPorFamiliaButton(WebDriver driver) {
		selectAccesorios(driver);
		clickConsultaPorFamiliaButton(driver);
	}

	private static void selectAccesorios(WebDriver driver) {
		driver.findElement(By.xpath(XPATH_SELECT_FAMILIA_OPTION_ACCESORIOS)).click();
	}

	private static void clickConsultaPorFamiliaButton(WebDriver driver) {
		click(By.xpath(XPATH_CONSULTAR_POR_FAMILIA_BUTTON), driver).waitLoadPage(30).exec();
	}

	public static void clickCambioFamiliaButton(WebDriver driver) {
		click(By.xpath(XPATH_CAMBIO_FAMILIA_BUTTON), driver).exec();
	}

	public static boolean isTablaProductosVisible(WebDriver driver) {
		return (state(Visible, By.xpath(XPATH_TABLA_PRODUCTOS), driver).check());
	}

	public static boolean checkFirstRowProductIsRight(WebDriver driver) {
		return driver.findElement(By.xpath(XPATH_TABLA_PRODUCTOS_FAMILIA_PRINCIPAL_FIRST_ROW)).getText().equals("Accesorios");
	}

	public static boolean isTablaCambioFamiliaVisible(WebDriver driver) {
		return (state(Visible, By.xpath(XPATH_CAMBIO_FAMILIA_TABLE), driver).check());
	}

}