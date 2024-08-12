package com.mng.robotest.tests.domains.compranew.tests;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.bolsa.steps.BolsaSteps;
import com.mng.robotest.tests.domains.compra.steps.PageResultPagoSteps;
import com.mng.robotest.tests.domains.micuenta.steps.PageMisComprasSteps;
import com.mng.robotest.testslegacy.utils.PaisGetter;

import static com.mng.robotest.testslegacy.data.PaisShop.SERBIA;

public class Cnw002 extends TestBase {
	
	public Cnw002() throws Exception {
		dataTest.setUserRegistered(true);
		if (inputParamsSuite.getListaPaises().isEmpty()) {
			dataTest.setPais(PaisGetter.from(SERBIA));
			dataTest.setIdioma(dataTest.getPais().getListIdiomas().get(0));
		}
	}
	
	@Override
	public void execute() throws Exception {
		if (isCheckeableNewCheckout()) {
			accessLoginAndClearBolsa();
			altaArticulosBolsaAndClickComprar();
			if (!isPRO()) {
				executeVisaPayment();
				checkMisCompras();
			}
		}
	}

	private void accessLoginAndClearBolsa() throws Exception {
		access();
		new BolsaSteps().clear();
	}
	
	private void checkMisCompras() {
		var dataPedido = dataTest.getDataPago().getDataPedido();
		new PageResultPagoSteps().selectMisCompras();
		new PageMisComprasSteps().checkIsCompraOnline(dataPedido.getCodpedido());
	}
}
