package com.mng.robotest.domains.loyalty.tests;

import java.util.concurrent.CopyOnWriteArrayList;

import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.domains.compra.tests.CompraCommons;
import com.mng.robotest.domains.loyalty.beans.User;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow;
import com.mng.robotest.test.steps.navigations.shop.GaleriaNavigationsSteps;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets.SecretType;

public class Loy001 extends TestBase {

	static final User USER = LoyaltyCommons.USER_PRO_WITH_LOY_POINTS;
	
	public Loy001() throws Exception {
		super();
		
		dataTest.userConnected = USER.getEmail();
		dataTest.userRegistered = true;
		dataTest.passwordUser = GetterSecrets.factory()
				.getCredentials(SecretType.SHOP_STANDARD_USER)
				.getPassword();
	}
	
	@Override
	public void execute() throws Exception {
		accessAndClearData();
		DataBag dataBag = addBagArticleNoRebajado();
		DataPago dataPago = checkoutExecution(dataBag);
		checkPedidosManto(dataPago.getListPedidos());
	}
	
	private DataBag addBagArticleNoRebajado() throws Exception {
		clickMenu("nuevo");
		
		//TODO en estos momentos algo raro le pasa al menú Nuevo que requiere un refresh para funcionar ok
		driver.navigate().refresh();
		return new GaleriaNavigationsSteps().selectTalla(dataTest.pais);
	}
	
	private DataPago checkoutExecution(DataBag dataBag) throws Exception {
		
		ConfigCheckout configCheckout = ConfigCheckout.config()
				.checkPagos()
				.emaiExists()
				.checkLoyaltyPoints().build();		
		
		DataPago dataPago = getDataPago(configCheckout);
		dataPago.getDataPedido().setDataBag(dataBag);
		
		dataPago = new CheckoutFlow.BuilderCheckout(dataPago)
			.pago(dataTest.pais.getPago("VISA"))
			.build()
			.checkout(From.BOLSA);
		
		return dataPago;
	}

	private void checkPedidosManto(CopyOnWriteArrayList<DataPedido> pedidos) throws Exception {
		CompraCommons.checkPedidosManto(pedidos, app, driver);
	}
}