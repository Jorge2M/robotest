package com.mng.robotest.tests.domains.compranew.tests;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.tests.domains.compra.steps.CheckoutSteps;
import com.mng.robotest.tests.domains.compra.steps.PageResultPagoSteps;
import com.mng.robotest.tests.domains.micuenta.steps.PageMisComprasSteps;
import com.mng.robotest.testslegacy.data.PaisShop;
import com.mng.robotest.testslegacy.datastored.DataPago;
import com.mng.robotest.testslegacy.utils.PaisGetter;

public class Cnw001 extends TestBase {
	
	private final CheckoutSteps checkoutSteps = new CheckoutSteps();
	
	public Cnw001() throws Exception {
		dataTest.setUserRegistered(true);
		dataTest.setPais(PaisGetter.from(PaisShop.SERBIA));
		dataTest.setIdioma(dataTest.getPais().getListIdiomas().get(0));
	}
	
	@Override
	public void execute() throws Exception {
		accessLoginAndClearBolsa();
		altaArticulosBolsaAndClickComprar();
		if (!isPRO()) {
			var dataPago = executeVisaPayment();
			checkMisCompras(dataPago);
		}
	}

	private void accessLoginAndClearBolsa() throws Exception {
		access();
		new SecBolsaSteps().clear();
	}
	
	public void checkIsPresentImportInBothCurrencies() throws Exception {
		checkoutSteps.isCroatiaImportInBothCurrencies();
	}
	
	private void checkMisCompras(DataPago dataPago) {
		String codigoPedido = dataPago.getDataPedido().getCodpedido();
		new PageResultPagoSteps().selectMisCompras();
		new PageMisComprasSteps().checkIsCompraOnline(codigoPedido);
	}
}
