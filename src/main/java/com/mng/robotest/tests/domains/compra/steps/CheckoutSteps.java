package com.mng.robotest.tests.domains.compra.steps;

import java.util.StringTokenizer;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.chequeregalo.beans.ChequeRegalo;
import com.mng.robotest.tests.domains.compra.pageobjects.PageCheckoutWrapper;
import com.mng.robotest.tests.domains.compra.pageobjects.beans.DiscountLikes;
import com.mng.robotest.tests.domains.compra.payments.billpay.steps.SecBillpaySteps;
import com.mng.robotest.tests.domains.compra.payments.kredikarti.steps.SecKrediKartiSteps;
import com.mng.robotest.tests.domains.compra.steps.envio.SecMetodoEnvioSteps;
import com.mng.robotest.testslegacy.beans.AccesoEmpl;
import com.mng.robotest.testslegacy.beans.Pago;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.generic.UtilsMangoTest;

import static com.mng.robotest.testslegacy.beans.TypePago.*;
import static com.github.jorge2m.testmaker.conf.State.*;
import static com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen.*;

public class CheckoutSteps extends StepBase {

	private final PageCheckoutWrapper pgCheckoutWrapper = new PageCheckoutWrapper(); 
	private final ModalDirecEnvioOldSteps mdDirecEnvioSteps = new ModalDirecEnvioOldSteps();
	private final SecMetodoEnvioSteps secMetodoEnvioDesktopSteps = new SecMetodoEnvioSteps();
	private final SecKrediKartiSteps secKrediKartiSteps = new SecKrediKartiSteps();
	private final SecBillpaySteps secBillpaySteps = new SecBillpaySteps();
	private final ModalDirecFacturaSteps mdDirecFacturaSteps = new ModalDirecFacturaSteps();
	private final Page1DktopCheckoutSteps pg1DktopCheckSteps = new Page1DktopCheckoutSteps();
	private final Page1EnvioCheckoutMobilSteps pg1MobilCheckSteps = new Page1EnvioCheckoutMobilSteps();
	private final SecTarjetaPciSteps secTarjetaPciSteps = new SecTarjetaPciSteps();
	private final PageRedirectPasarelaLoadingSteps pgRedirectPasarelaLoadingSteps = new PageRedirectPasarelaLoadingSteps();
	
	public PageCheckoutWrapper getPageCheckoutWrapper() {
		return pgCheckoutWrapper;
	}
	public Page1EnvioCheckoutMobilSteps getPage1CheckoutMobilSteps() {
		return pg1MobilCheckSteps;
	}
	
	public ModalDirecEnvioOldSteps getModalDirecEnvioSteps() {
		return mdDirecEnvioSteps;
	}

	public SecBillpaySteps getSecBillpaySteps() {
		return secBillpaySteps;
	}
	public SecTarjetaPciSteps getSecTarjetaPciSteps() {
		return secTarjetaPciSteps;
	}
	public SecKrediKartiSteps getSecKrediKartiSteps() {
		return secKrediKartiSteps;
	}
	public ModalDirecFacturaSteps getModalDirecFacturaSteps() {
		return mdDirecFacturaSteps;
	}
	
	public void checkIsFirstPage(boolean userLogged) {
		if (isMobile()) {
			pg1MobilCheckSteps.checkIsPage(userLogged);
		} else {
			pg1DktopCheckSteps.checkIsPageOK();
		}
	} 

	public void goToMetodosPagoMobile() {
		pg1MobilCheckSteps.clickContinuarToMetodosPago();
	}
	
	@Step (
		description=
			"Seleccionar el botón \"Editar\" asociado a la Dirección de Envío", 
		expected=
			"Aparece modal multidirección (usr logado) o " + 
			"introducción de la dirección de envío (usr express)")
	public void clickEditarDirecEnvio() {
		pgCheckoutWrapper.clickEditDirecEnvio();
		if (!dataTest.isUserRegistered()) {
			mdDirecEnvioSteps.validateIsOk();
		} else {
			new ModalMultidirectionSteps().checkIsVisible(2);
		}
		checksDefault();
	}	
	
