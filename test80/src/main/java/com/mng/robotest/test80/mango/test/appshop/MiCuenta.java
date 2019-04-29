package com.mng.robotest.test80.mango.test.appshop;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.reflect.Method;
import org.testng.ITestContext;
import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.mango.*;
import com.mng.robotest.test80.mango.test.data.ChannelEnum;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageMisCompras.TypeCompra;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageSuscripciones.idNewsletters;
import com.mng.robotest.test80.mango.test.pageobject.shop.pedidos.PageDetallePedido.DetallePedido;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.PagePrehomeStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.micuenta.PageDevolucionesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.micuenta.PageMiCuentaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.micuenta.PageMisComprasStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.micuenta.PageMisDatosStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.micuenta.PageSuscripcionesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.modales.ModalDetalleMisComprasStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.pedidos.PageDetallePedidoStpV;

public class MiCuenta extends GestorWebDriver {
    Pais españa = null;
    IdiomaPais castellano = null;
    String baseUrl;
    boolean acceptNextAlert = true;
    StringBuffer verificationErrors = new StringBuffer();
    private String index_fact = "";
    public int prioridad;
    Pais paisFactory = null;
    IdiomaPais idiomaFactory = null;
            
    public MiCuenta() {
    }         
    
    /**
     * Constructor para invocación desde @Factory
     */
    public MiCuenta(Pais pais, IdiomaPais idioma, int prioridad) {
        this.paisFactory = pais;
        this.idiomaFactory = idioma;
        this.index_fact = pais.getNombre_pais() + " (" + pais.getCodigo_pais() + ") " + "-" + idioma.getCodigo().getLiteral();
        this.prioridad = prioridad;
    }
      
    @BeforeMethod(groups={"Micuenta", "Canal:all_App:all", "SupportsFactoryCountrys"})
    @Parameters({"brwsr-path","urlBase", "AppEcom", "Channel"})
    public void login(String bpath, String urlAcceso, String appEcom, String channel, ITestContext context, Method method) 
    throws Exception {
        //Recopilación de parámetros comunes
        DataCtxShop dCtxSh = new DataCtxShop();
        dCtxSh.setAppEcom(appEcom);
        dCtxSh.setChannel(channel);
        dCtxSh.urlAcceso = urlAcceso;

        //Si el acceso es normal (no es desde una @Factory) utilizaremos el España/Castellano
        if (this.paisFactory==null) {
	        //Si no existe, obtenemos el país España
	        if (this.españa==null) {
	            Integer codEspanya = Integer.valueOf(1);
	            List<Pais> listaPaises = UtilsMangoTest.listaPaisesXML(new ArrayList<>(Arrays.asList(codEspanya)));
	            this.españa = UtilsMangoTest.getPaisFromCodigo("001", listaPaises);
	            this.castellano = this.españa.getListIdiomas().get(0);
	        }
	        
	        dCtxSh.pais = this.españa;
	        dCtxSh.idioma = this.castellano;
	    } else {
            dCtxSh.pais = this.paisFactory;
            dCtxSh.idioma = this.idiomaFactory;        	
        }
        
        //Almacenamiento final a nivel de Thread (para disponer de 1 x cada @Test)
        TestCaseData.storeInThread(dCtxSh);
        TestCaseData.getAndStoreDataFmwk(bpath, dCtxSh.urlAcceso, this.index_fact, dCtxSh.channel, context, method);
    }


    @SuppressWarnings("unused")
    @AfterMethod (groups={"Micuenta", "Canal:all_App:all", "SupportsFactoryCountrys"}, alwaysRun = true)
    public void logout(ITestContext context, Method method) throws Exception {
        WebDriver driver = TestCaseData.getWebDriver();
        super.quitWebDriver(driver, context);
    }       

    @Test (
        groups={"Micuenta", "Canal:desktop_App:shop", "Canal:desktop_App:outlet"}, alwaysRun=true, 
        description="Verificar opciones de 'mi cuenta'")
    @Parameters({"userConDevolucionPeroSoloEnPRO", "passwordUserConDevolucion"})
    public void MIC001_Opciones_Mi_Cuenta(String userConDevolucionPeroNoEnPRO, String passwordUserConDevolucion) 
    throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = TestCaseData.getdCtxSh();
        dCtxSh.userConnected = userConDevolucionPeroNoEnPRO;
        dCtxSh.passwordUser = passwordUserConDevolucion;
            
