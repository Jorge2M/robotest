package com.mng.robotest.test80.mango.test.appshop;

import org.testng.ITestContext;
import java.lang.reflect.Method;
import org.testng.annotations.*;

import com.mng.robotest.test80.Test80mng.TypeAccessFmwk;
import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.utils;
import com.mng.robotest.test80.arq.utils.controlTest.mango.*;
import com.mng.robotest.test80.arq.utils.otras.*;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.*;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.datastored.FlagsTestCkout;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.generic.ChequeRegalo;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.getdata.productos.ArticleStock;
import com.mng.robotest.test80.mango.test.getdata.productos.ManagerArticlesStock;
import com.mng.robotest.test80.mango.test.getdata.productos.ManagerArticlesStock.TypeArticleStock;
import com.mng.robotest.test80.mango.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test80.mango.test.getdata.usuarios.UserShop;
import com.mng.robotest.test80.mango.test.pageobject.chequeregalo.PageChequeRegaloInputData.Importe;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test80.mango.test.pageobject.shop.footer.SecFooter.FooterLink;
import com.mng.robotest.test80.mango.test.stpv.navigations.manto.PedidosNavigations;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecBolsaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecCabeceraStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecFooterStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checqueregalo.PageChequeRegaloInputDataStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.micuenta.PageMiCuentaStpV;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.WebDriver;

@SuppressWarnings("javadoc")
public class Compra extends GestorWebDriver {

    String baseUrl;
    boolean acceptNextAlert = true;
    StringBuffer verificationErrors = new StringBuffer();
	
    //Definimos un vale caducado
    Pais españa = null;
    Pais andorra = null;
    Pais suecia = null;
    Pais francia = null;
    IdiomaPais castellano = null;
    IdiomaPais frances = null;
    Pais colombia = null;
    IdiomaPais castellanoColomb = null;
	
    public Compra() {}	  
	  
    @BeforeMethod (groups={"Compra", "Canal:all_App:all", "shop-movil-web"})
    @Parameters({"brwsr-path","urlBase", "AppEcom", "Channel"})
    public void login(String bpath, String urlAcceso, String appEcom, String channel, ITestContext context, Method method) 
    throws Exception {
        //Obtenemos la lista de países del contexto y recuperamos los que necesitamos para los tests
        if (this.españa==null) {
            Integer codEspanya = Integer.valueOf(1);
            Integer codAndorra = Integer.valueOf(43);
            Integer codSuecia = Integer.valueOf(30);
            Integer codFrancia = Integer.valueOf(11);
            Integer codColombia = Integer.valueOf(480);
            List<Integer> lisCodCountrys = Arrays.asList(codEspanya, codAndorra, codSuecia, codFrancia, codColombia);
            List<Pais> listaPaises = UtilsMangoTest.listaPaisesXML(new ArrayList<>(lisCodCountrys));
            this.españa = UtilsMangoTest.getPaisFromCodigo("001", listaPaises);
            this.andorra = UtilsMangoTest.getPaisFromCodigo("043", listaPaises);
            this.suecia =  UtilsMangoTest.getPaisFromCodigo("030", listaPaises);
            this.francia = UtilsMangoTest.getPaisFromCodigo("011", listaPaises);
            this.colombia = UtilsMangoTest.getPaisFromCodigo("480", listaPaises);
            this.castellanoColomb = this.colombia.getListIdiomas().get(0);
            this.castellano = this.españa.getListIdiomas().get(0);
            this.frances = this.francia.getListIdiomas().get(0);
        }
        
        //Recopilación de parámetros comunes
        //DataCtxShop dCtxSh = new DataCtxShop();
        DataCtxShop dCtxSh = new DataCtxShop();
        dCtxSh.setAppEcom(appEcom);
        dCtxSh.setChannel(channel);
        dCtxSh.pais=this.españa;
        dCtxSh.idioma=this.castellano;
        dCtxSh.urlAcceso = urlAcceso;
        
        storeInThread(dCtxSh);
        getAndStoreDataFmwk(bpath, dCtxSh.urlAcceso, "", dCtxSh.channel, context, method);
    }
	
