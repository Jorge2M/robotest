package com.mng.robotest.test80.mango.test.pageobject.manto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;


public class PageGestorEstadisticasPedido extends WebdrvWrapp {

    public static String titulo = "ESTADISTICAS PEDIDOS";
    static String iniXPathTitulo = "//td[@class='txt11B' and text()[contains(.,'";
    static String XPathStartDate = "//input[@class='dateTimePicker']";
    static String XPathEndDate = "//input[@class='dateTimePickerFin']";
    static String XPathSelectOption = "//td[text()[contains(.,'Que informacion deseas?')]]/select";
    static String XPathTodosLosZalandosOption = "//option[@value='allzalando']";
    static String XPathMostrarPedidosButton = "//input[@id='form:consultar']";
    static String XPathLoadPopupImage = "//img[@src='../images/loadingFonsNegre.gif']";
    static String XPathTablaInformacion = "//td/table[@class='txt8' and @border='2']";
    static String XPathRadiusButtonDiaAnterior = "//label[text()[contains(.,'Dia Anterior')]]/../input[@type='radio']";
    static String XPathCompararButton = "//input[@id='form:comparar']";

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
		return (isElementPresent(driver, By.xpath(getXPathTitulo(titulo))));
	}

	public static boolean isVisibleStartDateInput(WebDriver driver) {
		return (isElementVisible(driver, By.xpath(XPathStartDate)));
	}
	
	public static boolean isVisibleEndDateInput(WebDriver driver) {
		return (isElementVisible(driver, By.xpath(XPathEndDate)));
	}

	public static void selectZalandoAndClickShowOrdersButton(WebDriver driver) throws Exception {
		selectTodosLosZalandos(driver);
		clickMostrarPedidosButton(driver);
	}

	private static void selectTodosLosZalandos(WebDriver driver) {
		driver.findElement(By.xpath(XPathSelectOption)).click();
		driver.findElement(By.xpath(XPathTodosLosZalandosOption)).click();
	}
	
	private static void clickMostrarPedidosButton(WebDriver driver) throws Exception {
		clickAndWaitLoad(driver, By.xpath(XPathMostrarPedidosButton), 60);
		isElementInvisibleUntil(driver, By.xpath(XPathLoadPopupImage), 60);
		
	}

	public static boolean isTablaInformacionVisible(WebDriver driver) {
		return isElementVisible(driver, By.xpath(XPathTablaInformacion));
	}

	public static boolean isColumnaCompararVerdeZero(WebDriver driver) {
		return isElementPresent(driver, By.xpath(getXPathColumnaCompararVerde(XPathTablaInformacion)));
	}
	
	public static boolean isColumnaCompararRojoZero(WebDriver driver) {
		return isElementPresent(driver, By.xpath(getXPathColumnaCompararRoja(XPathTablaInformacion)));
	}

	public static void selectDiaAnteriorAndClickCompararButton(WebDriver driver) throws Exception {
		selectDiaAnteriorRadius(driver);
		clickCompararButton(driver);
	}

	private static void selectDiaAnteriorRadius(WebDriver driver) {
		driver.findElement(By.xpath(XPathRadiusButtonDiaAnterior)).click();
	}
	
	private static void clickCompararButton(WebDriver driver) throws Exception {
		clickAndWaitLoad(driver, By.xpath(XPathCompararButton), 60);
		isElementInvisibleUntil(driver, By.xpath(XPathLoadPopupImage), 60);
	}


	public static boolean isColumnaCompararVerdeNoZero(WebDriver driver) {
		return (!isElementPresent(driver, By.xpath(getXPathColumnaCompararVerde(XPathTablaInformacion))));
	}

	public static boolean isColumnaCompararRojaNoZero(WebDriver driver) {
		return (!isElementPresent(driver, By.xpath(getXPathColumnaCompararRoja(XPathTablaInformacion))));
	}

	
}