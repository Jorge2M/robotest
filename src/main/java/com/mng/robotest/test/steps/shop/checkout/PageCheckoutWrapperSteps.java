package com.mng.robotest.test.steps.shop.checkout;

import java.util.List;
import java.util.StringTokenizer;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.beans.AccesoEmpl;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.beans.Pago.TypePago;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.generic.ChequeRegalo;
import com.mng.robotest.test.generic.UtilsMangoTest;
import com.mng.robotest.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test.steps.shop.checkout.envio.SecMetodoEnvioDesktopSteps;
import com.mng.robotest.test.steps.shop.checkout.ideal.SecIdealSteps;
import com.mng.robotest.test.steps.shop.checkout.tmango.SecTMangoSteps;

public class PageCheckoutWrapperSteps extends StepBase {

	private final PageCheckoutWrapper pageCheckoutWrapper; 
	
	private final ModalDirecEnvioSteps modalDirecEnvioSteps;
	private final SecMetodoEnvioDesktopSteps secMetodoEnvioDesktopSteps;
	private final SecStoreCreditSteps secStoreCreditSteps;
	private final SecTMangoSteps secTMangoSteps;
	private final SecKrediKartiSteps secKrediKartiSteps;
	private final SecBillpaySteps secBillpaySteps;
	private final ModalDirecFacturaSteps modalDirecFacturaSteps;
	private final ModalAvisoCambioPaisSteps modalAvisoCambioPaisSteps;
	private final Page1DktopCheckoutSteps page1DktopCheckSteps;
	private final Page1EnvioCheckoutMobilSteps page1MobilCheckSteps;
	private final SecIdealSteps secIdealSteps;
	private final SecTarjetaPciSteps secTarjetaPciSteps;
	
	public PageCheckoutWrapperSteps() {
		this.pageCheckoutWrapper = new PageCheckoutWrapper(channel, app);
		this.modalDirecEnvioSteps = new ModalDirecEnvioSteps(channel, app);
		this.secMetodoEnvioDesktopSteps = new SecMetodoEnvioDesktopSteps();
		this.secStoreCreditSteps = new SecStoreCreditSteps();
		this.secTMangoSteps = new SecTMangoSteps(channel, driver);
		this.secKrediKartiSteps = new SecKrediKartiSteps(channel, driver); 
		this.secBillpaySteps = new SecBillpaySteps(channel);
		this.modalDirecFacturaSteps = new ModalDirecFacturaSteps(channel, app);
		this.modalAvisoCambioPaisSteps = new ModalAvisoCambioPaisSteps(app);
		this.page1DktopCheckSteps = new Page1DktopCheckoutSteps(channel, app);
		this.page1MobilCheckSteps = new Page1EnvioCheckoutMobilSteps();
		this.secIdealSteps = new SecIdealSteps(channel, driver);
		this.secTarjetaPciSteps = new SecTarjetaPciSteps(channel, app, driver);
	}
	