    @SuppressWarnings("unused")
    @AfterMethod (groups={"Compra", "Canal:all_App:all"}, alwaysRun = true)
    public void logout(ITestContext context, Method method) throws Exception {
        WebDriver driver = getWebDriver();
        super.quitWebDriver(driver, context);
    }		
	
    @Test (
        groups={"Compra", "Canal:desktop_App:shop", "Canal:desktop_App:outlet"}, alwaysRun=true, priority=2, 
        description="[Usuario registrado][Tarjeta guardada] Compra con descuento empleado. Verificar compra en sección 'Mis compras'") //Lo marcamos con prioridad 2 para dar tiempo a que otro caso de prueba registre la tarjeta 
    public void COM001_Compra_TrjSaved_Empl(ITestContext context, Method method) throws Exception {
    	DataFmwkTest dFTest = getdFTest();
        DataCtxShop dCtxSh = getdCtxSh();
        UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
        dCtxSh.userConnected = userShop.user;
        dCtxSh.passwordUser = userShop.password;
        dCtxSh.userRegistered = true;

        //To checkout
        FlagsTestCkout FTCkout = new FlagsTestCkout();
        FTCkout.validaPasarelas = false;  
        FTCkout.validaPagos = false;
        FTCkout.emailExist = true; 
        FTCkout.trjGuardada = true;
        FTCkout.isEmpl = true;
        DataCtxPago dCtxPago = new DataCtxPago(dCtxSh);
        dCtxPago.setFTCkout(FTCkout);
        //TestAB.activateTestABiconoBolsaDesktop(0, dCtxSh, dFTest.driver);
        PagoNavigationsStpV.testFromLoginToExecPaymetIfNeeded(dCtxSh, dCtxPago, dFTest);

        //Pago
        Pago pagoVisaToTest = this.españa.getPago("VISA");
        DataPedido dataPedido = new DataPedido(dCtxSh.pais);
        dataPedido.setPago(pagoVisaToTest);
        
        PageCheckoutWrapper.getDataPedidoFromCheckout(dataPedido, dCtxSh.channel, dFTest.driver);
        dCtxPago.setDataPedido(dataPedido);
        dCtxPago.getDataPedido().setEmailCheckout(dCtxSh.userConnected);
        PagoNavigationsStpV.testPagoFromCheckoutToEnd(dCtxPago, dCtxSh, pagoVisaToTest, dFTest);
        
        //Validación en Manto de los Pedidos (si existen)
        PedidosNavigations.testPedidosEnManto(dCtxPago.getListPedidos(), dCtxSh.appE, dFTest);
    }

    @Test (
        groups={"Compra", "Canal:desktop_App:shop"}, alwaysRun=true,
        description="[Usuario registrado] Compra Cheque regalo")
    public void COM004_Cheque_Regalo_NoReg_emailExist(ITestContext context, Method method) throws Exception {
    	DataFmwkTest dFTest = getdFTest();
        DataCtxShop dCtxSh = getdCtxSh();
        UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
        dCtxSh.userConnected = userShop.user;
        dCtxSh.passwordUser = userShop.password;        
        dCtxSh.userRegistered = true;
        dCtxSh.pais = this.españa;
            
        //Creamos una estructura para ir almacenando los datos del proceso de pagos
        String nTarjeta;
        String cvvTarjeta = "";
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false/*clearArticulos*/, dFTest);
        SecMenusWrapperStpV.seleccionLinea(LineaType.she, null/*sublineaType*/, dCtxSh, dFTest);
        SecFooterStpV.clickLinkFooter(FooterLink.cheque_regalo, false, dCtxSh.channel, dCtxSh.appE, dFTest);
        if(dCtxSh.channel != Channel.movil_web){
            nTarjeta = "100000040043";
            cvvTarjeta = "618";
            PageChequeRegaloInputDataStpV.paginaConsultarSaldo(dFTest, nTarjeta);
            PageChequeRegaloInputDataStpV.insertCVVConsultaSaldo(dFTest, cvvTarjeta);
        }

