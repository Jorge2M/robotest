package com.mng.robotest.test.pageobject.shop.checkout;

import java.util.StringTokenizer;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.Select;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.pageobject.shop.checkout.envio.TipoTransporteEnum.TipoTransporte;
import com.mng.robotest.test.utils.ImporteScreen;


public class Page1EnvioCheckoutMobil extends PageBase {
	
	private static final String XPATH_LINK1_ENVIO = "//h2[@class[contains(.,'xwing-toggle')] and @data-toggle='step1']";
	private static final String XPATH_INPUT_PROMO = "//input[@id[contains(.,'oucherCode')]]";
	private static final String XPATH_BUTTON_APLICAR_PROMO = "//button[@id[contains(.,'voucherCode')]]";
	private static final String XPATH_LINK_CANCELAR_CODIGO = "//p[@class='discount-total-amount']/a]";
	private static final String XPATH_INPUT_APELLIDO_PROMO_EMPL = "//input[@id[contains(.,'employeeSurname')]]";
	private static final String XPATH_INPUT_DNI_PROMO_EMPL = "//input[@id[contains(.,'employeeDNI')]]";
	private static final String XPATH_DIA_NACI_PROMO_EMPL = "//select[@id[contains(.,'employeeBirthdateDay')]]";
	private static final String XPATH_MES_NACI_PROMO_EMPL = "//select[@id[contains(.,'employeeBirthdateMonth')]]";
	private static final String XPATH_ANY_NACI_PROMO_EMPL = "//select[@id[contains(.,'employeeBirthdateYear')]]";
	private static final String XPATH_ACEPTAR_PROMO_EMPL = "//input[@id[contains(.,'confirmEmployeeData')]]";
	private static final String XPATH_DESCUENTO_EMPLEADO = "//div[@class[contains(.,'employee-discount')]]//p[@class='discount-total-amount']/strong";
	private static final String XPATH_RADIO_ENVIO = "//input[@data-testid[contains(.,'checkout.delivery.methods')]]";
	private static final String XPATH_SELECT_FRANJA_HORARIA_METODO_URGENTE = "//select[@data-component-id='time-range-sameday_nextday_franjas']";
	private static final String XPATH_DIRECCION_ENVIO = "//*[@data-testid[contains(.,'delivery.methods.address')]]";
	private static final String XPATH_LINK_OTROS_MET_ENVIO_CLOSED = "//button[@data-testid[contains(.,'otherMethods.button')]]";
	private static final String XPATH_LINK_EDIT_DIREC_ENVIO = "//div[@id[contains(.,'addressBlock')]]//span[class='address']";
	private static final String XPATH_BOTON_CONTINUAR = "//button[@id[contains(.,'complete-step1')]]";
	private static final String XPATH_ERROR_PROMO = "//div[@data-component-id='error-voucherCode']";
	private static final String XPATH_ENVIO_STANDARD = "//div[@data-analytics-id='standard' and @class[contains(.,'checked')]]";
	
	private String getXPathBlockMetodo(TipoTransporte tipoTransporte) {
		return ("//label[@for='selection-" + tipoTransporte.getCodigo() + "']");
	}

	private String getXPathRadioMetodo(TipoTransporte tipoTransporte) {
		return getXPathBlockMetodo(tipoTransporte) + XPATH_RADIO_ENVIO;
	}

	public boolean isPageUntil(int maxSecondsToWait) {
		return (isVisibleInputCodigoPromoUntil(maxSecondsToWait));
	}
	
	public boolean isPresentEnvioStandard() {
		return state(Present, By.xpath(XPATH_ENVIO_STANDARD)).check();
	}

