package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import java.util.List;
import java.util.StringTokenizer;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.beans.AccesoEmpl;
import com.mng.robotest.test80.mango.test.beans.Pago;
import com.mng.robotest.test80.mango.test.beans.Pais;
import com.mng.robotest.test80.mango.test.beans.Pago.TypePago;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.generic.ChequeRegalo;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.envio.SecMetodoEnvioDesktopStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.ideal.SecIdealStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.tmango.SecTMangoStpV;

@SuppressWarnings({"static-access"})
public class PageCheckoutWrapperStpV {

	private final PageCheckoutWrapper pageCheckoutWrapper; 
	
    private final ModalDirecEnvioStpV modalDirecEnvioStpV;
    private final SecMetodoEnvioDesktopStpV secMetodoEnvioDesktopStpV;
    private final SecStoreCreditStpV secStoreCreditStpV;
    private final SecTMangoStpV secTMangoStpV;
    private final SecKrediKartiStpV secKrediKartiStpV;
    private final SecBillpayStpV secBillpayStpV;
    private final ModalDirecFacturaStpV modalDirecFacturaStpV;
    private final ModalAvisoCambioPaisStpV modalAvisoCambioPaisStpV;
    private final Page1DktopCheckoutStpV page1DktopCheckStpV;
    private final Page1EnvioCheckoutMobilStpV page1MobilCheckStpV;
    private final SecIdealStpV secIdealStpV;
    private final SecTarjetaPciStpV secTarjetaPciStpV;
    
	private final WebDriver driver;
	private final Channel channel;
	private final AppEcom app;


    public PageCheckoutWrapperStpV(Channel channel, AppEcom app, WebDriver driver) {
    	this.driver = driver;
    	this.channel = channel;
    	this.app = app;
    	
    	this.pageCheckoutWrapper = new PageCheckoutWrapper(channel, app, driver);
    	
    	this.modalDirecEnvioStpV = new ModalDirecEnvioStpV(channel, app, driver);
    	this.secMetodoEnvioDesktopStpV = new SecMetodoEnvioDesktopStpV(channel, app, driver);
        this.secStoreCreditStpV = new SecStoreCreditStpV(channel, app, driver);
        this.secTMangoStpV = new SecTMangoStpV(channel, app, driver);
        this.secKrediKartiStpV = new SecKrediKartiStpV(channel, driver); 
        this.secBillpayStpV = new SecBillpayStpV(channel, driver);
        this.modalDirecFacturaStpV = new ModalDirecFacturaStpV(channel, app, driver);
        this.modalAvisoCambioPaisStpV = new ModalAvisoCambioPaisStpV(app, driver);
        this.page1DktopCheckStpV = new Page1DktopCheckoutStpV(channel, app, driver);
        this.page1MobilCheckStpV = new Page1EnvioCheckoutMobilStpV(driver);
        this.secIdealStpV = new SecIdealStpV(channel, driver);
        this.secTarjetaPciStpV = new SecTarjetaPciStpV(channel, app, driver);
    }
    
    public PageCheckoutWrapper getPageCheckoutWrapper() {
    	return pageCheckoutWrapper;
    }
    public Page1EnvioCheckoutMobilStpV getPage1CheckoutMobilStpV() {
    	return page1MobilCheckStpV;
    }
    public ModalDirecEnvioStpV getModalDirecEnvioStpV() {
    	return modalDirecEnvioStpV;
    }
    public SecIdealStpV getSecIdealStpV() {
    	return secIdealStpV;
    }
    public SecBillpayStpV getSecBillpayStpV() {
    	return secBillpayStpV;
    }
    public SecTarjetaPciStpV getSecTarjetaPciStpV() {
    	return secTarjetaPciStpV;
    }
    public SecKrediKartiStpV getSecKrediKartiStpV() {
    	return secKrediKartiStpV;
    }
    public SecStoreCreditStpV getSecStoreCreditStpV() {
    	return secStoreCreditStpV;
    }
    public SecTMangoStpV getSecTMangoStpV() {
    	return secTMangoStpV;
    }
    public ModalDirecFacturaStpV getModalDirecFacturaStpV() {
    	return modalDirecFacturaStpV;
    }
    public ModalAvisoCambioPaisStpV getModalAvisoCambioPaisStpV() {
    	return modalAvisoCambioPaisStpV;
    }
    