        PageChequeRegaloInputDataStpV.seleccionarCantidades(dFTest);
        PageChequeRegaloInputDataStpV.clickQuieroComprarChequeRegalo(dFTest);
            
        ChequeRegalo chequeRegalo = new ChequeRegalo();
        chequeRegalo.setNombre("Jorge");
        chequeRegalo.setApellidos("Muñoz Martínez");
        chequeRegalo.setEmail(Constantes.mail_standard);
        chequeRegalo.setImporte(Importe.euro50);
        chequeRegalo.setMensaje("Ya sólo queda por determinar si el universo partió de cero o del infinito");
        PageChequeRegaloInputDataStpV.inputDataAndClickComprar(chequeRegalo, dFTest);

        //Ejecutar el pago
        FlagsTestCkout fTCkout = new FlagsTestCkout();
        fTCkout.validaPasarelas = true;  
        fTCkout.validaPagos = true;
        fTCkout.validaPedidosEnManto = true;
        fTCkout.emailExist = false; 
        fTCkout.trjGuardada = false;
        fTCkout.isEmpl = false;
        fTCkout.isChequeRegalo = true;
        DataCtxPago dCtxPago = new DataCtxPago(dCtxSh);
        dCtxPago.setFTCkout(fTCkout);
        Pago pagoVISA = this.españa.getPago("VISA");
        dCtxPago.getDataPedido().setPago(pagoVISA);
        DataPedido dataPedido = dCtxPago.getDataPedido();
        dataPedido.setImporteTotal(PageCheckoutWrapper.getPrecioTotalFromResumen(dCtxSh.channel, dFTest.driver));
        dataPedido.setDireccionEnvio("");
        dataPedido.setEmailCheckout(dCtxSh.userConnected);
        PagoNavigationsStpV.checkPasarelaPago(dCtxPago, dCtxSh, dFTest);
        