	@Validation (
		description="Acaba desapareciendo la capa de \"Cargando...\" " + SECONDS_WAIT,
		level=WARN)
	public boolean validateLoadingDisappears(int seconds) {
		waitMillis(200); //Damos tiempo a que aparezca la capa de "Cargando"
		return (pgCheckoutWrapper.isNoDivLoadingUntil(seconds));
	}
	
	void despliegaYValidaMetodosPago() {
		despliegaYValidaMetodosPago(false);
	}
	
	public void isCroatiaImportInBothCurrencies() throws Exception {
		isCroatiaImportInFn();
		isCroatiaImportInEuros();
	}
	
	@Validation
	public ChecksTM isCroatiaImportInFn() throws Exception {
		var checks = ChecksTM.getNew();
	 	String precioScreen = pgCheckoutWrapper.getPrecioTotalFromResumen(false);
	 	String importeStr = pgCheckoutWrapper.getPrecioTotalFromResumen(true);
	 	Float importe = Float.valueOf(importeStr.replace(",", "."));
	 	
	 	checks.add(
			"El precio (" + precioScreen + ") existe y es > 0",
			(importe!=null && importe>0));
		
	 	checks.add(
			"El precio contiene la divisa <b>Kn</b>",
			precioScreen.contains("Kn"));
	 	
		return checks;
	}
	
	@Validation
	public ChecksTM isCroatiaImportInEuros() throws Exception {
		var checks = ChecksTM.getNew();
	 	String precioScreenEuros = pgCheckoutWrapper.getCroaciaPrecioTotalInEuros(false);
	 	String importeStrEuros = pgCheckoutWrapper.getCroaciaPrecioTotalInEuros(true);
	 	Float importeEuros = Float.valueOf(importeStrEuros.replace(",", "."));
	 	
	 	checks.add(
			"El precio (" + precioScreenEuros + ") existe y es > 0",
			(importeEuros!=null && importeEuros>0));
		
	 	checks.add(
			"El precio contiene la divisa <b>€</b>",
			precioScreenEuros.contains("€"));
	 	
	 	return checks;
	}
	
	@Step (
		description="Si existen y están plegados, desplegamos el bloque con los métodos de pago", 
		expected="Aparecen los métodos de pagos asociados al país")
	public void despliegaYValidaMetodosPago(boolean isEmpl) {
		setStepDescription(getStepDescription() + ": " + dataTest.getPais().getStringPagosTest(app, isEmpl));
		pgCheckoutWrapper.despliegaMetodosPago();
		validaMetodosPagoDisponibles(isEmpl);
	}
	
	public void validaMetodosPagoDisponibles(boolean isEmpl) {
		checkAvailablePagos(isEmpl);
		checkLogosPagos(isEmpl);
	}
	
	@Validation
	private ChecksTM checkAvailablePagos(boolean isEmpl) {
		var checks = ChecksTM.getNew();
	 	checks.add(
			"El número de pagos disponibles, logos tarjetas, coincide con el de asociados al país " + 
			"(" + dataTest.getPais().getListPagosForTest(app, isEmpl).size() + ")",
			pgCheckoutWrapper.isNumMetodosPagoOK(isEmpl), WARN);	
	 	
		return checks;
	}
	
	@Validation
	private ChecksTM checkLogosPagos(boolean isEmpl) { 
		var checks = ChecksTM.getNew();
		var listPagos = dataTest.getPais().getListPagosForTest(app, isEmpl);
		if (listPagos.size()==1 && channel.isDevice()) {
			return checks;
		}
		for (int i=0; i<listPagos.size(); i++) {
			String pagoNameExpected = listPagos.get(i).getNombre(channel, app);
		 	checks.add(
				"Aparece el logo/pestaña asociado al pago <b>" + pagoNameExpected + "</b>",
				pgCheckoutWrapper.isMetodoPagoPresent(pagoNameExpected));	
		}   
		return checks;
	}
	