        PagePrehomeStpV.seleccionPaisIdiomaAndEnter(dCtxSh, dFTest.driver);
        dCtxSh.userRegistered = false;
        SecMenusWrapperStpV.seleccionLinea(LineaType.she, null/*sublineaType*/, dCtxSh, dFTest.driver);
        dCtxSh.userRegistered = true;
        AccesoStpV.identificacionEnMango(dCtxSh, dFTest.driver);                                           
        PageMiCuentaStpV.goToMisDatos(dCtxSh.userConnected, dCtxSh.appE, dCtxSh.channel, dFTest.driver);                
        String nombreActual = PageMisDatosStpV.modificaNombreYGuarda(dFTest);
        PageMiCuentaStpV.goToMisDatos(dCtxSh.userConnected, dCtxSh.appE, dCtxSh.channel, dFTest.driver);
        PageMisDatosStpV.validaContenidoNombre(nombreActual, dFTest.driver);
        if (dCtxSh.appE==AppEcom.shop) {
            PageMiCuentaStpV.goToMisComprasFromMenu(dCtxSh, dCtxSh.channel, dFTest.driver);
        } else {
            PageMiCuentaStpV.goToMisPedidos(dCtxSh.userConnected, dCtxSh.appE, dCtxSh.channel, dFTest.driver);
        }
            
        PageMiCuentaStpV.goToSuscripciones(dCtxSh.appE, dCtxSh.channel, dFTest.driver);
        ArrayList<idNewsletters> listNewsletters = new ArrayList<>();
        listNewsletters.add(idNewsletters.she);
        PageSuscripcionesStpV.selectNewslettersAndGuarda(listNewsletters, dFTest);
        if (dCtxSh.appE!=AppEcom.outlet) {
            PageMiCuentaStpV.goToDevoluciones(dCtxSh.appE, dCtxSh.channel, dFTest.driver);
            PageDevolucionesStpV.solicitarRegogidaGratuitaADomicilio(dFTest);
            PageMiCuentaStpV.goToReembolsos(dCtxSh.appE, dCtxSh.channel, dFTest.driver);
        }
    }
    
    /**
     * @param userConCompras Usuario con compras de ambos tipos (tienda, online) en PRE pero sólo de tipo online en PRO
     */
    @Test (
        groups={"Micuenta", "Canal:all_App:shop", "SupportsFactoryCountrys"}, alwaysRun=true, 
        description="Consulta de mis compras con un usuario con datos a nivel de Tienda y Online")
    @Parameters({"userConComprasPeroSoloOnlineEnPRO", "passwordUserConCompras"})
    public void MIC002_CheckConsultaMisCompras(String userConCompras, String passwordUserConCompras) throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = TestCaseData.getdCtxSh();
        dCtxSh.userConnected = userConCompras;
        dCtxSh.passwordUser = passwordUserConCompras;
        dCtxSh.userRegistered = true;
        boolean isPRO = UtilsMangoTest.isEntornoPRO(dCtxSh.appE, dFTest.driver);
            
        PagePrehomeStpV.seleccionPaisIdiomaAndEnter(dCtxSh, dFTest.driver);
        AccesoStpV.identificacionEnMango(dCtxSh, dFTest.driver);
        PageMiCuentaStpV.goToMisComprasFromMenu(dCtxSh, dCtxSh.channel, dFTest.driver);
        PageMisComprasStpV.selectBlock(TypeCompra.Online, true/*ordersExpected*/, dFTest.driver);
        int posicionCompra = 1;
        PageMisComprasStpV.selectCompraOnline(posicionCompra, dCtxSh.pais.getCodigo_pais(), dCtxSh.channel, dFTest.driver);
        if (dCtxSh.channel == ChannelEnum.Channel.desktop) {
	        PageMisComprasStpV.clickMoreInfo(dFTest.driver);
	        ModalDetalleMisComprasStpV.clickBuscarTiendaButton(dFTest.driver);
	        ModalDetalleMisComprasStpV.clickCloseModalBuscadorTiendas(dFTest.driver);
        }
        PageDetallePedidoStpV pageDetPedidoStpV = new PageDetallePedidoStpV(dFTest.driver);
        if (pageDetPedidoStpV.getPageDetalle().getTypeDetalle()==DetallePedido.New) {
        	//Tratamiento específico para el nuevo Detalle del Pedido...
        	//,,,
        }
        
        pageDetPedidoStpV.clickBackButton(dCtxSh.channel, dFTest);
        
        //Estamos utilizando un usuario que en PRO no dispone de tíckets de Compra en tienda 
        if (isPRO) {
            PageMisComprasStpV.selectBlock(TypeCompra.Tienda, false/*ordersExpected*/, dFTest.driver);
        }
        //TODO actualmente no funciona el alta automática de compras en PRE mediante Flyway
//        else {
//            PageMisComprasStpV.selectBlock(TypeCompra.Tienda, true/*ordersExpected*/, dFTest);
//            PageMisComprasStpV.selectCompraTienda(1/*posArticle*/, dCtxSh.channel, dFTest);
//            PageMisComprasStpV.SecDetalleCompraTienda.selectArticulo(1/*posPrenda*/, dFTest);
//        }
    }
}