package com.mng.robotest.test.pageobject.manto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageGestorEstadisticasPedido {

	public static String titulo = "ESTADISTICAS PEDIDOS";
	private static final String iniXPathTitulo = "//td[@class='txt11B' and text()[contains(.,'";
	private static final String XPathStartDate = "//input[@class='dateTimePicker']";
	private static final String XPathEndDate = "//input[@class='dateTimePickerFin']";
	private static final String XPathSelectOption = "//td[text()[contains(.,'Que informacion deseas?')]]/select";
	private static final String XPathTodosLosZalandosOption = "//option[@value='allzalando']";
	private static final String XPathMostrarPedidosButton = "//input[@id='form:consultar']";
	private static final String XPathLoadPopupImage = "//img[@src='../images/loadingFonsNegre.gif']";
	private static final String XPathTablaInformacion = "//td/table[@class='txt8' and @border='2']";
	private static final String XPathRadiusButtonDiaAnterior = "//label[text()[contains(.,'Dia Anterior')]]/../input[@type='radio']";
	private static final String XPathCompararButton = "//input[@id='form:comparar']";

	public static String getXPathTitulo(String title){
		return (iniXPathTitulo + title + "')]]");
	}

	public static String getXPathColumnaCompararVerde(String tabla){
		return (tabla + "//tr[1]//td[@class='colum5' and text()[contains(.,'0 €')]][1]");
	}

	public static String getXPathColumnaCompararRoja(String tabla){
		return (tabla + "//tr[1]//td[@class='colum6' and text()='0 %'][1]");
	}

	public static boolean isPage(WebDriver driver) {
		String xpath = getXPathTitulo(titulo);
		return (state(Present, By.xpath(xpath), driver).check());
	}

	public static boolean isVisibleStartDateInput(WebDriver driver) {
		return (state(Visible, By.xpath(XPathStartDate), driver).check());
	}
	
	public static boolean isVisibleEndDateInput(WebDriver driver) {
		return (state(Visible, By.xpath(XPathEndDate), driver).check());
	}

	public static void selectZalandoAndClickShowOrdersButton(WebDriver driver) throws Exception {
		selectTodosLosZalandos(driver);
		clickMostrarPedidosButton(driver);
	}

	private static void selectTodosLosZalandos(WebDriver driver) {
		driver.findElement(By.xpath(XPathSelectOption)).click();
		driver.findElement(By.xpath(XPathTodosLosZalandosOption)).click();
	}
	
	private static void clickMostrarPedidosButton(WebDriver driver) {
		click(By.xpath(XPathMostrarPedidosButton), driver).waitLoadPage(60).exec();
		state(Invisible, By.xpath(XPathLoadPopupImage), driver)
				.wait(60).check();
	}

	public static boolean isTablaInformacionVisible(WebDriver driver) {
		return (state(Visible, By.xpath(XPathTablaInformacion), driver).check());
	}

	public static boolean isColumnaCompararVerdeZero(WebDriver driver) {
		String xpath = getXPathColumnaCompararVerde(XPathTablaInformacion);
		return (state(Present, By.xpath(xpath), driver).check());
	}
	
	public static boolean isColumnaCompararRojoZero(WebDriver driver) {
		String xpath = getXPathColumnaCompararRoja(XPathTablaInformacion);
		return (state(Present, By.xpath(xpath), driver).check());
	}

	public static void selectDiaAnteriorAndClickCompararButton(WebDriver driver) throws Exception {
		selectDiaAnteriorRadius(driver);
		clickCompararButton(driver);
	}

	private static void selectDiaAnteriorRadius(WebDriver driver) {
		driver.findElement(By.xpath(XPathRadiusButtonDiaAnterior)).click();
	}
	
	private static void clickCompararButton(WebDriver driver) {
		click(By.xpath(XPathCompararButton), driver).waitLoadPage(60).exec();
		state(Invisible, By.xpath(XPathLoadPopupImage), driver)
			.wait(60).check();
	}

	public static boolean isColumnaCompararVerdeNoZero(WebDriver driver) {
		String xpath = getXPathColumnaCompararVerde(XPathTablaInformacion);
		return (!state(Present, By.xpath(xpath), driver).check());
	}

	public static boolean isColumnaCompararRojaNoZero(WebDriver driver) {
		String xpath = getXPathColumnaCompararRoja(XPathTablaInformacion);
		return (!state(Present, By.xpath(xpath), driver).check());
	}
}