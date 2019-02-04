package com.mng.robotest.test80.mango.test.stpv.navigations.shop;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mng.robotest.test80.Test80mng.TypeAccessFmwk;
import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.utils;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.ValesData;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.data.ValesData.Campanya;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago.TypePago;
import com.mng.robotest.test80.mango.test.generic.PasosGenAnalitica;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.generic.beans.ValePais;
import com.mng.robotest.test80.mango.test.getdata.productos.ArticleStock;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.DataDireccion;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.Page1EnvioCheckoutMobil;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.DataDireccion.DataDirType;
import com.mng.robotest.test80.mango.test.pageobject.shop.identificacion.PageIdentificacion;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecBolsaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.Page1DktopCheckoutStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.Page1EnvioCheckoutMobilStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.Page1IdentCheckoutStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.Page2IdentCheckoutStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageResultPagoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageResultPagoTpvStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory.FactoryPagos;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory.PagoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.favoritos.PageFavoritosStpV;
import com.mng.robotest.test80.mango.test.utils.UtilsTestMango;
import com.mng.robotest.test80.mango.test.utils.testab.TestAB;

@SuppressWarnings("javadoc")
public class PagoNavigationsStpV {
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
    
    public static void testFromLoginToExecPaymetIfNeeded(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest) 
    throws Exception {
        testFromLoginToExecPaymetIfNeeded(null, dCtxSh, dCtxPago, dFTest);        
    }
    
