package com.mng.robotest.test.pageobject.manto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageGestorEstadisticasPedido {

	public static final String TITULO = "ESTADISTICAS PEDIDOS";
	private static final String INI_XPATH_TITULO = "//td[@class='txt11B' and text()[contains(.,'";
	private static final String XPATH_START_DATE = "//input[@class='dateTimePicker']";
	private static final String XPATH_END_DATE = "//input[@class='dateTimePickerFin']";
	private static final String XPATH_SELECT_OPTION = "//td[text()[contains(.,'Que informacion deseas?')]]/select";
	private static final String XPATH_TODOS_LOS_ZALANDOS_OPTION = "//option[@value='allzalando']";
	private static final String XPATH_MOSTRAR_PEDIDOS_BUTTON = "//input[@id='form:consultar']";
	private static final String XPATH_LOAD_POPUP_IMAGE = "//img[@src='../images/loadingFonsNegre.gif']";
	private static final String XPATH_TABLA_INFORMACION = "//td/table[@class='txt8' and @border='2']";
	private static final String XPATH_RADIUS_BUTTON_DIA_ANTERIOR = "//label[text()[contains(.,'Dia Anterior')]]/../input[@type='radio']";
	private static final String XPATH_COMPARAR_BUTTON = "//input[@id='form:comparar']";

	public static String getXPathTitulo(String title) {
		return (INI_XPATH_TITULO + title + "')]]");
	}

	public static String getXPathColumnaCompararVerde(String tabla){
		return (tabla + "//tr[1]//td[@class='colum5' and text()[contains(.,'0 €')]][1]");
	}

	public static String getXPathColumnaCompararRoja(String tabla){
		return (tabla + "//tr[1]//td[@class='colum6' and text()='0 %'][1]");
	}

	public static boolean isPage(WebDriver driver) {
		String xpath = getXPathTitulo(TITULO);
		return (state(Present, By.xpath(xpath), driver).check());
	}

	public static boolean isVisibleStartDateInput(WebDriver driver) {
		return (state(Visible, By.xpath(XPATH_START_DATE), driver).check());
	}
	
	public static boolean isVisibleEndDateInput(WebDriver driver) {
		return (state(Visible, By.xpath(XPATH_END_DATE), driver).check());
	}

	public static void selectZalandoAndClickShowOrdersButton(WebDriver driver) {
		selectTodosLosZalandos(driver);
		clickMostrarPedidosButton(driver);
	}

	private static void selectTodosLosZalandos(WebDriver driver) {
		driver.findElement(By.xpath(XPATH_SELECT_OPTION)).click();
		driver.findElement(By.xpath(XPATH_TODOS_LOS_ZALANDOS_OPTION)).click();
	}
	
	private static void clickMostrarPedidosButton(WebDriver driver) {
		click(By.xpath(XPATH_MOSTRAR_PEDIDOS_BUTTON), driver).waitLoadPage(60).exec();
		state(Invisible, By.xpath(XPATH_LOAD_POPUP_IMAGE), driver)
				.wait(60).check();
	}

	public static boolean isTablaInformacionVisible(WebDriver driver) {
		return (state(Visible, By.xpath(XPATH_TABLA_INFORMACION), driver).check());
	}

	public static boolean isColumnaCompararVerdeZero(WebDriver driver) {
		String xpath = getXPathColumnaCompararVerde(XPATH_TABLA_INFORMACION);
		return (state(Present, By.xpath(xpath), driver).check());
	}
	
	public static boolean isColumnaCompararRojoZero(WebDriver driver) {
		String xpath = getXPathColumnaCompararRoja(XPATH_TABLA_INFORMACION);
		return (state(Present, By.xpath(xpath), driver).check());
	}

	public static void selectDiaAnteriorAndClickCompararButton(WebDriver driver) {
		selectDiaAnteriorRadius(driver);
		clickCompararButton(driver);
	}

	private static void selectDiaAnteriorRadius(WebDriver driver) {
		driver.findElement(By.xpath(XPATH_RADIUS_BUTTON_DIA_ANTERIOR)).click();
	}
	
	private static void clickCompararButton(WebDriver driver) {
		click(By.xpath(XPATH_COMPARAR_BUTTON), driver).waitLoadPage(60).exec();
		state(Invisible, By.xpath(XPATH_LOAD_POPUP_IMAGE), driver)
			.wait(60).check();
	}

	public static boolean isColumnaCompararVerdeNoZero(WebDriver driver) {
		String xpath = getXPathColumnaCompararVerde(XPATH_TABLA_INFORMACION);
		return (!state(Present, By.xpath(xpath), driver).check());
	}

	public static boolean isColumnaCompararRojaNoZero(WebDriver driver) {
		String xpath = getXPathColumnaCompararRoja(XPATH_TABLA_INFORMACION);
		return (!state(Present, By.xpath(xpath), driver).check());
	}
}