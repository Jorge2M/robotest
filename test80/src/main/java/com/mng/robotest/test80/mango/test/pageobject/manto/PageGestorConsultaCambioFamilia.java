package com.mng.robotest.test80.mango.test.pageobject.manto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageGestorConsultaCambioFamilia {

    public static String titulo = "Consulta y cambio de familia";
    static String iniXPathTitulo = "//td[@class='txt11B' and text()[contains(.,'";
    static String XPathConsultaTable = "//form[@id='formConsulta']/table";
    static String XPathCambioFamiliaTable = "//form[@id='formCambio']/table";
    static String iniXPathConsultaButton = "//form[@id='formOptions']//input[@value='Consultar'";
    static String XPathCambioFamiliaButton = "//form[@id='formOptions']//input[@value='Cambiar']";
    static String XPathSelectFamiliaOptionAccesorios = "//select[@id='formConsulta:FamiliaConsultar']//option[@value='Accesorios']";
    static String XPathConsultarPorFamiliaButton = "//input[@id='formConsulta:consultaPorFamilia']";
    static String XPathTablaProductos = "//th[text()='PRODUCTO']/ancestor::table";
    static String XPathTablaProductosFamiliaPrincipalFirstRow = XPathTablaProductos + "//tbody/tr[1]/td[5]";

    public static String getXPathTitulo(String title){
    	return (iniXPathTitulo + title + "')]]");
    }  
    
    public static String getXPathConsultaButtonDisabled() {
    	return (iniXPathConsultaButton + " and @disabled='disabled']");
    }
    
	public static boolean isPage(WebDriver driver) {
		String xpath = getXPathTitulo(titulo);
		return (state(Present, By.xpath(xpath), driver).check());
	}

	public static boolean isVisibleConsultaTable(WebDriver driver) {
		return (state(Visible, By.xpath(XPathConsultaTable), driver).check());
	}

	public static boolean isDisabledConsultaButton(WebDriver driver) {
		String xpath = getXPathConsultaButtonDisabled();
		return (state(Present, By.xpath(xpath), driver).check());
	}

	public static void selectAccesoriosAndClickConsultaPorFamiliaButton(WebDriver driver) throws Exception {
		selectAccesorios(driver);
		clickConsultaPorFamiliaButton(driver);
		
	}

	private static void selectAccesorios(WebDriver driver) {
		driver.findElement(By.xpath(XPathSelectFamiliaOptionAccesorios)).click();
	}

	private static void clickConsultaPorFamiliaButton(WebDriver driver) throws Exception {
		clickAndWaitLoad(driver, By.xpath(XPathConsultarPorFamiliaButton), 30);
	}

	public static void clickCambioFamiliaButton(WebDriver driver) throws Exception {
		clickAndWaitLoad(driver,By.xpath(XPathCambioFamiliaButton) ,30);
	}

	public static boolean isTablaProductosVisible(WebDriver driver) {
		return (state(Visible, By.xpath(XPathTablaProductos), driver).check());
	}

	public static boolean checkFirstRowProductIsRight(WebDriver driver) {
		return driver.findElement(By.xpath(XPathTablaProductosFamiliaPrincipalFirstRow)).getText().equals("Accesorios");
	}

	public static boolean isTablaCambioFamiliaVisible(WebDriver driver) {
		return (state(Visible, By.xpath(XPathCambioFamiliaTable), driver).check());
	}

}