    /**
     * Implementa el caso de prueba completo hasta la validación de un vale (válido o inválido)
     */
    public static void testFromLoginToExecPaymetIfNeeded(List<Pais> paisesDestino, DataCtxShop dCtxSh, 
    													 DataCtxPago dCtxPago, DataFmwkTest dFTest) throws Exception {
        //Step
        datosStep datosStep=null;    	
        String registro = "";
        if (dCtxSh.userRegistered)
            registro = "e Identificarse";
        else
            registro = " (si estamos logados cerramos sesión)";
        
        datosStep = new datosStep       (
            "Acceder a Mango " + registro, 
            "Se accede a Mango");
        datosStep.setGrabNettrafic(dFTest.ctx);
        try {
            AccesoNavigations.accesoHomeAppWeb(dCtxSh, dFTest);
            TestAB.activateTestABcheckoutMovilEnNPasos(0, dCtxSh, dFTest.driver);
            PageIdentificacion.loginOrLogoff(dCtxSh, dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        //Validaciones analítica (sólo para firefox y NetAnalysis)
        EnumSet<Constantes.AnalyticsVal> analyticSet = EnumSet.of(
            Constantes.AnalyticsVal.GoogleAnalytics,
            Constantes.AnalyticsVal.NetTraffic, 
            Constantes.AnalyticsVal.DataLayer );
        PasosGenAnalitica.validaHTTPAnalytics(dCtxSh.appE, LineaType.she, analyticSet, datosStep, dFTest);
        
        if (dCtxSh.userRegistered) {
            //Step. Vaciamos la bolsa y los favoritos
            SecBolsaStpV.clear(dCtxSh, dFTest);
            if (dCtxSh.appE==AppEcom.shop)
                PageFavoritosStpV.clearAll(dCtxSh, dFTest);
            
            //Validaciones estándar. 
            AllPagesStpV.validacionesEstandar(false/*validaSEO*/, false/*validaJS*/, false/*validaImgBroken*/, datosStep, dFTest);
        }
        
        int maxArticlesAwayVale = 2;
        List<ArticleStock> listArticles = UtilsTestMango.getArticlesForTestDependingVale(dCtxSh, maxArticlesAwayVale);
        	
	    //Step
        DataBag dataBag = dCtxPago.getDataPedido().getDataBag();
        SecBolsaStpV.altaListaArticulosEnBolsa(listArticles, dataBag, dCtxSh, dFTest);

        //Steps. Seleccionar el botón comprar y completar el proceso hasta la página de checkout con los métodos de pago
        dCtxPago.getFTCkout().testCodPromocional = true;
        testFromBolsaToCheckoutMetPago(dCtxSh, dCtxPago, dFTest);

        //Testeo de todos los métodos de pago del país
        if (dCtxSh.pais.getListPagosTest(dCtxSh.appE, dCtxPago.getFTCkout().isEmpl).size() > 0)
            checkMetodosPagos(dCtxSh, dCtxPago, paisesDestino, dFTest);
    }    
    
    /**
     * Testea desde la bolsa hasta la página de checkout con los métodos de pago
     */
    public static void testFromBolsaToCheckoutMetPago(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest)
    throws Exception {
        SecBolsaStpV.selectButtonComprar(dCtxPago.getDataPedido().getDataBag(), dCtxSh, dFTest);
        if (dCtxSh.userRegistered)
            dCtxPago.getDataPedido().setEmailCheckout(dCtxSh.userConnected);
        else
            testFromIdentToCheckoutIni(dCtxPago, dCtxSh, dFTest);
        
        test1rstPageCheckout(dCtxSh, dCtxPago, dFTest);

        if (dCtxSh.channel==Channel.movil_web)
        	Page1EnvioCheckoutMobilStpV.clickContinuarToMetodosPago(dCtxSh, dFTest);
    }
    
    /**
     * Testea la 1a página del checkout-resumen compra.
     */
    public static void test1rstPageCheckout(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest) 
    throws Exception {
        if ((dCtxPago.getFTCkout().testCodPromocional || dCtxPago.getFTCkout().isEmpl) && 
        	 dCtxSh.appE!=AppEcom.votf) {
            DataBag dataBag = dCtxPago.getDataPedido().getDataBag();    
            if (dCtxPago.getFTCkout().isEmpl)
                testInputCodPromoEmpl(dCtxSh, dataBag, dFTest);
            else {
                if (dCtxSh.vale!=null) {
                    if (dCtxSh.channel == Channel.movil_web){
                        Page1EnvioCheckoutMobil.inputCodigoPromo(dCtxSh.vale.getCodigoVale(), dFTest.driver);
                    } else {
                        Page1DktopCheckoutStpV.inputValeDescuento(dCtxSh.vale, dataBag, dCtxSh.appE, dFTest);
                    }
                    //testListOfVales(dCtxSh.vale.getCampanya(), dataBag, dCtxSh, dFTest);
                }
            }
        }
        
        if (dCtxSh.appE==AppEcom.votf && dCtxSh.pais.getCodigo_pais().compareTo("001")==0 /*España*/)
            Page1DktopCheckoutStpV.stepIntroduceCodigoVendedorVOTF("111111", dFTest);
    }
    
    @SuppressWarnings("unused")
	private static void testListOfVales(Campanya campanya, DataBag dataBag, DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
    	List<ValePais> listValesToTest = ValesData.getListVales(campanya, false);
    	for (ValePais valeToTest : listValesToTest) {
	        Page1DktopCheckoutStpV.inputValeDescuento(valeToTest, dataBag, dCtxSh.appE, dFTest);
	        if (dCtxSh.vale.isValid())
	        	Page1DktopCheckoutStpV.clearValeIfLinkExists(dFTest);
    	}
    }
    
    /**
     * Testea desde la página inicial de identificación hasta la 1a página de checkout 
     */
    @SuppressWarnings("static-access")
    public static datosStep testFromIdentToCheckoutIni(DataCtxPago dCtxPago, DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
        datosStep datosStep = null;
        boolean validaCharNoLatinos = (dCtxSh.pais!=null && dCtxSh.pais.getDireccharnolatinos().check() && dCtxSh.appE!=AppEcom.votf);
        DataBag dataBag = dCtxPago.getDataPedido().getDataBag();
        String emailCheckout = UtilsMangoTest.getEmailForCheckout(dCtxSh.pais, dCtxPago.getFTCkout().emailExist); 
        dCtxPago.getDataPedido().setEmailCheckout(emailCheckout);

        Page1IdentCheckoutStpV.secSoyNuevo.inputEmailAndContinue(emailCheckout, dCtxPago.getFTCkout().emailExist, dCtxSh.appE, dCtxSh.userRegistered, dCtxSh.pais, dCtxSh.channel, dFTest);
        HashMap<String, String> datosRegistro = Page2IdentCheckoutStpV.inputDataPorDefecto(dCtxSh.pais, emailCheckout, validaCharNoLatinos, dFTest);
        dCtxPago.setDatosRegistro(datosRegistro);
        if (validaCharNoLatinos) {
            datosStep = Page2IdentCheckoutStpV.clickContinuar(dCtxSh.userRegistered,true/*validaDirecCharNoLatinos*/, dataBag, dCtxSh.channel, dCtxSh.appE, dFTest);
            datosRegistro = Page2IdentCheckoutStpV.inputDataPorDefecto(dCtxSh.pais, emailCheckout, false/*validaCharNoLatinos*/, dFTest);
        }
        
        datosStep = Page2IdentCheckoutStpV.clickContinuar(dCtxSh.userRegistered, false/*validaDirecCharNoLatinos*/, dataBag, dCtxSh.channel, dCtxSh.appE, dFTest);
        
        //Validaciones para analytics (sólo para firefox y NetAnalysis)
        EnumSet<Constantes.AnalyticsVal> analyticSet = EnumSet.of(
            Constantes.AnalyticsVal.GoogleAnalytics,
            Constantes.AnalyticsVal.Criteo,
            Constantes.AnalyticsVal.NetTraffic,
            Constantes.AnalyticsVal.DataLayer
        );
        
        PasosGenAnalitica.validaHTTPAnalytics(dCtxSh.appE, LineaType.she, analyticSet, datosStep, dFTest);

        return datosStep;
    }
    
    public static void testPagoFromCheckoutToEnd(DataCtxPago dCtxPago, DataCtxShop dCtxSh, Pago pagoToTest, DataFmwkTest dFTest) throws Exception {
        datosStep datosStep = null;
        DataPedido dataPedido = dCtxPago.getDataPedido();
        dataPedido.setPago(pagoToTest);
        dataPedido.setResejecucion(State.Nok);
        
        //Obtenemos el objeto PagoStpV específico según el TypePago y ejecutamos el test 
        PagoStpV pagoStpV = FactoryPagos.makePagoStpV(dCtxSh, dCtxPago, dFTest);
        boolean execPay = iCanExecPago(pagoStpV, dCtxSh.appE, dFTest);
        datosStep = pagoStpV.testPagoFromCheckout(execPay);
        dataPedido = dCtxPago.getDataPedido();
        if (execPay) {
            //Validaciones
            if (pagoToTest.getTypePago()!=TypePago.TpvVotf) {
                PageResultPagoStpV.validateIsPageOk(dCtxPago, dCtxSh, datosStep, dFTest);
                if (dCtxSh.channel==Channel.desktop && !dCtxPago.getFTCkout().isChequeRegalo) {
                    if (testMisCompras(dCtxPago, dCtxSh))
                        PageResultPagoStpV.selectLinkMisComprasAndValidateCompra(dCtxPago, dCtxSh, dFTest);
                    else
                        PageResultPagoStpV.selectLinkPedidoAndValidatePedido(dataPedido, dCtxSh.appE, dFTest);
                }
            }
            else
                PageResultPagoTpvStpV.validateIsPageOk(dataPedido, dCtxSh.pais.getCodigo_pais(), datosStep, dFTest);
            
            //Almacenamos el pedido en el contexto para la futura validación en Manto
            pagoStpV.storePedidoForMantoAndResetData();

            //Validaciones Analítica (sólo para firefox y NetAnalysis)
            EnumSet<Constantes.AnalyticsVal> analyticSet = EnumSet.of(
                    Constantes.AnalyticsVal.GoogleAnalytics,
                    Constantes.AnalyticsVal.NetTraffic, 
                    Constantes.AnalyticsVal.Criteo,
                    Constantes.AnalyticsVal.DataLayer);
            if (dataPedido.getPago().getTestpolyvore()!=null && 
                dataPedido.getPago().getTestpolyvore().compareTo("s")==0) 
                analyticSet.add(Constantes.AnalyticsVal.Polyvore);
            
            PasosGenAnalitica.validaHTTPAnalytics(dCtxSh.appE, LineaType.she, dataPedido, analyticSet, datosStep, dFTest);
        }
    }
    
    private static boolean testMisCompras(DataCtxPago dCtxPago, DataCtxShop dCtxSh) {
    	return (
    		(dCtxSh.pais.isPaisWithMisCompras() && 
    		 dCtxSh.appE!=AppEcom.outlet) ||
    		 dCtxPago.getFTCkout().forceTestMisCompras
    	);
    }
    
    /**
     * Proceso que vuelve a la página de inicio y realiza un proceso mínimo hasta la página de resumen-checkout
     */
    public static void fluxQuickInitToCheckout(DataCtxShop dCtxSh, DataCtxPago dCtxPago, ArticuloScreen articuloDisp, DataFmwkTest dFTest)
    throws Exception {
        DataPedido dataPedido = dCtxPago.getDataPedido();
        DataBag dataBag = dataPedido.getDataBag();
        datosStep datosStep = new datosStep       (
            "Nos posicionamos en la página inicial", 
            "La acción se ejecuta correctamente");
        try {
            UtilsMangoTest.goToPaginaInicio(dCtxSh.channel, dCtxSh.appE, dFTest);
                                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Step. Cambiamos de país 
        //(en Chrome, cuando existe paralelización en ocasiones se pierden las cookies cuando se completa un pago con pasarela externa)
        AccesoNavigations.cambioPaisFromHomeIfNeeded(dCtxSh, dFTest);
        
        //Step. Seleccionar el artículo disponible y añadirlo a la bolsa
        SecBolsaStpV.altaArticlosConColores(1, dataBag, dCtxSh, dFTest);

        //STEPs Volvemos a comprar, identicarnos y ejecutar las acciones específicas sobre la página de checkout
        dCtxPago.getFTCkout().testCodPromocional = false;
        testFromBolsaToCheckoutMetPago(dCtxSh, dCtxPago, dFTest);
                    
        //En el caso de DESKTOP disponemos y almacenamos el importe total mostrado por pantalla (lo necesitaremos más adelante)
        if (dCtxSh.channel==Channel.desktop)
        	PageCheckoutWrapper.getDataPedidoFromCheckout(dataPedido, dCtxSh.channel, dFTest.driver);
    }    
    
    public static void testInputCodPromoEmpl(DataCtxShop dCtxSh, DataBag dataBag, DataFmwkTest dFTest) throws Exception {
        PageCheckoutWrapperStpV.inputTarjetaEmplEnCodPromo(dCtxSh.pais, dCtxSh.channel, dFTest);
        PageCheckoutWrapperStpV.inputDataEmplEnPromoAndAccept(dataBag, dCtxSh.pais, dCtxSh.channel, dCtxSh.appE, dFTest);
    }
    
    /**
     * Función que parte de la página de "Resumen de artículos" y que valida todos los métodos de pago del país
     */
    @SuppressWarnings("static-access")
    public static void checkMetodosPagos(DataCtxShop dCtxSh, DataCtxPago dCtxPago, List<Pais> paisesDestino, DataFmwkTest dFTest) 
    throws Exception {
        try {
            DataPedido dataPedido = dCtxPago.getDataPedido();
            datosStep datosStep=null;

            //En el caso de DESKTOP disponemos y almacenamos el importe total mostrado por pantalla (lo necesitaremos más adelante)
            if (dCtxSh.channel==Channel.desktop)
                PageCheckoutWrapper.getDataPedidoFromCheckout(dataPedido, dCtxSh.channel, dFTest.driver);
                
            //Despliega (si no lo están) los métodos de pago y valida que realmente sean los correctos
            PageCheckoutWrapperStpV.despliegaYValidaMetodosPago(dCtxSh.pais, dCtxPago.getFTCkout().isEmpl, dCtxSh.appE, dCtxSh.channel, dFTest);
                
            //Si en el fichero XML de configuración de TestNG está activado el parámetro validaPasarelas
            if (dCtxPago.getFTCkout().validaPasarelas)
                //Ejecutamos / Validamos las pasarelas de pago
                validaPasarelasPagoPais(dCtxSh, dCtxPago, dFTest);
                
            //En el caso de españa, después de validar todos los países probamos el botón "CHANGE DETAILS" sobre los países indicados en la lista
            if (dCtxSh.pais.getCodigo_pais().compareTo("001")==0 /*España*/ && paisesDestino!=null && paisesDestino.size()>0) {
                Pais paisChange = null;
                Iterator<Pais> itPaises = paisesDestino.iterator();
                while (itPaises.hasNext()) {
                    paisChange = itPaises.next();
                    if (dCtxSh.appE==AppEcom.shop) {
                        //Test funcionalidad "Quiero recibir factura"
                        PageCheckoutWrapperStpV.clickSolicitarFactura(dCtxSh.channel, dFTest);
                        DataDireccion dataDirFactura = new DataDireccion();
                        dataDirFactura.put(DataDirType.nif, "76367949Z");
                        dataDirFactura.put(DataDirType.name, "Carolina");
                        dataDirFactura.put(DataDirType.apellidos, "Rancaño Pérez");
                        dataDirFactura.put(DataDirType.codpostal, "08720");
                        dataDirFactura.put(DataDirType.direccion, "c./ mossen trens nº6 5º1ª");
                        dataDirFactura.put(DataDirType.email, "crp1974@hotmail.com");
                        dataDirFactura.put(DataDirType.telefono, "665015122");
                        dataDirFactura.put(DataDirType.poblacion, "PEREPAU");
                        PageCheckoutWrapperStpV.modalDirecFactura.inputDataAndActualizar(dataDirFactura, dFTest);
                    }
                    
                    if (dCtxSh.appE!=AppEcom.votf) {
                        //Test funcionalidad "Cambio dirección de envío"
                        PageCheckoutWrapperStpV.clickEditarDirecEnvio(dFTest);
                        DataDireccion dataDirEnvio = new DataDireccion();
                        dataDirEnvio.put(DataDirType.codigoPais, paisChange.getCodigo_pais());
                        dataDirEnvio.put(DataDirType.codpostal, paisChange.getCodpos());                    
                        dataDirEnvio.put(DataDirType.name, "Jorge");
                        dataDirEnvio.put(DataDirType.apellidos, "Muñoz Martínez");
                        dataDirEnvio.put(DataDirType.direccion, "c./ mossen trens nº6 5º1ª");
                        dataDirEnvio.put(DataDirType.email, "jorge.munoz.sge@mango.com");
                        dataDirEnvio.put(DataDirType.telefono, "665015122");
                        PageCheckoutWrapperStpV.modalDirecEnvio.inputDataAndActualizar(dataDirEnvio, dFTest);
                        datosStep = PageCheckoutWrapperStpV.modalAvisoCambioPais.clickConfirmar(paisChange, dFTest);
                        PageCheckoutWrapperStpV.validaMetodosPagoDisponibles(datosStep, paisChange, dCtxPago.getFTCkout().isEmpl, dCtxSh.appE, dCtxSh.channel, dFTest);
                    }
                }
            }
        }
        catch (Exception e) {
            pLogger.warn("Problem validating Payments methods of country {} ",  dCtxSh.pais.getNombre_pais(), e);
            throw e; 
        }
    }

    /**
     * Validación de todas las pasarelas de pago asociadas al país
     */
    public static void validaPasarelasPagoPais(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest) 
    throws Exception {
        DataBag dataBag = dCtxPago.getDataPedido().getDataBag();
        ArticuloScreen articuloDisp = dataBag.getArticulo(dataBag.getListArticulos().size() - 1);
        List<Pago> listPagos = dCtxSh.pais.getListPagosTest(dCtxSh.appE, dCtxPago.getFTCkout().isEmpl);
        for (int i=0; i<listPagos.size(); i++) {
            Pago pago = listPagos.get(i);
            if (pago.isNeededTestPasarelaDependingFilter(dCtxSh.channel, dFTest.ctx)) {
                dCtxPago.getDataPedido().setPago(pago);
                String urlPagChekoutToReturn = dFTest.driver.getCurrentUrl();
                if (dCtxPago.getFTCkout().validaPagos) {
                    //Si el pago está marcado para testearlo (fichero XML de países)
                    //Y en caso que nos hayamos quedado sin productos (porque previamente se ha efectuado un pago) -> Volvemos a seleccionar un producto
                    if (pago.getTestpago()!=null && pago.getTestpago().compareTo("s")==0 &&
                        !PageCheckoutWrapper.isArticulos(dCtxSh.channel, dFTest.driver))
                        //Proceso que vuelve a la página de inicio y realiza un proceso mínimo hasta la página de resumen-checkout
                        fluxQuickInitToCheckout(dCtxSh, dCtxPago, articuloDisp, dFTest);
                }
                                        
                checkPasarelaPago(dCtxPago, dCtxSh, dFTest);
                PageCheckoutWrapper.backPageMetodosPagos(urlPagChekoutToReturn, dCtxSh.channel, dFTest.driver);
            }
        }
    }

    public static void checkPasarelaPago(DataCtxPago dCtxPago, DataCtxShop dCtxSh, DataFmwkTest dFTest) throws Exception {
        DataPedido dataPedido = dCtxPago.getDataPedido(); 
        Pago pago = dataPedido.getPago();
        try {
            if (dCtxSh.channel==Channel.desktop) {
            	PageCheckoutWrapper.getDataPedidoFromCheckout(dataPedido, dCtxSh.channel, dFTest.driver);
            }
                            
            testPagoFromCheckoutToEnd(dCtxPago, dCtxSh, pago, dFTest);
        }
        catch (Exception e) {
            pLogger.warn("Problem checking Payment {} from country {}", pago.getNombre(), dCtxSh.pais.getNombre_pais(), e);
        }
    }
    
    /**
     * Pasos para aceptar la compra desde la página inicial de checkout
     *     Desktop: simplemente se selecciona el botón "Confirmar Compra"
     *     Movil  : se selecciona los botones "Ver resumen" y "Confirmación del pago)
     */
    public static datosStep aceptarCompraDesdeMetodosPago(DataCtxPago dCtxPago, Channel channel, DataFmwkTest dFTest) throws Exception {
        datosStep datosStep = null;
        DataPedido dataPedido = dCtxPago.getDataPedido();
        dataPedido.setCodtipopago("R");
        if (channel==Channel.desktop) {
        	PageCheckoutWrapper.getDataPedidoFromCheckout(dataPedido, channel, dFTest.driver);
            datosStep = PageCheckoutWrapperStpV.pasoBotonAceptarCompraDesktop(dFTest);
        }
        else {
            //Step
            datosStep = PageCheckoutWrapperStpV.pasoBotonVerResumenCheckout2Mobil(dFTest);
        	PageCheckoutWrapper.getDataPedidoFromCheckout(dataPedido, channel, dFTest.driver);
                            
            //Step
            datosStep = PageCheckoutWrapperStpV.pasoBotonConfirmarPagoCheckout3Mobil(dFTest);
        }       
                    
        return datosStep;
    }
    
    public static boolean iCanExecPago(PagoStpV pagoStpV, AppEcom appE, DataFmwkTest dFTest) {
        boolean validaPagos = pagoStpV.dCtxPago.getFTCkout().validaPagos;
        Pago pago = pagoStpV.dCtxPago.getDataPedido().getPago();
        return (
            //No estamos en el entorno productivo
            !UtilsMangoTest.isEntornoPRO(appE, dFTest) &&
            //No estamos en modo BATCH
            utils.getTypeAccessFmwk(dFTest.ctx)!=TypeAccessFmwk.Bat &&
            //Está activado el flag de pago en el fichero XML de configuración del test (testNG)
            validaPagos &&  
            //Está activado el test en el pago concreto que figura en el XML de países
            pago.getTestpago()!=null && pago.getTestpago().compareTo("s")==0 &&
            //Está implementado el test a nivel de la confirmación del pago
            pagoStpV.isAvailableExecPay
        );
    }
}