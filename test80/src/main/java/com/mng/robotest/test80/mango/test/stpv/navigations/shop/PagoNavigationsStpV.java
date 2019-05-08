package com.mng.robotest.test80.mango.test.stpv.navigations.shop;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;

import com.mng.robotest.test80.Test80mng.TypeAccessFmwk;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.utils;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.SaveWhen;
import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago.TypePago;
import com.mng.robotest.test80.mango.test.generic.PasosGenAnalitica;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.generic.beans.ValePais;
import com.mng.robotest.test80.mango.test.getdata.productos.ArticleStock;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.DataDireccion;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.Page1EnvioCheckoutMobil;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.DataDireccion.DataDirType;
import com.mng.robotest.test80.mango.test.pageobject.shop.identificacion.PageIdentificacion;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecBolsaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.StdValidationFlags;
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

public class PagoNavigationsStpV {
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
    
    public static void testFromLoginToExecPaymetIfNeeded(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest) 
    throws Exception {
        testFromLoginToExecPaymetIfNeeded(null, dCtxSh, dCtxPago, dFTest.driver, dFTest.ctx);        
    }
    
    /**
     * Implementa el caso de prueba completo hasta la validación de un vale (válido o inválido)
     */
    public static void testFromLoginToExecPaymetIfNeeded(List<Pais> paisesDestino, DataCtxShop dCtxSh, 
    													 DataCtxPago dCtxPago, WebDriver driver, ITestContext ctx) throws Exception {
    	accessShopAndLoginOrLogoff(dCtxSh, driver);
        if (dCtxSh.userRegistered) {
            SecBolsaStpV.clear(dCtxSh, driver);
            if (dCtxSh.appE==AppEcom.shop) {
                PageFavoritosStpV.clearAll(dCtxSh, driver);
            }
            
            StdValidationFlags flagsVal = StdValidationFlags.newOne();
            flagsVal.validaSEO = false;
            flagsVal.validaJS = false;
            flagsVal.validaImgBroken = false;
            AllPagesStpV.validacionesEstandar(flagsVal, driver);
        }
        
        int maxArticlesAwayVale = 2;
        List<ArticleStock> listArticles = UtilsTestMango.getArticlesForTestDependingVale(dCtxSh, maxArticlesAwayVale);
        DataBag dataBag = dCtxPago.getDataPedido().getDataBag();
        SecBolsaStpV.altaListaArticulosEnBolsa(listArticles, dataBag, dCtxSh, driver);
        dCtxPago.getFTCkout().testCodPromocional = true;
        testFromBolsaToCheckoutMetPago(dCtxSh, dCtxPago, driver);
        if (dCtxSh.pais.getListPagosTest(dCtxSh.appE, dCtxPago.getFTCkout().isEmpl).size() > 0) {
            checkMetodosPagos(dCtxSh, dCtxPago, paisesDestino, driver, ctx);
        }
    }
    	
    final static String tagLoginOrLogoff = "@TagLoginOfLogoff";
    @Step (
    	description="Acceder a Mango " + tagLoginOrLogoff, 
    	expected="Se accede a Mango",
    	saveNettraffic=SaveWhen.Always)
    public static void accessShopAndLoginOrLogoff(DataCtxShop dCtxSh, WebDriver driver) 
    throws Exception {
        DatosStep datosStep = TestCaseData.getDatosCurrentStep();    	
        if (dCtxSh.userRegistered) {
            datosStep.replaceInDescription(tagLoginOrLogoff, "e Identificarse");
        } else {
        	datosStep.replaceInDescription(tagLoginOrLogoff, "(si estamos logados cerramos sesión)");
        }
        
        AccesoNavigations.accesoHomeAppWeb(dCtxSh, driver);
        TestAB.activateTestABcheckoutMovilEnNPasos(0, dCtxSh, driver);
        PageIdentificacion.loginOrLogoff(dCtxSh, driver);

        //Validaciones analítica (sólo para firefox y NetAnalysis)
        EnumSet<Constantes.AnalyticsVal> analyticSet = EnumSet.of(
            Constantes.AnalyticsVal.GoogleAnalytics,
            Constantes.AnalyticsVal.NetTraffic, 
            Constantes.AnalyticsVal.DataLayer );
        PasosGenAnalitica.validaHTTPAnalytics(dCtxSh.appE, LineaType.she, analyticSet, driver);
    }
    
