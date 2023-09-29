package com.mng.robotest.tests.domains.compra.pageobjects;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.compra.pageobjects.envio.SecMetodoEnvioDesktop;
import com.mng.robotest.tests.domains.compra.pageobjects.envio.TipoTransporteEnum.TipoTransporte;
import com.mng.robotest.tests.domains.compra.pageobjects.pci.SecTarjetaPci;
import com.mng.robotest.tests.domains.compra.payments.eps.pageobjects.SecEps;
import com.mng.robotest.testslegacy.beans.Pago;
import com.mng.robotest.testslegacy.data.Descuento;
import com.mng.robotest.testslegacy.datastored.DataPedido;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageCheckoutWrapper extends PageBase {
 
	private final Page1DktopCheckout page1DktopCheckout = new Page1DktopCheckout();
	private final Page1EnvioCheckoutMobil page1MobilCheckout = new Page1EnvioCheckoutMobil();
	private final Page2DatosPagoCheckoutMobil page2MobilCheckout = new Page2DatosPagoCheckoutMobil();
	private final SecTarjetaPci secTarjetaPci = SecTarjetaPci.makeSecTarjetaPci(channel);
	
	//Abarca cualquier div de loading
	private static final String XPATH_DIV_LOADING = "//div[@class[contains(.,'panel_loading')] or @class[contains(.,'container-full-centered-loading')] or @class[contains(.,'loading-panel')]]";
	private static final String XPATH_DISCOUNT_LOYALTY_APPLIED_MOBIL = "//span[@class='redeem-likes__discount']";	
	
	public Page1DktopCheckout getPage1DktopCheckout() {
		return page1DktopCheckout;
	}
	public Page2DatosPagoCheckoutMobil getPage2MobilCheckout() {
		return page2MobilCheckout;
	}
	public SecTarjetaPci getSecTarjetaPci() {
		return secTarjetaPci;
	}
	
	public boolean isFirstPageUntil(int seconds) {
		if (isMobile()) {
			return page1MobilCheckout.isVisibleLink1EnvioUntil(seconds);
		}
		return page1DktopCheckout.isPageUntil(seconds);	
	}
	
	public void inputNumberPci(String numtarj) {
		secTarjetaPci.inputNumber(numtarj);
	}
	
	public void inputTitularPci(String titular) {
		secTarjetaPci.inputTitular(titular);
	}
	
	public void selectMesByVisibleTextPci(String mesCaducidad) {
		secTarjetaPci.selectMesByVisibleText(mesCaducidad);
	}
	
	public void selectAnyByVisibleTextPci(String anyCaducidad) {
		secTarjetaPci.selectAnyByVisibleText(anyCaducidad);
	}	
	
	public void inputCvcPci(String cvc) {
		secTarjetaPci.inputCvc(cvc);
	}
	
	//Específico para método de Pago Codensa (Colombia)
	public void inputDniPci(String dni) {
		secTarjetaPci.inputDni(dni);
	}
	
	public void inputCodigoPromoAndAccept(String codigoPromo) {
		if (isMobile()) {
			page1MobilCheckout.inputCodigoPromoAndAccept(codigoPromo);
		} else {
			page1DktopCheckout.showInputCodigoPromoAndAccept(codigoPromo);
		}
	}
	
	public void clickEliminarValeIfExists() {
		if (isMobile()) {
			page1MobilCheckout.clickEliminarValeIfExists();
		} else {
			page1DktopCheckout.clickEliminarValeIfExists();
		}
	}
	
	public boolean isPresentInputApellidoPromoEmplUntil(int seconds) {
		if (isMobile()) {
			return (page1MobilCheckout.isPresentInputApellidoPromoEmplUntil(seconds));
		}
		return (page1DktopCheckout.isPresentInputApellidoPromoEmplUntil(seconds));
	}
	
	public boolean isPresentInputDNIPromoEmpl() {
		if (isMobile()) {
			return (page1MobilCheckout.isPresentInputDNIPromoEmpl());
		}
		return (page1DktopCheckout.isPresentInputDNIPromoEmpl());
	}	
	
	public boolean isPresentDiaNaciPromoEmpl() {
		if (isMobile()) {
			return (page1MobilCheckout.isPresentDiaNaciPromoEmpl());
		}
		return (page1DktopCheckout.isPresentDiaNaciPromoEmpl());
	}	
	
	public void inputDNIPromoEmpl(String dni) {
		if (isMobile()) {
			page1MobilCheckout.inputDNIPromoEmpl(dni);
		} else {
			page1DktopCheckout.inputDNIPromoEmpl(dni);
		}
	}
	
	public void inputApellidoPromoEmpl(String apellido) {
		if (isMobile()) {
			page1MobilCheckout.inputApellidoPromoEmpl(apellido);
		} else {
			page1DktopCheckout.inputApellidoPromoEmpl(apellido);
		}
	}	

	private static final String XPATH_BUTTON_FOR_APPLY_LOYALTY_POINTS = "//button[@class[contains(.,'redeem-likes')] and @type='button']";
	public boolean isVisibleButtonForApplyLoyaltyPoints() {
		return (state(Visible, XPATH_BUTTON_FOR_APPLY_LOYALTY_POINTS).wait(2).check());
	}

	public float applyAndGetLoyaltyPoints() {
		WebElement buttonLoyalty = getElementsVisible(XPATH_BUTTON_FOR_APPLY_LOYALTY_POINTS).get(0);
		String textButtonApply = buttonLoyalty.getAttribute("innerHTML");
		String importeButton = ImporteScreen.normalizeImportFromScreen(textButtonApply);
		click(XPATH_BUTTON_FOR_APPLY_LOYALTY_POINTS).exec();
		isNoDivLoadingUntil(1);
		return (ImporteScreen.getFloatFromImporteMangoScreen(importeButton));
	}

	public float getDiscountLoyaltyAppliedMobil() {
		if (state(Visible, XPATH_DISCOUNT_LOYALTY_APPLIED_MOBIL).check()) {
			String discountApplied = getElement(XPATH_DISCOUNT_LOYALTY_APPLIED_MOBIL).getAttribute("innerHTML");
			return (ImporteScreen.getFloatFromImporteMangoScreen(discountApplied));
		}	
		return 0;
	}

	/**
	 * @param fechaNaci en formato "dd-mm-aaaa"
	 */
	public void selectFechaNacPromoEmpl(String fechaNaci) {
		if (isMobile()) {
			page1MobilCheckout.selectFechaNacPromoEmpl(fechaNaci);
		} else {
			page1DktopCheckout.selectFechaNacPromoEmpl(fechaNaci);
		}
	}
	
	public void clickGuardarPromo() {
		if (isMobile()) {
			page1MobilCheckout.clickAceptarPromo();
		} else {
			page1DktopCheckout.clickGuardarPromo();
		}
	}
	
	public void clickButtonAceptarPromoEmpl() {
		if (isMobile()) {
			page1MobilCheckout.clickButtonAceptarPromoEmpl();
		} else {
			page1DktopCheckout.clickGuardarPromo();
		}
	}
	
	public String getImporteDescuentoEmpleado() {
		if (isMobile()) {
			return page1MobilCheckout.getImporteDescuentoEmpleado();
		}
		return page1DktopCheckout.getImporteDescuentoEmpleado();
	}
	
	public boolean isVisibleDescuentoEmpleadoUntil(int seconds) {
		if (isMobile()) {
			return page1MobilCheckout.isVisibleDescuentoEmpleadoUntil(seconds);
		}
		return page1DktopCheckout.isVisibleDescuentoEmpleadoUntil(seconds); 
	}

	public boolean isNumMetodosPagoOK(boolean isEmpl) {
		if (isMobile()) {
			return page2MobilCheckout.isNumMetodosPagoOK(isEmpl);
		}
		return page1DktopCheckout.isNumMetodosPagoOK(isEmpl);
	}
	
	public boolean isNumpagos(int numPagosExpected) {
		if (isMobile()) {
			page2MobilCheckout.isNumpagos(numPagosExpected);
		}
		return page1DktopCheckout.isNumpagos(numPagosExpected);
	}
	
	public boolean isPresentMetodosPago() {
		if (isMobile()) {
			return page2MobilCheckout.isPresentMetodosPago();
		}
		return page1DktopCheckout.isPresentMetodosPago();
	}	
	
	public boolean isMetodoPagoPresent(String metodoPago) {
		if (isMobile()) {
			return page2MobilCheckout.isMetodoPagoPresent(metodoPago);
		}
		return page1DktopCheckout.isMetodoPagoPresent(metodoPago);
	}
	
	public void despliegaMetodosPago() {
		if (isMobile()) {
			page2MobilCheckout.despliegaMetodosPago();
		} else {
			page1DktopCheckout.despliegaMetodosPago();
		}
	}

	public String getPrecioTotalFromResumen(boolean normalize) {
		if (isMobile()) {
			return (page2MobilCheckout.getPrecioTotalFromResumen(normalize));
		}
		return (page1DktopCheckout.getPrecioTotalFromResumen(normalize));
	}
	
	public String getCroaciaPrecioTotalInEuros(boolean normalize) {
		if (isMobile()) {
			return page2MobilCheckout.getCroaciaPrecioTotalInEuros(normalize);
		}
		return page1DktopCheckout.getCroaciaPrecioTotalInEuros(normalize);
	}	
	
	public String getAlmacenFromNoProdEntorn() {
		if (isMobile()) {
			return "";
		}
		return (page1DktopCheckout.getAlmacenFromNoProdEntorn()); 
	}

	public boolean waitUntilNoDivLoading(int seconds) {
		return state(Invisible, XPATH_DIV_LOADING).wait(seconds).check();
	}

	public boolean isNoDivLoadingUntil(int seconds) {
		return state(Invisible, XPATH_DIV_LOADING).wait(seconds).check();
	}

	public String getMethodInputValue(String metodoPago) {
		if (metodoPago.contains("TRANSFERENCIA BANCARIA")) {
			return ("TRANSFERENCIA");
		}
		if (metodoPago.contains("наличными через терминал")) {
			return ("YANDEX OFFLINE");
		}

		if (metodoPago.contains("PŁATNOŚĆ PRZY ODBIORZE") ||
			metodoPago.contains("PLATBA NA DOBÍRKU") ||
			metodoPago.contains("UTÁNVÉTELES FIZETÉS") ||
			metodoPago.contains("PLATA PRIN RAMBURS")) {
			return ("ContraReembolsoSTD");
		}
		
		if (metodoPago.contains("mercadopago_transferencia")) {
			return ("mercadopago");
		}
		
		return metodoPago;
	}
	
	public void forceClickMetodoPagoAndWait(String metodoPago) {
		if (isMobile()) {
			page2MobilCheckout.forceClickMetodoPagoAndWait(metodoPago);
		} else {
			page1DktopCheckout.forceClickMetodoPagoAndWait(metodoPago);
		}
	}
	
	public boolean isAvailableTrjGuardada(String metodoPago) {
		if (isMobile()) {
			return (page2MobilCheckout.isVisibleRadioTrjGuardada(metodoPago));
		} else {
			return (page1DktopCheckout.isVisibleRadioTrjGuardada(metodoPago));
		}
	}

	public void clickRadioTrjGuardada() {
		if (isMobile()) {
			page2MobilCheckout.clickRadioTrjGuardada();
		} else {
			page1DktopCheckout.clickRadioTrjGuardada();
		}
	}

	public void inputCvcTrjGuardadaIfVisible(String cvc) { 
		if (isMobile()) {
			page2MobilCheckout.inputCvcTrjGuardadaIfVisible(cvc);
		} else {
			page1DktopCheckout.inputCvcTrjGuardadaIfVisible(cvc);
		}
	}
	
	public void clickSolicitarFactura() {
		if (isMobile()) {
			page2MobilCheckout.clickSolicitarFactura();
		} else {
			page1DktopCheckout.clickSolicitarFactura();
		}
	}	
	
	public void clickEditDirecEnvio() {
		if (isMobile()) {
			page1MobilCheckout.clickEditDirecEnvio();
		} else {
			page1DktopCheckout.clickEditDirecEnvio();
		}
	}
	
	public boolean isArticulos() {
		if (isMobile()) {
			return (page2MobilCheckout.isArticulos());
		}
		return (page1DktopCheckout.isArticulos());
	}
	
	public float getImportSubtotalDesktop() {
		String textImporte = page1DktopCheckout.getPrecioSubTotalFromResumen();
		return (ImporteScreen.getFloatFromImporteMangoScreen(textImporte));
	}
	
	public void confirmarPagoFromMetodos(DataPedido dataPedido) {
		getDataPedidoFromCheckout(dataPedido);
		if (isMobile()) {
			page2MobilCheckout.confirmarPagoFromMetodos();
		} else {
			page1DktopCheckout.confirmarPagoFromMetodos();
		}
	}
	
	public boolean isVisibleBloquePagoNoTRJIntegradaUntil(Pago pago, int seconds) {
		if (isMobile()) {
			return page2MobilCheckout.isVisibleTextoBajoPagoUntil(pago, seconds);
		}
		return page1DktopCheckout.isVisibleBloquePagoNoTRJIntegradaUntil(pago, seconds);
	}
	
	public String getTextDireccionEnvioCompleta() {
		if (isMobile()) {
			if (page1MobilCheckout.isPageUntil(0)) {
				return (page1MobilCheckout.getTextDireccionEnvioCompleta());
			}
			return (page2MobilCheckout.getTextDireccionEnvioCompleta());
		}
		return (page1DktopCheckout.getTextDireccionEnvioCompleta());
	}
	
	public void getDataPedidoFromCheckout(DataPedido dataPedido) {
		String direcEnvio = getTextDireccionEnvioCompleta();
		if ("".compareTo(direcEnvio)!=0) {
			dataPedido.setDireccionEnvio(direcEnvio);
		}
		dataPedido.setImporteTotal(getPrecioTotalFromResumen(true));
		dataPedido.setCodigoAlmacen(getAlmacenFromNoProdEntorn());
	}
	
	public String getDireccionEnvioCompleta() {
		if (isMobile()) {
			return (page1MobilCheckout.getTextDireccionEnvioCompleta());
		}
		return (page1DktopCheckout.getTextDireccionEnvioCompleta());
	}
	
	public boolean direcEnvioContainsPais(String nombrePais) {
		String direccionEnvio = getTextDireccionEnvioCompleta();
		return (direccionEnvio.toLowerCase().contains(nombrePais.toLowerCase()));
	}
	
	public boolean isPresentBlockMetodo(TipoTransporte tipoTransporte) {
		if (isMobile()) {
			return page1MobilCheckout.isPresentBlockMetodo(tipoTransporte);
		}
		return new SecMetodoEnvioDesktop().isPresentBlockMetodo(tipoTransporte);
	}
	
	public boolean validateDiscountOk(PreciosArticulo preciosArtScreen, Descuento descuento) {
		switch (descuento.getDiscountOver()) {
		case ORIGINAL_PRICE:
			return (validateDiscountOverOriginalPrice(preciosArtScreen, descuento.getPercentageDesc()));
		case LAST_PRICE_OR_SALE:
		default:
			return (validateDiscountOverLastPriceOrSale(preciosArtScreen, descuento.getPercentageDesc()));
		}
	}
	
	private boolean validateDiscountOverOriginalPrice(PreciosArticulo preciosArtScreen, int porcMinDiscount) {
		float porcMinDiscountLessOne = porcMinDiscount - 1f; //Restamos 1 por un tema de precisión
		float importeDescMinTeorico = preciosArtScreen.original * (1 - (porcMinDiscountLessOne/100));
		return (preciosArtScreen.definitivo <= importeDescMinTeorico);
	}
	
	private boolean validateDiscountOverLastPriceOrSale(PreciosArticulo preciosArtScreen, int porcMinDiscount) {
		float porcMinDiscountLessOne = porcMinDiscount - 1f; //Restamos 1 por un tema de precisión
		float importeDescMinTeorico = preciosArtScreen.ultimaRebaja * (1 - (porcMinDiscountLessOne/100));
		return (preciosArtScreen.definitivo <= importeDescMinTeorico);
	}
	
	public void backPageMetodosPagos(String urlPagCheckout) {
		if (driver.getCurrentUrl().compareTo(urlPagCheckout)!=0) {
			driver.get(urlPagCheckout);
			acceptAlertIfExists(driver);
		}

		//En el caso de móvil existen 3 páginas de checkout y no tenemos claro si estamos en la de los métodos de pago 
		//así que si existe, clickamos el link a la página-2 del checkout con los métodos de pago		
		if (channel==Channel.mobile) {
			page2MobilCheckout.clickLink2DatosPagoIfVisible();
		}
	}
	
	public String formateaPrecioTotal(String xpathImporteCheckout) {
		for (int i=0; i<2; i++) {
			try {
				return getElement(xpathImporteCheckout).getText();
			}
			catch (StaleElementReferenceException e) {
				//
			}
		}
		return "";
	}
	
	public void selectBancoEPS(String nombreBanco) {
		new SecEps().selectBanco(nombreBanco);
	}

	public boolean isBancoSeleccionado(String nombreBanco) {
		return new SecEps().isBancoSeleccionado(nombreBanco);
	}
	
	private boolean isMobile() {
		return channel==Channel.mobile;
	}	
}