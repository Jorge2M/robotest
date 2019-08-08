package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import java.util.StringTokenizer;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.mng.robotest.test80.arq.webdriverwrapper.TypeOfClick;
import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio.TipoTransporteEnum.TipoTransporte;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen; 

public class Page1EnvioCheckoutMobil extends WebdrvWrapp {
	static String XPathLink1Envio = "//h2[@class[contains(.,'xwing-toggle')] and @data-toggle='step1']";
	static String XPathInputPromo = "//input[@id[contains(.,'oucherCode')]]";
	static String XPathButtonAplicarPromo = "//button[@id[contains(.,'voucherCode')]]";
    static String XPathLinkCancelarCodigo = "//p[@class='discount-total-amount']/a]";
	static String XPathInputApellidoPromoEmpl = "//input[@id[contains(.,'employeeSurname')]]";
	static String XPathInputDNIPromoEmpl = "//input[@id[contains(.,'employeeDNI')]]";
	static String XPathDiaNaciPromoEmpl = "//select[@id[contains(.,'employeeBirthdateDay')]]";
	static String XPathMesNaciPromoEmpl = "//select[@id[contains(.,'employeeBirthdateMonth')]]";
	static String XPathAnyNaciPromoEmpl = "//select[@id[contains(.,'employeeBirthdateYear')]]";
	static String XPathAceptarPromoEmpl = "//input[@id[contains(.,'confirmEmployeeData')]]";
	static String XPathDescuentoEmpleado = "//div[@class[contains(.,'employee-discount')]]//p[@class='discount-total-amount']/strong";
	static String XPathRadioEnvio = "//div[@class[contains(.,'custom-radio')] and @data-custom-radio-id]";
	static String XPathSelectFranjaHorariaMetodoUrgente = "//select[@data-component-id='time-range-sameday_nextday_franjas']";
	static String XPathDireccionEnvio = "//span[@class='address']";
	static String XPathLinkOtrosMetEnvioClosed = "//div[@class[contains(.,'shipment-method')]]" + 
												 "//span[@class[contains(.,'others-title')] and not(@class[contains(.,'selected')])]";
	static String XPathLinkEditDirecEnvio = "//div[@id[contains(.,'addressBlock')]]//span[class='address']";
	static String XPathBotonContinuar = "//button[@id[contains(.,'complete-step1')]]";
	static String XPathErrorPromo = "//div[@data-component-id='error-voucherCode']";

	public static String getXPathBlockMetodo(TipoTransporte tipoTransporte) {
		return ("//div[@class[contains(.,'shipment-method')] and @data-custom-radio-option-id='"
				+ tipoTransporte.getCodigo() + "']");
	}

	public static String getXPathRadioMetodo(TipoTransporte tipoTransporte) {
		return getXPathBlockMetodo(tipoTransporte) + XPathRadioEnvio;
	}

	public static boolean isPageUntil(int maxSecondsToWait, WebDriver driver) {
		return (isVisibleInputCodigoPromoUntil(maxSecondsToWait, driver));
	}

	public static boolean isVisibleLink1EnvioUntil(WebDriver driver, int secondsWait) {
		return (isElementVisibleUntil(driver, By.xpath(XPathLink1Envio), secondsWait));
	}

	public static void clickLink1EnvioAndWaitForPage(WebDriver driver) {
		driver.findElement(By.xpath(XPathLink1Envio)).click();
		isPageUntil(2, driver);
	}

	public static boolean isPresentInputApellidoPromoEmplUntil(int maxSecondsToWait, WebDriver driver) {
		return (isElementPresentUntil(driver, By.xpath(XPathInputApellidoPromoEmpl), maxSecondsToWait));
	}

	public static void inputApellidoPromoEmpl(String apellido, WebDriver driver) {
		driver.findElement(By.xpath(XPathInputApellidoPromoEmpl)).sendKeys(apellido);
	}

	public static boolean isPresentInputDNIPromoEmpl(WebDriver driver) {
		return (isElementPresent(driver, By.xpath(XPathInputDNIPromoEmpl)));
	}

	public static boolean isPresentDiaNaciPromoEmpl(WebDriver driver) {
		return (isElementPresent(driver, By.xpath(XPathDiaNaciPromoEmpl)));
	}

	public static boolean isPresentMesNaciPromoEmpl(WebDriver driver) {
		return (isElementPresent(driver, By.xpath(XPathMesNaciPromoEmpl)));
	}

