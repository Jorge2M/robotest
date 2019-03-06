package com.mng.robotest.test80.mango.test.appshop;

import org.testng.ITestContext;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.*;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.mango.*;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.FlagsTestCkout;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test80.mango.test.getdata.usuarios.UserShop;
import com.mng.robotest.test80.mango.test.pageobject.shop.bolsa.SecBolsa.StateBolsa;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecBolsaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.ModalArticleNotAvailableStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.PageGaleriaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusWrapperStpV;

import org.openqa.selenium.WebDriver;


public class Bolsa extends GestorWebDriver {

    Pais españa = null;
    IdiomaPais castellano = null;
    String baseUrl;
    boolean acceptNextAlert = true;
    StringBuffer verificationErrors = new StringBuffer();
        
    public Bolsa() {}         
      
    @BeforeMethod (groups={"Bolsa", "Canal:desktop_App:all"})
    @Parameters({"brwsr-path", "urlBase", "AppEcom", "Channel"}) 
    public void login(String bpath, String urlAcceso, String appEcom, String channel, ITestContext context, Method method) 
    throws Exception {
        //Recopilación de parámetros
        DataCtxShop dCtxSh = new DataCtxShop();
        dCtxSh.setAppEcom(appEcom);
        dCtxSh.setChannel(channel);
        dCtxSh.urlAcceso = urlAcceso;
        if (this.españa==null) {
            Integer codEspanya = Integer.valueOf(1);
            List<Pais> listaPaises = UtilsMangoTest.listaPaisesXML(new ArrayList<>(Arrays.asList(codEspanya)));
            this.españa = UtilsMangoTest.getPaisFromCodigo("001", listaPaises);
            this.castellano = this.españa.getListIdiomas().get(0);
        }
        
        dCtxSh.pais = this.españa;
        dCtxSh.idioma = this.castellano;
        
        TestCaseData.storeInThread(dCtxSh);
        TestCaseData.getAndStoreDataFmwk(bpath, dCtxSh.urlAcceso, "", dCtxSh.channel, context, method);
    }
    
    @SuppressWarnings("unused")
    @AfterMethod (groups={"Bolsa", "Canal:desktop_App:all"}, alwaysRun = true)
    public void logout(ITestContext context, Method method) throws Exception {
        WebDriver driver = TestCaseData.getWebDriver();
        super.quitWebDriver(driver, context);
    }       

    @Test (
        groups={"Bolsa", "Canal:desktop_App:shop", "Canal:desktop_App:outlet"}, alwaysRun=true, 
        description="[Usuario no registrado] Añadir artículo a la bolsa")
    public void BOR001_AddBolsaFromGaleria_NoReg() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = TestCaseData.getdCtxSh();
        dCtxSh.userRegistered = false;
        
