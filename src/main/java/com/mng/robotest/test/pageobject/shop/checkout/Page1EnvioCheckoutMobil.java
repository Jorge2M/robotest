package com.mng.robotest.test.pageobject.shop.checkout;

import java.util.StringTokenizer;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.pageobject.shop.checkout.envio.TipoTransporteEnum.TipoTransporte;
import com.mng.robotest.test.utils.ImporteScreen;

public class Page1EnvioCheckoutMobil extends PageObjTM {
	
	private final static String XPathLink1Envio = "//h2[@class[contains(.,'xwing-toggle')] and @data-toggle='step1']";
	private final static String XPathInputPromo = "//input[@id[contains(.,'oucherCode')]]";
	private final static String XPathButtonAplicarPromo = "//button[@id[contains(.,'voucherCode')]]";
	private final static String XPathLinkCancelarCodigo = "//p[@class='discount-total-amount']/a]";
	private final static String XPathInputApellidoPromoEmpl = "//input[@id[contains(.,'employeeSurname')]]";
	private final static String XPathInputDNIPromoEmpl = "//input[@id[contains(.,'employeeDNI')]]";
	private final static String XPathDiaNaciPromoEmpl = "//select[@id[contains(.,'employeeBirthdateDay')]]";
	private final static String XPathMesNaciPromoEmpl = "//select[@id[contains(.,'employeeBirthdateMonth')]]";
	private final static String XPathAnyNaciPromoEmpl = "//select[@id[contains(.,'employeeBirthdateYear')]]";
	private final static String XPathAceptarPromoEmpl = "//input[@id[contains(.,'confirmEmployeeData')]]";
	private final static String XPathDescuentoEmpleado = "//div[@class[contains(.,'employee-discount')]]//p[@class='discount-total-amount']/strong";
	private final static String XPathRadioEnvio = "//input[@data-testid[contains(.,'checkout.delivery.methods')]]";
	private final static String XPathSelectFranjaHorariaMetodoUrgente = "//select[@data-component-id='time-range-sameday_nextday_franjas']";
	private final static String XPathDireccionEnvio = "//div[@data-testid[contains(.,'delivery.methods.address')]]";
	private final static String XPathLinkOtrosMetEnvioClosed = "//button[@data-testid[contains(.,'otherMethods.button')]]";
	private final static String XPathLinkEditDirecEnvio = "//div[@id[contains(.,'addressBlock')]]//span[class='address']";
	private final static String XPathBotonContinuar = "//button[@id[contains(.,'complete-step1')]]";
	private final static String XPathErrorPromo = "//div[@data-component-id='error-voucherCode']";
	private final static String XPathEnvioStandard = "//div[@data-analytics-id='standard' and @class[contains(.,'checked')]]";

	
	public Page1EnvioCheckoutMobil(WebDriver driver) {
		super(driver);
	}
	
	private String getXPathBlockMetodo(TipoTransporte tipoTransporte) {
		return ("//label[@for='selection-" + tipoTransporte.getCodigo() + "']");
	}

	private String getXPathRadioMetodo(TipoTransporte tipoTransporte) {
		return getXPathBlockMetodo(tipoTransporte) + XPathRadioEnvio;
	}

	public boolean isPageUntil(int maxSecondsToWait) {
		return (isVisibleInputCodigoPromoUntil(maxSecondsToWait));
	}
	
	public boolean isPresentEnvioStandard() {
		return state(Present, By.xpath(XPathEnvioStandard)).check();
	}