    /**
     * Testea desde la bolsa hasta la página de checkout con los métodos de pago
     */
    public static void testFromBolsaToCheckoutMetPago(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver)
    throws Exception {
        SecBolsaStpV.selectButtonComprar(dCtxPago.getDataPedido().getDataBag(), dCtxSh, driver);
        if (dCtxSh.userRegistered) {
            dCtxPago.getDataPedido().setEmailCheckout(dCtxSh.userConnected);
        } else {
            testFromIdentToCheckoutIni(dCtxPago, dCtxSh, driver);
        }
        
        test1rstPageCheckout(dCtxSh, dCtxPago, driver);
        if (dCtxSh.channel==Channel.movil_web) {
        	Page1EnvioCheckoutMobilStpV.clickContinuarToMetodosPago(dCtxSh, driver);
        }
    }
    
    /**
     * Testea la 1a página del checkout-resumen compra.
     */
    public static void test1rstPageCheckout(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) 
    throws Exception {
        if ((dCtxPago.getFTCkout().testCodPromocional || dCtxPago.getFTCkout().isEmpl) && 
        	 dCtxSh.appE!=AppEcom.votf) {
            DataBag dataBag = dCtxPago.getDataPedido().getDataBag();    
            if (dCtxPago.getFTCkout().isEmpl) {
                testInputCodPromoEmpl(dCtxSh, dataBag, driver);
            } else {
                if (dCtxSh.vale!=null) {
                    if (dCtxSh.channel == Channel.movil_web) {
                        Page1EnvioCheckoutMobil.inputCodigoPromo(dCtxSh.vale.getCodigoVale(), driver);
                    } else {
                    	testValeDescuento(dCtxSh.vale, dataBag, dCtxSh.appE, driver);
                    }
                }
            }
        }
        
        if (dCtxSh.appE==AppEcom.votf && dCtxSh.pais.getCodigo_pais().compareTo("001")==0 /*España*/) {
            Page1DktopCheckoutStpV.stepIntroduceCodigoVendedorVOTF("111111", driver);
        }
        
        if (dCtxPago.getFTCkout().loyaltyPoints) {
	        PageCheckoutWrapperStpV.validateBlockLoyalty(driver);
	        PageCheckoutWrapperStpV.loyaltyPointsApply(dCtxSh.channel, driver);
        }
    }
    
    private static void testValeDescuento(ValePais vale, DataBag dataBag, AppEcom app, WebDriver driver) throws Exception {
    	if ("".compareTo(vale.getTextoCheckout())!=0) {
    		if (vale.isValid()) {
    			Page1DktopCheckoutStpV.checkIsVisibleTextVale(vale, driver);
    		} else {
    			Page1DktopCheckoutStpV.checkIsNotVisibleTextVale(vale, driver);
    		}
    	}
    	
    	Page1DktopCheckoutStpV.inputValeDescuento(vale, dataBag, app, driver);
    }
    
    /**
     * Testea desde la página inicial de identificación hasta la 1a página de checkout 
     */
    @SuppressWarnings("static-access")
    public static void testFromIdentToCheckoutIni(DataCtxPago dCtxPago, DataCtxShop dCtxSh, WebDriver driver) 
    throws Exception {
        boolean validaCharNoLatinos = (dCtxSh.pais!=null && dCtxSh.pais.getDireccharnolatinos().check() && dCtxSh.appE!=AppEcom.votf);
        DataBag dataBag = dCtxPago.getDataPedido().getDataBag();
        String emailCheckout = UtilsMangoTest.getEmailForCheckout(dCtxSh.pais, dCtxPago.getFTCkout().emailExist); 
        dCtxPago.getDataPedido().setEmailCheckout(emailCheckout);

        Page1IdentCheckoutStpV.secSoyNuevo.inputEmailAndContinue(emailCheckout, dCtxPago.getFTCkout().emailExist, dCtxSh.appE, dCtxSh.userRegistered, dCtxSh.pais, dCtxSh.channel, driver);
        HashMap<String, String> datosRegistro = Page2IdentCheckoutStpV.inputDataPorDefecto(dCtxSh.pais, emailCheckout, validaCharNoLatinos, driver);
        dCtxPago.setDatosRegistro(datosRegistro);
        if (validaCharNoLatinos) {
            Page2IdentCheckoutStpV.clickContinuarAndExpectAvisoDirecWithNoLatinCharacters(driver);
            datosRegistro = Page2IdentCheckoutStpV.inputDataPorDefecto(dCtxSh.pais, emailCheckout, false, driver);
        }
        
        Page2IdentCheckoutStpV.clickContinuar(dCtxSh.userRegistered, dataBag, dCtxSh.channel, driver);
        
        //Validaciones para analytics (sólo para firefox y NetAnalysis)
        EnumSet<Constantes.AnalyticsVal> analyticSet = EnumSet.of(
            Constantes.AnalyticsVal.GoogleAnalytics,
            Constantes.AnalyticsVal.Criteo,
            Constantes.AnalyticsVal.NetTraffic,
            Constantes.AnalyticsVal.DataLayer
        );
        
        PasosGenAnalitica.validaHTTPAnalytics(dCtxSh.appE, LineaType.she, analyticSet, driver);
    }
    
