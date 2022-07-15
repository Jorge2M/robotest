package com.mng.robotest.domains.loyalty.tests;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.mng.robotest.domains.loyalty.beans.User;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.datastored.DataCheckPedidos;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.datastored.FlagsTestCkout;
import com.mng.robotest.test.datastored.DataCheckPedidos.CheckPedido;
import com.mng.robotest.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test.stpv.navigations.manto.PedidoNavigations;
import com.mng.robotest.test.stpv.navigations.shop.CheckoutFlow;
import com.mng.robotest.test.stpv.navigations.shop.GaleriaNavigationsStpV;
import com.mng.robotest.test.stpv.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test.stpv.shop.menus.SecMenusWrapperStpV;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets.SecretType;
import com.mng.robotest.utils.DataTest;


public class Loy001 extends LoyaltyTestBase {

	private final static User USER = new User(USER_PRO_WITH_LOY_POINTS, "6051483560048388114", "ES");
	private final DataCtxShop dataTest;
	
	public Loy001() throws Exception {
		super();
		dataTest = getDataTest();
	}
	
	@Override
	public void execute() throws Exception {
		AccesoStpV.oneStep(dataTest, true, driver);
		DataBag dataBag = addBagArticleNoRebajado();
		DataCtxPago dCtxPago = checkoutExecution(dataBag);
		checkPedidosManto(dCtxPago.getListPedidos());
	}
	
	private DataCtxShop getDataTest() throws Exception {
		DataCtxShop dataTest = DataTest.getData(PaisShop.ESPANA);
		dataTest.userConnected = USER.getEmail();
		dataTest.userRegistered = true;
		dataTest.passwordUser = GetterSecrets.factory()
				.getCredentials(SecretType.SHOP_STANDARD_USER)
				.getPassword();
		return dataTest;
	}
	
	private DataBag addBagArticleNoRebajado() throws Exception {
		
		Menu1rstLevel menuNewCollection;
		menuNewCollection = MenuTreeApp.getMenuLevel1From(app, KeyMenu1rstLevel.from(LineaType.she, null, "nuevo"));
		SecMenusWrapperStpV secMenusStpV = SecMenusWrapperStpV.getNew(dataTest, driver);
		secMenusStpV.selectMenu1rstLevelTypeCatalog(menuNewCollection, dataTest);
		//TODO en estos momentos algo raro le pasa al menú Nuevo que requiere un refresh para funcionar ok
		driver.navigate().refresh();
		
		DataBag dataBag = GaleriaNavigationsStpV.selectArticleAvailableFromGaleria(dataTest, driver);
		return dataBag;
	}
	
	private DataCtxPago checkoutExecution(DataBag dataBag) throws Exception {
		
		FlagsTestCkout FTCkout = new FlagsTestCkout();
		FTCkout.validaPasarelas = true;  
		FTCkout.validaPagos = true;
		FTCkout.emailExist = true; 
		FTCkout.loyaltyPoints = true;
		
		DataCtxPago dCtxPago = new DataCtxPago(dataTest);
		dCtxPago.setFTCkout(FTCkout);
		dCtxPago.getDataPedido().setDataBag(dataBag);
		
		dCtxPago = new CheckoutFlow.BuilderCheckout(dataTest, dCtxPago, driver)
			.pago(dataTest.pais.getPago("VISA"))
			.build()
			.checkout(From.Bolsa);
		
		return dCtxPago;
	}

	private void checkPedidosManto(CopyOnWriteArrayList<DataPedido> pedidos) throws Exception {
		
		List<CheckPedido> listChecks = Arrays.asList(
			CheckPedido.consultarBolsa, 
			CheckPedido.consultarPedido);
		
		DataCheckPedidos checksPedidos = DataCheckPedidos.newInstance(pedidos, listChecks);
		PedidoNavigations.testPedidosEnManto(checksPedidos, app, driver);
	}
}