	public boolean isVisibleLink1EnvioUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPathLink1Envio)).wait(maxSeconds).check());
	}

	public void clickLink1EnvioAndWaitForPage() {
		driver.findElement(By.xpath(XPathLink1Envio)).click();
		isPageUntil(2);
	}

	public boolean isPresentInputApellidoPromoEmplUntil(int maxSeconds) {
		return (state(Present, By.xpath(XPathInputApellidoPromoEmpl)).wait(maxSeconds).check());
	}

	public void inputApellidoPromoEmpl(String apellido) {
		driver.findElement(By.xpath(XPathInputApellidoPromoEmpl)).sendKeys(apellido);
	}

	public boolean isPresentInputDNIPromoEmpl() {
		return (state(Present, By.xpath(XPathInputDNIPromoEmpl)).check());
	}

	public boolean isPresentDiaNaciPromoEmpl() {
		return (state(Present, By.xpath(XPathDiaNaciPromoEmpl)).check());
	}

	public boolean isPresentMesNaciPromoEmpl() {
		return (state(Present, By.xpath(XPathMesNaciPromoEmpl)).check());
	}

	public boolean isPresentAnyNaciPromoEmpl() {
		return (state(Present, By.xpath(XPathAnyNaciPromoEmpl)).check());
	}

	/**
	 * @param fechaNaci en formato "dd-mm-aaaa"
	 */
	public void selectFechaNacPromoEmpl(String fechaNaci) {
		StringTokenizer fechaTokenizada = (new StringTokenizer(fechaNaci, "-"));
		selectDiaNacPromoEmpl(fechaTokenizada.nextToken());
		selectMesNacPromoEmpl(fechaTokenizada.nextToken());
		selectAnyNacPromoEmpl(fechaTokenizada.nextToken());
	}

	public void selectDiaNacPromoEmpl(String value) {
		new Select(driver.findElement(By.xpath(XPathDiaNaciPromoEmpl))).selectByValue(value);
	}

	public void selectMesNacPromoEmpl(String value) {
		new Select(driver.findElement(By.xpath(XPathMesNaciPromoEmpl))).selectByValue(value);
	}

	public void selectAnyNacPromoEmpl(String value) {
		new Select(driver.findElement(By.xpath(XPathAnyNaciPromoEmpl))).selectByValue(value);
	}

	public void inputDNIPromoEmpl(String dni) throws Exception {
		waitForPageLoaded(driver);
		driver.findElement(By.xpath(XPathInputDNIPromoEmpl)).sendKeys(dni);
	}

	public boolean isVisibleButtonAceptarPromoEmpl() {
		return (state(Visible, By.xpath(XPathAceptarPromoEmpl)).check());
	}

	public void clickButtonAceptarPromoEmpl() {
		waitForPageLoaded(driver); //For avoid StaleElement exception
		click(By.xpath(XPathAceptarPromoEmpl)).exec();

		// Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no
		// funciona así que ejecutamos un 2o
//		Thread.sleep(200);
//		if (isVisibleButtonAceptarPromoEmpl(driver)) {
//			clickAndWaitLoad(driver, By.xpath(XPathAceptarPromoEmpl));
//		}
	}

	public void inputCodigoPromoAndAccept(String codigoPromo) throws Exception {
		inputCodigoPromo(codigoPromo);
		clickAceptarPromo();
	}

	public boolean isVisibleInputCodigoPromoUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPathInputPromo)).wait(maxSeconds).check());
	}

	public void inputCodigoPromo(String codigoPromo) {
		driver.findElement(By.xpath(XPathInputPromo)).clear();
		driver.findElement(By.xpath(XPathInputPromo)).sendKeys(codigoPromo);
	}

	public void clickAceptarPromo() {
		click(By.xpath(XPathButtonAplicarPromo)).exec();
		
//		// Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no
//		// funciona así que ejecutamos un 2o
//		if (WebdrvWrapp.isElementVisibleUntil(driver, By.xpath(XPathButtonAplicarPromo), 2)) {
//			clickAndWaitLoad(driver, By.xpath(XPathButtonAplicarPromo), TypeOfClick.javascript);
//		}
	}
	
	public void clickEliminarValeIfExists() {
		By byEliminar = By.xpath(XPathLinkCancelarCodigo);
		if (state(Visible, byEliminar).check()) {
			click(byEliminar).exec();
		}
	}
	public String getImporteDescuentoEmpleado() {
		if (state(Present, By.xpath(XPathDescuentoEmpleado)).check()) {
			return (driver.findElement(By.xpath(XPathDescuentoEmpleado)).getText());
		}
		return "";
	}

	public boolean isVisibleDescuentoEmpleadoUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPathDescuentoEmpleado)).wait(maxSeconds).check());
	}
	
	public boolean validateDiscountEmpleadoNotNull() throws Exception {
		String descuentoScreen = getImporteDescuentoEmpleado();
		if ("".compareTo(descuentoScreen)==0) {
			return false;
		}
		
		float descuentoFloat = ImporteScreen.getFloatFromImporteMangoScreen(descuentoScreen);
		return (descuentoFloat > 0);
	}

	public void selectMetodoAfterPositioningIn1Envio(TipoTransporte tipoTransporte)
	throws Exception {
		if (!isPageUntil(0)) {
			clickLink1EnvioAndWaitForPage();
		}
		selectMetodoEnvioAfterLinkOtrosIfNeeded(tipoTransporte);
	}

	public void selectMetodoEnvioAfterLinkOtrosIfNeeded(TipoTransporte tipoTransporte) throws Exception {
		openOtrosMetodosDeEnvio();
		if (tipoTransporte.isDroppoint() &&
			isBlockSelectedUntil(tipoTransporte, 0) &&
			isVisibleEditarDireccion()) {
			clickEditarDireccion();
		} else {
			String xpathLink = getXPathRadioMetodo(tipoTransporte);
			click(By.xpath(xpathLink)).type(javascript).exec();
		}
	}
	
	private final String XPathEditarDireccion = "//*[@data-testid[contains(.,'delivery.methods.editAddress.button')]]";
	private boolean isVisibleEditarDireccion() {
		return (state(Visible, By.xpath(XPathEditarDireccion), driver).check());
	}
	private void clickEditarDireccion() {
		click(By.xpath(XPathEditarDireccion)).exec();
	}

	public boolean isPresentBlockMetodo(TipoTransporte tipoTransporte) {
		String xpathBLock = getXPathBlockMetodo(tipoTransporte);
		return (state(Present, By.xpath(xpathBLock), driver).check());
	}

	public void openOtrosMetodosDeEnvio() {
		if (isClosedOtrosMetodos()) {
			click(By.xpath(XPathLinkOtrosMetEnvioClosed)).type(javascript).exec();
		}
	}

	public boolean isClosedOtrosMetodos() {
		return (state(Visible, By.xpath(XPathLinkOtrosMetEnvioClosed)).check());
	}

	public boolean isBlockSelectedUntil(TipoTransporte tipoTransporte, int maxSecondsToWait)
	throws Exception {
		String xpathBlock = getXPathBlockMetodo(tipoTransporte);
		for (int i = 0; i <= maxSecondsToWait; i++) {
			try {
				if (driver.findElement(By.xpath(xpathBlock)) != null && 
					driver.findElement(By.xpath(xpathBlock)).getAttribute("class").contains("radio-on")) {
					return true;
				}
			} catch (StaleElementReferenceException ex) {
				//
			}

			Thread.sleep(1000);
		}

		return false;
	}

	public String getDireccionEntrega() {
		return (driver.findElement(By.xpath(XPathDireccionEnvio)).getText());
	}

	public String getTextDireccionEnvioCompleta() {
		String direccion = "";
		if (getElementsVisible(driver, By.xpath(XPathDireccionEnvio)).size() > 0) {
			direccion = getElementsVisible(driver, By.xpath(XPathDireccionEnvio)).get(0).getText();
		}
		return direccion;
	}

	public void clickEditDirecEnvio() {
		driver.findElement(By.xpath(XPathLinkEditDirecEnvio)).click();
	}

	public void clickContinuar() {
		click(By.xpath(XPathBotonContinuar)).exec();

		// Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no
		// funciona así que ejecutamos un 2o
		if (isVisibleButtonContinuarUntil(2)) {
			click(By.xpath(XPathBotonContinuar)).type(javascript).exec();
		}
	}

	public void clickContinuarAndWaitPage2(AppEcom app) {
		clickContinuar();
		(new Page2DatosPagoCheckoutMobil(Channel.mobile, app, driver)).isPageUntil(2);
	}
	
	public boolean isVisibleButtonContinuarUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPathBotonContinuar)).wait(maxSeconds).check());
	}

	public boolean isVisibleErrorPromoUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPathErrorPromo)).wait(maxSeconds).check());
	}

	public void selectFranjaHorariaUrgente(int posicion) {
		Select selectHorario = new Select(driver.findElement(By.xpath(XPathSelectFranjaHorariaMetodoUrgente)));
		selectHorario.selectByIndex(posicion);
	}
}