	/**
	 * Realiza una navegación (conjunto de pasos/validaciones) mediante la que se selecciona el método de envío y finalmente el método de pago 
	 */
	public void selectDeliveryAndClickPaymentMethod() throws Exception {
		boolean pagoPintado = false;
		var dataPago = dataTest.getDataPago();
		if (dataPago.isSelectEnvioType() &&
			!dataPago.getFTCkout().chequeRegalo) {
			pagoPintado = secMetodoEnvioDesktopSteps.fluxSelectEnvio();
		}
		boolean methodSelectedOK = forceClickIconoPagoAndWait(dataPago.getDataPedido().getPago(), !pagoPintado);
		if (!methodSelectedOK) {
			//En caso de no conseguir seleccionar correctamente el pago no nos podemos arriesgar a continuar con el pago
			//porque quizás esté seleccionado otro método de pago del tipo Contrareembolso y un "Confirmar Pago" desencadenaría la compra en PRO
			throw new RuntimeException("Problem selecting payment method " + dataPago.getDataPedido().getPago().getNombre() + " in country " + dataTest.getPais().getNombrePais());
		}
	}
	
	/**
	 * Paso consistente en clickar un determinado método de pago de la página de resumen de artículos (precompra)
	 */
	@Step (
		description="Seleccionamos el icono/pestaña correspondiente al método de pago y esperamos la desaparición de los \"loading\"",
		expected="La operación se ejecuta correctamente")
	public boolean forceClickIconoPagoAndWait(Pago pago, boolean pintaNombrePago) {
		if (pintaNombrePago) {
			String pintaPago = "<b style=\"color:blue;\">" + pago.getNombre(channel, app) + "</b>:"; 
			setStepDescription(pintaPago + getStepDescription());
		}

		try {
			pgCheckoutWrapper.forceClickMetodoPagoAndWait(pago.getNombre(channel, app));
			checksDefault();
		}
		catch (Exception e) {
			Log4jTM.getLogger().warn("Problem clicking icono pago for payment {} in country {}", pago.getNombre(), dataTest.getPais().getNombrePais(), e);
		}

		if (pago.getTypePago()==TARJETA_INTEGRADA || 
			pago.getTypePago()==KREDI_KARTI ||
			pago.getTypePago()==BANCONTACT) {
			checkSelectPagoTRJintegrada(pago);
			return true;
		} else {
			return validateSelectPagoNoTRJintegrada(pago);
		}
	}
	
	public void checkSelectPagoTRJintegrada(Pago pago) {
		if (!isMobile()) {
			checkIsPresentButtonCompraDesktop();
		}
		getSecTarjetaPciSteps().checkIsSectionOk(pago);
	}
	
	public boolean validateSelectPagoNoTRJintegrada(Pago pago) {
		if (!isMobile()) {
			checkIsPresentButtonCompraDesktop();
		}
		return checkIsVisibleTextUnderPayment(pago.getNombreInCheckout(channel, app), pago, 2);
	}
	
	@Validation (
		description="Se hace visible el texto bajo el método de pago: #{nombrePago} " + SECONDS_WAIT)
	private boolean checkIsVisibleTextUnderPayment(String nombrePago, Pago pago, int seconds) {
		return pgCheckoutWrapper.isVisibleBloquePagoNoTRJIntegradaUntil(pago, seconds);
	}
	
	@Validation (description="Aparece el botón de \"Confirmar Compra\"")
	public boolean checkIsPresentButtonCompraDesktop() {
		return pgCheckoutWrapper.getPage1DktopCheckout().isPresentButtonConfPago();
	}
	
	@Step(description="Seleccionamos el checkbox para grabar la tarjeta", expected="")
	public void selectSaveCard() {
		pgCheckoutWrapper.selectSaveCard();
	}
	