    public static void testPagoFromCheckoutToEnd(DataCtxPago dCtxPago, DataCtxShop dCtxSh, Pago pagoToTest, WebDriver driver) 
    throws Exception {
        DataPedido dataPedido = dCtxPago.getDataPedido();
        dataPedido.setPago(pagoToTest);
        dataPedido.setResejecucion(State.Nok);
        
        //Obtenemos el objeto PagoStpV específico según el TypePago y ejecutamos el test 
        PagoStpV pagoStpV = FactoryPagos.makePagoStpV(dCtxSh, dCtxPago, driver);
        boolean execPay = iCanExecPago(pagoStpV, dCtxSh.appE, driver);
        pagoStpV.testPagoFromCheckout(execPay);
        dataPedido = dCtxPago.getDataPedido();
        if (execPay) {
            //Validaciones
            if (pagoToTest.getTypePago()!=TypePago.TpvVotf) {
                PageResultPagoStpV.validateIsPageOk(dCtxPago, dCtxSh, driver);
                if (dCtxSh.channel==Channel.desktop && !dCtxPago.getFTCkout().isChequeRegalo) {
                    if (testMisCompras(dCtxPago, dCtxSh)) {
                        PageResultPagoStpV.selectLinkMisComprasAndValidateCompra(dCtxPago, dCtxSh, driver);
                    } else {
                        PageResultPagoStpV.selectLinkPedidoAndValidatePedido(dataPedido, driver);
                    }
                }
            } else {
                PageResultPagoTpvStpV.validateIsPageOk(dataPedido, dCtxSh.pais.getCodigo_pais(), driver);
            }
            
            //Almacenamos el pedido en el contexto para la futura validación en Manto
            pagoStpV.storePedidoForMantoAndResetData();

            //Validaciones Analítica (sólo para firefox y NetAnalysis)
            EnumSet<Constantes.AnalyticsVal> analyticSet = EnumSet.of(
                    Constantes.AnalyticsVal.GoogleAnalytics,
                    Constantes.AnalyticsVal.NetTraffic, 
                    Constantes.AnalyticsVal.Criteo,
                    Constantes.AnalyticsVal.DataLayer);
            if (dataPedido.getPago().getTestpolyvore()!=null && 
                dataPedido.getPago().getTestpolyvore().compareTo("s")==0) { 
                analyticSet.add(Constantes.AnalyticsVal.Polyvore);
            }
            
            PasosGenAnalitica.validaHTTPAnalytics(dCtxSh.appE, LineaType.she, dataPedido, analyticSet, driver);
        }
    }
    
    private static boolean testMisCompras(DataCtxPago dCtxPago, DataCtxShop dCtxSh) {
    	return (
    		(dCtxSh.pais.isPaisWithMisCompras() && 
    		 dCtxSh.appE!=AppEcom.outlet) ||
    		 dCtxPago.getFTCkout().forceTestMisCompras
    	);
    }
    
    @Step (
    	description="Nos posicionamos en la página inicial", 
        expected="La acción se ejecuta correctamente")
    public static void fluxQuickInitToCheckout(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver)
    throws Exception {
        DataPedido dataPedido = dCtxPago.getDataPedido();
        DataBag dataBag = dataPedido.getDataBag();
        UtilsMangoTest.goToPaginaInicio(dCtxSh.channel, dCtxSh.appE, driver);
        
        //(en Chrome, cuando existe paralelización en ocasiones se pierden las cookies cuando se completa un pago con pasarela externa)
        AccesoNavigations.cambioPaisFromHomeIfNeeded(dCtxSh, driver);
        
        SecBolsaStpV.altaArticlosConColores(1, dataBag, dCtxSh, driver);
        dCtxPago.getFTCkout().testCodPromocional = false;
        testFromBolsaToCheckoutMetPago(dCtxSh, dCtxPago, driver);
        if (dCtxSh.channel==Channel.desktop) {
        	PageCheckoutWrapper.getDataPedidoFromCheckout(dataPedido, dCtxSh.channel, driver);
        }
    }    
    
