package com.mng.robotest.tests.domains.compra.tests;

import java.util.Arrays;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.tests.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pago;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.BuilderCheckout;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.testslegacy.utils.UtilsTest;

public class Com010 extends TestBase {

	private final Pago pago;
	private final boolean testVale;
	private final boolean manyArticles;
	private final boolean empleado;
//	private final boolean checkAnulaPedido;
	
	public Com010(
			Pais pais, IdiomaPais idioma, Pago pago, boolean usrRegistrado, boolean testVale, 
			boolean manyArticles, boolean empleado, boolean checkAnulaPedido) {
		dataTest.setPais(pais);
		dataTest.setIdioma(idioma);
		dataTest.setUserRegistered(usrRegistrado);
		this.pago = pago;
		this.testVale = testVale;
		this.manyArticles = manyArticles;
		this.empleado = empleado;
//		this.checkAnulaPedido = checkAnulaPedido;
	}
	
	@Override
	public void execute() throws Exception {
		access(dataTest.isUserRegistered());
		var listArticles = UtilsTest.getArticlesForTest(dataTest.getPais(), app, 3, driver);
		if (!manyArticles) {
			listArticles = Arrays.asList(listArticles.get(0));
		}
		
		new SecBolsaSteps().altaListaArticulosEnBolsa(listArticles);

		//Hasta página Checkout
		var configCheckout = ConfigCheckout.config()
				.checkPagos()
				.checkMisCompras()
//				.checkManto(!channel.isDevice())
				.emaiExists()
				.checkPromotionalCode(testVale || empleado)
				.userIsEmployee(empleado).build();
		
		dataTest.setDataPago(configCheckout);
		new BuilderCheckout(dataTest.getDataPago())
			.pago(this.pago)
			.build()
			.checkout(From.BOLSA);
		
//		if (dataPago.getFTCkout().checkManto) {
//			List<CheckPedido> listChecks = new ArrayList<>(Arrays.asList(
//				CheckPedido.CONSULTAR_BOLSA, 
//				CheckPedido.CONSULTAR_PEDIDO));
//			if (checkAnulaPedido) {
//				listChecks.add(CheckPedido.ANULAR);
//			}
//			checkPedidosManto(listChecks, dataPago.getListPedidos());
//		}
	}

}