    public void validateIsFirstPage(boolean userLogged, DataBag dataBag) throws Exception {
    	if (channel==Channel.mobile) {
    		page1MobilCheckStpV.validateIsPage(userLogged);
    	} else {
    		page1DktopCheckStpV.validateIsPageOK(dataBag);
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
    	ChecksTM validations = ChecksTM.getNew();
	 	validations.add(
			"El número de pagos disponibles, logos tarjetas, coincide con el de asociados al país (" + pais.getListPagosForTest(app, isEmpl).size() + ")",
			pageCheckoutWrapper.isNumMetodosPagoOK(pais, isEmpl), State.Defect);    	
    	return validations;
    }
    
    @Validation
    private ChecksTM checkLogosPagos(Pais pais, boolean isEmpl) { 
    	ChecksTM validations = ChecksTM.getNew();
        List<Pago> listPagos = pais.getListPagosForTest(app, isEmpl);
        if (listPagos.size()==1 && channel.isDevice()) {
        	return validations;
        }
        for (int i=0; i<listPagos.size(); i++) {
            if (listPagos.get(i).getTypePago()!=TypePago.TpvVotf) {
            	String pagoNameExpected = listPagos.get(i).getNombre(channel, app);
        	 	validations.add(
        			"Aparece el logo/pestaña asociado al pago <b>" + pagoNameExpected + "</b>",
        			pageCheckoutWrapper.isMetodoPagoPresent(pagoNameExpected), State.Defect);    
            }
        }   
        
        return validations;
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
    public void fluxSelectEnvioAndClickPaymentMethod(DataCtxPago dCtxPago, DataCtxShop dCtxSh) throws Exception {
        boolean pagoPintado = false;
        if (!dCtxPago.getFTCkout().isChequeRegalo) {
            pagoPintado = secMetodoEnvioDesktopStpV.fluxSelectEnvio(dCtxPago, dCtxSh);
        }
        boolean methodSelectedOK = forceClickIconoPagoAndWait(dCtxSh.pais, dCtxPago.getDataPedido().getPago(), !pagoPintado);
        if (!methodSelectedOK) {
        	//En caso de no conseguir seleccionar correctamente el pago no nos podemos arriesgar a continuar con el pago
        	//porque quizás esté seleccionado otro método de pago del tipo Contrareembolso y un "Confirmar Pago" desencadenaría la compra en PRO
        	throw new RuntimeException("Problem selecting payment method " + dCtxPago.getDataPedido().getPago().getNombre() + " in country " + dCtxSh.pais.getNombre_pais());
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
        getSecTarjetaPciStpV().validateIsSectionOk(pago, pais);
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
    
    final static String tagTipoTarj = "@TagTipoTarj";
    final static String tagNumTarj = "@TagNumTarj";
    @Step (
    	description="Introducimos los datos de la tarjeta (" + tagTipoTarj + ") " + tagNumTarj + " y pulsamos el botón \"Confirmar pago\"",
    	expected="Aparece la página de resultado OK")
    public void inputDataTrjAndConfirmPago(DataCtxPago dCtxPago) throws Exception {
        Pago pago = dCtxPago.getDataPedido().getPago();
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

        pageCheckoutWrapper.confirmarPagoFromMetodos(dCtxPago.getDataPedido());
        PageRedirectPasarelaLoadingStpV.validateDisappeared(5, driver);
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
	public void selectTrjGuardadaAndConfirmPago(DataCtxPago dCtxPago, String cvc) throws Exception {
		pageCheckoutWrapper.clickRadioTrjGuardada();
		pageCheckoutWrapper.inputCvcTrjGuardadaIfVisible(cvc);
		pageCheckoutWrapper.confirmarPagoFromMetodos(dCtxPago.getDataPedido());
		PageRedirectPasarelaLoadingStpV.validateDisappeared(5, driver);
	}

	@Step (
		description="Seleccionar el radiobutton \"Quiero recibir una factura\"", 
		expected="Aparece el modal para la introducción de la dirección de facturación")
	public void clickSolicitarFactura() {
		pageCheckoutWrapper.clickSolicitarFactura();
		modalDirecFacturaStpV.validateIsOk();
	}

	@Step (
		description="Seleccionar el botón \"Editar\" asociado a la Dirección de Envío", 
		expected="Aparece el modal para la introducción de la dirección de envío")
	public void clickEditarDirecEnvio() throws Exception {
		pageCheckoutWrapper.clickEditDirecEnvio();
		modalDirecEnvioStpV.validateIsOk();
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
            int maxSecondsToWait = 20;
            pageCheckoutWrapper.getPage2MobilCheckout().clickFinalizarCompraAndWait(maxSecondsToWait);
        }
        catch (Exception e) {
        	Log4jTM.getLogger().warn("Problem in click Confirm payment button", e);
        }
                            
        PageRedirectPasarelaLoadingStpV.validateDisappeared(5, driver);
    }    
    
    private final static String tagTarjeta = "@TagTarjeta";
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
    	ChecksTM validations = ChecksTM.getNew();
	    int maxSeconds = 5;
	 	validations.add(
			"Aparece el campo de introducción del primer apellido (lo esperamos hasta " + maxSeconds + " segundos)",
			pageCheckoutWrapper.isPresentInputApellidoPromoEmplUntil(maxSeconds), State.Defect);
    	
    	boolean isPresentInputDni = pageCheckoutWrapper.isPresentInputDNIPromoEmpl();
    	if (accesoEmpl.getNif()!=null) {
		 	validations.add(
				"Aparece el campo de introducción del DNI/Pasaporte",
				isPresentInputDni, State.Defect);
    	} else {
		 	validations.add(
				"Noparece el campo de introducción del DNI/Pasaporte",
				!isPresentInputDni, State.Defect);
    	}
    	
    	boolean isPresentInputFechaNac = pageCheckoutWrapper.isPresentDiaNaciPromoEmpl();
	 	validations.add(
			"No aparece el campo de introducción de la fecha de nacimiento",
			!isPresentInputFechaNac, State.Defect);	
    	
    	return validations;
    }
    
    final static String tag1erApellido = "@Tag1erApellido";
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
        	page1MobilCheckStpV.validaResultImputPromoEmpl();
        } else {
        	page1DktopCheckStpV.validaResultImputPromoEmpl(dataBag);
        }
    }    
    
    public void validaIsVersionChequeRegalo(ChequeRegalo chequeRegalo) {
    	page1DktopCheckStpV.validateIsVersionChequeRegalo(chequeRegalo);
    }
    
    final static String tagNombreBanco = "@TagNombreBanco";
    @Step (
    	description="Escogemos el banco \"" + tagNombreBanco + "\" en la pestaña de selección", 
        expected="El banco aparece seleccionado")
	public void selectBancoEPS(DataCtxShop dCtxSh) throws Exception {
    	String nombreBanco = "Easybank";
		if (!UtilsMangoTest.isEntornoPRO(dCtxSh.appE, driver)) {
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