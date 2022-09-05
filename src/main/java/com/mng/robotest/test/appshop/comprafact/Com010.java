package com.mng.robotest.test.appshop.comprafact;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.domains.compra.tests.CompraCommons;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.datastored.DataCheckPedidos.CheckPedido;
import com.mng.robotest.test.generic.UtilsMangoTest;
import com.mng.robotest.test.getdata.products.data.GarmentCatalog;
import com.mng.robotest.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test.getdata.usuarios.UserShop;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.BuilderCheckout;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.SecBolsaSteps;
import com.mng.robotest.test.utils.UtilsTest;

public class Com010 extends TestBase {

	private final Pago pago;
	private final boolean testVale;
	private final boolean manyArticles;
	private final boolean empleado;
	private final boolean checkAnulaPedido;
	
	public Com010(
			Pais pais, IdiomaPais idioma, Pago pago, boolean usrRegistrado, boolean testVale, 
			boolean manyArticles, boolean empleado, boolean checkAnulaPedido) {
		dataTest.pais = pais;
		dataTest.idioma = idioma;
		dataTest.userRegistered = usrRegistrado;
		this.pago = pago;
		this.testVale = testVale(testVale);
		this.manyArticles = manyArticles;
		this.empleado = empleado;
		this.checkAnulaPedido = checkAnulaPedido;
	}
	
	@Override
	public void execute() throws Exception {
		if (dataTest.userRegistered) {
			UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
			dataTest.userConnected = userShop.user;
			dataTest.passwordUser = userShop.password;
		}
		
		new AccesoSteps().oneStep(dataTest.userRegistered);
		List<GarmentCatalog> listArticles = UtilsTest.getArticlesForTest(dataTest.pais, app, 3, driver);
		
		if (!manyArticles) {
			listArticles = Arrays.asList(listArticles.get(0));
		}
		
		DataBag dataBag = new DataBag(); 
		SecBolsaSteps secBolsaSteps = new SecBolsaSteps();
		secBolsaSteps.altaListaArticulosEnBolsa(listArticles, dataBag);
		
		//Hasta página Checkout
		ConfigCheckout configCheckout = ConfigCheckout.config()
				.checkPagos()
				.checkMisCompras()
				.checkManto()
				.emaiExists()
				.checkPromotionalCode(testVale || empleado)
				.userIsEmployee(empleado).build();
		
		DataPago dataPago = getDataPago(configCheckout, dataBag);
		dataPago = new BuilderCheckout(dataPago)
			.pago(this.pago)
			.build()
			.checkout(From.BOLSA);
		
		if (dataPago.getFTCkout().checkManto) {
			List<CheckPedido> listChecks = new ArrayList<CheckPedido>(Arrays.asList(
				CheckPedido.consultarBolsa, 
				CheckPedido.consultarPedido));
			if (checkAnulaPedido) {
				listChecks.add(CheckPedido.anular);
			}
			
			CompraCommons.checkPedidosManto(listChecks, dataPago.getListPedidos(), app, driver);
		}
	}
	
	private boolean testVale(boolean testVale) {
		return (
			testVale && 
			!new UtilsMangoTest().isEntornoPRO());
	}	

}
