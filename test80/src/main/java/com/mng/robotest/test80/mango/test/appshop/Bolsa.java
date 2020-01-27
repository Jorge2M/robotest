package com.mng.robotest.test80.mango.test.appshop;

import org.testng.annotations.*;

import com.mng.robotest.test80.access.InputParamsMango;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.PaisShop;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.FlagsTestCkout;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test80.mango.test.getdata.usuarios.UserShop;
import com.mng.robotest.test80.mango.test.pageobject.shop.bolsa.SecBolsa.StateBolsa;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.GaleriaNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecBolsaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusWrapperStpV;
import com.mng.robotest.test80.mango.test.utils.PaisGetter;
import com.mng.testmaker.service.TestMaker;

import org.openqa.selenium.WebDriver;

public class Bolsa {
	
    public Bolsa() {}

    private DataCtxShop getCtxShForTest() throws Exception {
    	InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getTestCase().getInputParamsSuite();
        DataCtxShop dCtxSh = new DataCtxShop();
        dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
        dCtxSh.setChannel(inputParamsSuite.getChannel());
        dCtxSh.urlAcceso = inputParamsSuite.getUrlBase();
		dCtxSh.pais = PaisGetter.get(PaisShop.España);
        dCtxSh.idioma = dCtxSh.pais.getListIdiomas().get(0);
        return dCtxSh;
    }

    @Test (
        groups={"Bolsa", "Canal:desktop_App:shop,outlet"}, alwaysRun=true, 
        description="[Usuario no registrado] Añadir artículo a la bolsa")
    public void BOR001_AddBolsaFromGaleria_NoReg() throws Exception {
    	WebDriver driver = TestMaker.getDriverTestCase();
        DataCtxShop dCtxSh = getCtxShForTest();
        dCtxSh.userRegistered = false;
        
        AccesoStpV.accesoAplicacionEnVariosPasos(dCtxSh, driver);
        SecMenusWrapperStpV secMenusStpV = SecMenusWrapperStpV.getNew(dCtxSh, driver);
        Menu1rstLevel menuVestidos = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "vestidos"));
        secMenusStpV.accesoMenuXRef(menuVestidos, dCtxSh);
        DataBag dataBag = GaleriaNavigationsStpV.selectArticleAvailableFromGaleria(dCtxSh, driver);

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
        PagoNavigationsStpV.testFromBolsaToCheckoutMetPago(dCtxSh, dCtxPago, driver);
    }

    @Test (
        groups={"Bolsa", "Canal:desktop_App:all"}, alwaysRun=true, 
        description="[Usuario no registrado] Añadir y eliminar artículos de la bolsa")
    public void BOR005_Gest_Prod_Bolsa_Noreg() throws Exception {
    	WebDriver driver = TestMaker.getDriverTestCase();
        DataCtxShop dCtxSh = getCtxShForTest();
        dCtxSh.userRegistered = false;
        BOR005_6_Gest_Prod_Bolsa(dCtxSh, driver);
    }

    @Test (
        groups={"Bolsa", "Canal:desktop_App:shop,outlet"}, alwaysRun=true, 
        description="[Usuario registrado] Añadir artículo a la bolsa")
    public void BOR002_AnyadirBolsa_yCompra_SiReg() throws Exception {
    	WebDriver driver = TestMaker.getDriverTestCase();
        DataCtxShop dCtxSh = getCtxShForTest();
        UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
        dCtxSh.userConnected = userShop.user;
        dCtxSh.passwordUser = userShop.password;
        dCtxSh.userRegistered = true;
        DataBag dataBag = new DataBag();
        
        AccesoStpV.accesoAplicacionEnVariosPasos(dCtxSh, driver);
        SecMenusWrapperStpV secMenusStpV = SecMenusWrapperStpV.getNew(dCtxSh, driver);
        Menu1rstLevel menuVestidos = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "vestidos"));
        secMenusStpV.accesoMenuXRef(menuVestidos, dCtxSh);
        SecBolsaStpV.altaArticlosConColores(1, dataBag, dCtxSh, driver);
        
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
        PagoNavigationsStpV.testFromBolsaToCheckoutMetPago(dCtxSh, dCtxPago, driver);
    }

	@Test (
		groups={"Bolsa", "Canal:desktop_App:shop,outlet"}, alwaysRun=true, 
		description="[Usuario registrado] Añadir y eliminar artículos de la bolsa")
	public void BOR006_Gest_Prod_Bolsa_Sireg() throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = getCtxShForTest();
		UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
		dCtxSh.userConnected = userShop.user;
		dCtxSh.passwordUser = userShop.password;
		dCtxSh.userRegistered = true;
		BOR005_6_Gest_Prod_Bolsa(dCtxSh, driver);
	}

    public static void BOR005_6_Gest_Prod_Bolsa(DataCtxShop dCtxSh, WebDriver driver) 
    throws Exception {
    	//TestAB.activateTestABiconoBolsaDesktop(2, dCtxSh, dFTest.driver);
        DataBag dataBag = new DataBag();
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, dCtxSh.userRegistered, driver);
        SecBolsaStpV.altaArticlosConColores(2, dataBag, dCtxSh, driver);
        SecBolsaStpV.forceStateBolsaTo(StateBolsa.Closed, dCtxSh.appE, dCtxSh.channel, driver);
        SecBolsaStpV.forceStateBolsaTo(StateBolsa.Open, dCtxSh.appE, dCtxSh.channel, driver); 
        SecBolsaStpV.clear1erArticuloBolsa(dataBag, dCtxSh.appE, dCtxSh.channel, driver);                                
        SecBolsaStpV.altaArticlosConColores(1, dataBag, dCtxSh, driver);
        SecBolsaStpV.click1erArticuloBolsa(dataBag, dCtxSh.appE, dCtxSh.channel, driver);
    }
}
