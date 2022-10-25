package com.mng.robotest.test.pageobject.manto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageGestorSaldosTPV {

	public static final String TITULO = "Gestor de Saldos de TPV";
	private static final String INI_XPATH_TITULO = "//td[@class='txt11B' and text()[contains(.,'";
	private static final String XPATH_INPUT_TPV = "//textarea[@id='form:tpvInput']";
	private static final String XPATH_CONSULTAR_SALDOS_BUTTON = "//input[@id='form:consultar']";
	private static final String XPATH_TABLA_SALDOS = "//span[text()='SALDOS']/ancestor::table";
	private static final String INI_XPATH_ID_TPV_TABLA_SALDOS = XPATH_TABLA_SALDOS + "//span[text()[contains(.,'SHOP TPV : ";
	private static final String XPATH_WIDGET_ERROR = "//div[@aria-describedby='error']";
	private static final String XPATH_LOAD_POPUP_IMAGE = "//img[@src='../images/loadingFonsNegre.gif']";

	public static String getXPathTitulo(String title){
		return (INI_XPATH_TITULO + title + "')]]");
	}  
	
	public static String getXPathIdTPVTablaSaldos(String tpv) {
		return INI_XPATH_ID_TPV_TABLA_SALDOS + tpv + "')] or text()[contains(.,'OUTLET TPV : " + tpv + "')]]";
	}
	
	public static boolean isPage(WebDriver driver) {
		String xpath = getXPathTitulo(TITULO);
		return (state(Present, By.xpath(xpath), driver).check());
	}

	public static boolean isVisibleTPVInput(WebDriver driver) {
		return (state(Visible, By.xpath(XPATH_INPUT_TPV), driver).check());
	}

	public static void insertTPVAndClickConsultarSaldos(String tpv, WebDriver driver) {
		insertTPV(tpv, driver);
		clickConsultarSaldos(driver);
	}

	private static void insertTPV(String tpv, WebDriver driver) {
		driver.findElement(By.xpath(XPATH_INPUT_TPV)).click();
		driver.findElement(By.xpath(XPATH_INPUT_TPV)).clear();
		driver.findElement(By.xpath(XPATH_INPUT_TPV)).sendKeys(tpv);
		
	}
	
	private static void clickConsultarSaldos(WebDriver driver) {
		driver.findElement(By.xpath(XPATH_CONSULTAR_SALDOS_BUTTON)).click();
		state(Invisible, By.xpath(XPATH_LOAD_POPUP_IMAGE), driver)
			.wait(120).check();
	}

	public static boolean isTablaSaldosVisible(WebDriver driver) {
		waitForPageLoaded(driver);
		return (state(Visible, By.xpath(XPATH_TABLA_SALDOS), driver).check());
	}

	public static boolean isTPVIDVisible(String tpv, WebDriver driver) {
		String xpath = getXPathIdTPVTablaSaldos(tpv);
		return (state(Visible, By.xpath(xpath), driver).check());
	}

	public static boolean isUnvalidTPVMessageVisible(WebDriver driver) {
		waitForPageLoaded(driver);
		return (state(Visible, By.xpath(XPATH_WIDGET_ERROR), driver).check());
	}
	
}