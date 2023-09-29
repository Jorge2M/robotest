package com.mng.robotest.tests.domains.compra.steps;

import java.util.List;
import java.util.StringTokenizer;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.chequeregalo.beans.ChequeRegalo;
import com.mng.robotest.tests.domains.compra.pageobjects.PageCheckoutWrapper;
import com.mng.robotest.tests.domains.compra.payments.billpay.steps.SecBillpaySteps;
import com.mng.robotest.tests.domains.compra.payments.ideal.steps.SecIdealSteps;
import com.mng.robotest.tests.domains.compra.payments.kredikarti.steps.SecKrediKartiSteps;
import com.mng.robotest.tests.domains.compra.payments.tmango.steps.SecTMangoSteps;
import com.mng.robotest.tests.domains.compra.steps.envio.SecMetodoEnvioSteps;
import com.mng.robotest.testslegacy.beans.AccesoEmpl;
import com.mng.robotest.testslegacy.beans.Pago;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.beans.TypePago;
import com.mng.robotest.testslegacy.datastored.DataPago;
import com.mng.robotest.testslegacy.generic.UtilsMangoTest;

import static com.github.jorge2m.testmaker.conf.State.*;

public class CheckoutSteps extends StepBase {

	private final PageCheckoutWrapper pageCheckoutWrapper = new PageCheckoutWrapper(); 
	private final ModalDirecEnvioOldSteps modalDirecEnvioSteps = new ModalDirecEnvioOldSteps();
	private final SecMetodoEnvioSteps secMetodoEnvioDesktopSteps = new SecMetodoEnvioSteps();
	private final SecTMangoSteps secTMangoSteps = new SecTMangoSteps();
	private final SecKrediKartiSteps secKrediKartiSteps = new SecKrediKartiSteps();
	private final SecBillpaySteps secBillpaySteps = new SecBillpaySteps();
	private final ModalDirecFacturaSteps modalDirecFacturaSteps = new ModalDirecFacturaSteps();
	private final ModalAvisoCambioPaisSteps modalAvisoCambioPaisSteps = new ModalAvisoCambioPaisSteps();
	private final Page1DktopCheckoutSteps page1DktopCheckSteps = new Page1DktopCheckoutSteps();
	private final Page1EnvioCheckoutMobilSteps page1MobilCheckSteps = new Page1EnvioCheckoutMobilSteps();
	private final SecIdealSteps secIdealSteps = new SecIdealSteps();
	private final SecTarjetaPciSteps secTarjetaPciSteps = new SecTarjetaPciSteps();
	private final PageRedirectPasarelaLoadingSteps pageRedirectPasarelaLoadingSteps = new PageRedirectPasarelaLoadingSteps();
	
	public PageCheckoutWrapper getPageCheckoutWrapper() {
		return pageCheckoutWrapper;
	}
	public Page1EnvioCheckoutMobilSteps getPage1CheckoutMobilSteps() {
		return page1MobilCheckSteps;
	}
	
