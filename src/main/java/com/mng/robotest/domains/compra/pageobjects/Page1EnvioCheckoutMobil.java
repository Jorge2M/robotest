package com.mng.robotest.domains.compra.pageobjects;

import java.util.StringTokenizer;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.Select;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.domains.compra.pageobjects.envio.TipoTransporteEnum.TipoTransporte;
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
	private static final String XPATH_CLOSE_METODOS_ENVIO = "//button[@data-testid[contains(.,'otherMethods.button')]]//span[@class[contains(.,'icon-outline-up')]]";
	private static final String XPATH_OPEN_METODOS_ENVIO = "//button[@data-testid[contains(.,'otherMethods.button')]]//span[@class[contains(.,'icon-outline-down')]]";
	private static final String XPATH_BUTTON_EDIT_DIREC_ENVIO = "//button[@data-testid[contains(.,'editAddress.button')]]";
	private static final String XPATH_BOTON_CONTINUAR = "//button[@id[contains(.,'complete-step1')]]";
	private static final String XPATH_ERROR_PROMO = "//div[@data-component-id='error-voucherCode']";
	private static final String XPATH_ENVIO_STANDARD = "//div[@data-analytics-id='standard' and @class[contains(.,'checked')]]";
	private static final String XPATH_EDITAR_DIRECCION = "//*[@data-testid[contains(.,'delivery.methods.editAddress.button')]]";	
	
	private String getXPathBlockMetodo(TipoTransporte tipoTransporte) {
		return ("//label[@for='selection-" + tipoTransporte.getCodigo() + "']");
	}

	private String getXPathRadioMetodo(TipoTransporte tipoTransporte) {
		return getXPathBlockMetodo(tipoTransporte) + XPATH_RADIO_ENVIO;
	}

	public boolean isPageUntil(int seconds) {
		return isVisibleInputCodigoPromoUntil(seconds);
	}
	
	public boolean isPresentEnvioStandard() {
		return state(Present, XPATH_ENVIO_STANDARD).check();
	}

	public boolean isVisibleLink1EnvioUntil(int seconds) {
		return state(Visible, XPATH_LINK1_ENVIO).wait(seconds).check();
	}

	public void clickLink1EnvioAndWaitForPage() {
		click(XPATH_LINK1_ENVIO).type(javascript).exec();
		isPageUntil(2);
	}

	public boolean isPresentInputApellidoPromoEmplUntil(int seconds) {
		return state(Present, XPATH_INPUT_APELLIDO_PROMO_EMPL).wait(seconds).check();
	}

	public void inputApellidoPromoEmpl(String apellido) {
		getElement(XPATH_INPUT_APELLIDO_PROMO_EMPL).sendKeys(apellido);
	}

	public boolean isPresentInputDNIPromoEmpl() {
		return state(Present, XPATH_INPUT_DNI_PROMO_EMPL).check();
	}

	public boolean isPresentDiaNaciPromoEmpl() {
		return state(Present, XPATH_DIA_NACI_PROMO_EMPL).check();
	}

	public boolean isPresentMesNaciPromoEmpl() {
		return state(Present, XPATH_MES_NACI_PROMO_EMPL).check();
	}

	public boolean isPresentAnyNaciPromoEmpl() {
		return state(Present, XPATH_ANY_NACI_PROMO_EMPL).check();
	}

	/**
	 * @param fechaNaci en formato "dd-mm-aaaa"
	 */
	public void selectFechaNacPromoEmpl(String fechaNaci) {
		var fechaTokenizada = (new StringTokenizer(fechaNaci, "-"));
		selectDiaNacPromoEmpl(fechaTokenizada.nextToken());
		selectMesNacPromoEmpl(fechaTokenizada.nextToken());
		selectAnyNacPromoEmpl(fechaTokenizada.nextToken());
	}

	public void selectDiaNacPromoEmpl(String value) {
		new Select(getElement(XPATH_DIA_NACI_PROMO_EMPL)).selectByValue(value);
	}

	public void selectMesNacPromoEmpl(String value) {
		new Select(getElement(XPATH_MES_NACI_PROMO_EMPL)).selectByValue(value);
	}

	public void selectAnyNacPromoEmpl(String value) {
		new Select(getElement(XPATH_ANY_NACI_PROMO_EMPL)).selectByValue(value);
	}

	public void inputDNIPromoEmpl(String dni) {
		waitLoadPage();
		getElement(XPATH_INPUT_DNI_PROMO_EMPL).sendKeys(dni);
	}

	public boolean isVisibleButtonAceptarPromoEmpl() {
		return state(Visible, XPATH_ACEPTAR_PROMO_EMPL).check();
	}

	public void clickButtonAceptarPromoEmpl() {
		waitLoadPage(); //For avoid StaleElement exception
		click(XPATH_ACEPTAR_PROMO_EMPL).exec();
	}

	public void inputCodigoPromoAndAccept(String codigoPromo) {
		inputCodigoPromo(codigoPromo);
		clickAceptarPromo();
	}

	public boolean isVisibleInputCodigoPromoUntil(int seconds) {
		return state(Visible, XPATH_INPUT_PROMO).wait(seconds).check();
	}

	public void inputCodigoPromo(String codigoPromo) {
		getElement(XPATH_INPUT_PROMO).clear();
		getElement(XPATH_INPUT_PROMO).sendKeys(codigoPromo);
	}

	public void clickAceptarPromo() {
		click(XPATH_BUTTON_APLICAR_PROMO).exec();
	}
	
	public void clickEliminarValeIfExists() {
		if (state(Visible, XPATH_LINK_CANCELAR_CODIGO).check()) {
			click(XPATH_LINK_CANCELAR_CODIGO).exec();
		}
	}
	public String getImporteDescuentoEmpleado() {
		if (state(Present, XPATH_DESCUENTO_EMPLEADO).check()) {
			return getElement(XPATH_DESCUENTO_EMPLEADO).getText();
		}
		return "";
	}

	public boolean isVisibleDescuentoEmpleadoUntil(int seconds) {
		return state(Visible, XPATH_DESCUENTO_EMPLEADO).wait(seconds).check();
	}
	
	public boolean validateDiscountEmpleadoNotNull() {
		String descuentoScreen = getImporteDescuentoEmpleado();
		if ("".compareTo(descuentoScreen)==0) {
			return false;
		}
		
		float descuentoFloat = ImporteScreen.getFloatFromImporteMangoScreen(descuentoScreen);
		return (descuentoFloat > 0);
	}

	public void selectMetodoAfterPositioningIn1Envio(TipoTransporte tipoTransporte) {
		if (!isPageUntil(0)) {
			clickLink1EnvioAndWaitForPage();
		}
		selectMetodoEnvioAfterLinkOtrosIfNeeded(tipoTransporte);
	}

	public void selectMetodoEnvioAfterLinkOtrosIfNeeded(TipoTransporte tipoTransporte) {
		openOtrosMetodosDeEnvio();
		if (tipoTransporte.isDroppoint() &&
			isBlockSelectedUntil(tipoTransporte, 0) &&
			isVisibleEditarDireccion()) {
			clickEditarDireccion();
		} else {
			String xpathLink = getXPathRadioMetodo(tipoTransporte);
			state(Visible, xpathLink).wait(2).check();
			click(xpathLink).type(javascript).waitLink(2).exec();
			waitMillis(1000);
		}
	}
	
	private boolean isVisibleEditarDireccion() {
		return state(Visible, XPATH_EDITAR_DIRECCION).check();
	}
	private void clickEditarDireccion() {
		click(XPATH_EDITAR_DIRECCION).exec();
	}

	public boolean isPresentBlockMetodo(TipoTransporte tipoTransporte) {
		String xpathBLock = getXPathBlockMetodo(tipoTransporte);
		return state(Present, xpathBLock).check();
	}

	public void openOtrosMetodosDeEnvio() {
		if (state(Visible, XPATH_OPEN_METODOS_ENVIO).wait(2).check()) {
			try {
				click(XPATH_OPEN_METODOS_ENVIO).type(javascript).exec();
			} catch (Exception e) {
				click(XPATH_OPEN_METODOS_ENVIO).exec();
			}
			state(Visible, XPATH_CLOSE_METODOS_ENVIO).wait(2).check();
		}
	}

	public boolean isBlockSelectedUntil(TipoTransporte tipoTransporte, int seconds) {
		String xpathBlock = getXPathBlockMetodo(tipoTransporte);
		for (int i = 0; i <= seconds; i++) {
			try {
				if (getElement(xpathBlock) != null && 
					getElement(xpathBlock).getAttribute("class").contains("radio-on")) {
					return true;
				}
			} catch (StaleElementReferenceException ex) {
				//
			}
			waitMillis(1000);
		}
		return false;
	}

	public String getDireccionEntrega() {
		return getElement(XPATH_DIRECCION_ENVIO).getText();
	}

	public String getTextDireccionEnvioCompleta() {
		String direccion = "";
		if (!getElementsVisible(XPATH_DIRECCION_ENVIO).isEmpty()) {
			direccion = getElementsVisible(XPATH_DIRECCION_ENVIO).get(0).getText();
		}
		return direccion;
	}

	public void clickEditDirecEnvio() {
		waitForPageLoaded(driver);
		try { 
			click(XPATH_BUTTON_EDIT_DIREC_ENVIO).waitLink(2).exec();
		} catch (Exception e ) {
			waitMillis(1000);
			click(XPATH_BUTTON_EDIT_DIREC_ENVIO).waitLink(2).exec();
		}
	}
	
	public void clickContinuar() {
		click(XPATH_BOTON_CONTINUAR).exec();

		// Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no
		// funciona así que ejecutamos un 2o
		if (isVisibleButtonContinuarUntil(2)) {
			click(XPATH_BOTON_CONTINUAR).type(javascript).exec();
		}
	}

	public void clickContinuarAndWaitPage2() {
		clickContinuar();
		new Page2DatosPagoCheckoutMobil().isPageUntil(2);
	}
	
	public boolean isVisibleButtonContinuarUntil(int seconds) {
		return state(Visible, XPATH_BOTON_CONTINUAR).wait(seconds).check();
	}

	public boolean isVisibleErrorPromoUntil(int seconds) {
		return state(Visible, XPATH_ERROR_PROMO).wait(seconds).check();
	}

	public void selectFranjaHorariaUrgente(int posicion) {
		Select selectHorario = new Select(getElement(XPATH_SELECT_FRANJA_HORARIA_METODO_URGENTE));
		selectHorario.selectByIndex(posicion);
	}
}
