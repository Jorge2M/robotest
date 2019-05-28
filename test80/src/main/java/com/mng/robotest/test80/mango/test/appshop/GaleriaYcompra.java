package com.mng.robotest.test80.mango.test.appshop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.mango.GestorWebDriver;
import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.datastored.FlagsTestCkout;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.getdata.productos.ArticleStock;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecBolsaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.LocationArticle;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.PageGaleriaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusWrapperStpV;
import com.mng.robotest.test80.mango.test.utils.UtilsTestMango;

//TODO clase temporal para pruebas Loyalty
public class GaleriaYcompra extends GestorWebDriver {
    Pais españa = null;
    IdiomaPais castellano = null;
    String baseUrl;
    boolean acceptNextAlert = true;
    StringBuffer verificationErrors = new StringBuffer();
    private String index_fact = "";
    
    public GaleriaYcompra (int iteration) {
        this.index_fact = String.valueOf(iteration);
    }
	    
    @BeforeMethod (groups={"Loyalty", "Canal:all_App:all", "SupportsFactoryCountrys"})
    @Parameters({"brwsr-path", "urlBase", "AppEcom", "Channel"})
    public void login(String bpath, String urlAcceso, String appEcom, String channel, ITestContext context, Method method) 
    throws Exception {
        //Recopilación de parámetros
        DataCtxShop dCtxSh = new DataCtxShop();
        dCtxSh.setAppEcom(appEcom);
        dCtxSh.setChannel(channel);
        dCtxSh.urlAcceso = urlAcceso;
        
        //Si no existe, obtenemos el país España
        if (this.españa==null) {
            Integer codEspanya = Integer.valueOf(1);
            List<Pais> listaPaises = UtilsMangoTest.listaPaisesXML(new ArrayList<>(Arrays.asList(codEspanya)));
            this.españa = UtilsMangoTest.getPaisFromCodigo("001", listaPaises);
            this.castellano = this.españa.getListIdiomas().get(0);
        }
        
        TestCaseData.storeData(Constantes.idCtxSh, dCtxSh.clone());
        TestCaseData.getAndStoreDataFmwk(bpath, dCtxSh.urlAcceso, this.index_fact, dCtxSh.channel, context, method);
    }
	
    @SuppressWarnings("unused")
    @AfterMethod (groups={"FichaProducto", "Canal:all_App:all", "SupportsFactoryCountrys"}, alwaysRun = true)
    public void logout(ITestContext context, Method method) throws Exception {
        WebDriver driver = TestCaseData.getWebDriver();
        super.quitWebDriver(driver, context);
    }		
	
    @Test (
        groups={"FichaProducto", "Canal:desktop_App:all", "SupportsFactoryCountrys"}, alwaysRun=true, 
        description="[Usuario registrado] Se testean las features principales de una ficha con origen el buscador: añadir a la bolsa, selección color/talla, buscar en tienda, añadir a favoritos")
    public void GAC_GaleriaYcompra() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = (DataCtxShop)TestCaseData.getData(Constantes.idCtxSh);
        dCtxSh.pais=this.españa;
        dCtxSh.idioma=this.castellano;
        //dCtxSh.userConnected = "TESTCOMPRA22@GMAIL.COM";
        //dCtxSh.userConnected="QAEV70000900000@MANGO.COM";
        //dCtxSh.passwordUser = "mango123";
        //dCtxSh.userConnected="test.performance01@MANGO.COM";
        //dCtxSh.passwordUser = "Mango123";
        dCtxSh.userConnected="mng_test_2019-03-06101748.347@mango.com";
        dCtxSh.passwordUser = "sirjorge74";
        dCtxSh.userRegistered=true;
        
        boolean clearArticulos = true;
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, clearArticulos, dFTest.driver);
    	navegaGaleria(dCtxSh, dFTest);
    	testPago(dCtxSh, dFTest);
    }
    
    private void navegaGaleria(DataCtxShop dCtxSh, DataFmwkTest dFTest) throws Exception {
        Menu1rstLevel menuCamisas = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "camisas"));
        SecMenusWrapperStpV.selectMenu1rstLevelTypeCatalog(menuCamisas, dCtxSh, dFTest.driver);
        
        PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, dFTest.driver);
        LocationArticle loc1rsArticle1rstPage = LocationArticle.getInstanceInPage(2, 1);
        pageGaleriaStpV.selectArticuloEnPestanyaAndBack(loc1rsArticle1rstPage);
    }
    
    private void testPago(DataCtxShop dCtxSh, DataFmwkTest dFTest) throws Exception {
        FlagsTestCkout FTCkout = new FlagsTestCkout();
        FTCkout.validaPasarelas = true;  
        FTCkout.validaPagos = true;
        FTCkout.emailExist = true; 
        FTCkout.trjGuardada = false;
        FTCkout.isEmpl = true;
        DataCtxPago dCtxPago = new DataCtxPago(dCtxSh);
        dCtxPago.setFTCkout(FTCkout);
        
        int maxArticlesAwayVale = 1;
        List<ArticleStock> listArticles = UtilsTestMango.getArticlesForTestDependingVale(dCtxSh, maxArticlesAwayVale);
        
        DataBag dataBag = dCtxPago.getDataPedido().getDataBag();
        SecBolsaStpV.altaListaArticulosEnBolsa(listArticles, dataBag, dCtxSh, dFTest.driver);

        //Steps. Seleccionar el botón comprar y completar el proceso hasta la página de checkout con los métodos de pago
        dCtxPago.getFTCkout().testCodPromocional = true;
        PagoNavigationsStpV.testFromBolsaToCheckoutMetPago(dCtxSh, dCtxPago, dFTest.driver);
        
        //Pago
        Pago pagoVisaToTest = this.españa.getPago("VISA");
        DataPedido dataPedido = new DataPedido(dCtxSh.pais);
        dataPedido.setPago(pagoVisaToTest);
        
        PageCheckoutWrapper.getDataPedidoFromCheckout(dataPedido, dCtxSh.channel, dFTest.driver);
        dCtxPago.setDataPedido(dataPedido);
        dCtxPago.getDataPedido().setEmailCheckout(dCtxSh.userConnected);
        PagoNavigationsStpV.testPagoFromCheckoutToEnd(dCtxPago, dCtxSh, pagoVisaToTest, dFTest.driver);
    }
}
