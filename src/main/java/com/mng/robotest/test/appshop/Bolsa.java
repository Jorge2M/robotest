package com.mng.robotest.test.appshop;

import org.testng.annotations.*;

import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test.getdata.usuarios.UserShop;
import com.mng.robotest.test.pageobject.shop.bolsa.SecBolsa.StateBolsa;
import com.mng.robotest.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow;
import com.mng.robotest.test.steps.navigations.shop.GaleriaNavigationsSteps;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.SecBolsaSteps;
import com.mng.robotest.test.steps.shop.menus.SecMenusWrapperSteps;
import com.mng.robotest.test.utils.PaisGetter;

import com.github.jorge2m.testmaker.service.TestMaker;

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
		
		AccesoSteps.manySteps(dCtxSh, driver);
		SecMenusWrapperSteps secMenusSteps = SecMenusWrapperSteps.getNew(dCtxSh);
		Menu1rstLevel menuVestidos = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "vestidos"));
		secMenusSteps.accesoMenuXRef(menuVestidos, dCtxSh);
		DataBag dataBag = GaleriaNavigationsSteps.selectArticleAvailableFromGaleria(dCtxSh, driver);

		ConfigCheckout configCheckout = ConfigCheckout.config()
				.emaiExists()
				.checkSavedCard()
				.userIsEmployee().build();
		
		DataCtxPago dCtxPago = new DataCtxPago(dCtxSh, configCheckout);

		dCtxPago.getDataPedido().setDataBag(dataBag);
		
		new CheckoutFlow.BuilderCheckout(dCtxSh, dCtxPago, driver).build().checkout(From.BOLSA);
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
		
		AccesoSteps.manySteps(dCtxSh, driver);
		SecMenusWrapperSteps secMenusSteps = SecMenusWrapperSteps.getNew(dCtxSh);
		Menu1rstLevel menuVestidos = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "vestidos"));
		secMenusSteps.accesoMenuXRef(menuVestidos, dCtxSh);
		
		SecBolsaSteps secBolsaSteps = new SecBolsaSteps(dCtxSh);
		secBolsaSteps.altaArticlosConColores(1, dataBag);
		
		//Hasta página de Checkout
		ConfigCheckout configCheckout = ConfigCheckout.config()
				.emaiExists()
				.checkSavedCard()
				.userIsEmployee().build();
		
		DataCtxPago dCtxPago = new DataCtxPago(dCtxSh, configCheckout);
		
		dCtxPago.getDataPedido().setDataBag(dataBag);
		
		new CheckoutFlow.BuilderCheckout(dCtxSh, dCtxPago, driver).build().checkout(From.BOLSA);
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
		SecBolsaSteps secBolsaSteps = new SecBolsaSteps(dCtxSh);
		DataBag dataBag = new DataBag();
		
		AccesoSteps.oneStep(dCtxSh, dCtxSh.userRegistered, driver);
		secBolsaSteps.altaArticlosConColores(2, dataBag);
		secBolsaSteps.forceStateBolsaTo(StateBolsa.CLOSED);
		secBolsaSteps.forceStateBolsaTo(StateBolsa.OPEN); 
		secBolsaSteps.clear1erArticuloBolsa(dataBag);								
		secBolsaSteps.altaArticlosConColores(1, dataBag);
		secBolsaSteps.click1erArticuloBolsa(dataBag);
	}
	
	private DataCtxShop getCtxShForTest() throws Exception {
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getInputParamsSuite();
		DataCtxShop dCtxSh = new DataCtxShop();
		dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
		dCtxSh.setChannel(inputParamsSuite.getChannel());
		dCtxSh.pais = PaisGetter.get(PaisShop.ESPANA);
		dCtxSh.idioma = dCtxSh.pais.getListIdiomas().get(0);
		return dCtxSh;
	}
}