	public ModalDirecEnvioOldSteps getModalDirecEnvioSteps() {
		return modalDirecEnvioSteps;
	}
	public SecIdealSteps getSecIdealSteps() {
		return secIdealSteps;
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
	public SecTMangoSteps getSecTMangoSteps() {
		return secTMangoSteps;
	}
	public ModalDirecFacturaSteps getModalDirecFacturaSteps() {
		return modalDirecFacturaSteps;
	}
	public ModalAvisoCambioPaisSteps getModalAvisoCambioPaisSteps() {
		return modalAvisoCambioPaisSteps;
	}
	
	public void validateIsFirstPage(boolean userLogged) {
		if (isMobile()) {
			page1MobilCheckSteps.validateIsPage(userLogged);
		} else {
			page1DktopCheckSteps.validateIsPageOK();
		}
	} 

	public void goToMetodosPagoMobile() {
		page1MobilCheckSteps.clickContinuarToMetodosPago();
	}
	
	@Step (
		description=
			"Seleccionar el botón \"Editar\" asociado a la Dirección de Envío", 
		expected=
			"Aparece modal multidirección (usr logado) o " + 
			"introducción de la dirección de envío (usr express)")
	public void clickEditarDirecEnvio() {
		pageCheckoutWrapper.clickEditDirecEnvio();
		if (!dataTest.isUserRegistered()) {
			modalDirecEnvioSteps.validateIsOk();
		} else {
			new ModalMultidirectionSteps().checkIsVisible(2);
		}
		checksDefault();
	}	
	
	@Validation (
		description="Acaba desapareciendo la capa de \"Cargando...\" " + SECONDS_WAIT,
		level=Warn)
	public boolean validateLoadingDisappears(int seconds) {
		waitMillis(200); //Damos tiempo a que aparezca la capa de "Cargando"
		return (pageCheckoutWrapper.isNoDivLoadingUntil(seconds));
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
	 	String precioScreen = pageCheckoutWrapper.getPrecioTotalFromResumen(false);
	 	String importeStr = pageCheckoutWrapper.getPrecioTotalFromResumen(true);
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
	 	String precioScreenEuros = pageCheckoutWrapper.getCroaciaPrecioTotalInEuros(false);
	 	String importeStrEuros = pageCheckoutWrapper.getCroaciaPrecioTotalInEuros(true);
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
		TestMaker.getCurrentStepInExecution().addExpectedText(": " + dataTest.getPais().getStringPagosTest(app, isEmpl));
		pageCheckoutWrapper.despliegaMetodosPago();
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
			pageCheckoutWrapper.isNumMetodosPagoOK(isEmpl), Warn);		
		return checks;
	}
	
	@Validation
	private ChecksTM checkLogosPagos(boolean isEmpl) { 
		var checks = ChecksTM.getNew();
		List<Pago> listPagos = dataTest.getPais().getListPagosForTest(app, isEmpl);
		if (listPagos.size()==1 && channel.isDevice()) {
			return checks;
		}
		for (int i=0; i<listPagos.size(); i++) {
			if (listPagos.get(i).getTypePago()!=TypePago.TPV_VOTF) {
				String pagoNameExpected = listPagos.get(i).getNombre(channel, app);
			 	checks.add(
					"Aparece el logo/pestaña asociado al pago <b>" + pagoNameExpected + "</b>",
					pageCheckoutWrapper.isMetodoPagoPresent(pagoNameExpected));	
			}
		}   
		
		return checks;
	}
	
	@Step (
		description="<b>#{nombrePagoTpvVOTF}</b>: no clickamos el icono pues no existe", 
		expected="No aplica")
	public void noClickIconoVotf(String nombrePagoTpvVOTF) throws Exception {		
		//No hacemos nada pues el pago con TPV en VOTF no tiene icono ni pasarelas asociadas
	}
	
	/**
	 * Realiza una navegación (conjunto de pasos/validaciones) mediante la que se selecciona el método de envío y finalmente el método de pago 
	 */
	public void fluxSelectEnvioAndClickPaymentMethod(DataPago dataPago) throws Exception {
		boolean pagoPintado = false;
		if (!dataPago.getFTCkout().chequeRegalo) {
			pagoPintado = secMetodoEnvioDesktopSteps.fluxSelectEnvio(dataPago);
		}
		boolean methodSelectedOK = forceClickIconoPagoAndWait(dataPago.getDataPedido().getPago(), !pagoPintado);
		if (!methodSelectedOK) {
			//En caso de no conseguir seleccionar correctamente el pago no nos podemos arriesgar a continuar con el pago
			//porque quizás esté seleccionado otro método de pago del tipo Contrareembolso y un "Confirmar Pago" desencadenaría la compra en PRO
			throw new RuntimeException("Problem selecting payment method " + dataPago.getDataPedido().getPago().getNombre() + " in country " + dataTest.getPais().getNombre_pais());
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
			StepTM step = TestMaker.getCurrentStepInExecution();
			String newDescription = pintaPago + step.getDescripcion();
			step.setDescripcion(newDescription);
		}

		try {
			pageCheckoutWrapper.forceClickMetodoPagoAndWait(pago.getNombre(channel, app));
			checksDefault();
		}
		catch (Exception e) {
			Log4jTM.getLogger().warn("Problem clicking icono pago for payment {} in country {}", pago.getNombre(), dataTest.getPais().getNombre_pais(), e);
		}

		if (pago.getTypePago()==TypePago.TARJETA_INTEGRADA || 
			pago.getTypePago()==TypePago.KREDI_KARTI ||
			pago.getTypePago()==TypePago.BANCONTACT) {
			validateSelectPagoTRJintegrada(pago);
			return true;
		} else {
			return validateSelectPagoNoTRJintegrada(pago);
		}
	}
	