	private static final String TAG_TIPO_TARJ = "@TagTipoTarj";
	private static final String TAG_NUM_TARJ = "@TagNumTarj";
	@Step (
		description="Introducimos los datos de la tarjeta (" + TAG_TIPO_TARJ + ") " + TAG_NUM_TARJ + " y pulsamos el botón \"Confirmar pago\"",
		expected="Aparece la página de resultado OK")
	public void inputDataTrjAndConfirmPago() {
		var dataPago = dataTest.getDataPago();
		Pago pago = dataPago.getDataPedido().getPago();
		replaceStepDescription(TAG_TIPO_TARJ, pago.getTipotarj());
		replaceStepDescription(TAG_NUM_TARJ, pago.getNumtarj());
	   
		if (pago.getNumtarj()!=null && "".compareTo(pago.getNumtarj())!=0) {
			pgCheckoutWrapper.inputNumberPci(pago.getNumtarj());
		}
		pgCheckoutWrapper.inputTitularPci(pago.getTitular());
		if (pago.getMescad()!=null && "".compareTo(pago.getMescad())!=0) {
			pgCheckoutWrapper.selectMesByVisibleTextPci(pago.getMescad());
		}
		if (pago.getAnycad()!=null && "".compareTo(pago.getAnycad())!=0) {
			pgCheckoutWrapper.selectAnyByVisibleTextPci(pago.getAnycad());
		}
		if (pago.getCvc()!=null && "".compareTo(pago.getCvc())!=0) {
			pgCheckoutWrapper.inputCvcPci(pago.getCvc());
		}
		if (pago.getDni()!=null && "".compareTo(pago.getDni())!=0) {
			pgCheckoutWrapper.inputDniPci(pago.getDni());   
		}

		pgCheckoutWrapper.confirmarPagoFromMetodos(dataPago.getDataPedido());
		pgRedirectPasarelaLoadingSteps.checkDisappeared(5);
	}

	@Validation (
		description="Está disponible una tarjeta guardada de tipo #{tipoTarjeta}",
		level=WARN)
	public boolean isTarjetaGuardadaAvailable(String tipoTarjeta) {
		return (pgCheckoutWrapper.isAvailableTrjGuardada(tipoTarjeta));
	}
	
	@Step (
		description="Seleccionamos la tarjeta guardada, si nos lo pide introducimos el cvc #{cvc} y pulsamos el botón \"Confirmar pago\"",
		expected="Aparece la página de resultado OK")
	public void selectTrjGuardadaAndConfirmPago(String cvc) {
		pgCheckoutWrapper.clickRadioTrjGuardada();
		pgCheckoutWrapper.inputCvcTrjGuardadaIfVisible(cvc);
		pgCheckoutWrapper.confirmarPagoFromMetodos(dataTest.getDataPago().getDataPedido());
		pgRedirectPasarelaLoadingSteps.checkDisappeared(5);
	}

	@Step (
		description="Seleccionar el radiobutton \"Quiero recibir una factura\"", 
		expected="Aparece el modal para la introducción de la dirección de facturación")
	public void clickSolicitarFactura() {
		pgCheckoutWrapper.clickSolicitarFactura();
		mdDirecFacturaSteps.validateIsOk();
	}

	@Step (
		description="Seleccionamos el botón \"Confirmar Pago\"", 
		expected="Aparece una pasarela de pago",
		saveImagePage=ALWAYS)
	public void pasoBotonAceptarCompraDesktop() {
		pgCheckoutWrapper.getPage1DktopCheckout().clickConfirmarPago();
	}	  
	
	@Validation (
		description="Aparece el botón de \"Confirmar Pago\" " + SECONDS_WAIT,
		level=WARN)
	private boolean checkAfterClickVerResumen(int seconds) {
		return (pgCheckoutWrapper.getPage2MobilCheckout().isClickableButtonFinalizarCompraUntil(seconds));
	}
			
	@Step (
		description="Seleccionamos el botón \"Finalizar Compra\" (previamente esperamos hasta 20 segundos a que desaparezca la capa \"Espera unos segundos...\")", 
		expected="Aparece una pasarela de pago",
		saveImagePage=ALWAYS)
	public void pasoBotonConfirmarPagoCheckout3Mobil() {
		try {
			pgCheckoutWrapper.getPage2MobilCheckout().clickFinalizarCompraAndWait(20);
		}
		catch (Exception e) {
			Log4jTM.getLogger().warn("Problem in click Confirm payment button", e);
		}
		pgRedirectPasarelaLoadingSteps.checkDisappeared(5);
	}	
	