        //Validación en Manto de los Pedidos (si existen)
        if (fTCkout.validaPedidosEnManto)
            PedidosNavigations.testPedidosEnManto(dCtxPago.getListPedidos(), dCtxSh.appE, dFTest);
     }    
    
    @Test (
        groups={"Compra", "Canal:desktop_App:shop", "Canal:desktop_App:outlet"}, alwaysRun=true,
        description="[Usuario no registrado] Compra con cambio datos en dirección de envío en checkout")
    public void COM003_Compra_y_CambioPais_Noreg_emailExist(ITestContext context, Method method) throws Exception {
    	DataFmwkTest dFTest = getdFTest();
        DataCtxShop dCtxSh = getdCtxSh();
        dCtxSh.userRegistered = false;
            
        //Indicamos la lista de países hacia los que queremos cambiar/verificar en la página de precompra 
        List<Pais> paisesDestino = new ArrayList<>();
        paisesDestino.add(this.francia);
        
        //Hasta página de Checkout
        FlagsTestCkout FTCkout = new FlagsTestCkout();
        FTCkout.validaPasarelas = false;  
        FTCkout.validaPagos = false;
        FTCkout.emailExist = true; 
        FTCkout.trjGuardada = false;
        FTCkout.isEmpl = false;
        DataCtxPago dCtxPago = new DataCtxPago(dCtxSh);
        dCtxPago.setFTCkout(FTCkout);
        //TestAB.activateTestABiconoBolsaDesktop(0, dCtxSh, dFTest.driver);
        PagoNavigationsStpV.testFromLoginToExecPaymetIfNeeded(paisesDestino, dCtxSh, dCtxPago, dFTest);
        
        //Validación en Manto de los Pedidos (si existen)
        if (FTCkout.validaPedidosEnManto)
            PedidosNavigations.testPedidosEnManto(dCtxPago.getListPedidos(), dCtxSh.appE, dFTest);
    }
            
    @SuppressWarnings("static-access")
    @Test (
        groups={"Compra", "Canal:desktop_App:all"}, alwaysRun=true,
        description="[Usuario no registrado] Pre-compra. Cierre/Inicio sesión correcto")
    public void COM005_Compra_noReg_emailNoExist(ITestContext context, Method method) throws Exception {
    	DataFmwkTest dFTest = getdFTest();
        DataCtxShop dCtxSh = getdCtxSh();
        dCtxSh.userRegistered = false;
	    
        //No permitiremos la ejecución diaria de este tipo de checkout porque implica la ejecución de un registro de usuario con el nuevo email introducido 
        if (utils.getTypeAccessFmwk(dFTest.ctx)!=TypeAccessFmwk.Bat) {
            //Hasta página de Checkout
            FlagsTestCkout FTCkout = new FlagsTestCkout();
            FTCkout.validaPasarelas = false;  
            FTCkout.validaPagos = false;
            FTCkout.emailExist = false; 
            FTCkout.trjGuardada = false;
            FTCkout.isEmpl = false;
            DataCtxPago dCtxPago = new DataCtxPago(dCtxSh);
            dCtxPago.setFTCkout(FTCkout);
            //TestAB.activateTestABiconoBolsaDesktop(0, dCtxSh, dFTest.driver);
            PagoNavigationsStpV.testFromLoginToExecPaymetIfNeeded(dCtxSh, dCtxPago, dFTest);
                    
            //Seleccionamos el logo de Mango (necesitamos acceder a una página con los links del menú superior)
            SecCabeceraStpV secCabeceraStpV = SecCabeceraStpV.getNew(dCtxSh, dFTest);
            secCabeceraStpV.selecLogo();
            if (dCtxSh.appE!=AppEcom.votf) {
                //Cerramos sesión y nos volvemos a identificar con los datos del registro
                String usrEmail = dCtxPago.getDatosRegistro().get("cfEmail");
                String password = dCtxPago.getDatosRegistro().get("cfPass");
                SecMenusWrapperStpV.secMenuUser.logoffLogin(usrEmail, password, dCtxSh.channel, dCtxSh.appE, dFTest);
                    
                //Ejecutamos la consulta de Mis datos comprobando que son coherentes con los utilizados en el registro
                //ListDataRegistro dataRegistroOK = new ListDataRegistro();
                HashMap<String,String> datosRegistro = dCtxPago.getDatosRegistro();
                datosRegistro.put("cfEmail", usrEmail);
                datosRegistro.put("cfPass", password);
                datosRegistro.put("", "Barcelona");
                datosRegistro.put("provinciaPais", "Barcelona");
                PageMiCuentaStpV.goToMisDatosAndValidateData(datosRegistro, dCtxSh.pais.getCodigo_pais(), dCtxSh.appE, dCtxSh.channel, dFTest);
            }            
            
            //Validación en Manto de los Pedidos (si existen)
            if (FTCkout.validaPedidosEnManto)
                PedidosNavigations.testPedidosEnManto(dCtxPago.getListPedidos(), dCtxSh.appE, dFTest);
        }
    }

    @Test (
        groups={"Compra", "Canal:desktop_App:votf"}, /*dependsOnGroups = {"Bolsa"},*/ alwaysRun=true,
        description="description=[Usuario no registrado] Test en VOTF compra desde tienda Francia")
    public void COM006_Compra_Francia_Tienda(ITestContext context, Method method) throws Exception {
    	DataFmwkTest dFTest = getdFTest();
        DataCtxShop dCtxSh = getdCtxSh();
        dCtxSh.pais=this.francia;
        dCtxSh.idioma=this.frances;   
        dCtxSh.userRegistered = false;
        
        //Hasta página de Checkout
        FlagsTestCkout FTCkout = new FlagsTestCkout();
        FTCkout.validaPasarelas = true;  
        FTCkout.validaPagos = true;
        FTCkout.validaPedidosEnManto = true;
        FTCkout.emailExist = true; 
        FTCkout.trjGuardada = false;
        FTCkout.isEmpl = false;
        DataCtxPago dCtxPago = new DataCtxPago(dCtxSh);
        dCtxPago.setFTCkout(FTCkout);
        PagoNavigationsStpV.testFromLoginToExecPaymetIfNeeded(dCtxSh, dCtxPago, dFTest);
        
        //Validación en Manto de los Pedidos (si existen)
        if (FTCkout.validaPedidosEnManto)
            PedidosNavigations.testPedidosEnManto(dCtxPago.getListPedidos(), dCtxSh.appE, dFTest);
    }
    
    //@Test (
    //    groups={"Compra", "Canal:desktop_App:shop"}, alwaysRun=true, priority=1,
    //    description="description=[Usuario sin conectar] Alta pago con Codensa (Colombia) y posterior cambios de estado mediante API")
    public void COM008_Compra_ColombiaCodensa(ITestContext context, Method method) throws Exception {
    	DataFmwkTest dFTest = getdFTest();
        DataCtxShop dCtxSh = getdCtxSh();
        dCtxSh.pais=this.colombia;
        dCtxSh.idioma=this.castellanoColomb;   
        dCtxSh.userRegistered = false;
        
        //Acceder a Colombia
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false/*clearArticulos*/, dFTest);
        
        //Alta artículos en la bolsa
        ArrayList<ArticleStock> listParaAlta = new ArrayList<>();
        ArticleStock article = ManagerArticlesStock.getArticleStock(TypeArticleStock.articlesWithMoreOneColour, dCtxSh);
        listParaAlta.add(article);
        DataBag dataBag = new DataBag(); 
        SecBolsaStpV.altaListaArticulosEnBolsa(listParaAlta, dataBag, dCtxSh, dFTest);        
        
        //Seleccionar el botón comprar y completar el proceso hasta la página de checkout con los métodos de pago
        FlagsTestCkout FTCkout = new FlagsTestCkout();
        FTCkout.validaPasarelas = false;  
        FTCkout.validaPagos = false;
        FTCkout.emailExist = false; 
        FTCkout.trjGuardada = false;
        FTCkout.isEmpl = false;
        FTCkout.testCodPromocional = false;
        FTCkout.validaPedidosEnManto = true;
        DataCtxPago dCtxPago = new DataCtxPago(dCtxSh);
        dCtxPago.setFTCkout(FTCkout);
        dCtxPago.getDataPedido().setDataBag(dataBag);
        PagoNavigationsStpV.testFromBolsaToCheckoutMetPago(dCtxSh, dCtxPago, dFTest);
        
        //Informamos datos varios necesarios para el proceso de pagos de modo que se pruebe el pago Codensa sobre el país de colombia
        dCtxPago.getDataPedido().setEmailCheckout(dCtxSh.userConnected);
        dCtxPago.getFTCkout().validaPagos = true;
        Pago pagoCodensa = dCtxSh.pais.getPago("CODENSA");
        dCtxPago.getDataPedido().setPago(pagoCodensa);
        PagoNavigationsStpV.checkPasarelaPago(dCtxPago, dCtxSh, dFTest);        
        
        //Validación en Manto de los Pedidos (si existen)
        if (FTCkout.validaPedidosEnManto) {
            PedidosNavigations.testPedidosEnManto(dCtxPago.getListPedidos(), dCtxSh.appE, dFTest);
            
            //String idCompra = dCtxPago.getListPedidos().get(0).getIdCompra();
            //Test API...
        }
    }
}