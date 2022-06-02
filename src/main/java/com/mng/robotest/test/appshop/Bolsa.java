package com.mng.robotest.test.appshop;

import org.testng.annotations.*;

import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.datastored.FlagsTestCkout;
import com.mng.robotest.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test.getdata.usuarios.UserShop;
import com.mng.robotest.test.pageobject.shop.bolsa.SecBolsa.StateBolsa;
import com.mng.robotest.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test.stpv.navigations.shop.CheckoutFlow;
import com.mng.robotest.test.stpv.navigations.shop.GaleriaNavigationsStpV;
import com.mng.robotest.test.stpv.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test.stpv.shop.SecBolsaStpV;
import com.mng.robotest.test.stpv.shop.menus.SecMenusWrapperStpV;
import com.mng.robotest.test.utils.PaisGetter;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;

import com.github.jorge2m.testmaker.service.TestMaker;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.openqa.selenium.WebDriver;

public class Bolsa {
	
	public Bolsa() {}

	@Test (
		groups={"Bolsa", "Canal:desktop_App:shop,outlet"}, alwaysRun=true, 
		description="[Usuario no registrado] Añadir artículo a la bolsa")
	public void BOR001_AddBolsaFromGaleria_NoReg() throws Exception {
		
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = getCtxShForTest();
		dCtxSh.userRegistered = false;
		
		AccesoStpV.manySteps(dCtxSh, driver);
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
		
		new CheckoutFlow.BuilderCheckout(dCtxSh, dCtxPago, driver).build().checkout(From.Bolsa);
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
		
		AccesoStpV.manySteps(dCtxSh, driver);
		SecMenusWrapperStpV secMenusStpV = SecMenusWrapperStpV.getNew(dCtxSh, driver);
		Menu1rstLevel menuVestidos = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "vestidos"));
		secMenusStpV.accesoMenuXRef(menuVestidos, dCtxSh);
		
		SecBolsaStpV secBolsaStpV = new SecBolsaStpV(dCtxSh, driver);
		secBolsaStpV.altaArticlosConColores(1, dataBag);
		
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
		
		new CheckoutFlow.BuilderCheckout(dCtxSh, dCtxPago, driver).build().checkout(From.Bolsa);
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
		SecBolsaStpV secBolsaStpV = new SecBolsaStpV(dCtxSh, driver);
		DataBag dataBag = new DataBag();
		
		AccesoStpV.oneStep(dCtxSh, dCtxSh.userRegistered, driver);
		secBolsaStpV.altaArticlosConColores(2, dataBag);
		secBolsaStpV.forceStateBolsaTo(StateBolsa.Closed);
		secBolsaStpV.forceStateBolsaTo(StateBolsa.Open); 
		secBolsaStpV.clear1erArticuloBolsa(dataBag);								
		secBolsaStpV.altaArticlosConColores(1, dataBag);
		secBolsaStpV.click1erArticuloBolsa(dataBag);
	}
	
	private DataCtxShop getCtxShForTest() throws Exception {
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getInputParamsSuite();
		DataCtxShop dCtxSh = new DataCtxShop();
		dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
		dCtxSh.setChannel(inputParamsSuite.getChannel());
		dCtxSh.pais = PaisGetter.get(PaisShop.Espana);
		dCtxSh.idioma = dCtxSh.pais.getListIdiomas().get(0);
		return dCtxSh;
	}

	public BrowserMobProxy forAllProxy()
	{
		BrowserMobProxy proxy = new BrowserMobProxyServer();
	    try {
	        String authHeader = "Basic " + Base64.getEncoder().encodeToString("webelement:click".getBytes("utf-8"));
	        proxy.addHeader("checkauth", authHeader);
	    }
	    catch (UnsupportedEncodingException e)
	    {
	        System.err.println("the Authorization can not be passed");
	        e.printStackTrace();
	    }
	    proxy.start(0);
	    return proxy;
	}
}