	private static final String TAG_TARJETA = "@TagTarjeta";
	
	@Step (
		description="Introducir la tarjeta de empleado " + TAG_TARJETA + " y pulsar el botón \"Aplicar\"", 
		expected="Aparecen los datos para la introducción del 1er apellido y el nif")
	public void inputTarjetaEmplEnCodPromo(Pais pais, AccesoEmpl accesoEmpl) {
		replaceStepDescription(TAG_TARJETA, accesoEmpl.getTarjeta());
		pgCheckoutWrapper.inputCodigoPromoAndAccept(accesoEmpl.getTarjeta());
		checkAfterInputTarjetaEmpleado(pais, accesoEmpl);
		checksDefault();
	}
	
	@Validation
	private ChecksTM checkAfterInputTarjetaEmpleado(Pais pais, AccesoEmpl accesoEmpl) {
		var checks = ChecksTM.getNew();
		int seconds = 7;
	 	checks.add(
			"Aparece el campo de introducción del primer apellido " + getLitSecondsWait(seconds),
			pgCheckoutWrapper.isPresentInputApellidoPromoEmplUntil(seconds));
		
		boolean isPresentInputDni = pgCheckoutWrapper.isPresentInputDNIPromoEmpl();
		if (accesoEmpl.getNif()!=null) {
		 	checks.add(
				"Aparece el campo de introducción del DNI/Pasaporte",
				isPresentInputDni);
		} else {
		 	checks.add(
				"Noparece el campo de introducción del DNI/Pasaporte",
				!isPresentInputDni);
		}
		
		boolean isPresentInputFechaNac = pgCheckoutWrapper.isPresentDiaNaciPromoEmpl();
	 	checks.add(
			"No aparece el campo de introducción de la fecha de nacimiento",
			!isPresentInputFechaNac);	
		
		return checks;
	}
	
	private static final String TAG_1ER_APELLIDO = "@Tag1erApellido";
	@Step (
		description="Introducir el primer apellido " + TAG_1ER_APELLIDO + " y pulsar el botón \"Guardar\"", 
		expected="Se aplican los descuentos correctamente")
	public void inputDataEmplEnPromoAndAccept(AccesoEmpl accesoEmpl) {
		String primerApellido = (new StringTokenizer(accesoEmpl.getNombre(), " ")).nextToken();
		replaceStepDescription(TAG_1ER_APELLIDO, primerApellido);
		
		if (accesoEmpl.getNif()!=null) {
			getCurrentStep().addRightDescriptionText("Introducir el NIF del usuario " + accesoEmpl.getNif() + ". ");
			pgCheckoutWrapper.inputDNIPromoEmpl(accesoEmpl.getNif());
		}
		pgCheckoutWrapper.inputApellidoPromoEmpl(primerApellido);
		pgCheckoutWrapper.clickButtonAceptarPromoEmpl();
		
		validaResultImputPromoEmpl();
	}
		
	public void selectSeguirDeShopping() {
		new PageResultPagoSteps().selectSeguirDeShopping();
	}
	
	public void validaResultImputPromoEmpl() {
		if (isMobile()) {
			pg1MobilCheckSteps.validaResultImputPromoEmpl();
		} else {
			pg1DktopCheckSteps.validaResultImputPromoEmpl();
		}
	}	
	
	public void validaIsVersionChequeRegalo(ChequeRegalo chequeRegalo) {
		pg1DktopCheckSteps.validateIsVersionChequeRegalo(chequeRegalo);
	}
	
