package com.mng.robotest.domains.compra.tests;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.shop.checkout.PageCheckoutWrapperSteps;
import com.mng.robotest.test.steps.shop.checkout.PageResultPagoSteps;
import com.mng.robotest.test.steps.shop.checkout.pagosfactory.FactoryPagos;
import com.mng.robotest.test.steps.shop.checkout.pagosfactory.PagoSteps;
import com.mng.robotest.test.utils.PaisGetter;

public class Com008 extends TestBase {

	PageCheckoutWrapperSteps pageCheckoutWrapperSteps = new PageCheckoutWrapperSteps();
	
	public Com008() throws Exception {
		dataTest.userConnected = "e2e.hr.test@mango.com";
		dataTest.passwordUser = "hsXPv7rUoYw3QnMKRhPT";		
		dataTest.userRegistered = true;
		dataTest.pais = PaisGetter.get(PaisShop.CROATIA);
		dataTest.idioma = dataTest.pais.getListIdiomas().get(0);
	}
	
	@Override
	public void execute() throws Exception {
		accessLoginAndClearBolsa();
		altaArticulosBolsaAndClickComprar();
		continueAndUnfoldPayments();
		checkIsPresentImportInBothCurrencies();
		if (!isPRO()) {
			executeVisaPayment();
		}
		//checkPedido();
	}

	private void accessLoginAndClearBolsa() throws Exception {
		access();
		new SecBolsaSteps().clear();
	}
	
	private DataBag altaArticulosBolsaAndClickComprar() throws Exception {
		DataBag dataBag = new SecBolsaSteps().altaListaArticulosEnBolsa(getArticles(1));
		new SecBolsaSteps().selectButtonComprar(dataBag);
		return dataBag;
	}
	
	public void checkIsPresentImportInBothCurrencies() throws Exception {
		pageCheckoutWrapperSteps.isCroatiaImportInBothCurrencies();
	}
	
	private void continueAndUnfoldPayments() throws Exception {
		if (channel==Channel.mobile) {
			pageCheckoutWrapperSteps.goToMetodosPagoMobile();
		}
		pageCheckoutWrapperSteps.despliegaYValidaMetodosPago();
	}
	
	private void executeVisaPayment() throws Exception {
		DataPago dataPago = getDataPago();		
		dataPago.setPago(dataTest.pais.getPago("VISA"));
		PagoSteps pagoSteps = FactoryPagos.makePagoSteps(dataPago);
		pagoSteps.testPagoFromCheckout(true);
		new PageResultPagoSteps().validateIsPageOk(dataPago);
	}	
	
//	private void checkPedido() throws Exception {
//		List<CheckPedido> listChecks = Arrays.asList(
//			CheckPedido.consultarBolsa, 
//			CheckPedido.consultarPedido,
//			CheckPedido.anular); 
//		
//		CompraCommons.checkPedidosManto(listChecks, dataPago.getListPedidos(), app, driver);
//	}
}