	public void validateSelectPagoTRJintegrada(Pago pago) {
		if (!isMobile()) {
			validateIsPresentButtonCompraDesktop();
		}
		getSecTarjetaPciSteps().validateIsSectionOk(pago);
	}
	
	public boolean validateSelectPagoNoTRJintegrada(Pago pago) {
		if (!isMobile()) {
			validateIsPresentButtonCompraDesktop();
		}
		return checkIsVisibleTextUnderPayment(pago.getNombreInCheckout(channel, app), pago, 2);
	}
	
	@Validation (
		description="Se hace visible el texto bajo el método de pago: #{nombrePago} " + SECONDS_WAIT)
	private boolean checkIsVisibleTextUnderPayment(String nombrePago, Pago pago, int seconds) {
		return (pageCheckoutWrapper.isVisibleBloquePagoNoTRJIntegradaUntil(pago, seconds));
	}
	
	@Validation (description="Aparece el botón de \"Confirmar Compra\"")
	public boolean validateIsPresentButtonCompraDesktop() {
		return (pageCheckoutWrapper.getPage1DktopCheckout().isPresentButtonConfPago());
	}
	
	private static final String TAG_TIPO_TARJ = "@TagTipoTarj";
	private static final String TAG_NUM_TARJ = "@TagNumTarj";
	@Step (
		description="Introducimos los datos de la tarjeta (" + TAG_TIPO_TARJ + ") " + TAG_NUM_TARJ + " y pulsamos el botón \"Confirmar pago\"",
		expected="Aparece la página de resultado OK")
	public void inputDataTrjAndConfirmPago(DataPago dataPago) {
		Pago pago = dataPago.getDataPedido().getPago();
		StepTM step = TestMaker.getCurrentStepInExecution();
		step.replaceInDescription(TAG_TIPO_TARJ, pago.getTipotarj());
		step.replaceInDescription(TAG_NUM_TARJ, pago.getNumtarj());
	   
		if (pago.getNumtarj()!=null && "".compareTo(pago.getNumtarj())!=0) {
			pageCheckoutWrapper.inputNumberPci(pago.getNumtarj());
		}
		pageCheckoutWrapper.inputTitularPci(pago.getTitular());
		if (pago.getMescad()!=null && "".compareTo(pago.getMescad())!=0) {
			pageCheckoutWrapper.selectMesByVisibleTextPci(pago.getMescad());
		}
		if (pago.getAnycad()!=null && "".compareTo(pago.getAnycad())!=0) {
			pageCheckoutWrapper.selectAnyByVisibleTextPci(pago.getAnycad());
		}
		if (pago.getCvc()!=null && "".compareTo(pago.getCvc())!=0) {
			pageCheckoutWrapper.inputCvcPci(pago.getCvc());
		}
		if (pago.getDni()!=null && "".compareTo(pago.getDni())!=0) {
			pageCheckoutWrapper.inputDniPci(pago.getDni());   
		}

		pageCheckoutWrapper.confirmarPagoFromMetodos(dataPago.getDataPedido());
		pageRedirectPasarelaLoadingSteps.validateDisappeared(5);
	}

	@Validation (
		description="Está disponible una tarjeta guardada de tipo #{tipoTarjeta}",
		level=Warn)
	public boolean isTarjetaGuardadaAvailable(String tipoTarjeta) {
		return (pageCheckoutWrapper.isAvailableTrjGuardada(tipoTarjeta));
	}
	
