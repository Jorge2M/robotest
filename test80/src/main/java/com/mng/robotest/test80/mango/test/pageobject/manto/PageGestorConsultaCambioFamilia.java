package com.mng.robotest.test80.mango.test.pageobject.manto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

@SuppressWarnings("javadoc")
public class PageGestorConsultaCambioFamilia extends WebdrvWrapp {

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
    
    public static String getXPathConsultaButtonDisabled(String consultaButton) {
    	return (iniXPathConsultaButton + " and @disabled='disabled']");
    }
    
	public static boolean isPage(WebDriver driver) {
		return (isElementPresent(driver, By.xpath(getXPathTitulo(titulo))));
	}

	public static boolean isVisibleConsultaTable(WebDriver driver) {
		return isElementVisible(driver, By.xpath(XPathConsultaTable));
	}

	public static boolean isDisabledConsultaButton(WebDriver driver) {
		return isElementPresent(driver, By.xpath(getXPathConsultaButtonDisabled(iniXPathConsultaButton)));
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
		return isElementVisible(driver, By.xpath(XPathTablaProductos));
	}

	public static boolean checkFirstRowProductIsRight(WebDriver driver) {
		return driver.findElement(By.xpath(XPathTablaProductosFamiliaPrincipalFirstRow)).getText().equals("Accesorios");
	}

	public static boolean isTablaCambioFamiliaVisible(WebDriver driver) {
		return isElementVisible(driver, By.xpath(XPathCambioFamiliaTable));
	}

	
	
}