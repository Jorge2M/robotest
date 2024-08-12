package com.mng.robotest.tests.domains.compranew.tests;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.bolsa.steps.BolsaSteps;
import com.mng.robotest.tests.domains.compra.steps.PageResultPagoSteps;
import com.mng.robotest.tests.domains.compranew.steps.CheckoutNewSteps;
import com.mng.robotest.tests.domains.micuenta.steps.PageMisComprasSteps;
import com.mng.robotest.testslegacy.utils.PaisGetter;

import static com.mng.robotest.testslegacy.data.PaisShop.*;

public class Cnw001 extends TestBase {
	
	private CheckoutNewSteps checkoutSteps = new CheckoutNewSteps();
	
	public Cnw001() throws Exception {
		setDataTest();
	}
	
	private void setDataTest() {
		dataTest.setUserRegistered(true);
		if (inputParamsSuite.getListaPaises().isEmpty()) {
			dataTest.setPais(PaisGetter.from(EGYPT));
			dataTest.setIdioma(dataTest.getPais().getListIdiomas().get(0));
		}
	}
	
	@Override
	public void execute() throws Exception {
		if (isCheckeableNewCheckout()) {
			accessLoginAndClearBolsa();
			altaArticulosBolsaAndClickComprar();
			if (!isPRO()) {
				if (isTarjetaVisaSaved()) {
					removeSavedCard();
				}
				execPaymentSavingCardAndRepeatCheckout();
				executeVisaPaymentSelectingSavedCard();
				checkMisCompras();
			}
		}
	}

	private void accessLoginAndClearBolsa() throws Exception {
		access();
		new BolsaSteps().clear();
	}
	
	private boolean isTarjetaVisaSaved() {
		return checkoutSteps.isTarjetaGuardadaAvailable("VISA");
	}
	
	private void removeSavedCard() {
		checkoutSteps.removeSavedCard();
	}
	
	private void execPaymentSavingCardAndRepeatCheckout() throws Exception {
		executeVisaPaymentSavingCard();
		renewTestCase();
		access();
        dataTest.getDataPago().setSelectSaveCard(false);
		altaArticulosBolsaAndClickComprar(2);
	}
	
	private void renewTestCase() {
		renewBrowser();
		checkoutSteps = new CheckoutNewSteps();
		setDataTest();		
		dataTest.getDataPago().setUseSavedCard(false);
	}
	
	private void executeVisaPaymentSelectingSavedCard() throws Exception {
        var dataPago = dataTest.getDataPago();
        dataPago.setPago(dataTest.getPais().getPago("VISA"));
        dataPago.setUseSavedCard(true);
        executePayment();
	}	
	
	private void checkMisCompras() {
		var dataPedido = dataTest.getDataPago().getDataPedido();
		new PageResultPagoSteps().selectMisCompras();
		new PageMisComprasSteps().checkIsCompraOnline(dataPedido.getCodpedido());
	}
}
