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
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
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
        } else {
            page1DktopCheck.validateIsPageOK(dataBag, driver);
        }
    } 
    
    @Validation (
    	description="Acaba desapareciendo la capa de \"Cargando...\" (lo esperamos hasta #{maxSecondsWait} segundos)",
    	level=State.Warn)
    public static boolean validateLoadingDisappears(int maxSecondsWait, WebDriver driver) throws Exception {
        Thread.sleep(200); //Damos tiempo a que aparezca la capa de "Cargando"
        return (PageCheckoutWrapper.isNoDivLoadingUntil(maxSecondsWait, driver));
    }
    
    @Step (
    	description="Si existen y están plegados, desplegamos el bloque con los métodos de pago", 
        expected="Aparecen los métodos de pagos asociados al país")
    public static void despliegaYValidaMetodosPago(Pais pais, boolean isEmpl, AppEcom app, Channel channel, WebDriver driver) 
    throws Exception {
    	TestCaseData.getDatosCurrentStep().addExpectedText(": " + pais.getStringPagosTest(app, isEmpl));
        PageCheckoutWrapper.despliegaMetodosPago(channel, driver);
        validaMetodosPagoDisponibles(pais, isEmpl, app, channel, driver);
    }
    
    public static void validaMetodosPagoDisponibles(Pais pais, boolean isEmpl, AppEcom app, Channel channel, WebDriver driver) {
    	checkAvailablePagos(pais, isEmpl, app, channel, driver);
    	checkLogosPagos(pais, isEmpl, app, channel, driver);
    }
    
    @Validation
    private static ChecksResult checkAvailablePagos(Pais pais, boolean isEmpl, AppEcom app, Channel channel, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"El número de pagos disponibles, logos tarjetas, coincide con el de asociados al país (" + pais.getListPagosEnOrdenPantalla(app, isEmpl).size() + ")",
			PageCheckoutWrapper.isNumMetodosPagoOK(pais, app, channel, isEmpl, driver), State.Defect);    	
    	return validations;
    }
    
    @Validation
    private static ChecksResult checkLogosPagos(Pais pais, boolean isEmpl, AppEcom app, Channel channel, WebDriver driver) { 
    	ChecksResult validations = ChecksResult.getNew();
        List<Pago> listaPagosEnOrden = pais.getListPagosEnOrdenPantalla(app, isEmpl);
        for (int i=0; i<listaPagosEnOrden.size(); i++) {
            if (listaPagosEnOrden.get(i).getTypePago()!=TypePago.TpvVotf) {
        	 	validations.add(
        			"Aparece el logo/pestaña asociado al pago <b>" + listaPagosEnOrden.get(i).getNombre(channel) + "</b>",
        			PageCheckoutWrapper.isMetodoPagoPresent(listaPagosEnOrden.get(i).getNombre(channel), listaPagosEnOrden.get(i).getIndexpant(), channel, pais.getLayoutPago(), driver),
        			State.Defect);    
            }
        }   
        
        return validations;
    }
    
    @Step (
    	description="<b>#{nombrePagoTpvVOTF}</b>: no clickamos el icono pues no existe", 
        expected="No aplica")
    public static void noClickIconoVotf(@SuppressWarnings("unused") String nombrePagoTpvVOTF) throws Exception {        
        //No hacemos nada pues el pago con TPV en VOTF no tiene icono ni pasarelas asociadas
    }
    
    /**
     * Realiza una navegación (conjunto de pasos/validaciones) mediante la que se selecciona el método de envío y finalmente el método de pago 
     */
    public static void fluxSelectEnvioAndClickPaymentMethod(DataCtxPago dCtxPago, DataCtxShop dCtxSh, WebDriver driver) 
    throws Exception {
        boolean pagoPintado = false;
        if (!dCtxPago.getFTCkout().isChequeRegalo) {
            pagoPintado = fluxSelectEnvio(dCtxPago, dCtxSh);
        }
        
        PageCheckoutWrapperStpV.forceClickIconoPagoAndWait(dCtxSh.pais, dCtxPago.getDataPedido().getPago(), dCtxSh.channel, !pagoPintado, driver);
    }
    
    public static boolean fluxSelectEnvio(DataCtxPago dCtxPago, DataCtxShop dCtxSh) 
    throws Exception {
        boolean pagoPintado = false;
        Pago pago = dCtxPago.getDataPedido().getPago();
        DataFmwkTest dFTest = TestCaseData.getdFTest();
        if (pago.getTipoEnvio(dCtxSh.appE)!=null) {
            String nombrePago = dCtxPago.getDataPedido().getPago().getNombre(dCtxSh.channel);
            selectMetodoEnvio(dCtxPago, nombrePago, dCtxSh.appE, dCtxSh.channel, dFTest);
            pagoPintado = true;
            TipoTransporte tipoEnvio = pago.getTipoEnvioType(dCtxSh.appE);
            if (tipoEnvio.isDroppoint()) {
            	ModalDroppointsStpV.fluxSelectDroppoint(dCtxPago, dCtxSh, dFTest.driver);
            }
            if (tipoEnvio.isFranjaHoraria()) {
            	selectFranjaHorariaUrgente(dCtxSh.channel, dFTest);
            }
        }        
        
        return pagoPintado;
    }
    
    public static void selectFranjaHorariaUrgente(Channel channel, DataFmwkTest dFTest) {
        switch (channel) {
        case desktop:
            SecMetodoEnvioDesktopStpV.selectFranjaHorariaUrgente(1, dFTest.driver);
            break;
        case movil_web:
            Page1EnvioCheckoutMobilStpV.selectFranjaHorariaUrgente(1, dFTest.driver);
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
            Page1EnvioCheckoutMobilStpV.selectMetodoEnvio(tipoTransporte, nombrePago, dCtxPago, dFTest.driver);
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
	            if (PageCheckoutWrapper.isPresentBlockMetodo(pago.getTipoEnvioType(appE), channel, driver)) {
	            	break;
	            }
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
        } else {
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
    
    final static String tagTipoTarj = "@TagTipoTarj";
    final static String tagNumTarj = "@TagNumTarj";
    @Step (
    	description="Introducimos los datos de la tarjeta (" + tagTipoTarj + ") " + tagNumTarj + " y pulsamos el botón \"Confirmar pago\"",
    	expected="Aparece la página de resultado OK")
    public static void inputDataTrjAndConfirmPago(DataCtxPago dCtxPago, Channel channel, WebDriver driver) 
    throws Exception {
        Pago pago = dCtxPago.getDataPedido().getPago();
        DatosStep datosStep = TestCaseData.getDatosCurrentStep();
        datosStep.replaceInDescription(tagTipoTarj, pago.getTipotarj());
        datosStep.replaceInDescription(tagNumTarj, pago.getNumtarj());
       
    	PageCheckoutWrapper pageCheckout = new PageCheckoutWrapper();
        if (pago.getNumtarj()!=null && "".compareTo(pago.getNumtarj())!=0) {
        	pageCheckout.inputNumberPci(pago.getNumtarj(), channel, driver);
        }
        pageCheckout.inputTitularPci(pago.getTitular(), channel, driver);
        if (pago.getMescad()!=null && "".compareTo(pago.getMescad())!=0) {
        	pageCheckout.selectMesByVisibleTextPci(pago.getMescad(), channel, driver);
        }
        if (pago.getAnycad()!=null && "".compareTo(pago.getAnycad())!=0) {
        	pageCheckout.selectAnyByVisibleTextPci(pago.getAnycad(), channel, driver);
        }
        if (pago.getCvc()!=null && "".compareTo(pago.getCvc())!=0) {
        	pageCheckout.inputCvcPci(pago.getCvc(), channel, driver);
        }
        if (pago.getDni()!=null && "".compareTo(pago.getDni())!=0) {
        	pageCheckout.inputDniPci(pago.getDni(), channel, driver);   
        }

        PageCheckoutWrapper.confirmarPagoFromMetodos(channel, dCtxPago.getDataPedido(), driver);
        PageRedirectPasarelaLoadingStpV.validateDisappeared(5, driver);
    }
    
    @Validation (
    	description="Está disponible una tarjeta guardada de tipo #{tipoTarjeta}",
    	level=State.Warn)
    public static boolean isTarjetaGuardadaAvailable(String tipoTarjeta, Channel channel, WebDriver driver) {
    	return (PageCheckoutWrapper.isAvailableTrjGuardada(tipoTarjeta, channel, driver));
    }
    
    @Step (
    	description="Seleccionamos la tarjeta guardada y pulsamos el botón \"Confirmar pago\"",
    	expected="Aparece la página de resultado OK")
    public static void selectTrjGuardadaAndConfirmPago(DataCtxPago dCtxPago, Channel channel, WebDriver driver) 
    throws Exception {
        PageCheckoutWrapper.clickRadioTrjGuardada(channel, driver);
        PageCheckoutWrapper.confirmarPagoFromMetodos(channel, dCtxPago.getDataPedido(), driver);
        PageRedirectPasarelaLoadingStpV.validateDisappeared(5, driver);
    }
    
    @Step (
    	description="Seleccionar el radiobutton \"Quiero recibir una factura\"", 
        expected="Aparece el modal para la introducción de la dirección de facturación")
    public static void clickSolicitarFactura(Channel channel, WebDriver driver) {
        PageCheckoutWrapper.clickSolicitarFactura(channel, driver);
        modalDirecFactura.validateIsOk(driver);
    }
    
    @Step (
    	description="Seleccionar el botón \"Editar\" asociado a la Dirección de Envío", 
        expected="Aparece el modal para la introducción de la dirección de envío")
    public static void clickEditarDirecEnvio(WebDriver driver) throws Exception {
        PageCheckoutWrapper.clickEditDirecEnvio(Channel.desktop, driver);
        modalDirecEnvio.validateIsOk(driver);
    }
    
    @Step (
    	description="Seleccionamos el botón \"Confirmar Pago\"", 
        expected="Aparece una pasarela de pago",
        saveImagePage=SaveWhen.Always)
    public static void pasoBotonAceptarCompraDesktop(WebDriver driver) throws Exception {
    	PageCheckoutWrapper.page1DktopCheckout.clickConfirmarPago(driver);
    }

    @Step (
    	description="Seleccionamos el botón \"Ver resumen\"", 
        expected="Aparece la página-3 del checkout",
        saveImagePage=SaveWhen.Always)
    public static void pasoBotonVerResumenCheckout2Mobil(WebDriver driver) throws Exception {
        PageCheckoutWrapper.page2MobilCheckout.waitAndClickFinalizarCompra(2, driver);
        checkAfterClickVerResumen(2, driver);
    }       
    
    @Validation (
    	description="Aparece el botón de \"Confirmar Pago\" (esperamos hasta #{maxSecondsWait} segundos)",
    	level=State.Warn)
    private static boolean checkAfterClickVerResumen(int maxSecondsWait, WebDriver driver) {
        return (PageCheckoutWrapper.page2MobilCheckout.isClickableButtonFinalizarCompraUntil(maxSecondsWait, driver));
    }
            
    @Step (
    	description="Seleccionamos el botón \"Finalizar Compra\" (previamente esperamos hasta 20 segundos a que desaparezca la capa \"Espera unos segundos...\")", 
        expected="Aparece una pasarela de pago",
        saveImagePage=SaveWhen.Always)
    public static void pasoBotonConfirmarPagoCheckout3Mobil(WebDriver driver) throws Exception {
        try {
            int maxSecondsToWait = 20;
            PageCheckoutWrapper.page2MobilCheckout.clickFinalizarCompraAndWait(maxSecondsToWait, driver);
        }
        catch (Exception e) {
            pLogger.warn("Problem in click Confirm payment button", e);
        }
                            
        PageRedirectPasarelaLoadingStpV.validateDisappeared(5, driver);
    }    
    
    final static String tagTarjeta = "@TagTarjeta";
    @Step (
    	description="Introducir la tarjeta de empleado " + tagTarjeta + " y pulsar el botón \"Aplicar\"", 
        expected="Aparecen los datos para la introducción del 1er apellido y el nif")
    public static void inputTarjetaEmplEnCodPromo(Pais pais, Channel channel, WebDriver driver) throws Exception {
    	TestCaseData.getDatosCurrentStep().replaceInDescription(tagTarjeta, pais.getAccesoEmpl().getTarjeta());
        PageCheckoutWrapper.inputCodigoPromoAndAccept(pais.getAccesoEmpl().getTarjeta(), channel, driver);
        checkAfterInputTarjetaEmpleado(pais, channel, driver);
    }
    
    @Validation
    private static ChecksResult checkAfterInputTarjetaEmpleado(Pais pais, Channel channel, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
	    int maxSecondsWait = 5;
	 	validations.add(
			"Aparece el campo de introducción del primer apellido (lo esperamos hasta " + maxSecondsWait + " segundos)",
			PageCheckoutWrapper.isPresentInputApellidoPromoEmplUntil(channel, maxSecondsWait, driver), State.Defect);
    	
    	boolean isPresentInputDni = PageCheckoutWrapper.isPresentInputDNIPromoEmpl(channel, driver);
    	if (pais.getAccesoEmpl().getNif()!=null) {
		 	validations.add(
				"Aparece el campo de introducción del DNI/Pasaporte",
				isPresentInputDni, State.Defect);
    	} else {
		 	validations.add(
				"Noparece el campo de introducción del DNI/Pasaporte",
				!isPresentInputDni, State.Defect);
    	}
    	
    	boolean isPresentInputFechaNac = PageCheckoutWrapper.isPresentDiaNaciPromoEmpl(channel, driver);
    	if (pais.getAccesoEmpl().getFecnac()!=null) {
		 	validations.add(
				"Aparece el campo de introducción de la fecha de nacimiento",
				isPresentInputFechaNac, State.Defect);
    	} else {
		 	validations.add(
				"No aparece el campo de introducción de la fecha de nacimiento",
				!isPresentInputFechaNac, State.Defect);	
    	}
    	
    	return validations;
    }
    
    final static String tag1erApellido = "@Tag1erApellido";
    @Step (
    	description="Introducir el primer apellido " + tag1erApellido + " y pulsar el botón \"Guardar\"", 
        expected="Se aplican los descuentos correctamente")
    public static void inputDataEmplEnPromoAndAccept(DataBag dataBag, Pais pais, Channel channel, AppEcom app, WebDriver driver) 
    throws Exception {
    	DatosStep datosStep = TestCaseData.getDatosCurrentStep();
    	String primerApellido = (new StringTokenizer(pais.getAccesoEmpl().getNombre(), " ")).nextToken();
    	datosStep.replaceInDescription(tag1erApellido, primerApellido);
    	
        if (pais.getAccesoEmpl().getNif()!=null) {
        	datosStep.addRightDescriptionText("Introducir el NIF del usuario " + pais.getAccesoEmpl().getNif() + ". ");
        	PageCheckoutWrapper.inputDNIPromoEmpl(pais.getAccesoEmpl().getNif(), channel, driver);
        }
        PageCheckoutWrapper.inputApellidoPromoEmpl(primerApellido, channel, driver);
        if (pais.getAccesoEmpl().getFecnac()!=null) {
        	datosStep.addRightDescriptionText("Introducir la fecha de nacimiento " + pais.getAccesoEmpl().getFecnac() + ". ");
        	PageCheckoutWrapper.selectFechaNacPromoEmpl(pais.getAccesoEmpl().getFecnac(), channel, driver); 
        }
        PageCheckoutWrapper.clickButtonAceptarPromoEmpl(channel, driver);
        
        validaResultImputPromoEmpl(dataBag, channel, app, driver);
    }
        
    public static void validaResultImputPromoEmpl(DataBag dataBag, Channel channel, AppEcom app, WebDriver driver) 
    throws Exception {
        if (channel==Channel.movil_web) {
            Page1EnvioCheckoutMobilStpV.validaResultImputPromoEmpl(driver);
        } else {
            Page1DktopCheckoutStpV.validaResultImputPromoEmpl(dataBag, app, driver);
        }
    }    
    
    public static void validaIsVersionChequeRegalo(ChequeRegalo chequeRegalo, WebDriver driver) {
        Page1DktopCheckoutStpV.validateIsVersionChequeRegalo(chequeRegalo, driver);
    }
    
    final static String tagNombreBanco = "@TagNombreBanco";
    @Step (
    	description="Escogemos el banco \"" + tagNombreBanco + "\" en la pestaña de selección", 
        expected="El banco aparece seleccionado")
	public static void selectBancoEPS(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
    	String nombreBanco = "Easybank";
		if (!UtilsMangoTest.isEntornoPRO(dCtxSh.appE, driver)) {
			nombreBanco = "Test Issuer";
		}
		TestCaseData.getDatosCurrentStep().replaceInDescription(tagNombreBanco, nombreBanco);
            
		PageCheckoutWrapper.selectBancoEPS(nombreBanco, driver);
		checkIsVisibleBank(nombreBanco, driver);
	}
    
    @Validation (
    	description="Aparece el banco \"#{ombreBanco}\" en el cuadro de selección",
    	level=State.Defect)
    private static boolean checkIsVisibleBank(String nombreBanco, WebDriver driver) {
        return (PageCheckoutWrapper.isBancoSeleccionado(nombreBanco, driver));
    }

}