        AccesoStpV.accesoAplicacionEnVariosPasos(dCtxSh, dFTest.driver);
        Menu1rstLevel menuVestidos = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "vestidos"));
        SecMenusWrapperStpV.accesoMenuXRef(menuVestidos, dCtxSh, dFTest);
        DataBag dataBag = new DataBag();
        
        //Seleccionamos artículos/tallas hasta que damos con uno disponible (no aparece el modal de "Avísame")
        int posArticulo=1;
        boolean articleAvailable = false;
        while (!articleAvailable && posArticulo<5) {
        	PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE);
        	pageGaleriaStpV.selectLinkAddArticuloToBagDesktop(posArticulo);
            articleAvailable = pageGaleriaStpV.selectTallaArticuloDesktop(posArticulo, 1/*posTalla*/, dataBag, dCtxSh);
            if (!articleAvailable) {
                ModalArticleNotAvailableStpV.clickAspaForClose(dFTest.driver);
                posArticulo+=1;
            }
        }
                
        //Hasta página de Checkout
        FlagsTestCkout FTCkout = new FlagsTestCkout();
        FTCkout.validaPasarelas = false;  
        FTCkout.validaPagos = false;
        FTCkout.validaPedidosEnManto = false;
        FTCkout.emailExist = true; 
        FTCkout.trjGuardada = true;
        FTCkout.isEmpl = true;
        FTCkout.testCodPromocional = false;
        DataCtxPago dCtxPago = new DataCtxPago(dCtxSh);
        dCtxPago.setFTCkout(FTCkout);
        dCtxPago.getDataPedido().setDataBag(dataBag);
        PagoNavigationsStpV.testFromBolsaToCheckoutMetPago(dCtxSh, dCtxPago, dFTest);
    }

    @Test (
        groups={"Bolsa", "Canal:desktop_App:all"}, alwaysRun=true, 
        description="[Usuario no registrado] Añadir y eliminar artículos de la bolsa")
    public void BOR005_Gest_Prod_Bolsa_Noreg() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = TestCaseData.getdCtxSh();
        dCtxSh.userRegistered = false;
        BOR005_6_Gest_Prod_Bolsa(dCtxSh, dFTest);
    }

    @Test (
        groups={"Bolsa", "Canal:desktop_App:shop", "Canal:desktop_App:outlet"}, alwaysRun=true, 
        description="[Usuario registrado] Añadir artículo a la bolsa")
    public void BOR002_AnyadirBolsa_yCompra_SiReg() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = TestCaseData.getdCtxSh();
        UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
        dCtxSh.userConnected = userShop.user;
        dCtxSh.passwordUser = userShop.password;
        dCtxSh.userRegistered = true;
        DataBag dataBag = new DataBag();
        
        //TestAB.activateTestABiconoBolsaDesktop(0, dCtxSh, dFTest.driver);
        AccesoStpV.accesoAplicacionEnVariosPasos(dCtxSh, dFTest.driver);
        Menu1rstLevel menuVestidos = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "vestidos"));
        SecMenusWrapperStpV.accesoMenuXRef(menuVestidos, dCtxSh, dFTest);
        SecBolsaStpV.altaArticlosConColores(1, dataBag, dCtxSh, dFTest.driver);
        
        //Hasta página de Checkout
        FlagsTestCkout FTCkout = new FlagsTestCkout();
        FTCkout.validaPasarelas = false;  
        FTCkout.validaPagos = false;
        FTCkout.emailExist = true; 
        FTCkout.trjGuardada = true;
        FTCkout.isEmpl = false;
        FTCkout.testCodPromocional = false;
        DataCtxPago dCtxPago = new DataCtxPago(dCtxSh);
        dCtxPago.setFTCkout(FTCkout);
        dCtxPago.getDataPedido().setDataBag(dataBag);
        PagoNavigationsStpV.testFromBolsaToCheckoutMetPago(dCtxSh, dCtxPago, dFTest);
    }

    @Test (
        groups={"Bolsa", "Canal:desktop_App:shop", "Canal:desktop_App:outlet"}, alwaysRun=true, 
        description="[Usuario registrado] Añadir y eliminar artículos de la bolsa")
    public void BOR006_Gest_Prod_Bolsa_Sireg() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = TestCaseData.getdCtxSh();
        UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
        dCtxSh.userConnected = userShop.user;
        dCtxSh.passwordUser = userShop.password;
        dCtxSh.userRegistered = true;
        BOR005_6_Gest_Prod_Bolsa(dCtxSh, dFTest);
    }

    public static void BOR005_6_Gest_Prod_Bolsa(DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
    	//TestAB.activateTestABiconoBolsaDesktop(2, dCtxSh, dFTest.driver);
        DataBag dataBag = new DataBag();
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, dCtxSh.userRegistered/*clearArticulos*/, dFTest.driver);
        SecBolsaStpV.altaArticlosConColores(2, dataBag, dCtxSh, dFTest.driver);
        SecBolsaStpV.forceStateBolsaTo(StateBolsa.Closed, dCtxSh.appE, dCtxSh.channel, dFTest.driver);
        SecBolsaStpV.forceStateBolsaTo(StateBolsa.Open, dCtxSh.appE, dCtxSh.channel, dFTest.driver); 
        SecBolsaStpV.clear1erArticuloBolsa(dataBag, dCtxSh.appE, dCtxSh.channel, dFTest.driver);                                
        SecBolsaStpV.altaArticlosConColores(1, dataBag, dCtxSh, dFTest.driver);
        SecBolsaStpV.click1erArticuloBolsa(dataBag, dCtxSh.appE, dCtxSh.channel, dFTest.driver);
    }
}
