package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import java.util.List;
import java.util.StringTokenizer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.SaveWhen;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago.TypePago;
import com.mng.robotest.test80.mango.test.generic.ChequeRegalo;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio.TipoTransporteEnum.TipoTransporte;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.envio.ModalDroppointsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.envio.SecMetodoEnvioDesktopStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.ideal.SecIdealStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.tmango.SecTMangoStpV;

@SuppressWarnings({"static-access"})
public class PageCheckoutWrapperStpV {
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);

    public static SecMetodoEnvioDesktopStpV secMetodoEnvio;
    public static SecStoreCreditStpV secStoreCredit;
    public static SecTarjetaPciStpV secTarjetaPci;
    public static SecIdealStpV secIdeal;
    public static SecTMangoStpV secTMango;
    public static SecKrediKartiStpV secKrediKarti;
    public static SecBillpayStpV secBillpay;
    public static SecKlarnaStpV secKlarna;
    public static SecKlarnaDeutschStpV secKlarnaDeutsch;
    public static ModalDirecFacturaStpV modalDirecFactura;
    public static ModalDirecEnvioStpV modalDirecEnvio;
    public static ModalAvisoCambioPaisStpV modalAvisoCambioPais;
    public static Page1DktopCheckoutStpV page1DktopCheck;
    public static Page1EnvioCheckoutMobilStpV page1MobilCheck;
    
    public static void validateIsFirstPage(boolean userLogged, DataBag dataBag, Channel channel, WebDriver driver) 
    throws Exception {
        if (channel==Channel.movil_web) {
            page1MobilCheck.validateIsPage(userLogged, driver);
        }
        else {
            page1DktopCheck.validateIsPageOK(dataBag, driver);
        }
    } 
    
    public static void validateLoadingDisappears(DatosStep datosStep, DataFmwkTest dFTest) throws Exception {
    //Validaciones
	    int maxSecondsToWait = 10;
	    String descripValidac = "1) Acaba desapareciendo la capa de \"Cargando...\" (lo esperamos hasta " + maxSecondsToWait + " segundos)";
	    datosStep.setNOKstateByDefault();  
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
	    try {
	        Thread.sleep(200); //Damos tiempo a que aparezca la capa de "Cargando"
	        if (!PageCheckoutWrapper.isNoDivLoadingUntil(maxSecondsToWait, dFTest.driver)) {
	            listVals.add(1, State.Warn);     
	        }
	
	        datosStep.setListResultValidations(listVals);
	    }
	    finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    /**
     * Despliega (si no lo están) los métodos de pago y valida que realmente sean los correctos
     */
    public static DatosStep despliegaYValidaMetodosPago(Pais pais, boolean isEmpl, AppEcom app, Channel channel, DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep (
            "Si existen y están plegados, desplegamos el bloque con los métodos de pago", 
            "Aparecen los métodos de pagos asociados al país: " + pais.getStringPagosTest(app, isEmpl));
        try {
            PageCheckoutWrapper.despliegaMetodosPago(channel, dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }

        //Validaciones (validamos los métodos de pago disponibles)
        validaMetodosPagoDisponibles(pais, isEmpl, app, channel, dFTest.driver);
        
        return datosStep;
    }
    
    public static void validaMetodosPagoDisponibles(Pais pais, boolean isEmpl, AppEcom app, Channel channel, WebDriver driver) {
    	checkAvailablePagos(pais, isEmpl, app, channel, driver);
    	checkLogosPagos(pais, isEmpl, app, channel, driver);
    }
    
    @Validation
    private static ListResultValidation checkAvailablePagos(Pais pais, boolean isEmpl, AppEcom app, Channel channel, WebDriver driver) {
    	ListResultValidation validations = ListResultValidation.getNew();
	 	validations.add(
			"El número de pagos disponibles, logos tarjetas, coincide con el de asociados al país (" + pais.getListPagosEnOrdenPantalla(app, isEmpl).size() + ")",
			PageCheckoutWrapper.isNumMetodosPagoOK(pais, app, channel, isEmpl, driver), State.Defect);    	
    	return validations;
    }
    
    @Validation
    private static ListResultValidation checkLogosPagos(Pais pais, boolean isEmpl, AppEcom app, Channel channel, WebDriver driver) { 
    	ListResultValidation validations = ListResultValidation.getNew();
        List<Pago> listaPagosEnOrden = pais.getListPagosEnOrdenPantalla(app, isEmpl);
        for (int i=0; i<listaPagosEnOrden.size(); i++) {
            if (listaPagosEnOrden.get(i).getTypePago()!=TypePago.TpvVotf) {
        	 	validations.add(
        			"Aparece el logo/pestaña asociado al pago <b>" + listaPagosEnOrden.get(i).getNombre(channel) + "</b><br>",
        			PageCheckoutWrapper.isMetodoPagoPresent(listaPagosEnOrden.get(i).getNombre(channel), listaPagosEnOrden.get(i).getIndexpant(), channel, pais.getLayoutPago(), driver),
        			State.Defect);    
            }
        }   
        
        return validations;
    }
    
    /**
     * Paso ficticio consistente en "no-clicar" el icono de VOTF.
     */
    public static DatosStep noClickIconoVotf(String nombrePagoTpvVOTF) throws Exception {        
        DatosStep datosStep = null;
        
        //Step. No hacemos nada pues el pago con TPV en VOTF no tiene icono ni pasarelas asociadas
        datosStep = new DatosStep       (
            "<b>" + nombrePagoTpvVOTF + "</b>: no clickamos el icono pues no existe", 
            "No aplica");
        try {
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }         
            
        return datosStep;
    }
    
    /**
     * Realiza una navegación (conjunto de pasos/validaciones) mediante la que se selecciona el método de envío y finalmente el método de pago 
     */
    public static void fluxSelectEnvioAndClickPaymentMethod(DataCtxPago dCtxPago, DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
        boolean pagoPintado = false;
        if (!dCtxPago.getFTCkout().isChequeRegalo) {
            pagoPintado = fluxSelectEnvio(dCtxPago, dCtxSh, dFTest);
        }
        
        PageCheckoutWrapperStpV.forceClickIconoPagoAndWait(dCtxSh.pais, dCtxPago.getDataPedido().getPago(), dCtxSh.channel, !pagoPintado, dFTest.driver);
    }
    
    public static boolean fluxSelectEnvio(DataCtxPago dCtxPago, DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
        boolean pagoPintado = false;
        Pago pago = dCtxPago.getDataPedido().getPago();
        if (pago.getTipoEnvio(dCtxSh.appE)!=null) {
            String nombrePago = dCtxPago.getDataPedido().getPago().getNombre(dCtxSh.channel);
            selectMetodoEnvio(dCtxPago, nombrePago, dCtxSh.appE, dCtxSh.channel, dFTest);
            pagoPintado = true;
            TipoTransporte tipoEnvio = pago.getTipoEnvioType(dCtxSh.appE);
            if (tipoEnvio.isDroppoint())
            	ModalDroppointsStpV.fluxSelectDroppoint(dCtxPago, dCtxSh, dFTest);
            
            if (tipoEnvio.isFranjaHoraria())
            	selectFranjaHorariaUrgente(dCtxSh.channel, dFTest);
        }        
        
        return pagoPintado;
    }
    
    public static void selectFranjaHorariaUrgente(Channel channel, DataFmwkTest dFTest) {
        switch (channel) {
        case desktop:
            SecMetodoEnvioDesktopStpV.selectFranjaHorariaUrgente(1, dFTest.driver);
            break;
        case movil_web:
            Page1EnvioCheckoutMobilStpV.selectFranjaHorariaUrgente(1, dFTest);
        }    
    }

    public static void selectMetodoEnvio(DataCtxPago dCtxPago, String nombrePago, AppEcom appE, Channel channel, DataFmwkTest dFTest) 
    throws Exception {
        alterTypeEnviosAccordingContext(dCtxPago, appE, channel, dFTest.driver);
        Pago pago = dCtxPago.getDataPedido().getPago();
        TipoTransporte tipoTransporte = pago.getTipoEnvioType(appE);
        switch (channel) {
        case desktop:
            SecMetodoEnvioDesktopStpV.selectMetodoEnvio(tipoTransporte, nombrePago, dCtxPago, dFTest.driver);
            break;
        case movil_web:
            Page1EnvioCheckoutMobilStpV.selectMetodoEnvio(tipoTransporte, nombrePago, dCtxPago, dFTest);
            break;
        }
    }
    
    /**
     * No tenemos posibilidad sencilla de determinar si nos aparecerá el envío de tipo "Urgente" o "SendayNextday" así que si no encontramos uno ejecutamos la prueba con el otro
     */
    private static void alterTypeEnviosAccordingContext(DataCtxPago dCtxPago, AppEcom appE, Channel channel, WebDriver driver) {
    	alterTypeEnviosTiendaStandar(dCtxPago, appE);
    	Pago pago = dCtxPago.getDataPedido().getPago();
        alterTypeEnviosNextaySomedayUntilExists(pago, appE, channel, driver);
    }
    
    private static void alterTypeEnviosTiendaStandar(DataCtxPago dCtxPago, AppEcom appE) {
        //If employee and Spain not "Recogida en Tienda"
        Pago pago = dCtxPago.getDataPedido().getPago();
        if (dCtxPago.getFTCkout().isEmpl && 
            "001".compareTo(dCtxPago.getDataPedido().getCodigoPais())==0) {
            if (pago.getTipoEnvioType(appE)==TipoTransporte.TIENDA) {
                pago.setTipoEnvioShop(TipoTransporte.STANDARD);
                pago.setTipoEnvioOutlet(TipoTransporte.STANDARD);
            	pLogger.info("Modificado tipo de envío: " + pago.getTipoEnvioType(appE) + " -> " + TipoTransporte.STANDARD);
            }
            
            //Esto no está muy claro si es correcto, pero la configuración en Manto de los transportes dice que en el caso de Outlet los 
            //empleados no tienen PickPoint así que nos ceñimos a ella.
            if (appE==AppEcom.outlet &&
                pago.getTipoEnvioType(appE)==TipoTransporte.ASM)
                pago.setTipoEnvioOutlet(TipoTransporte.STANDARD);
        }    	
    }
    
    private static void alterTypeEnviosNextaySomedayUntilExists(Pago pago, AppEcom appE, Channel channel, WebDriver driver) {
        //Estos tipos de pago se intercambian constantemente a nivel de configuración en la shop
        if (pago.getTipoEnvioType(appE)==TipoTransporte.NEXTDAY ||
        	pago.getTipoEnvioType(appE)==TipoTransporte.NEXTDAY_FRANJAS ||
        	pago.getTipoEnvioType(appE)==TipoTransporte.SAMEDAY ||
        	pago.getTipoEnvioType(appE)==TipoTransporte.SAMEDAY_NEXTDAY_FRANJAS) {
        	for (int i=0; i<4; i++) {
	            if (PageCheckoutWrapper.isPresentBlockMetodo(pago.getTipoEnvioType(appE), channel, driver))
	            	break;
	            
            	switch (pago.getTipoEnvioType(appE)) {
            	case NEXTDAY:
                    pago.setTipoEnvioShop(TipoTransporte.NEXTDAY_FRANJAS);
                    break;
            	case NEXTDAY_FRANJAS:
                    pago.setTipoEnvioShop(TipoTransporte.SAMEDAY_NEXTDAY_FRANJAS);
                    break;
            	case SAMEDAY_NEXTDAY_FRANJAS:
                    pago.setTipoEnvioShop(TipoTransporte.SAMEDAY);
                    break;
               	case SAMEDAY:
               	default:
                    pago.setTipoEnvioShop(TipoTransporte.NEXTDAY_FRANJAS);
                    break;                    
            	}
            }
        }
    }
    
    /**
     * Paso consistente en clickar un determinado método de pago de la página de resumen de artículos (precompra)
     */
    @Step (
    	description="Seleccionamos el icono/pestaña correspondiente al método de pago y esperamos la desaparición de los \"loading\"",
    	expected="La operación se ejecuta correctamente")
    public static void forceClickIconoPagoAndWait(Pais pais, Pago pago, Channel channel, boolean pintaNombrePago, WebDriver driver) throws Exception {
        if (pintaNombrePago) {
            String pintaPago = "<b style=\"color:blue;\">" + pago.getNombre(channel) + "</b>:"; 
            String newDescription = pintaPago + TestCaseData.getDatosCurrentStep().getDescripcion();
            TestCaseData.getDatosCurrentStep().setDescripcion(newDescription);
        }

        try {
            PageCheckoutWrapper.forceClickMetodoPagoAndWait(pago.getNombre(channel), pago.getIndexpant(), pais, channel, driver);
        }
        catch (Exception e) {
            pLogger.warn("Problem clicking icono pago for payment {} in country {}", pago.getNombre(), pais.getNombre_pais(), e);
        }

        if (pago.getTypePago()==TypePago.TarjetaIntegrada || 
            pago.getTypePago()==TypePago.KrediKarti ||
            pago.getTypePago()==TypePago.Bancontact) {
            PageCheckoutWrapperStpV.validateSelectPagoTRJintegrada(pago, pais, channel, driver);
        }
        else {
            PageCheckoutWrapperStpV.validateSelectPagoNoTRJintegrada(pago, channel, driver);
        }
    }
    
    public static void validateSelectPagoTRJintegrada(Pago pago, Pais pais, Channel channel, WebDriver driver) {
        if (channel==Channel.desktop) {
            validateIsPresentButtonCompraDesktop(driver);
        }
        
        PageCheckoutWrapperStpV.secTarjetaPci.validateIsSectionOk(pago, pais, channel, driver);
    }
    
    public static void validateSelectPagoNoTRJintegrada(Pago pago, Channel channel, WebDriver driver) {
        if (channel==Channel.desktop) {
            validateIsPresentButtonCompraDesktop(driver);
        }

        int maxSecondsWait = 2;
        checkIsVisibleTextUnderPayment(pago.getNombre(channel), pago, maxSecondsWait, channel, driver);
    }
    
    @Validation (
    	description="Se hace visible el texto bajo el método de pago: #{nombrePago} (lo esperamos hasta #{maxSecondsWait} segundos)",
    	level=State.Warn)
    private static boolean checkIsVisibleTextUnderPayment(@SuppressWarnings("unused") String nombrePago, Pago pago, int maxSecondsWait, Channel channel, WebDriver driver) {
        return (PageCheckoutWrapper.isVisibleBloquePagoNoTRJIntegradaUntil(pago, channel, maxSecondsWait, driver));
    }
    
    @Validation (
    	description="Aparece el botón de \"Confirmar Compra\"",
    	level=State.Defect)
    public static boolean validateIsPresentButtonCompraDesktop(WebDriver driver) {
        return (PageCheckoutWrapper.page1DktopCheckout.isPresentButtonConfPago(driver));
    }
    
    public static DatosStep inputDataTrjAndConfirmPago(DataCtxPago dCtxPago, Channel channel, DataFmwkTest dFTest) 
    throws Exception {
        Pago pago = dCtxPago.getDataPedido().getPago();
        String descripcionStep = "Introducimos los datos de la tarjeta (" + pago.getTipotarj() + ") " + pago.getNumtarj() + " y pulsamos el botón \"Confirmar pago\"";
        if (dCtxPago.getFTCkout().trjGuardada)
            descripcionStep = "Seleccionamos la tarjeta guardada y pulsamos el botón \"Confirmar pago\""; 
        
        DatosStep datosStep = new DatosStep       (
            descripcionStep, 
            "Aparece la página de resultado OK");
        try {
            if (dCtxPago.getFTCkout().trjGuardada)
                PageCheckoutWrapper.clickRadioTrjGuardada(channel, dFTest.driver);
            else {
                if (pago.getNumtarj()!=null && "".compareTo(pago.getNumtarj())!=0)
                    PageCheckoutWrapper.inputNumberPci(pago.getNumtarj(), channel, dFTest.driver);
                
                PageCheckoutWrapper.inputTitularPci(pago.getTitular(), channel, dFTest.driver);
                
                if (pago.getMescad()!=null && "".compareTo(pago.getMescad())!=0)
                    PageCheckoutWrapper.selectMesByVisibleTextPci(pago.getMescad(), channel, dFTest.driver);
                
                if (pago.getAnycad()!=null && "".compareTo(pago.getAnycad())!=0)
                    PageCheckoutWrapper.selectAnyByVisibleTextPci(pago.getAnycad(), channel, dFTest.driver);
                
                if (pago.getCvc()!=null && "".compareTo(pago.getCvc())!=0)
                    PageCheckoutWrapper.inputCvcPci(pago.getCvc(), channel, dFTest.driver);
                
                if (pago.getDni()!=null && "".compareTo(pago.getDni())!=0)
                    PageCheckoutWrapper.inputDniPci(pago.getDni(), channel, dFTest.driver);                
            }

            PageCheckoutWrapper.confirmarPagoFromMetodos(channel, dCtxPago.getDataPedido(), dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
        
        //Validaciones
        PageRedirectPasarelaLoadingStpV.validateDisappeared(datosStep, dFTest);
        
        return datosStep;
    }
    
    public static DatosStep clickSolicitarFactura(Channel channel, DataFmwkTest dFTest) {
        //Step
        DatosStep datosStep = new DatosStep (
            "Seleccionar el radiobutton \"Quiero recibir una factura\"", 
            "Aparece el modal para la introducción de la dirección de facturación");
        try {
            PageCheckoutWrapper.clickSolicitarFactura(channel, dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }

        //Validaciones
        modalDirecFactura.validateIsOk(datosStep, dFTest);
        
        return datosStep;
    }
    
    public static DatosStep clickEditarDirecEnvio(DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep (
            "Seleccionar el botón \"Editar\" asociado a la Dirección de Envío", 
            "Aparece el modal para la introducción de la dirección de envío");
        try {
            PageCheckoutWrapper.clickEditDirecEnvio(Channel.desktop, dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }

        //Validaciones
        modalDirecEnvio.validateIsOk(dFTest.driver);
        
        return datosStep;
    }
    
    //Seleccionar el botón necesario para aceptar la compra
    public static DatosStep pasoBotonAceptarCompraDesktop(DataFmwkTest dFTest) throws Exception {
        DatosStep datosStep = new DatosStep (
            "Seleccionamos el botón \"Confirmar Pago\"", 
            "Aparece una pasarela de pago");
        datosStep.setSaveImagePage(SaveWhen.Always);
        try {
            PageCheckoutWrapper.page1DktopCheckout.clickConfirmarPago(dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        catch (Exception e) {
            //
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }

        return datosStep;
    }

    //Seleccionar el botón "Ver resumen" de la página-2 de checkout (2. Datos de pago) de móvil 
    public static DatosStep pasoBotonVerResumenCheckout2Mobil(DataFmwkTest dFTest) throws Exception {
        int maxSecondsToWait = 2;
        DatosStep datosStep = new DatosStep       (
            "Seleccionamos el botón \"Ver resumen\" (lo esperamos " + maxSecondsToWait + " segundos)", 
            "Aparece la página-3 del checkout");
        datosStep.setSaveImagePage(SaveWhen.Always);
        try {
            PageCheckoutWrapper.page2MobilCheckout.waitAndClickVerResumen(maxSecondsToWait, dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        catch (Exception e) {
            //
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }

        //Validaciones
        maxSecondsToWait = 2;
        String descripValidac = "1) Aparece el botón de \"Confirmar Pago\" (esperamos hasta " + maxSecondsToWait + " segundos)";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try { 
            if (!PageCheckoutWrapper.page3MobilCheckout.isClickableButtonConfirmarPagoUntil(maxSecondsToWait, dFTest.driver)) {
                listVals.add(1,State.Warn);
            }
                                            
            datosStep.setListResultValidations(listVals); 
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
                            
        return datosStep;
    }       
            
    /**
     * Seleccionar el botón "Confirmar pago" de la página-3 de checkout (3. Resumen) de móvil
     */
    public static DatosStep pasoBotonConfirmarPagoCheckout3Mobil(DataFmwkTest dFTest) throws Exception {
        int maxSecondsToWait = 20;
        DatosStep datosStep = new DatosStep (
            "Seleccionamos el botón \"Confirmar pago\" (esperamos hasta " + maxSecondsToWait + " a que desaparezca la capa \"Espera unos segundos...\")", 
            "Aparece una pasarela de pago");
        datosStep.setSaveImagePage(SaveWhen.Always);
        try {
            PageCheckoutWrapper.page3MobilCheckout.clickConfirmaPagoAndWait(maxSecondsToWait, dFTest.driver);
                                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        catch (Exception e) {
            pLogger.warn("Problem in click Confirm payment button", e);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
                            
        //Validaciones
        PageRedirectPasarelaLoadingStpV.validateDisappeared(datosStep, dFTest);

        return datosStep;
    }    
    
    public static DatosStep inputTarjetaEmplEnCodPromo(Pais pais, Channel channel, DataFmwkTest dFTest) throws Exception {
        //Step.
        DatosStep datosStep = new DatosStep     (
            "Introducir la tarjeta de empleado " + pais.getAccesoEmpl().getTarjeta() + " y pulsar el botón \"Aplicar\"", 
            "Aparecen los datos para la introducción del 1er apellido y el nif");
        try {   
            PageCheckoutWrapper.inputCodigoPromoAndAccept(pais.getAccesoEmpl().getTarjeta(), channel, dFTest.driver);
        
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
        
        //Validaciones
        int maxSecondsToWait = 5;
        String descripcion2 = "";
        if (pais.getAccesoEmpl().getNif()!=null) 
            descripcion2 = "<br>2) SÍ Aparece el campo de introducción del DNI/Pasaporte";
        else
            descripcion2 = "<br>2) NO Aparece el campo de introducción del DNI/Pasaporte";
        
        String descripcion3 = "";
        if (pais.getAccesoEmpl().getFecnac()!=null) 
            descripcion3 = "<br>3) SÍ Aparece el campo de introducción de la fecha de nacimiento";
        else
            descripcion3 = "<br>3) NO Aparece el campo de introducción de la fecha de nacimiento";
    
        String descripValidac = 
            "1) Aparece el campo de introducción del primer apellido (lo esperamos hasta " + maxSecondsToWait + " segundos)" +
            descripcion2 +
            descripcion3;
        datosStep.setNOKstateByDefault();  
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageCheckoutWrapper.isPresentInputApellidoPromoEmplUntil(channel, maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if (pais.getAccesoEmpl().getNif()!=null) { 
                if (!PageCheckoutWrapper.isPresentInputDNIPromoEmpl(channel, dFTest.driver)) {
                    listVals.add(2,State.Defect);
                }
            }
            else {
                if (PageCheckoutWrapper.isPresentInputDNIPromoEmpl(channel, dFTest.driver)) {
                    listVals.add(2,State.Defect);
                }
            }
            if (pais.getAccesoEmpl().getFecnac()!=null) { 
                if (!PageCheckoutWrapper.isPresentDiaNaciPromoEmpl(channel, dFTest.driver)) {
                    listVals.add(3, State.Defect);
                }
            }
            else {
                if (PageCheckoutWrapper.isPresentDiaNaciPromoEmpl(channel, dFTest.driver)) {
                    listVals.add(3, State.Defect);
                }
            }
    
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        return datosStep;
    }
    
    public static DatosStep inputDataEmplEnPromoAndAccept(DataBag dataBag, Pais pais, Channel channel, AppEcom app, DataFmwkTest dFTest) 
    throws Exception {
        //Step.
        String descrStep = "";
        if (pais.getAccesoEmpl().getNif()!=null) 
            descrStep = "Introducir el NIF del usuario " + pais.getAccesoEmpl().getNif() + ". ";
        
        if (pais.getAccesoEmpl().getFecnac()!=null) 
            descrStep += "Introducir la fecha de nacimiento " + pais.getAccesoEmpl().getFecnac() + ". ";
        
        String primerApellido = (new StringTokenizer(pais.getAccesoEmpl().getNombre(), " ")).nextToken();
    
        DatosStep datosStep = new DatosStep       (
            descrStep + "Introducir el primer apellido " + primerApellido + " y pulsar el botón \"Guardar\"", 
            "Se aplican los descuentos correctamente");
        try {
            if (pais.getAccesoEmpl().getNif()!=null)
                PageCheckoutWrapper.inputDNIPromoEmpl(pais.getAccesoEmpl().getNif(), channel, dFTest.driver);
                
            PageCheckoutWrapper.inputApellidoPromoEmpl(primerApellido, channel, dFTest.driver);
            
            if (pais.getAccesoEmpl().getFecnac()!=null)
                PageCheckoutWrapper.selectFechaNacPromoEmpl(pais.getAccesoEmpl().getFecnac(), channel, dFTest.driver);  
            
            PageCheckoutWrapper.clickButtonAceptarPromoEmpl(channel, dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
        
        //Validaciones
        validaResultImputPromoEmpl(dataBag, channel, app, datosStep, dFTest);
        
        return datosStep;
    }
        
    public static void validaResultImputPromoEmpl(DataBag dataBag, Channel channel, AppEcom app, DatosStep datosStep, DataFmwkTest dFTest) 
    throws Exception {
        if (channel==Channel.movil_web) {
            Page1EnvioCheckoutMobilStpV.validaResultImputPromoEmpl(datosStep, dFTest);
        }
        else {
            Page1DktopCheckoutStpV.validaResultImputPromoEmpl(dataBag, app, datosStep, dFTest);
        }
    }    
    
    public static void validaIsVersionChequeRegalo(ChequeRegalo chequeRegalo, DatosStep datosStep, DataFmwkTest dFTest) {
        Page1DktopCheckoutStpV.validateIsVersionChequeRegalo(chequeRegalo, datosStep, dFTest);
    }

    
	public static DatosStep selectBancoEPS(DataCtxShop dCtxSh, DataFmwkTest dFTest) throws Exception {
        //Step.
		String nombreBanco = "Easybank";
		if (!UtilsMangoTest.isEntornoPRO(dCtxSh.appE, dFTest))
			nombreBanco = "Test Issuer";
		
        DatosStep datosStep = new DatosStep       (
            "Escogemos el banco \"" + nombreBanco + "\" en la pestaña de selección", 
            "El banco aparece seleccionado");
        try {
        	
            PageCheckoutWrapper.selectBancoEPS(nombreBanco, dFTest);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
        
      //Validaciones
        String descripValidac = 
                "1) Aparece el banco \"" + nombreBanco + "\" en el cuadro de selección";
        datosStep.setNOKstateByDefault();      
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageCheckoutWrapper.isBancoSeleccionado(nombreBanco, dFTest)) {
                listVals.add(1, State.Defect);
            }
    
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        return datosStep;
		
	}
}