package com.mng.robotest.tests.domains.compra.pageobjects.mobile;

import java.util.StringTokenizer;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.Select;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.compra.pageobjects.envio.TipoTransporteEnum.TipoTransporte;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

public class Page1EnvioCheckoutMobil extends PageBase {
	
	private static final String XP_LINK1_ENVIO = "//h2[@class[contains(.,'xwing-toggle')] and @data-toggle='step1']";
	private static final String XP_INPUT_PROMO = "//input[@id[contains(.,'oucherCode')]]";
	private static final String XP_BUTTON_APLICAR_PROMO = "//button[@id[contains(.,'voucherCode')]]";
	private static final String XP_LINK_CANCELAR_CODIGO = "//p[@class='discount-total-amount']/a]";
	private static final String XP_INPUT_APELLIDO_PROMO_EMPL = "//input[@id[contains(.,'employeeSurname')]]";
	private static final String XP_INPUT_DNI_PROMO_EMPL = "//input[@id[contains(.,'employeeDNI')]]";
	private static final String XP_DIA_NACI_PROMO_EMPL = "//select[@id[contains(.,'employeeBirthdateDay')]]";
	private static final String XP_MES_NACI_PROMO_EMPL = "//select[@id[contains(.,'employeeBirthdateMonth')]]";
	private static final String XP_ANY_NACI_PROMO_EMPL = "//select[@id[contains(.,'employeeBirthdateYear')]]";
	private static final String XP_ACEPTAR_PROMO_EMPL = "//input[@id[contains(.,'confirmEmployeeData')]]";
	private static final String XP_DESCUENTO_EMPLEADO = "//div[@class[contains(.,'employee-discount')]]//p[@class='discount-total-amount']/strong";
	private static final String XP_RADIO_ENVIO = "//input[@data-testid[contains(.,'checkout.delivery.methods')]]";
	private static final String XP_SELECT_FRANJA_HORARIA_METODO_URGENTE = "//select[@data-component-id='time-range-sameday_nextday_franjas']";
	private static final String XP_DIRECCION_ENVIO = "//*[@data-testid[contains(.,'delivery.methods.address')]]";
	private static final String XP_CLOSE_METODOS_ENVIO = "//button[@data-testid[contains(.,'otherMethods.button')]]//span[@class[contains(.,'icon-outline-up')]]";
	private static final String XP_OPEN_METODOS_ENVIO = "//button[@data-testid[contains(.,'otherMethods.button')]]//span[@class[contains(.,'icon-outline-down')]]";
	private static final String XP_BUTTON_EDIT_DIREC_ENVIO = "//button[@data-testid[contains(.,'editAddress.button')]]";
	private static final String XP_BOTON_CONTINUAR = "//button[@id[contains(.,'complete-step1')]]";
	private static final String XP_ERROR_PROMO = "//div[@data-component-id='error-voucherCode']";
	private static final String XP_ENVIO_STANDARD = "//div[@data-analytics-id='standard' and @class[contains(.,'checked')]]";
	private static final String XP_EDITAR_DIRECCION = "//*[@data-testid[contains(.,'delivery.methods.editAddress.button')]]";
	
	private String getXPathBlockMetodo(TipoTransporte tipoTransporte) {
		return ("//label[@for='selection-" + tipoTransporte.getCodigo() + "']");
	}

	private String getXPathRadioMetodo(TipoTransporte tipoTransporte) {
		return getXPathBlockMetodo(tipoTransporte) + XP_RADIO_ENVIO;
	}

	public boolean isPage(int seconds) {
		return isVisibleInputCodigoPromoUntil(seconds);
	}
	
	public boolean isPresentEnvioStandard() {
		return state(PRESENT, XP_ENVIO_STANDARD).check();
	}

	public boolean isVisibleLink1EnvioUntil(int seconds) {
		return state(VISIBLE, XP_LINK1_ENVIO).wait(seconds).check();
	}

	public void clickLink1EnvioAndWaitForPage() {
		click(XP_LINK1_ENVIO).type(JAVASCRIPT).exec();
		isPage(2);
	}

	public boolean isPresentInputApellidoPromoEmplUntil(int seconds) {
		return state(PRESENT, XP_INPUT_APELLIDO_PROMO_EMPL).wait(seconds).check();
	}

	public void inputApellidoPromoEmpl(String apellido) {
		getElement(XP_INPUT_APELLIDO_PROMO_EMPL).sendKeys(apellido);
	}

	public boolean isPresentInputDNIPromoEmpl() {
		return state(PRESENT, XP_INPUT_DNI_PROMO_EMPL).check();
	}

	public boolean isPresentDiaNaciPromoEmpl() {
		return state(PRESENT, XP_DIA_NACI_PROMO_EMPL).check();
	}

	public boolean isPresentMesNaciPromoEmpl() {
		return state(PRESENT, XP_MES_NACI_PROMO_EMPL).check();
	}

	public boolean isPresentAnyNaciPromoEmpl() {
		return state(PRESENT, XP_ANY_NACI_PROMO_EMPL).check();
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
		new Select(getElement(XP_DIA_NACI_PROMO_EMPL)).selectByValue(value);
	}