	@Step (
		description="Seleccionamos la tarjeta guardada, si nos lo pide introducimos el cvc #{cvc} y pulsamos el botón \"Confirmar pago\"",
		expected="Aparece la página de resultado OK")
	public void selectTrjGuardadaAndConfirmPago(DataPago dataPago, String cvc) {
		pageCheckoutWrapper.clickRadioTrjGuardada();
		pageCheckoutWrapper.inputCvcTrjGuardadaIfVisible(cvc);
		pageCheckoutWrapper.confirmarPagoFromMetodos(dataPago.getDataPedido());
		pageRedirectPasarelaLoadingSteps.validateDisappeared(5);
	}

	@Step (
		description="Seleccionar el radiobutton \"Quiero recibir una factura\"", 
		expected="Aparece el modal para la introducción de la dirección de facturación")
	public void clickSolicitarFactura() {
		pageCheckoutWrapper.clickSolicitarFactura();
		modalDirecFacturaSteps.validateIsOk();
	}

	@Step (
		description="Seleccionamos el botón \"Confirmar Pago\"", 
		expected="Aparece una pasarela de pago",
		saveImagePage=SaveWhen.Always)
	public void pasoBotonAceptarCompraDesktop() {
		pageCheckoutWrapper.getPage1DktopCheckout().clickConfirmarPago();
	}	  
	
	@Validation (
		description="Aparece el botón de \"Confirmar Pago\" " + SECONDS_WAIT,
		level=Warn)
	private boolean checkAfterClickVerResumen(int seconds) {
		return (pageCheckoutWrapper.getPage2MobilCheckout().isClickableButtonFinalizarCompraUntil(seconds));
	}
			
	@Step (
		description="Seleccionamos el botón \"Finalizar Compra\" (previamente esperamos hasta 20 segundos a que desaparezca la capa \"Espera unos segundos...\")", 
		expected="Aparece una pasarela de pago",
		saveImagePage=SaveWhen.Always)
	public void pasoBotonConfirmarPagoCheckout3Mobil() {
		try {
			pageCheckoutWrapper.getPage2MobilCheckout().clickFinalizarCompraAndWait(20);
		}
		catch (Exception e) {
			Log4jTM.getLogger().warn("Problem in click Confirm payment button", e);
		}
							
		pageRedirectPasarelaLoadingSteps.validateDisappeared(5);
	}	
	
	private static final String TAG_TARJETA = "@TagTarjeta";
	
	@Step (
		description="Introducir la tarjeta de empleado " + TAG_TARJETA + " y pulsar el botón \"Aplicar\"", 
		expected="Aparecen los datos para la introducción del 1er apellido y el nif")
	public void inputTarjetaEmplEnCodPromo(Pais pais, AccesoEmpl accesoEmpl) {
		TestMaker.getCurrentStepInExecution().replaceInDescription(TAG_TARJETA, accesoEmpl.getTarjeta());
		pageCheckoutWrapper.inputCodigoPromoAndAccept(accesoEmpl.getTarjeta());
		checkAfterInputTarjetaEmpleado(pais, accesoEmpl);
		checksDefault();
	}
	
	@Validation
	private ChecksTM checkAfterInputTarjetaEmpleado(Pais pais, AccesoEmpl accesoEmpl) {
		var checks = ChecksTM.getNew();
		int seconds = 5;
	 	checks.add(
			"Aparece el campo de introducción del primer apellido " + getLitSecondsWait(seconds),
			pageCheckoutWrapper.isPresentInputApellidoPromoEmplUntil(seconds));
		
		boolean isPresentInputDni = pageCheckoutWrapper.isPresentInputDNIPromoEmpl();
		if (accesoEmpl.getNif()!=null) {
		 	checks.add(
				"Aparece el campo de introducción del DNI/Pasaporte",
				isPresentInputDni);
		} else {
		 	checks.add(
				"Noparece el campo de introducción del DNI/Pasaporte",
				!isPresentInputDni);
		}
		
		boolean isPresentInputFechaNac = pageCheckoutWrapper.isPresentDiaNaciPromoEmpl();
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
		StepTM step = TestMaker.getCurrentStepInExecution();
		String primerApellido = (new StringTokenizer(accesoEmpl.getNombre(), " ")).nextToken();
		step.replaceInDescription(TAG_1ER_APELLIDO, primerApellido);
		
		if (accesoEmpl.getNif()!=null) {
			step.addRightDescriptionText("Introducir el NIF del usuario " + accesoEmpl.getNif() + ". ");
			pageCheckoutWrapper.inputDNIPromoEmpl(accesoEmpl.getNif());
		}
		pageCheckoutWrapper.inputApellidoPromoEmpl(primerApellido);
		pageCheckoutWrapper.clickButtonAceptarPromoEmpl();
		
		validaResultImputPromoEmpl();
	}
		
