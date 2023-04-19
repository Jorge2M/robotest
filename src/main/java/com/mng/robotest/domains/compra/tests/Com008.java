package com.mng.robotest.domains.compra.tests;

import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.domains.compra.steps.CheckoutSteps;
import com.mng.robotest.domains.compra.steps.PageResultPagoSteps;
import com.mng.robotest.domains.micuenta.steps.PageMisComprasSteps;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.utils.PaisGetter;

public class Com008 extends TestBase {

	private final CheckoutSteps checkoutSteps = new CheckoutSteps();
	
	public Com008() throws Exception {
		dataTest.setUserRegistered(true);
		dataTest.setPais(PaisGetter.from(PaisShop.SERBIA));
		dataTest.setIdioma(dataTest.getPais().getListIdiomas().get(0));
	}
	
	@Override
	public void execute() throws Exception {
		accessLoginAndClearBolsa();
		altaArticulosBolsaAndClickComprar();
		continueAndUnfoldPayments();
		if (!isPRO()) {
			var dataPago = executeVisaPayment();
			checkMisCompras(dataPago);
		}
	}

	private void accessLoginAndClearBolsa() throws Exception {
		access();
		new SecBolsaSteps().clear();
	}
	
	private void continueAndUnfoldPayments() {
		checkoutSteps.goToMetodosPagoMobile();
	}	
	
	public void checkIsPresentImportInBothCurrencies() throws Exception {
		checkoutSteps.isCroatiaImportInBothCurrencies();
	}
	
	private void checkMisCompras(DataPago dataPago) {
		String codigoPedido = dataPago.getDataPedido().getCodpedido();
		new PageResultPagoSteps().selectMisCompras();
		new PageMisComprasSteps().validateIsCompraOnline(codigoPedido);
	}
	
}