	public void selectMesNacPromoEmpl(String value) {
		new Select(getElement(XP_MES_NACI_PROMO_EMPL)).selectByValue(value);
	}

	public void selectAnyNacPromoEmpl(String value) {
		new Select(getElement(XP_ANY_NACI_PROMO_EMPL)).selectByValue(value);
	}

	public void inputDNIPromoEmpl(String dni) {
		waitLoadPage();
		getElement(XP_INPUT_DNI_PROMO_EMPL).sendKeys(dni);
	}

	public boolean isVisibleButtonAceptarPromoEmpl() {
		return state(VISIBLE, XP_ACEPTAR_PROMO_EMPL).check();
	}

	public void clickButtonAceptarPromoEmpl() {
		waitLoadPage(); //For avoid StaleElement exception
		click(XP_ACEPTAR_PROMO_EMPL).exec();
	}

	public void inputCodigoPromoAndAccept(String codigoPromo) {
		inputCodigoPromo(codigoPromo);
		clickAceptarPromo();
	}

	public boolean isVisibleInputCodigoPromoUntil(int seconds) {
		return state(VISIBLE, XP_INPUT_PROMO).wait(seconds).check();
	}

	public void inputCodigoPromo(String codigoPromo) {
		getElement(XP_INPUT_PROMO).clear();
		getElement(XP_INPUT_PROMO).sendKeys(codigoPromo);
	}

	public void clickAceptarPromo() {
		click(XP_BUTTON_APLICAR_PROMO).exec();
	}
	
	public void clickEliminarValeIfExists() {
		if (state(VISIBLE, XP_LINK_CANCELAR_CODIGO).check()) {
			click(XP_LINK_CANCELAR_CODIGO).exec();
		}
	}
	public String getImporteDescuentoEmpleado() {
		if (state(PRESENT, XP_DESCUENTO_EMPLEADO).check()) {
			return getElement(XP_DESCUENTO_EMPLEADO).getText();
		}
		return "";
	}

	public boolean isVisibleDescuentoEmpleadoUntil(int seconds) {
		return state(VISIBLE, XP_DESCUENTO_EMPLEADO).wait(seconds).check();
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
		if (!isPage(0)) {
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
			state(VISIBLE, xpathLink).wait(2).check();
			click(xpathLink).type(JAVASCRIPT).waitLink(2).exec();
			waitMillis(1000);
		}
	}
	
	private boolean isVisibleEditarDireccion() {
		return state(VISIBLE, XP_EDITAR_DIRECCION).check();
	}
	private void clickEditarDireccion() {
		click(XP_EDITAR_DIRECCION).exec();
	}

	public boolean isPresentBlockMetodo(TipoTransporte tipoTransporte) {
		String xpathBLock = getXPathBlockMetodo(tipoTransporte);
		return state(PRESENT, xpathBLock).check();
	}

	public void openOtrosMetodosDeEnvio() {
		if (state(VISIBLE, XP_OPEN_METODOS_ENVIO).wait(2).check()) {
			try {
				click(XP_OPEN_METODOS_ENVIO).type(JAVASCRIPT).exec();
			} catch (Exception e) {
				click(XP_OPEN_METODOS_ENVIO).exec();
			}
			state(VISIBLE, XP_CLOSE_METODOS_ENVIO).wait(2).check();
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
		return getElement(XP_DIRECCION_ENVIO).getText();
	}

	public String getTextDireccionEnvioCompleta() {
		String direccion = "";
		if (!getElementsVisible(XP_DIRECCION_ENVIO).isEmpty()) {
			direccion = getElementsVisible(XP_DIRECCION_ENVIO).get(0).getText();
		}
		return direccion;
	}

	public void clickEditDirecEnvio() {
		waitForPageLoaded(driver);
		try { 
			state(VISIBLE, XP_BUTTON_EDIT_DIREC_ENVIO).wait(2).check();
			click(XP_BUTTON_EDIT_DIREC_ENVIO).exec();
		} catch (Exception e ) {
			waitMillis(2000);
			state(VISIBLE, XP_BUTTON_EDIT_DIREC_ENVIO).wait(2).check();
			click(XP_BUTTON_EDIT_DIREC_ENVIO).exec();
		}
	}
	
	public void clickContinuar() {
		click(XP_BOTON_CONTINUAR).exec();

		// Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no
		// funciona así que ejecutamos un 2o
		if (isVisibleButtonContinuarUntil(2)) {
			click(XP_BOTON_CONTINUAR).type(JAVASCRIPT).exec();
		}
	}

	public void clickContinuarAndWaitPage2() {
		clickContinuar();
		new Page2DatosPagoCheckoutMobil().isPage(2);
	}
	
	public boolean isVisibleButtonContinuarUntil(int seconds) {
		return state(VISIBLE, XP_BOTON_CONTINUAR).wait(seconds).check();
	}

	public boolean isVisibleErrorPromoUntil(int seconds) {
		return state(VISIBLE, XP_ERROR_PROMO).wait(seconds).check();
	}

	public void selectFranjaHorariaUrgente(int posicion) {
		Select selectHorario = new Select(getElement(XP_SELECT_FRANJA_HORARIA_METODO_URGENTE));
		selectHorario.selectByIndex(posicion);
	}
}