	public void validaResultImputPromoEmpl() {
		if (isMobile()) {
			page1MobilCheckSteps.validaResultImputPromoEmpl();
		} else {
			page1DktopCheckSteps.validaResultImputPromoEmpl();
		}
	}	
	
	public void validaIsVersionChequeRegalo(ChequeRegalo chequeRegalo) {
		page1DktopCheckSteps.validateIsVersionChequeRegalo(chequeRegalo);
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
		TestMaker.getCurrentStepInExecution().replaceInDescription(TAG_NOMBRE_BANCO, nombreBanco);
			
		pageCheckoutWrapper.selectBancoEPS(nombreBanco);
		checkIsVisibleBank(nombreBanco);
	}
	
	@Validation (description="Aparece el banco \"#{ombreBanco}\" en el cuadro de selección")
	private boolean checkIsVisibleBank(String nombreBanco) {
		return (pageCheckoutWrapper.isBancoSeleccionado(nombreBanco));
	}

	@Validation (description="Aparece el botón que permite aplicar los Loyalty Points")
	public boolean validateBlockLoyalty() {
		return (pageCheckoutWrapper.isVisibleButtonForApplyLoyaltyPoints());
	}
	
	@Step (
		description="Seleccionamos el botón para aplicar el descuento de Loyalty Points",
		expected="Se aplica correctamente el descuento")
	public void loyaltyPointsApply() {
		if (isMobile()) {
			loyaltyPointsApplyMobil();
		} else {
			loyaltyPointsApplyDesktop();
		}
	}
	
	public void loyaltyPointsApplyDesktop() {
		float subTotalInicial = UtilsMangoTest.round(pageCheckoutWrapper.getImportSubtotalDesktop(), 2);
		float loyaltyPointsNoRound = pageCheckoutWrapper.applyAndGetLoyaltyPoints();
		float loyaltyPoints = UtilsMangoTest.round(loyaltyPointsNoRound, 2);
		validateLoyaltyPointsDiscountDesktopUntil(loyaltyPoints, subTotalInicial, 3);
		checksDefault();
	}
	
	@Validation (
		description=
			"Se aplica el descuento de <b>#{descuento}</b> al subtotal inicial de #{subtotalInicial} " + 
			SECONDS_WAIT)
	public boolean validateLoyaltyPointsDiscountDesktopUntil(
			float descuento, float subtotalInicial, int seconds) {
		
		for (int i=0; i<seconds; i++) {
			float subTotalActual = pageCheckoutWrapper.getImportSubtotalDesktop();
			float estimado = UtilsMangoTest.round(subtotalInicial - descuento, 2);
			if (estimado == subTotalActual) {
				return true;
			}
			waitMillis(1000);
		}
		
		return false;
	}
	
	public void loyaltyPointsApplyMobil() {
		float loyaltyPointsNoRound = pageCheckoutWrapper.applyAndGetLoyaltyPoints();
		float loyaltyPoints = UtilsMangoTest.round(loyaltyPointsNoRound, 2);
		validateLoyaltyPointsDiscountMobilUntil(loyaltyPoints, 3);
	}
	
	@Validation (
		description="Aparece un descuento aplicado de #{descuento} " + SECONDS_WAIT)
	public boolean validateLoyaltyPointsDiscountMobilUntil(float descuento, int seconds) {
		for (int i=0; i<seconds; i++) {
			float discountApplied = UtilsMangoTest.round(pageCheckoutWrapper.getDiscountLoyaltyAppliedMobil(), 2);
			if (discountApplied == descuento) {
				return true;
			}
			waitMillis(1000);
		}
		
		return false;
	}
	
	private boolean isMobile() {
		return channel==Channel.mobile;
	}	
}