	public static boolean isPresentAnyNaciPromoEmpl(WebDriver driver) {
		return (isElementPresent(driver, By.xpath(XPathAnyNaciPromoEmpl)));
	}

	/**
	 * @param fechaNaci en formato "dd-mm-aaaa"
	 */
	public static void selectFechaNacPromoEmpl(String fechaNaci, WebDriver driver) {
		StringTokenizer fechaTokenizada = (new StringTokenizer(fechaNaci, "-"));
		selectDiaNacPromoEmpl(fechaTokenizada.nextToken(), driver);
		selectMesNacPromoEmpl(fechaTokenizada.nextToken(), driver);
		selectAnyNacPromoEmpl(fechaTokenizada.nextToken(), driver);
	}

	public static void selectDiaNacPromoEmpl(String value, WebDriver driver) {
		new Select(driver.findElement(By.xpath(XPathDiaNaciPromoEmpl))).selectByValue(value);
	}

	public static void selectMesNacPromoEmpl(String value, WebDriver driver) {
		new Select(driver.findElement(By.xpath(XPathMesNaciPromoEmpl))).selectByValue(value);
	}

	public static void selectAnyNacPromoEmpl(String value, WebDriver driver) {
		new Select(driver.findElement(By.xpath(XPathAnyNaciPromoEmpl))).selectByValue(value);
	}

	public static void inputDNIPromoEmpl(String dni, WebDriver driver) throws Exception {
		waitForPageLoaded(driver);
		driver.findElement(By.xpath(XPathInputDNIPromoEmpl)).sendKeys(dni);
	}

	public static boolean isVisibleButtonAceptarPromoEmpl(WebDriver driver) {
		return (isElementVisible(driver, By.xpath(XPathAceptarPromoEmpl)));
	}

	public static void clickButtonAceptarPromoEmpl(WebDriver driver) throws Exception {
		waitForPageLoaded(driver); //For avoid StaleElement exception
		clickAndWaitLoad(driver, By.xpath(XPathAceptarPromoEmpl));

		// Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no
		// funciona así que ejecutamos un 2o
//		Thread.sleep(200);
//		if (isVisibleButtonAceptarPromoEmpl(driver)) {
//			clickAndWaitLoad(driver, By.xpath(XPathAceptarPromoEmpl));
//		}
	}

	public static void inputCodigoPromoAndAccept(String codigoPromo, WebDriver driver) throws Exception {
		inputCodigoPromo(codigoPromo, driver);
		clickAceptarPromo(driver);
	}

	public static boolean isVisibleInputCodigoPromoUntil(int maxSecondsToWait, WebDriver driver) {
		return (isElementVisibleUntil(driver, By.xpath(XPathInputPromo), maxSecondsToWait));
	}

	public static void inputCodigoPromo(String codigoPromo, WebDriver driver) {
		driver.findElement(By.xpath(XPathInputPromo)).clear();
		driver.findElement(By.xpath(XPathInputPromo)).sendKeys(codigoPromo);
	}

	public static void clickAceptarPromo(WebDriver driver) throws Exception {
		clickAndWaitLoad(driver, By.xpath(XPathButtonAplicarPromo));
		
//		// Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no
//		// funciona así que ejecutamos un 2o
//		if (WebdrvWrapp.isElementVisibleUntil(driver, By.xpath(XPathButtonAplicarPromo), 2)) {
//			clickAndWaitLoad(driver, By.xpath(XPathButtonAplicarPromo), TypeOfClick.javascript);
//		}
	}
	
    public static void clickEliminarValeIfExists(WebDriver driver) throws Exception {
    	By byEliminar = By.xpath(XPathLinkCancelarCodigo);
    	if (isElementVisible(driver, byEliminar)) {
    		clickAndWaitLoad(driver, byEliminar);
    	}
    }
	public static String getImporteDescuentoEmpleado(WebDriver driver) {
		if (isElementPresent(driver, By.xpath(XPathDescuentoEmpleado))) {
			return (driver.findElement(By.xpath(XPathDescuentoEmpleado)).getText());
		}
		return "";
	}

	public static boolean isVisibleDescuentoEmpleadoUntil(WebDriver driver, int secondsToWait) {
		return (isElementVisibleUntil(driver, By.xpath(XPathDescuentoEmpleado), secondsToWait));
	}
	
