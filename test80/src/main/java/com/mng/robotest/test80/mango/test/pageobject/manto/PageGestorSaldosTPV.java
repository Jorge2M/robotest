package com.mng.robotest.test80.mango.test.pageobject.manto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageGestorSaldosTPV {

	public static String titulo = "Gestor de Saldos de TPV";
	static String iniXPathTitulo = "//td[@class='txt11B' and text()[contains(.,'";
	static String XPathInputTPV = "//textarea[@id='form:tpvInput']";
	static String XPathConsultarSaldosButton = "//input[@id='form:consultar']";
	static String XPathTablaSaldos = "//span[text()='SALDOS']/ancestor::table";
	static String iniXPathIdTPVTablaSaldos = XPathTablaSaldos + "//span[text()[contains(.,'SHOP TPV : ";
	static String XPathWidgetError = "//div[@aria-describedby='error']";
	static String XPathLoadPopupImage = "//img[@src='../images/loadingFonsNegre.gif']";

	public static String getXPathTitulo(String title){
		return (iniXPathTitulo + title + "')]]");
	}  
	
	public static String getXPathIdTPVTablaSaldos(String tpv) {
		return iniXPathIdTPVTablaSaldos + tpv + "')] or text()[contains(.,'OUTLET TPV : " + tpv + "')]]";
	}
	
	public static boolean isPage(WebDriver driver) {
		String xpath = getXPathTitulo(titulo);
		return (state(Present, By.xpath(xpath), driver).check());
	}

	public static boolean isVisibleTPVInput(WebDriver driver) {
		return (state(Visible, By.xpath(XPathInputTPV), driver).check());
	}

	public static void insertTPVAndClickConsultarSaldos(String tpv, WebDriver driver) {
		insertTPV(tpv, driver);
		clickConsultarSaldos(driver);
	}

	private static void insertTPV(String tpv, WebDriver driver) {
		driver.findElement(By.xpath(XPathInputTPV)).click();
		driver.findElement(By.xpath(XPathInputTPV)).clear();
		driver.findElement(By.xpath(XPathInputTPV)).sendKeys(tpv);
		
	}
	
	private static void clickConsultarSaldos(WebDriver driver) {
		driver.findElement(By.xpath(XPathConsultarSaldosButton)).click();
		state(Invisible, By.xpath(XPathLoadPopupImage), driver)
			.wait(120).check();
	}

	public static boolean isTablaSaldosVisible(WebDriver driver) throws Exception {
		waitForPageLoaded(driver);
		return (state(Visible, By.xpath(XPathTablaSaldos), driver).check());
	}

	public static boolean isTPVIDVisible(String tpv, WebDriver driver) {
		String xpath = getXPathIdTPVTablaSaldos(tpv);
		return (state(Visible, By.xpath(xpath), driver).check());
	}

	public static boolean isUnvalidTPVMessageVisible(WebDriver driver) throws Exception {
		waitForPageLoaded(driver);
		return (state(Visible, By.xpath(XPathWidgetError), driver).check());
	}
	
}