    public static void testInputCodPromoEmpl(DataCtxShop dCtxSh, DataBag dataBag, WebDriver driver) throws Exception {
        PageCheckoutWrapperStpV.inputTarjetaEmplEnCodPromo(dCtxSh.pais, dCtxSh.channel, driver);
        PageCheckoutWrapperStpV.inputDataEmplEnPromoAndAccept(dataBag, dCtxSh.pais, dCtxSh.channel, dCtxSh.appE, driver);
    }
    
    /**
     * Función que parte de la página de "Resumen de artículos" y que valida todos los métodos de pago del país
     */
    @SuppressWarnings("static-access")
    public static void checkMetodosPagos(DataCtxShop dCtxSh, DataCtxPago dCtxPago, List<Pais> paisesDestino, WebDriver driver, ITestContext ctx) 
    throws Exception {
        try {
            DataPedido dataPedido = dCtxPago.getDataPedido();
            if (dCtxSh.channel==Channel.desktop) {
                PageCheckoutWrapper.getDataPedidoFromCheckout(dataPedido, dCtxSh.channel, driver);
            }
                
            PageCheckoutWrapperStpV.despliegaYValidaMetodosPago(dCtxSh.pais, dCtxPago.getFTCkout().isEmpl, dCtxSh.appE, dCtxSh.channel, driver);
            if (dCtxPago.getFTCkout().validaPasarelas) {
                validaPasarelasPagoPais(dCtxSh, dCtxPago, driver, ctx);
            }
                
            //En el caso de españa, después de validar todos los países probamos el botón "CHANGE DETAILS" sobre los países indicados en la lista
            if (dCtxSh.pais.getCodigo_pais().compareTo("001")==0 /*España*/ && paisesDestino!=null && paisesDestino.size()>0) {
                Pais paisChange = null;
                Iterator<Pais> itPaises = paisesDestino.iterator();
                while (itPaises.hasNext()) {
                    paisChange = itPaises.next();
                    if (dCtxSh.appE==AppEcom.shop) {
                        //Test funcionalidad "Quiero recibir factura"
                        PageCheckoutWrapperStpV.clickSolicitarFactura(dCtxSh.channel, driver);
                        DataDireccion dataDirFactura = new DataDireccion();
                        dataDirFactura.put(DataDirType.nif, "76367949Z");
                        dataDirFactura.put(DataDirType.name, "Carolina");
                        dataDirFactura.put(DataDirType.apellidos, "Rancaño Pérez");
                        dataDirFactura.put(DataDirType.codpostal, "08720");
                        dataDirFactura.put(DataDirType.direccion, "c./ mossen trens nº6 5º1ª");
                        dataDirFactura.put(DataDirType.email, "crp1974@hotmail.com");
                        dataDirFactura.put(DataDirType.telefono, "665015122");
                        dataDirFactura.put(DataDirType.poblacion, "PEREPAU");
                        PageCheckoutWrapperStpV.modalDirecFactura.inputDataAndActualizar(dataDirFactura, driver);
                    }
                    
                    if (dCtxSh.appE!=AppEcom.votf) {
                        //Test funcionalidad "Cambio dirección de envío"
                        PageCheckoutWrapperStpV.clickEditarDirecEnvio(driver);
                        DataDireccion dataDirEnvio = new DataDireccion();
                        dataDirEnvio.put(DataDirType.codigoPais, paisChange.getCodigo_pais());
                        dataDirEnvio.put(DataDirType.codpostal, paisChange.getCodpos());                    
                        dataDirEnvio.put(DataDirType.name, "Jorge");
                        dataDirEnvio.put(DataDirType.apellidos, "Muñoz Martínez");
                        dataDirEnvio.put(DataDirType.direccion, "c./ mossen trens nº6 5º1ª");
                        dataDirEnvio.put(DataDirType.email, "jorge.munoz.sge@mango.com");
                        dataDirEnvio.put(DataDirType.telefono, "665015122");
                        PageCheckoutWrapperStpV.modalDirecEnvio.inputDataAndActualizar(dataDirEnvio, driver);
                        PageCheckoutWrapperStpV.modalAvisoCambioPais.clickConfirmar(paisChange, driver);
                        PageCheckoutWrapperStpV.validaMetodosPagoDisponibles(paisChange, dCtxPago.getFTCkout().isEmpl, dCtxSh.appE, dCtxSh.channel, driver);
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
    public static void validaPasarelasPagoPais(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver, ITestContext ctx) 
    throws Exception {
        List<Pago> listPagos = dCtxSh.pais.getListPagosTest(dCtxSh.appE, dCtxPago.getFTCkout().isEmpl);
        for (int i=0; i<listPagos.size(); i++) {
            Pago pago = listPagos.get(i);
            if (pago.isNeededTestPasarelaDependingFilter(dCtxSh.channel, ctx)) {
                dCtxPago.getDataPedido().setPago(pago);
                String urlPagChekoutToReturn = driver.getCurrentUrl();
                if (dCtxPago.getFTCkout().validaPagos) {
                    //Si el pago está marcado para testearlo (fichero XML de países)
                    //Y en caso que nos hayamos quedado sin productos (porque previamente se ha efectuado un pago) -> Volvemos a seleccionar un producto
                    if (pago.getTestpago()!=null && pago.getTestpago().compareTo("s")==0 &&
                        !PageCheckoutWrapper.isArticulos(dCtxSh.channel, driver))
                        //Proceso que vuelve a la página de inicio y realiza un proceso mínimo hasta la página de resumen-checkout
                        fluxQuickInitToCheckout(dCtxSh, dCtxPago, driver);
                }
                                        
                checkPasarelaPago(dCtxPago, dCtxSh, driver);
                PageCheckoutWrapper.backPageMetodosPagos(urlPagChekoutToReturn, dCtxSh.channel, driver);
            }
        }
    }

    public static void checkPasarelaPago(DataCtxPago dCtxPago, DataCtxShop dCtxSh, WebDriver driver) throws Exception {
        DataPedido dataPedido = dCtxPago.getDataPedido(); 
        Pago pago = dataPedido.getPago();
        try {
            if (dCtxSh.channel==Channel.desktop) {
            	PageCheckoutWrapper.getDataPedidoFromCheckout(dataPedido, dCtxSh.channel, driver);
            }
            testPagoFromCheckoutToEnd(dCtxPago, dCtxSh, pago, driver);
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
    public static DatosStep aceptarCompraDesdeMetodosPago(DataCtxPago dCtxPago, Channel channel, WebDriver driver)
    throws Exception {
        DataPedido dataPedido = dCtxPago.getDataPedido();
        dataPedido.setCodtipopago("R");
        if (channel==Channel.desktop) {
        	PageCheckoutWrapper.getDataPedidoFromCheckout(dataPedido, channel, driver);
            PageCheckoutWrapperStpV.pasoBotonAceptarCompraDesktop(driver);
        } else {
            PageCheckoutWrapperStpV.pasoBotonVerResumenCheckout2Mobil(driver);
        	PageCheckoutWrapper.getDataPedidoFromCheckout(dataPedido, channel, driver);
            PageCheckoutWrapperStpV.pasoBotonConfirmarPagoCheckout3Mobil(driver);
        }       
                    
        return (TestCaseData.getDatosLastStep());
    }
    
    public static boolean iCanExecPago(PagoStpV pagoStpV, AppEcom appE, WebDriver driver) {
        boolean validaPagos = pagoStpV.dCtxPago.getFTCkout().validaPagos;
        Pago pago = pagoStpV.dCtxPago.getDataPedido().getPago();
        ITestContext ctx = TestCaseData.getdFTest().ctx;
        return (
            //No estamos en el entorno productivo
            !UtilsMangoTest.isEntornoPRO(appE, driver) &&
            //No estamos en modo BATCH
            utils.getTypeAccessFmwk(ctx)!=TypeAccessFmwk.Bat &&
            //Está activado el flag de pago en el fichero XML de configuración del test (testNG)
            validaPagos &&  
            //Está activado el test en el pago concreto que figura en el XML de países
            pago.getTestpago()!=null && pago.getTestpago().compareTo("s")==0 &&
            //Está implementado el test a nivel de la confirmación del pago
            pagoStpV.isAvailableExecPay
        );
    }
}