	public PageCheckoutWrapper getPageCheckoutWrapper() {
		return pageCheckoutWrapper;
	}
	public Page1EnvioCheckoutMobilSteps getPage1CheckoutMobilSteps() {
		return page1MobilCheckSteps;
	}
	public ModalDirecEnvioSteps getModalDirecEnvioSteps() {
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
	public SecStoreCreditSteps getSecStoreCreditSteps() {
		return secStoreCreditSteps;
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
	
	public void validateIsFirstPage(boolean userLogged, DataBag dataBag) throws Exception {
		if (channel==Channel.mobile) {
			page1MobilCheckSteps.validateIsPage(userLogged);
		} else {
			page1DktopCheckSteps.validateIsPageOK(dataBag);
		}
	} 
	
	@Validation (
		description="Acaba desapareciendo la capa de \"Cargando...\" (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Warn)
	public boolean validateLoadingDisappears(int maxSeconds) throws Exception {
		Thread.sleep(200); //Damos tiempo a que aparezca la capa de "Cargando"
		return (pageCheckoutWrapper.isNoDivLoadingUntil(maxSeconds));
	}
	
	@Step (
		description="Si existen y están plegados, desplegamos el bloque con los métodos de pago", 
		expected="Aparecen los métodos de pagos asociados al país")
	public void despliegaYValidaMetodosPago(Pais pais, boolean isEmpl) throws Exception {
		TestMaker.getCurrentStepInExecution().addExpectedText(": " + pais.getStringPagosTest(app, isEmpl));
		pageCheckoutWrapper.despliegaMetodosPago();
		validaMetodosPagoDisponibles(pais, isEmpl);
	}
	
	public void validaMetodosPagoDisponibles(Pais pais, boolean isEmpl) {
		checkAvailablePagos(pais, isEmpl);
		checkLogosPagos(pais, isEmpl);
	}
	
	@Validation
	private ChecksTM checkAvailablePagos(Pais pais, boolean isEmpl) {
		ChecksTM checks = ChecksTM.getNew();
	 	checks.add(
			"El número de pagos disponibles, logos tarjetas, coincide con el de asociados al país (" + pais.getListPagosForTest(app, isEmpl).size() + ")",
			pageCheckoutWrapper.isNumMetodosPagoOK(pais, isEmpl), State.Defect);		
		return checks;
	}
	
	@Validation
	private ChecksTM checkLogosPagos(Pais pais, boolean isEmpl) { 
		ChecksTM checks = ChecksTM.getNew();
		List<Pago> listPagos = pais.getListPagosForTest(app, isEmpl);
		if (listPagos.size()==1 && channel.isDevice()) {
			return checks;
		}
		for (int i=0; i<listPagos.size(); i++) {
			if (listPagos.get(i).getTypePago()!=TypePago.TpvVotf) {
				String pagoNameExpected = listPagos.get(i).getNombre(channel, app);
			 	checks.add(
					"Aparece el logo/pestaña asociado al pago <b>" + pagoNameExpected + "</b>",
					pageCheckoutWrapper.isMetodoPagoPresent(pagoNameExpected), State.Defect);	
			}
		}   
		
		return checks;
	}
	
	@Step (
		description="<b>#{nombrePagoTpvVOTF}</b>: no clickamos el icono pues no existe", 
		expected="No aplica")
	public void noClickIconoVotf(@SuppressWarnings("unused") String nombrePagoTpvVOTF) throws Exception {		
		//No hacemos nada pues el pago con TPV en VOTF no tiene icono ni pasarelas asociadas
	}
	
	/**
	 * Realiza una navegación (conjunto de pasos/validaciones) mediante la que se selecciona el método de envío y finalmente el método de pago 
	 */
	public void fluxSelectEnvioAndClickPaymentMethod(DataPago dataPago, Pais pais) throws Exception {
		boolean pagoPintado = false;
		if (!dataPago.getFTCkout().chequeRegalo) {
			pagoPintado = secMetodoEnvioDesktopSteps.fluxSelectEnvio(dataPago, pais);
		}
		boolean methodSelectedOK = forceClickIconoPagoAndWait(pais, dataPago.getDataPedido().getPago(), !pagoPintado);
		if (!methodSelectedOK) {
			//En caso de no conseguir seleccionar correctamente el pago no nos podemos arriesgar a continuar con el pago
			//porque quizás esté seleccionado otro método de pago del tipo Contrareembolso y un "Confirmar Pago" desencadenaría la compra en PRO
			throw new RuntimeException("Problem selecting payment method " + dataPago.getDataPedido().getPago().getNombre() + " in country " + pais.getNombre_pais());
		}
	}
	
	/**
	 * Paso consistente en clickar un determinado método de pago de la página de resumen de artículos (precompra)
	 */
	@Step (
		description="Seleccionamos el icono/pestaña correspondiente al método de pago y esperamos la desaparición de los \"loading\"",
		expected="La operación se ejecuta correctamente")
	public boolean forceClickIconoPagoAndWait(Pais pais, Pago pago, boolean pintaNombrePago) throws Exception {
		if (pintaNombrePago) {
			String pintaPago = "<b style=\"color:blue;\">" + pago.getNombre(channel, app) + "</b>:"; 
			StepTM step = TestMaker.getCurrentStepInExecution();
			String newDescription = pintaPago + step.getDescripcion();
			step.setDescripcion(newDescription);
		}

		try {
			pageCheckoutWrapper.forceClickMetodoPagoAndWait(pago.getNombre(channel, app), pais);
		}
		catch (Exception e) {
			Log4jTM.getLogger().warn("Problem clicking icono pago for payment {} in country {}", pago.getNombre(), pais.getNombre_pais(), e);
		}

		if (pago.getTypePago()==TypePago.TarjetaIntegrada || 
			pago.getTypePago()==TypePago.KrediKarti ||
			pago.getTypePago()==TypePago.Bancontact) {
			validateSelectPagoTRJintegrada(pago, pais);
			return true;
		} else {
			return validateSelectPagoNoTRJintegrada(pago);
		}
	}
	
	public void validateSelectPagoTRJintegrada(Pago pago, Pais pais) {
		if (channel==Channel.desktop) {
			validateIsPresentButtonCompraDesktop();
		}
		getSecTarjetaPciSteps().validateIsSectionOk(pago, pais);
	}
	
	public boolean validateSelectPagoNoTRJintegrada(Pago pago) {
		if (channel==Channel.desktop) {
			validateIsPresentButtonCompraDesktop();
		}
		return checkIsVisibleTextUnderPayment(pago.getNombreInCheckout(channel, app), pago, 2);
	}
	
	@Validation (
		description="Se hace visible el texto bajo el método de pago: #{nombrePago} (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	private boolean checkIsVisibleTextUnderPayment(@SuppressWarnings("unused") String nombrePago, Pago pago, int maxSeconds) {
		return (pageCheckoutWrapper.isVisibleBloquePagoNoTRJIntegradaUntil(pago, maxSeconds));
	}
	
	@Validation (
		description="Aparece el botón de \"Confirmar Compra\"",
		level=State.Defect)
	public boolean validateIsPresentButtonCompraDesktop() {
		return (pageCheckoutWrapper.getPage1DktopCheckout().isPresentButtonConfPago());
	}
	
	static final String tagTipoTarj = "@TagTipoTarj";
	static final String tagNumTarj = "@TagNumTarj";
	@Step (
		description="Introducimos los datos de la tarjeta (" + tagTipoTarj + ") " + tagNumTarj + " y pulsamos el botón \"Confirmar pago\"",
		expected="Aparece la página de resultado OK")
	public void inputDataTrjAndConfirmPago(DataPago dataPago) throws Exception {
		Pago pago = dataPago.getDataPedido().getPago();
		StepTM step = TestMaker.getCurrentStepInExecution();
		step.replaceInDescription(tagTipoTarj, pago.getTipotarj());
		step.replaceInDescription(tagNumTarj, pago.getNumtarj());
	   
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
		PageRedirectPasarelaLoadingSteps.validateDisappeared(5, driver);
	}

	@Validation (
		description="Está disponible una tarjeta guardada de tipo #{tipoTarjeta}",
		level=State.Warn)
	public boolean isTarjetaGuardadaAvailable(String tipoTarjeta) {
		return (pageCheckoutWrapper.isAvailableTrjGuardada(tipoTarjeta));
	}

	@Step (
		description="Seleccionamos la tarjeta guardada, si nos lo pide introducimos el cvc #{cvc} y pulsamos el botón \"Confirmar pago\"",
		expected="Aparece la página de resultado OK")
	public void selectTrjGuardadaAndConfirmPago(DataPago dataPago, String cvc) throws Exception {
		pageCheckoutWrapper.clickRadioTrjGuardada();
		pageCheckoutWrapper.inputCvcTrjGuardadaIfVisible(cvc);
		pageCheckoutWrapper.confirmarPagoFromMetodos(dataPago.getDataPedido());
		PageRedirectPasarelaLoadingSteps.validateDisappeared(5, driver);
	}

	@Step (
		description="Seleccionar el radiobutton \"Quiero recibir una factura\"", 
		expected="Aparece el modal para la introducción de la dirección de facturación")
	public void clickSolicitarFactura() {
		pageCheckoutWrapper.clickSolicitarFactura();
		modalDirecFacturaSteps.validateIsOk();
	}

	@Step (
		description="Seleccionar el botón \"Editar\" asociado a la Dirección de Envío", 
		expected="Aparece el modal para la introducción de la dirección de envío")
	public void clickEditarDirecEnvio() throws Exception {
		pageCheckoutWrapper.clickEditDirecEnvio();
		modalDirecEnvioSteps.validateIsOk();
	}

	@Step (
		description="Seleccionamos el botón \"Confirmar Pago\"", 
		expected="Aparece una pasarela de pago",
		saveImagePage=SaveWhen.Always)
	public void pasoBotonAceptarCompraDesktop() throws Exception {
		pageCheckoutWrapper.getPage1DktopCheckout().clickConfirmarPago();
	}	  
	
	@Validation (
		description="Aparece el botón de \"Confirmar Pago\" (esperamos hasta #{maxSeconds} segundos)",
		level=State.Warn)
	private boolean checkAfterClickVerResumen(int maxSeconds) {
		return (pageCheckoutWrapper.getPage2MobilCheckout().isClickableButtonFinalizarCompraUntil(maxSeconds));
	}
			
	@Step (
		description="Seleccionamos el botón \"Finalizar Compra\" (previamente esperamos hasta 20 segundos a que desaparezca la capa \"Espera unos segundos...\")", 
		expected="Aparece una pasarela de pago",
		saveImagePage=SaveWhen.Always)
	public void pasoBotonConfirmarPagoCheckout3Mobil() throws Exception {
		try {
			pageCheckoutWrapper.getPage2MobilCheckout().clickFinalizarCompraAndWait(20);
		}
		catch (Exception e) {
			Log4jTM.getLogger().warn("Problem in click Confirm payment button", e);
		}
							
		PageRedirectPasarelaLoadingSteps.validateDisappeared(5, driver);
	}	
	
	private static final String tagTarjeta = "@TagTarjeta";
	@Step (
		description="Introducir la tarjeta de empleado " + tagTarjeta + " y pulsar el botón \"Aplicar\"", 
		expected="Aparecen los datos para la introducción del 1er apellido y el nif")
	public void inputTarjetaEmplEnCodPromo(Pais pais, AccesoEmpl accesoEmpl) throws Exception {
		TestMaker.getCurrentStepInExecution().replaceInDescription(tagTarjeta, accesoEmpl.getTarjeta());
		pageCheckoutWrapper.inputCodigoPromoAndAccept(accesoEmpl.getTarjeta());
		checkAfterInputTarjetaEmpleado(pais, accesoEmpl);
	}
	
	@Validation
	private ChecksTM checkAfterInputTarjetaEmpleado(Pais pais, AccesoEmpl accesoEmpl) {
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 5;
	 	checks.add(
			"Aparece el campo de introducción del primer apellido (lo esperamos hasta " + maxSeconds + " segundos)",
			pageCheckoutWrapper.isPresentInputApellidoPromoEmplUntil(maxSeconds), State.Defect);
		
		boolean isPresentInputDni = pageCheckoutWrapper.isPresentInputDNIPromoEmpl();
		if (accesoEmpl.getNif()!=null) {
		 	checks.add(
				"Aparece el campo de introducción del DNI/Pasaporte",
				isPresentInputDni, State.Defect);
		} else {
		 	checks.add(
				"Noparece el campo de introducción del DNI/Pasaporte",
				!isPresentInputDni, State.Defect);
		}
		
		boolean isPresentInputFechaNac = pageCheckoutWrapper.isPresentDiaNaciPromoEmpl();
	 	checks.add(
			"No aparece el campo de introducción de la fecha de nacimiento",
			!isPresentInputFechaNac, State.Defect);	
		
		return checks;
	}
	
	static final String tag1erApellido = "@Tag1erApellido";
	@Step (
		description="Introducir el primer apellido " + tag1erApellido + " y pulsar el botón \"Guardar\"", 
		expected="Se aplican los descuentos correctamente")
	public void inputDataEmplEnPromoAndAccept(DataBag dataBag, AccesoEmpl accesoEmpl, Pais pais, AppEcom app) throws Exception {
		StepTM step = TestMaker.getCurrentStepInExecution();
		String primerApellido = (new StringTokenizer(accesoEmpl.getNombre(), " ")).nextToken();
		step.replaceInDescription(tag1erApellido, primerApellido);
		
		if (accesoEmpl.getNif()!=null) {
			step.addRightDescriptionText("Introducir el NIF del usuario " + accesoEmpl.getNif() + ". ");
			pageCheckoutWrapper.inputDNIPromoEmpl(accesoEmpl.getNif());
		}
		pageCheckoutWrapper.inputApellidoPromoEmpl(primerApellido);
		pageCheckoutWrapper.clickButtonAceptarPromoEmpl();
		
		validaResultImputPromoEmpl(dataBag, app);
	}
		
	public void validaResultImputPromoEmpl(DataBag dataBag, AppEcom app) throws Exception {
		if (channel.isDevice()) {
			page1MobilCheckSteps.validaResultImputPromoEmpl();
		} else {
			page1DktopCheckSteps.validaResultImputPromoEmpl(dataBag);
		}
	}	
	
	public void validaIsVersionChequeRegalo(ChequeRegalo chequeRegalo) {
		page1DktopCheckSteps.validateIsVersionChequeRegalo(chequeRegalo);
	}
	
	static final String tagNombreBanco = "@TagNombreBanco";
	@Step (
		description="Escogemos el banco \"" + tagNombreBanco + "\" en la pestaña de selección", 
		expected="El banco aparece seleccionado")
	public void selectBancoEPS() throws Exception {
		String nombreBanco = "Easybank";
		if (!new UtilsMangoTest().isEntornoPRO()) {
			nombreBanco = "Test Issuer";
		}
		TestMaker.getCurrentStepInExecution().replaceInDescription(tagNombreBanco, nombreBanco);
			
		pageCheckoutWrapper.selectBancoEPS(nombreBanco);
		checkIsVisibleBank(nombreBanco);
	}
	
	@Validation (
		description="Aparece el banco \"#{ombreBanco}\" en el cuadro de selección",
		level=State.Defect)
	private boolean checkIsVisibleBank(String nombreBanco) {
		return (pageCheckoutWrapper.isBancoSeleccionado(nombreBanco));
	}

	@Validation (
		description="Aparece el botón que permite aplicar los Loyalty Points",
		level=State.Defect)
	public boolean validateBlockLoyalty() {
		return (pageCheckoutWrapper.isVisibleButtonForApplyLoyaltyPoints());
	}
	
	@Step (
		description="Seleccionamos el botón para aplicar el descuento de Loyalty Points",
		expected="Se aplica correctamente el descuento")
	public void loyaltyPointsApply() throws Exception {
		switch (channel) {
		case desktop:
		case tablet:
			loyaltyPointsApplyDesktop();
			break;
		case mobile:
		default:
			loyaltyPointsApplyMobil();
		}
	}
	
	public void loyaltyPointsApplyDesktop() throws Exception {
		float subTotalInicial = UtilsMangoTest.round(pageCheckoutWrapper.getImportSubtotalDesktop(), 2);
		float loyaltyPointsNoRound = pageCheckoutWrapper.applyAndGetLoyaltyPoints();
		float loyaltyPoints = UtilsMangoTest.round(loyaltyPointsNoRound, 2);
		validateLoyaltyPointsDiscountDesktopUntil(loyaltyPoints, subTotalInicial, 3);
	}
	
	@Validation (
		description=
			"Se aplica el descuento de <b>#{descuento}</b> al subtotal inicial de #{subtotalInicial} " + 
			"(lo esperamos hasta #{maxSeconds})",
		level=State.Defect)
	public boolean validateLoyaltyPointsDiscountDesktopUntil(float descuento, float subtotalInicial, int maxSeconds) 
	throws Exception {
		for (int i=0; i<maxSeconds; i++) {
			float subTotalActual = pageCheckoutWrapper.getImportSubtotalDesktop();
			float estimado = UtilsMangoTest.round(subtotalInicial - descuento, 2);
			if (estimado == subTotalActual) {
				return true;
			}
			Thread.sleep(1000);
		}
		
		return false;
	}
	
	public void loyaltyPointsApplyMobil() throws Exception {
		float loyaltyPointsNoRound = pageCheckoutWrapper.applyAndGetLoyaltyPoints();
		float loyaltyPoints = UtilsMangoTest.round(loyaltyPointsNoRound, 2);
		validateLoyaltyPointsDiscountMobilUntil(loyaltyPoints, 3);
	}
	
	@Validation (
		description="Aparece un descuento aplicado de #{descuento} (lo esperamos hasta #{maxSeconds})",
		level=State.Defect)
	public boolean validateLoyaltyPointsDiscountMobilUntil(float descuento, int maxSeconds) throws Exception {
		for (int i=0; i<maxSeconds; i++) {
			float discountApplied = UtilsMangoTest.round(pageCheckoutWrapper.getDiscountLoyaltyAppliedMobil(), 2);
			if (discountApplied == descuento) {
				return true;
			}
			Thread.sleep(1000);
		}
		
		return false;
	}
}