package com.mng.robotest.domains.compra.tests;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.repository.productlist.entity.GarmentCatalog.Article;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.BuilderCheckout;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.utils.UtilsTest;

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
		List<Article> listArticles = UtilsTest.getArticlesForTest(dataTest.getPais(), app, 3, driver);
		if (!manyArticles) {
			listArticles = Arrays.asList(listArticles.get(0));
		}
		
		new SecBolsaSteps().altaListaArticulosEnBolsa(listArticles);

		//Hasta página Checkout
		ConfigCheckout configCheckout = ConfigCheckout.config()
				.checkPagos()
				.checkMisCompras()
//				.checkManto(!channel.isDevice())
				.emaiExists()
				.checkPromotionalCode(testVale || empleado)
				.userIsEmployee(empleado).build();
		
		DataPago dataPago = getDataPago(configCheckout);
		dataPago = new BuilderCheckout(dataPago)
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
