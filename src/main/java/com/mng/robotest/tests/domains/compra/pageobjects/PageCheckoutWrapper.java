package com.mng.robotest.tests.domains.compra.pageobjects;

import org.openqa.selenium.StaleElementReferenceException;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.compra.pageobjects.beans.PreciosArticulo;
import com.mng.robotest.tests.domains.compra.pageobjects.desktop.Page1DktopCheckout;
import com.mng.robotest.tests.domains.compra.pageobjects.envio.SecMetodoEnvioDesktop;
import com.mng.robotest.tests.domains.compra.pageobjects.envio.TipoTransporteEnum.TipoTransporte;
import com.mng.robotest.tests.domains.compra.pageobjects.mobile.Page1EnvioCheckoutMobil;
import com.mng.robotest.tests.domains.compra.pageobjects.mobile.Page2DatosPagoCheckoutMobil;
import com.mng.robotest.tests.domains.compra.pageobjects.pci.SecTarjetaPci;
import com.mng.robotest.tests.domains.compra.payments.eps.pageobjects.SecEps;
import com.mng.robotest.testslegacy.beans.Pago;
import com.mng.robotest.testslegacy.data.Descuento;
import com.mng.robotest.testslegacy.datastored.DataPedido;
import com.mng.robotest.testslegacy.generic.UtilsMangoTest;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageCheckoutWrapper extends PageBase {
 
	private final Page1DktopCheckout pg1DktopCheckout = new Page1DktopCheckout();
	private final Page1EnvioCheckoutMobil pg1MobilCheckout = new Page1EnvioCheckoutMobil();
	private final Page2DatosPagoCheckoutMobil pg2MobilCheckout = new Page2DatosPagoCheckoutMobil();
	private final SecTarjetaPci secTarjetaPci = SecTarjetaPci.makeSecTarjetaPci(channel);
	
	//Abarca cualquier div de loading
	private static final String XP_DIV_LOADING = "//div[@class[contains(.,'panel_loading')] or @class[contains(.,'container-full-centered-loading')] or @class[contains(.,'loading-panel')]]";
	private static final String XP_DISCOUNT_LOYALTY_APPLIED_MOBIL = "//span[@class='redeem-likes__discount']";
	private static final String XP_BUTTON_FOR_APPLY_LOYALTY_POINTS = "//button[@data-testid='redeemLikesDesktop.applyDiscount.button']";	
	
	public Page1DktopCheckout getPage1DktopCheckout() {
		return pg1DktopCheckout;
	}
	public Page2DatosPagoCheckoutMobil getPage2MobilCheckout() {
		return pg2MobilCheckout;
	}
	public SecTarjetaPci getSecTarjetaPci() {
		return secTarjetaPci;
	}
	
	public boolean isFirstPageUntil(int seconds) {
		if (isMobile()) {
			return pg1MobilCheckout.isVisibleLink1EnvioUntil(seconds);
		}
		return pg1DktopCheckout.isPage(seconds);	
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
			pg1MobilCheckout.inputCodigoPromoAndAccept(codigoPromo);
		} else {
			pg1DktopCheckout.showInputCodigoPromoAndAccept(codigoPromo);
		}
	}
	
	public void clickEliminarValeIfExists() {
		if (isMobile()) {
			pg1MobilCheckout.clickEliminarValeIfExists();
		} else {
			pg1DktopCheckout.clickEliminarValeIfExists();
		}
	}
	
	public boolean isPresentInputApellidoPromoEmplUntil(int seconds) {
		if (isMobile()) {
			return (pg1MobilCheckout.isPresentInputApellidoPromoEmplUntil(seconds));
		}
		return (pg1DktopCheckout.isPresentInputApellidoPromoEmplUntil(seconds));
	}
	
	public boolean isPresentInputDNIPromoEmpl() {
		if (isMobile()) {
			return (pg1MobilCheckout.isPresentInputDNIPromoEmpl());
		}
		return (pg1DktopCheckout.isPresentInputDNIPromoEmpl());
	}	
	
	public boolean isPresentDiaNaciPromoEmpl() {
		if (isMobile()) {
			return (pg1MobilCheckout.isPresentDiaNaciPromoEmpl());
		}
		return (pg1DktopCheckout.isPresentDiaNaciPromoEmpl());
	}	
	
	public void inputDNIPromoEmpl(String dni) {
		if (isMobile()) {
			pg1MobilCheckout.inputDNIPromoEmpl(dni);
		} else {
			pg1DktopCheckout.inputDNIPromoEmpl(dni);
		}
	}
	
	public void inputApellidoPromoEmpl(String apellido) {
		if (isMobile()) {
			pg1MobilCheckout.inputApellidoPromoEmpl(apellido);
		} else {
			pg1DktopCheckout.inputApellidoPromoEmpl(apellido);
		}
	}	

	public boolean isVisibleButtonForApplyLoyaltyPoints() {
		return (state(VISIBLE, XP_BUTTON_FOR_APPLY_LOYALTY_POINTS).wait(2).check());
	}

	public float applyAndGetLoyaltyPoints() {
		var buttonLoyalty = getElementsVisible(XP_BUTTON_FOR_APPLY_LOYALTY_POINTS).get(0);
		String textButtonApply = buttonLoyalty.getAttribute("innerHTML");
		String importeButton = ImporteScreen.normalizeImportFromScreen(textButtonApply);
		click(XP_BUTTON_FOR_APPLY_LOYALTY_POINTS).exec();
		isNoDivLoadingUntil(1);
		return (ImporteScreen.getFloatFromImporteMangoScreen(importeButton));
	}

	public float getDiscountLoyaltyAppliedMobil() {
		if (state(VISIBLE, XP_DISCOUNT_LOYALTY_APPLIED_MOBIL).check()) {
			String discountApplied = getElement(XP_DISCOUNT_LOYALTY_APPLIED_MOBIL).getAttribute("innerHTML");
			return (ImporteScreen.getFloatFromImporteMangoScreen(discountApplied));
		}	
		return 0;
	}

	/**
	 * @param fechaNaci en formato "dd-mm-aaaa"
	 */
	public void selectFechaNacPromoEmpl(String fechaNaci) {
		if (isMobile()) {
			pg1MobilCheckout.selectFechaNacPromoEmpl(fechaNaci);
		} else {
			pg1DktopCheckout.selectFechaNacPromoEmpl(fechaNaci);
		}
	}
	
	public void clickGuardarPromo() {
		if (isMobile()) {
			pg1MobilCheckout.clickAceptarPromo();
		} else {
			pg1DktopCheckout.clickGuardarPromo();
		}
	}
	
	public void clickButtonAceptarPromoEmpl() {
		if (isMobile()) {
			pg1MobilCheckout.clickButtonAceptarPromoEmpl();
		} else {
			pg1DktopCheckout.clickGuardarPromo();
		}
	}
	
	public String getImporteDescuentoEmpleado() {
		if (isMobile()) {
			return pg1MobilCheckout.getImporteDescuentoEmpleado();
		}
		return pg1DktopCheckout.getImporteDescuentoEmpleado();
	}
	
	public boolean isVisibleDescuentoEmpleadoUntil(int seconds) {
		if (isMobile()) {
			return pg1MobilCheckout.isVisibleDescuentoEmpleadoUntil(seconds);
		}
		return pg1DktopCheckout.isVisibleDescuentoEmpleadoUntil(seconds); 
	}

	public boolean isNumMetodosPagoOK(boolean isEmpl) {
		if (isMobile()) {
			return pg2MobilCheckout.isNumMetodosPagoOK(isEmpl);
		}
		return pg1DktopCheckout.isNumMetodosPagoOK(isEmpl);
	}
	
	public boolean isNumpagos(int numPagosExpected) {
		if (isMobile()) {
			pg2MobilCheckout.isNumpagos(numPagosExpected);
		}
		return pg1DktopCheckout.isNumpagos(numPagosExpected);
	}
	
	public boolean isPresentMetodosPago() {
		if (isMobile()) {
			return pg2MobilCheckout.isPresentMetodosPago();
		}
		return pg1DktopCheckout.isPresentMetodosPago();
	}	
	
	public boolean isMetodoPagoPresent(String metodoPago) {
		if (isMobile()) {
			return pg2MobilCheckout.isMetodoPagoPresent(metodoPago);
		}
		return pg1DktopCheckout.isMetodoPagoPresent(metodoPago);
	}
	
	public void despliegaMetodosPago() {
		if (isMobile()) {
			pg2MobilCheckout.despliegaMetodosPago();
		} else {
			pg1DktopCheckout.despliegaMetodosPago();
		}
	}

	public String getPrecioTotalFromResumen(boolean normalize) {
		if (isMobile()) {
			return (pg2MobilCheckout.getPrecioTotalFromResumen(normalize));
		}
		return (pg1DktopCheckout.getPrecioTotalFromResumen(normalize));
	}
	
	public String getCroaciaPrecioTotalInEuros(boolean normalize) {
		if (isMobile()) {
			return pg2MobilCheckout.getCroaciaPrecioTotalInEuros(normalize);
		}
		return pg1DktopCheckout.getCroaciaPrecioTotalInEuros(normalize);
	}	
	
	public String getAlmacenFromNoProdEntorn() {
		if (isMobile()) {
			return "";
		}
		return (pg1DktopCheckout.getAlmacenFromNoProdEntorn()); 
	}

	public boolean waitUntilNoDivLoading(int seconds) {
		return state(INVISIBLE, XP_DIV_LOADING).wait(seconds).check();
	}

	public boolean isNoDivLoadingUntil(int seconds) {
		return state(INVISIBLE, XP_DIV_LOADING).wait(seconds).check();
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
			pg2MobilCheckout.forceClickMetodoPagoAndWait(metodoPago);
		} else {
			pg1DktopCheckout.forceClickMetodoPagoAndWait(metodoPago);
		}
	}
	
	public boolean isAvailableTrjGuardada(String metodoPago) {
		if (isMobile()) {
			return (pg2MobilCheckout.isVisibleRadioTrjGuardada(metodoPago));
		} else {
			return (pg1DktopCheckout.isVisibleRadioTrjGuardada(metodoPago));
		}
	}

	public void clickRadioTrjGuardada() {
		if (isMobile()) {
			pg2MobilCheckout.clickRadioTrjGuardada();
		} else {
			pg1DktopCheckout.clickRadioTrjGuardada();
		}
	}

	public void inputCvcTrjGuardadaIfVisible(String cvc) { 
		if (isMobile()) {
			pg2MobilCheckout.inputCvcTrjGuardadaIfVisible(cvc);
		} else {
			pg1DktopCheckout.inputCvcTrjGuardadaIfVisible(cvc);
		}
	}
	
	public void clickSolicitarFactura() {
		if (isMobile()) {
			pg2MobilCheckout.clickSolicitarFactura();
		} else {
			pg1DktopCheckout.clickSolicitarFactura();
		}
	}	
	
	public void clickEditDirecEnvio() {
		if (isMobile()) {
			pg1MobilCheckout.clickEditDirecEnvio();
		} else {
			pg1DktopCheckout.clickEditDirecEnvio();
		}
	}
	
	public boolean isArticulos() {
		if (isMobile()) {
			return (pg2MobilCheckout.isArticulos());
		}
		return (pg1DktopCheckout.isArticulos());
	}
	
	public float getSumProductImports() {
		if (isMobile()) {
			return pg2MobilCheckout.getSumPreciosArticles();
		}
		return pg1DktopCheckout.getSumPreciosArticles();
	}
	
	public float getImportSubtotal() {
		if (isMobile()) {
			return pg2MobilCheckout.getPrecioSubTotalFromResumen();
		}
		return pg1DktopCheckout.getPrecioSubTotalFromResumen();
	}
	
	public float getImportSubtotalRounded(int decimalPlace) {
		return UtilsMangoTest.round(getImportSubtotal(), decimalPlace);
	}
	
	public void confirmarPagoFromMetodos(DataPedido dataPedido) {
		getDataPedidoFromCheckout(dataPedido);
		if (isMobile()) {
			pg2MobilCheckout.confirmarPagoFromMetodos();
		} else {
			pg1DktopCheckout.confirmarPagoFromMetodos();
		}
	}
	
	public boolean isVisibleBloquePagoNoTRJIntegradaUntil(Pago pago, int seconds) {
		if (isMobile()) {
			return pg2MobilCheckout.isVisibleTextoBajoPagoUntil(pago, seconds);
		}
		return pg1DktopCheckout.isVisibleBloquePagoNoTRJIntegradaUntil(pago, seconds);
	}
	
	public String getTextDireccionEnvioCompleta() {
		if (isMobile()) {
			if (pg1MobilCheckout.isPage(0)) {
				return (pg1MobilCheckout.getTextDireccionEnvioCompleta());
			}
			return (pg2MobilCheckout.getTextDireccionEnvioCompleta());
		}
		return (pg1DktopCheckout.getTextDireccionEnvioCompleta());
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
			return pg1MobilCheckout.getTextDireccionEnvioCompleta();
		}
		return pg1DktopCheckout.getTextDireccionEnvioCompleta();
	}
	
	public boolean direcEnvioContainsPais(String nombrePais) {
		String direccionEnvio = getTextDireccionEnvioCompleta();
		return (direccionEnvio.toLowerCase().contains(nombrePais.toLowerCase()));
	}
	
	public boolean isPresentBlockMetodo(TipoTransporte tipoTransporte) {
		if (isMobile()) {
			return pg1MobilCheckout.isPresentBlockMetodo(tipoTransporte);
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
		float importeDescMinTeorico = preciosArtScreen.getOriginal() * (1 - (porcMinDiscountLessOne/100));
		return (preciosArtScreen.getDefinitivo() <= importeDescMinTeorico);
	}
	
	private boolean validateDiscountOverLastPriceOrSale(PreciosArticulo preciosArtScreen, int porcMinDiscount) {
		float porcMinDiscountLessOne = porcMinDiscount - 1f; //Restamos 1 por un tema de precisión
		float importeDescMinTeorico = preciosArtScreen.getUltimaRebaja() * (1 - (porcMinDiscountLessOne/100));
		return (preciosArtScreen.getDefinitivo() <= importeDescMinTeorico);
	}
	
	public void backPageMetodosPagos(String urlPagCheckout) {
		if (driver.getCurrentUrl().compareTo(urlPagCheckout)!=0) {
			driver.get(urlPagCheckout);
			acceptAlertIfExists(driver);
		}

		//En el caso de móvil existen 3 páginas de checkout y no tenemos claro si estamos en la de los métodos de pago 
		//así que si existe, clickamos el link a la página-2 del checkout con los métodos de pago		
		if (isMobile()) {
			pg2MobilCheckout.clickLink2DatosPagoIfVisible();
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
	
	public boolean isVisibleMessageErrorPago(int seconds) {
		if (isMobile()) {
			return pg2MobilCheckout.isVisibleMessageErrorPayment(seconds);
		} else {
			return pg1DktopCheckout.isVisibleMessageErrorPayment(seconds);
		}
	}

}