    public static boolean validateDiscountEmpleadoNotNull(WebDriver driver) throws Exception {
    	String descuentoScreen = getImporteDescuentoEmpleado(driver);
    	if ("".compareTo(descuentoScreen)==0) {
    		return false;
    	}
    	
    	float descuentoFloat = ImporteScreen.getFloatFromImporteMangoScreen(descuentoScreen);
    	return (descuentoFloat > 0);
    }

	public static void selectMetodoAfterPositioningIn1Envio(TipoTransporte tipoTransporte, WebDriver driver)
	throws Exception {
		if (!isPageUntil(0, driver)) {
			clickLink1EnvioAndWaitForPage(driver);
		}
		selectMetodoEnvioAfterLinkOtrosIfNeeded(tipoTransporte, driver);
	}

	public static void selectMetodoEnvioAfterLinkOtrosIfNeeded(TipoTransporte tipoTransporte, WebDriver driver)
	throws Exception {
		openOtrosMetodosDeEnvio(driver);
		String xpathLink = getXPathRadioMetodo(tipoTransporte);
		clickAndWaitLoad(driver, By.xpath(xpathLink), TypeOfClick.javascript);
	}

	public static boolean isPresentBlockMetodo(TipoTransporte tipoTransporte, WebDriver driver) {
		String xpathBLock = getXPathBlockMetodo(tipoTransporte);
		return (isElementPresent(driver, By.xpath(xpathBLock)));
	}

	public static void openOtrosMetodosDeEnvio(WebDriver driver) throws Exception {
		if (isClosedOtrosMetodos(driver)) {
			clickAndWaitLoad(driver, By.xpath(XPathLinkOtrosMetEnvioClosed), TypeOfClick.javascript);
		}
	}

	public static boolean isClosedOtrosMetodos(WebDriver driver) {
		return (isElementVisible(driver, By.xpath(XPathLinkOtrosMetEnvioClosed)));
	}

	public static boolean isBlockSelectedUntil(TipoTransporte tipoTransporte, int maxSecondsToWait, WebDriver driver)
	throws Exception {
		String xpathBlock = getXPathBlockMetodo(tipoTransporte);
		for (int i = 0; i < maxSecondsToWait; i++) {
			try {
				if (driver.findElement(By.xpath(xpathBlock)) != null && 
					driver.findElement(By.xpath(xpathBlock)).getAttribute("class").contains("selected")) {
					return true;
				}
			} catch (StaleElementReferenceException ex) {
				//
			}

			Thread.sleep(1000);
		}

		return false;
	}

	public static String getDireccionEntrega(WebDriver driver) {
		return (driver.findElement(By.xpath(XPathDireccionEnvio)).getText());
	}

	public static String getTextDireccionEnvioCompleta(WebDriver driver) {
		String direccion = "";
		if (getElementsVisible(driver, By.xpath(XPathDireccionEnvio)).size() > 0) {
			direccion = getElementsVisible(driver, By.xpath(XPathDireccionEnvio)).get(0).getText();
		}
		return direccion;
	}

	public static void clickEditDirecEnvio(WebDriver driver) {
		driver.findElement(By.xpath(XPathLinkEditDirecEnvio)).click();
	}

	public static void clickContinuar(WebDriver driver) throws Exception {
		clickAndWaitLoad(driver, By.xpath(XPathBotonContinuar));

		// Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no
		// funciona así que ejecutamos un 2o
		if (isVisibleButtonContinuarUntil(2, driver)) {
			clickAndWaitLoad(driver, By.xpath(XPathBotonContinuar), TypeOfClick.javascript);
		}
	}

	public static void clickContinuarAndWaitPage2(WebDriver driver) throws Exception {
		clickContinuar(driver);
		Page2DatosPagoCheckoutMobil.isPageUntil(2/* maxSecondsToWait */, driver);
	}
	
	public static boolean isVisibleButtonContinuarUntil(int maxSecondsToWait, WebDriver driver) {
		return (isElementVisibleUntil(driver, By.xpath(XPathBotonContinuar), maxSecondsToWait));
	}

	public static boolean isVisibleErrorPromoUntil(int maxSecondsToWait, WebDriver driver) {
		return (isElementVisibleUntil(driver, By.xpath(XPathErrorPromo), maxSecondsToWait));
	}

	public static void selectFranjaHorariaUrgente(int posicion, WebDriver driver) {
		Select selectHorario = new Select(driver.findElement(By.xpath(XPathSelectFranjaHorariaMetodoUrgente)));
		selectHorario.selectByIndex(posicion);
	}
}