	public boolean isVisibleLink1EnvioUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPATH_LINK1_ENVIO)).wait(maxSeconds).check());
	}

	public void clickLink1EnvioAndWaitForPage() {
		driver.findElement(By.xpath(XPATH_LINK1_ENVIO)).click();
		isPageUntil(2);
	}

	public boolean isPresentInputApellidoPromoEmplUntil(int maxSeconds) {
		return (state(Present, By.xpath(XPATH_INPUT_APELLIDO_PROMO_EMPL)).wait(maxSeconds).check());
	}

	public void inputApellidoPromoEmpl(String apellido) {
		driver.findElement(By.xpath(XPATH_INPUT_APELLIDO_PROMO_EMPL)).sendKeys(apellido);
	}

	public boolean isPresentInputDNIPromoEmpl() {
		return (state(Present, By.xpath(XPATH_INPUT_DNI_PROMO_EMPL)).check());
	}

	public boolean isPresentDiaNaciPromoEmpl() {
		return (state(Present, By.xpath(XPATH_DIA_NACI_PROMO_EMPL)).check());
	}

	public boolean isPresentMesNaciPromoEmpl() {
		return (state(Present, By.xpath(XPATH_MES_NACI_PROMO_EMPL)).check());
	}

	public boolean isPresentAnyNaciPromoEmpl() {
		return (state(Present, By.xpath(XPATH_ANY_NACI_PROMO_EMPL)).check());
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
		new Select(driver.findElement(By.xpath(XPATH_DIA_NACI_PROMO_EMPL))).selectByValue(value);
	}

	public void selectMesNacPromoEmpl(String value) {
		new Select(driver.findElement(By.xpath(XPATH_MES_NACI_PROMO_EMPL))).selectByValue(value);
	}

	public void selectAnyNacPromoEmpl(String value) {
		new Select(driver.findElement(By.xpath(XPATH_ANY_NACI_PROMO_EMPL))).selectByValue(value);
	}

	public void inputDNIPromoEmpl(String dni) throws Exception {
		waitForPageLoaded(driver);
		driver.findElement(By.xpath(XPATH_INPUT_DNI_PROMO_EMPL)).sendKeys(dni);
	}

	public boolean isVisibleButtonAceptarPromoEmpl() {
		return (state(Visible, By.xpath(XPATH_ACEPTAR_PROMO_EMPL)).check());
	}

	public void clickButtonAceptarPromoEmpl() {
		waitForPageLoaded(driver); //For avoid StaleElement exception
		click(By.xpath(XPATH_ACEPTAR_PROMO_EMPL)).exec();
	}

	public void inputCodigoPromoAndAccept(String codigoPromo) throws Exception {
		inputCodigoPromo(codigoPromo);
		clickAceptarPromo();
	}

	public boolean isVisibleInputCodigoPromoUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPATH_INPUT_PROMO)).wait(maxSeconds).check());
	}

	public void inputCodigoPromo(String codigoPromo) {
		driver.findElement(By.xpath(XPATH_INPUT_PROMO)).clear();
		driver.findElement(By.xpath(XPATH_INPUT_PROMO)).sendKeys(codigoPromo);
	}

	public void clickAceptarPromo() {
		click(By.xpath(XPATH_BUTTON_APLICAR_PROMO)).exec();
	}
	
	public void clickEliminarValeIfExists() {
		By byEliminar = By.xpath(XPATH_LINK_CANCELAR_CODIGO);
		if (state(Visible, byEliminar).check()) {
			click(byEliminar).exec();
		}
	}
	public String getImporteDescuentoEmpleado() {
		if (state(Present, By.xpath(XPATH_DESCUENTO_EMPLEADO)).check()) {
			return (driver.findElement(By.xpath(XPATH_DESCUENTO_EMPLEADO)).getText());
		}
		return "";
	}

	public boolean isVisibleDescuentoEmpleadoUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPATH_DESCUENTO_EMPLEADO)).wait(maxSeconds).check());
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
			click(By.xpath(XPATH_LINK_OTROS_MET_ENVIO_CLOSED)).type(javascript).exec();
		}
	}

	public boolean isClosedOtrosMetodos() {
		return (state(Visible, By.xpath(XPATH_LINK_OTROS_MET_ENVIO_CLOSED)).check());
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
		return (driver.findElement(By.xpath(XPATH_DIRECCION_ENVIO)).getText());
	}

	public String getTextDireccionEnvioCompleta() {
		String direccion = "";
		if (!getElementsVisible(driver, By.xpath(XPATH_DIRECCION_ENVIO)).isEmpty()) {
			direccion = getElementsVisible(driver, By.xpath(XPATH_DIRECCION_ENVIO)).get(0).getText();
		}
		return direccion;
	}

	public void clickEditDirecEnvio() {
		driver.findElement(By.xpath(XPATH_LINK_EDIT_DIREC_ENVIO)).click();
	}

	public void clickContinuar() {
		click(By.xpath(XPATH_BOTON_CONTINUAR)).exec();

		// Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no
		// funciona así que ejecutamos un 2o
		if (isVisibleButtonContinuarUntil(2)) {
			click(By.xpath(XPATH_BOTON_CONTINUAR)).type(javascript).exec();
		}
	}

	public void clickContinuarAndWaitPage2(AppEcom app) {
		clickContinuar();
		(new Page2DatosPagoCheckoutMobil(Channel.mobile, app)).isPageUntil(2);
	}
	
	public boolean isVisibleButtonContinuarUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPATH_BOTON_CONTINUAR)).wait(maxSeconds).check());
	}

	public boolean isVisibleErrorPromoUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPATH_ERROR_PROMO)).wait(maxSeconds).check());
	}

	public void selectFranjaHorariaUrgente(int posicion) {
		Select selectHorario = new Select(driver.findElement(By.xpath(XPATH_SELECT_FRANJA_HORARIA_METODO_URGENTE)));
		selectHorario.selectByIndex(posicion);
	}
}