	private static final String TAG_NOMBRE_BANCO = "@TagNombreBanco";
	@Step (
		description="Escogemos el banco \"" + TAG_NOMBRE_BANCO + "\" en la pestaña de selección", 
		expected="El banco aparece seleccionado")
	public void selectBancoEPS() {
		String nombreBanco = "Easybank";
		if (!isPRO()) {
			nombreBanco = "Test Issuer";
		}
		replaceStepDescription(TAG_NOMBRE_BANCO, nombreBanco);
			
		pgCheckoutWrapper.selectBancoEPS(nombreBanco);
		checkIsVisibleBank(nombreBanco);
	}
	
	@Validation (description="Aparece el banco \"#{ombreBanco}\" en el cuadro de selección")
	private boolean checkIsVisibleBank(String nombreBanco) {
		return pgCheckoutWrapper.isBancoSeleccionado(nombreBanco);
	}

	@Validation (description="Aparece el botón que permite aplicar los Loyalty Points")
	public boolean checkBlockLoyalty() {
		return pgCheckoutWrapper.isVisibleButtonForApplyLoyaltyPoints();
	}
	
	@Step (
		description="Seleccionamos el botón para aplicar el descuento de Loyalty Points",
		expected="Se aplica correctamente el descuento")
	public DiscountLikes loyaltyPointsApply() {
		if (isMobile()) {
			return loyaltyPointsApplyMobil();
		} else {
			return loyaltyPointsApplyDesktop();
		}
	}
	
	public DiscountLikes loyaltyPointsApplyMobil() {
		var dataLikesDiscount = pgCheckoutWrapper.applyLoyaltyPoints();
		checkLoyaltyPointsDiscountMobilUntil(dataLikesDiscount.getDiscount(), 3);
		return dataLikesDiscount;
	}
	
	public DiscountLikes loyaltyPointsApplyDesktop() {
		float subTotalInicial = pgCheckoutWrapper.getImportSubtotalRounded(2);
		var dataLikesDiscount = pgCheckoutWrapper.applyLoyaltyPoints();
		checkLoyaltyPointsDiscountDesktopUntil(dataLikesDiscount.getDiscount(), subTotalInicial, 3);
		checksDefault();
		return dataLikesDiscount;
	}
	
	@Validation (
		description=
			"Se aplica el descuento de <b>#{descuento}</b> al subtotal inicial de #{subtotalInicial} " + 
			SECONDS_WAIT)
	public boolean checkLoyaltyPointsDiscountDesktopUntil(
			float descuento, float subtotalInicial, int seconds) {
		
		for (int i=0; i<seconds; i++) {
			float subTotalActual = pgCheckoutWrapper.getImportSubtotal();
			float estimado = UtilsMangoTest.round(subtotalInicial - descuento, 2);
			if (estimado == subTotalActual) {
				return true;
			}
			waitMillis(1000);
		}
		return false;
	}
	
	@Validation(description="Aparece un descuento aplicado de #{descuento} " + SECONDS_WAIT)
	public boolean checkLoyaltyPointsDiscountMobilUntil(float descuento, int seconds) {
		for (int i=0; i<seconds; i++) {
			float discountApplied = UtilsMangoTest.round(pgCheckoutWrapper.getDiscountLoyaltyAppliedMobil(), 2);
			if (discountApplied == descuento) {
				return true;
			}
			waitMillis(1000);
		}
		return false;
	}
	
	@Validation(description="Aparece un mensaje de error de <b>Pago no completado</b> " + SECONDS_WAIT)
	public boolean isVisibleMessageErrorPayment(int seconds) {
		return pgCheckoutWrapper.isVisibleMessageErrorPago(seconds);
	}
	
	public boolean checkTotalImport() {
		float sumImportProducts = pgCheckoutWrapper.getSumProductImports();
		float subtotal = pgCheckoutWrapper.getImportSubtotal();
		return checkTotalImport(subtotal, sumImportProducts);
	}
	
	@Validation(description=
		"Cuadra el subtotal <b>#{subTotal}</b> con " + 
		"la suma de los importes de los productos <b>#{sumImportProducts}</b>")
	private boolean checkTotalImport(float subTotal, float sumImportProducts) {
		return subTotal == sumImportProducts;
	}

}