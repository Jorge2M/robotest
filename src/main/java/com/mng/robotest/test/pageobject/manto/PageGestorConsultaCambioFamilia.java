package com.mng.robotest.test.pageobject.manto;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageGestorConsultaCambioFamilia extends PageBase {

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

	public String getXPathTitulo(String title){
		return (INI_XPATH_TITULO + title + "')]]");
	}  
	
	public String getXPathConsultaButtonDisabled() {
		return (INI_XPATH_CONSULTA_BUTTON + " and @disabled='disabled']");
	}
	
	public boolean isPage() {
		String xpath = getXPathTitulo(TITULO);
		return state(Present, xpath).check();
	}

	public boolean isVisibleConsultaTable() {
		return state(Visible, XPATH_CONSULTA_TABLE).check();
	}

	public boolean isDisabledConsultaButton() {
		String xpath = getXPathConsultaButtonDisabled();
		return state(Present, xpath).check();
	}

	public void selectAccesoriosAndClickConsultaPorFamiliaButton() {
		selectAccesorios();
		clickConsultaPorFamiliaButton();
	}

	private void selectAccesorios() {
		click(XPATH_SELECT_FAMILIA_OPTION_ACCESORIOS).exec();
	}

	private void clickConsultaPorFamiliaButton() {
		click(XPATH_CONSULTAR_POR_FAMILIA_BUTTON).waitLoadPage(30).exec();
	}

	public void clickCambioFamiliaButton() {
		click(XPATH_CAMBIO_FAMILIA_BUTTON).exec();
	}

	public boolean isTablaProductosVisible() {
		return state(Visible, XPATH_TABLA_PRODUCTOS).check();
	}

	public boolean checkFirstRowProductIsRight() {
		return getElement(XPATH_TABLA_PRODUCTOS_FAMILIA_PRINCIPAL_FIRST_ROW).getText().equals("Accesorios");
	}

	public boolean isTablaCambioFamiliaVisible() {
		return state(Visible, XPATH_CAMBIO_FAMILIA_TABLE).check();
	}

}