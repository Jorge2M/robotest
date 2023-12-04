package com.mng.robotest.tests.domains.manto.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageGestorEstadisticasPedido extends PageBase {

	public static final String TITULO = "ESTADISTICAS PEDIDOS";
	private static final String INI_XP_TITULO = "//td[@class='txt11B' and text()[contains(.,'";
	private static final String XP_START_DATE = "//input[@class='dateTimePicker']";
	private static final String XP_END_DATE = "//input[@class='dateTimePickerFin']";
	private static final String XP_SELECT_OPTION = "//td[text()[contains(.,'Que informacion deseas?')]]/select";
	private static final String XP_INPUT_FECHA_INICIO = "//input[@class='dateTimePicker']";
	private static final String XP_ZALANDO_ES_OPTION = "//option[@value='marketplace_40']";
	private static final String XP_MOSTRAR_PEDIDOS_BUTTON = "//input[@id='form:consultar']";
	private static final String XP_LOAD_POPUP_IMAGE = "//img[@src='../images/loadingFonsNegre.gif']";
	private static final String XP_TABLA_INFORMACION = "//td/table[@class='txt8' and @border='2']";
	private static final String XP_RADIUS_BUTTON_DIA_ANTERIOR = "//label[text()[contains(.,'Dia Anterior')]]/../input[@type='radio']";
	private static final String XP_COMPARAR_BUTTON = "//input[@id='form:comparar']";

	public String getXPathTitulo(String title) {
		return (INI_XP_TITULO + title + "')]]");
	}

	public String getXPathColumnaCompararVerde(String tabla){
		return (tabla + "//tr[1]//td[@class='colum5' and text()[contains(.,'0 €')]][1]");
	}

	public String getXPathColumnaCompararRoja(String tabla){
		return (tabla + "//tr[1]//td[@class='colum6' and text()='0 %'][1]");
	}

	public boolean isPage() {
		return state(PRESENT, getXPathTitulo(TITULO)).check();
	}

	public boolean isVisibleStartDateInput() {
		return state(VISIBLE, XP_START_DATE).check();
	}
	
	public boolean isVisibleEndDateInput() {
		return state(VISIBLE, XP_END_DATE).check();
	}

	public void selectZalandoEs() {
		click(XP_SELECT_OPTION).exec();
		click(XP_ZALANDO_ES_OPTION).exec();
	}
	
	public void inputFechaInicioYesterday() {
		LocalDate dateYesterday = LocalDate.now().minusDays(1);
		String fechaInput = dateYesterday.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		getElement(XP_INPUT_FECHA_INICIO).clear();
		getElement(XP_INPUT_FECHA_INICIO).sendKeys(fechaInput);
	}
	
	public void clickMostrarPedidosButton() {
		click(XP_MOSTRAR_PEDIDOS_BUTTON).waitLoadPage(60).exec();
		state(INVISIBLE, XP_LOAD_POPUP_IMAGE).wait(60).check();
	}

	public boolean isTablaInformacionVisible() {
		return state(VISIBLE, XP_TABLA_INFORMACION).check();
	}

	public boolean isColumnaCompararVerdeZero() {
		String xpath = getXPathColumnaCompararVerde(XP_TABLA_INFORMACION);
		return state(PRESENT, xpath).check();
	}
	
	public boolean isColumnaCompararRojoZero() {
		String xpath = getXPathColumnaCompararRoja(XP_TABLA_INFORMACION);
		return state(PRESENT, xpath).check();
	}

	public void selectDiaAnteriorAndClickCompararButton() {
		selectDiaAnteriorRadius();
		clickCompararButton();
	}

	private void selectDiaAnteriorRadius() {
		click(XP_RADIUS_BUTTON_DIA_ANTERIOR).exec();
	}
	
	private void clickCompararButton() {
		click(XP_COMPARAR_BUTTON).waitLoadPage(60).exec();
		state(INVISIBLE, XP_LOAD_POPUP_IMAGE).wait(60).check();
	}

	public boolean isColumnaCompararVerdeNoZero() {
		String xpath = getXPathColumnaCompararVerde(XP_TABLA_INFORMACION);
		return !state(PRESENT, xpath).check();
	}

	public boolean isColumnaCompararRojaNoZero() {
		String xpath = getXPathColumnaCompararRoja(XP_TABLA_INFORMACION);
		return !state(PRESENT, xpath).check();
	}
}