package com.mng.robotest.domains.compra.tests;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.domains.micuenta.steps.PageMisComprasSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.shop.checkout.CheckoutSteps;
import com.mng.robotest.test.steps.shop.checkout.PageResultPagoSteps;
import com.mng.robotest.test.utils.PaisGetter;

public class Com008 extends TestBase {

	private final CheckoutSteps checkoutSteps = new CheckoutSteps();
	private final CompraSteps compraSteps = new CompraSteps();
	
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
			DataPago dataPago = executeVisaPayment();
			checkMisCompras(dataPago);
			//checkPedidoManto(dataPago);
		}
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
	
	private void continueAndUnfoldPayments() throws Exception {
		if (channel==Channel.mobile) {
			checkoutSteps.goToMetodosPagoMobile();
		}
		checkoutSteps.despliegaYValidaMetodosPago();
	}	
	
	public void checkIsPresentImportInBothCurrencies() throws Exception {
		checkoutSteps.isCroatiaImportInBothCurrencies();
	}

	private DataPago executeVisaPayment() throws Exception {
		DataPago dataPago = getDataPago();
		dataPago.setPago(dataTest.pais.getPago("VISA"));
		compraSteps.startPayment(dataPago, true);
	
		new PageResultPagoSteps().validateIsPageOk(dataPago);
		return dataPago;
	}	
	
	private void checkMisCompras(DataPago dataPago) throws Exception {
		String codigoPedido = dataPago.getDataPedido().getCodpedido();
		new PageResultPagoSteps().selectMisCompras();
		new PageMisComprasSteps().validateIsCompraOnline(codigoPedido);
	}
	
//	private void checkPedidoManto(DataPago dataPago) throws Exception {
//		List<CheckPedido> listChecks = Arrays.asList(
//			CheckPedido.consultarBolsa, 
//			CheckPedido.consultarPedido); 
//		
//		compraSteps.checkPedidosManto(listChecks, dataPago.getListPedidos());
//	}
}
