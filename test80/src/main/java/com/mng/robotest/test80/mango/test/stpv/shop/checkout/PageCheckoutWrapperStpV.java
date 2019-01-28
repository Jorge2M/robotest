package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
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

@SuppressWarnings({"javadoc", "static-access"})
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
    
    public static void validateIsFirstPage(boolean userLogged, DataBag dataBag, Channel channel, AppEcom app, DatosStep datosStep, DataFmwkTest dFTest) 
    throws Exception {
        if (channel==Channel.movil_web)
            page1MobilCheck.validateIsPage(userLogged, datosStep, dFTest);
        else
            page1DktopCheck.validateIsPageOK(dataBag, app, datosStep, dFTest);
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
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        //Validaciones (validamos los métodos de pago disponibles)
        validaMetodosPagoDisponibles(datosStep, pais, isEmpl, app, channel, dFTest);
        
        return datosStep;
    }
    
    /**
     * Valida los métodos de pago disponibles en la página de checkout
     */
    public static void validaMetodosPagoDisponibles(DatosStep datosStep, Pais pais, boolean isEmpl, AppEcom app, Channel channel, DataFmwkTest dFTest) {
        //Validaciones
        String descripValidac = 
            "1) El número de pagos disponibles, logos tarjetas, coincide con el de asociados al país (" + pais.getListPagosEnOrdenPantalla(app, isEmpl).size() + ")";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageCheckoutWrapper.isNumMetodosPagoOK(pais, app, channel, isEmpl, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        catch (Exception e) {
            /*
             * 
             */
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        //Validaciones
        descripValidac = 
            "1) Aparece un logo/pestaña por cada uno de los pagos asociados al país: " + pais.getStringPagosEnOrdenPantalla(app, isEmpl);
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            List<Pago> listaPagosEnOrden = pais.getListPagosEnOrdenPantalla(app, isEmpl);
            for (int i=0; i<listaPagosEnOrden.size(); i++) {
                if (listaPagosEnOrden.get(i).getTypePago()!=TypePago.TpvVotf) {
                    if (!PageCheckoutWrapper.isMetodoPagoPresent(listaPagosEnOrden.get(i).getNombre(channel), listaPagosEnOrden.get(i).getIndexpant(), channel, pais.getLayoutPago(), dFTest.driver)) {                                 
                        fmwkTest.addValidation(1, State.Defect, listVals); 
                        break;
                    }
                }
            }

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals); 
        }
        catch (Exception e) {
            /*
             * 
             */
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }            
    }
    
    /**
     * Paso ficticio consistente en "no-clicar" el icono de VOTF.
     */
    public static DatosStep noClickIconoVotf(String nombrePagoTpvVOTF, DataFmwkTest dFTest) throws Exception {        
        DatosStep datosStep = null;
        
        //Step. No hacemos nada pues el pago con TPV en VOTF no tiene icono ni pasarelas asociadas
        datosStep = new DatosStep       (
            "<b>" + nombrePagoTpvVOTF + "</b>: no clickamos el icono pues no existe", 
            "No aplica");
        try {
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }         
            
        return datosStep;
    }
    
    /**
     * Realiza una navegación (conjunto de pasos/validaciones) mediante la que se selecciona el método de envío y finalmente el método de pago 
     */
    public static DatosStep fluxSelectEnvioAndClickPaymentMethod(DataCtxPago dCtxPago, DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
        boolean pagoPintado = false;
        if (!dCtxPago.getFTCkout().isChequeRegalo)
            pagoPintado = fluxSelectEnvio(dCtxPago, dCtxSh, dFTest);
        
        return PageCheckoutWrapperStpV.forceClickIconoPagoAndWait(dCtxSh.pais, dCtxPago.getDataPedido().getPago(), dCtxSh.channel, !pagoPintado, dFTest);
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
    
    public static DatosStep selectFranjaHorariaUrgente(Channel channel, DataFmwkTest dFTest) {
        switch (channel) {
        case desktop:
            return SecMetodoEnvioDesktopStpV.selectFranjaHorariaUrgente(1, dFTest);
        case movil_web:
            return Page1EnvioCheckoutMobilStpV.selectFranjaHorariaUrgente(1, dFTest);
        default:
            return null;
        }    
    }

    public static DatosStep selectMetodoEnvio(DataCtxPago dCtxPago, String nombrePago, AppEcom appE, Channel channel, DataFmwkTest dFTest) 
    throws Exception {
        alterTypeEnviosAccordingContext(dCtxPago, appE, channel, dFTest.driver);
        Pago pago = dCtxPago.getDataPedido().getPago();
        TipoTransporte tipoTransporte = pago.getTipoEnvioType(appE);
        switch (channel) {
        case desktop:
            return SecMetodoEnvioDesktopStpV.selectMetodoEnvio(tipoTransporte, nombrePago, dCtxPago, dFTest);
        case movil_web:
            return Page1EnvioCheckoutMobilStpV.selectMetodoEnvio(tipoTransporte, nombrePago, dCtxPago, dFTest);
        default:
            return null;
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
    public static DatosStep forceClickIconoPagoAndWait(Pais pais, Pago pago, Channel channel, boolean pintaNombrePago, DataFmwkTest dFTest) throws Exception {
        String pintaPago = "";
        if (pintaNombrePago)
            pintaPago = "<b style=\"color:blue;\">" + pago.getNombre(channel) + "</b>:"; 
        DatosStep datosStep = new DatosStep     (
            pintaPago + "Seleccionamos el icono/pestaña correspondiente al método de pago y esperamos la desaparición de los \"loading\"", 
            "La operación se ejecuta correctamente");
        try {
            PageCheckoutWrapper.forceClickMetodoPagoAndWait(pago.getNombre(channel), pago.getIndexpant(), pais, channel, dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        catch (Exception e) {
            pLogger.warn("Problem clicking icono pago for payment {} in country {}", pago.getNombre(), pais.getNombre_pais(), e);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        if (pago.getTypePago()==TypePago.TarjetaIntegrada || 
            pago.getTypePago()==TypePago.KrediKarti ||
            pago.getTypePago()==TypePago.Bancontact)
            PageCheckoutWrapperStpV.validateSelectPagoTRJintegrada(pago, pais, channel, datosStep, dFTest);
        else
            PageCheckoutWrapperStpV.validateSelectPagoNoTRJintegrada(pago, channel, datosStep, dFTest);
        
        return datosStep;
    }
    
    public static void validateSelectPagoTRJintegrada(Pago pago, Pais pais, Channel channel, DatosStep datosStep, DataFmwkTest dFTest) {
        if (channel==Channel.desktop)
            validateIsPresentButtonCompraDesktop(datosStep, dFTest);
        
        PageCheckoutWrapperStpV.secTarjetaPci.validateIsSectionOk(pago, pais, channel, datosStep, dFTest);
    }
    
    public static void validateSelectPagoNoTRJintegrada(Pago pago, Channel channel, DatosStep datosStep, DataFmwkTest dFTest) {
        if (channel==Channel.desktop)
            validateIsPresentButtonCompraDesktop(datosStep, dFTest);

        int maxSecondsToWait = 2;
        String descripValidac = 
            "1) Se hace visible el texto bajo el método de pago: " + pago.getNombre(channel) + " (lo esperamos hasta " + maxSecondsToWait + " segundos)";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageCheckoutWrapper.isVisibleBloquePagoNoTRJIntegradaUntil(pago, channel, maxSecondsToWait, dFTest.driver)) 
                fmwkTest.addValidation(1, State.Warn, listVals);                    

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        catch (Exception e) {
            /*
             * 
             */
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static void validateIsPresentButtonCompraDesktop(DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
        "1) Aparece el botón de \"Confirmar Compra\"";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
    
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageCheckoutWrapper.page1DktopCheckout.isPresentButtonConfPago(dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        catch (Exception e) {
            //
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
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
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
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
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

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
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        //Validaciones
        modalDirecEnvio.validateIsOk(datosStep, dFTest);
        
        return datosStep;
    }
    
    //Seleccionar el botón necesario para aceptar la compra
    public static DatosStep pasoBotonAceptarCompraDesktop(DataFmwkTest dFTest) throws Exception {
        DatosStep datosStep = new DatosStep (
            "Seleccionamos el botón \"Confirmar Pago\"", 
            "Aparece una pasarela de pago");
        datosStep.setGrabImage(true);
        try {
            PageCheckoutWrapper.page1DktopCheckout.clickConfirmarPago(dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        catch (Exception e) {
            //
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        return datosStep;
    }

    //Seleccionar el botón "Ver resumen" de la página-2 de checkout (2. Datos de pago) de móvil 
    public static DatosStep pasoBotonVerResumenCheckout2Mobil(DataFmwkTest dFTest) throws Exception {
        int maxSecondsToWait = 2;
        DatosStep datosStep = new DatosStep       (
            "Seleccionamos el botón \"Ver resumen\" (lo esperamos " + maxSecondsToWait + " segundos)", 
            "Aparece la página-3 del checkout");
        datosStep.setGrabImage(true);
        try {
            PageCheckoutWrapper.page2MobilCheckout.waitAndClickVerResumen(maxSecondsToWait, dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        catch (Exception e) {
            //
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        //Validaciones
        maxSecondsToWait = 2;
        String descripValidac = "1) Aparece el botón de \"Confirmar Pago\" (esperamos hasta " + maxSecondsToWait + " segundos)";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);                 
        try { 
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageCheckoutWrapper.page3MobilCheckout.isClickableButtonConfirmarPagoUntil(maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1,State.Warn, listVals);
                                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals); 
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest);  }
                            
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
        datosStep.setGrabImage(true);
        try {
            PageCheckoutWrapper.page3MobilCheckout.clickConfirmaPagoAndWait(maxSecondsToWait, dFTest.driver);
                                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        catch (Exception e) {
            pLogger.warn("Problem in click Confirm payment button", e);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
                            
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
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
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
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageCheckoutWrapper.isPresentInputApellidoPromoEmplUntil(channel, maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (pais.getAccesoEmpl().getNif()!=null) { 
                if (!PageCheckoutWrapper.isPresentInputDNIPromoEmpl(channel, dFTest.driver)) 
                    fmwkTest.addValidation(2,State.Defect, listVals);
            }
            else {
                if (PageCheckoutWrapper.isPresentInputDNIPromoEmpl(channel, dFTest.driver))
                    fmwkTest.addValidation(2,State.Defect, listVals);
            }
            //3)
            if (pais.getAccesoEmpl().getFecnac()!=null) { 
                if (!PageCheckoutWrapper.isPresentDiaNaciPromoEmpl(channel, dFTest.driver))
                    fmwkTest.addValidation(3, State.Defect, listVals);
            }
            else {
                if (PageCheckoutWrapper.isPresentDiaNaciPromoEmpl(channel, dFTest.driver))
                    fmwkTest.addValidation(3, State.Defect, listVals);
            }
    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
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
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validaciones
        validaResultImputPromoEmpl(dataBag, channel, app, datosStep, dFTest);
        
        return datosStep;
    }
        
    public static void validaResultImputPromoEmpl(DataBag dataBag, Channel channel, AppEcom app, DatosStep datosStep, DataFmwkTest dFTest) 
    throws Exception {
        if (channel==Channel.movil_web)
            Page1EnvioCheckoutMobilStpV.validaResultImputPromoEmpl(dataBag, app, datosStep, dFTest);
        else
            Page1DktopCheckoutStpV.validaResultImputPromoEmpl(dataBag, app, datosStep, dFTest);
    }    
    
    public static void validaIsVersionChequeRegalo(ChequeRegalo chequeRegalo, DatosStep datosStep, DataFmwkTest dFTest) {
        Page1DktopCheckoutStpV.validateIsVersionChequeRegalo(chequeRegalo, datosStep, dFTest);
    }

    
	public static DatosStep selectBancoEPS(DataCtxPago dCtxPago, DataCtxShop dCtxSh, DataFmwkTest dFTest) throws Exception {
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
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
      //Validaciones
        String descripValidac = 
                "1) Aparece el banco \"" + nombreBanco + "\" en el cuadro de selección";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageCheckoutWrapper.isBancoSeleccionado(nombreBanco, dFTest))
                fmwkTest.addValidation(1, State.Defect, listVals);
    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        return datosStep;
		
	}
}