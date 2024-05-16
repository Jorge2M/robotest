package com.mng.robotest.tests.domains.compra.tests;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.tests.domains.compra.steps.CheckoutSteps;
import com.mng.robotest.tests.domains.compra.steps.PageResultPagoSteps;
import com.mng.robotest.tests.domains.micuenta.steps.MiCuentaSteps;
import com.mng.robotest.tests.domains.micuenta.steps.PageMisComprasSteps;
import com.mng.robotest.tests.repository.secrets.GetterSecrets;
import com.mng.robotest.tests.repository.secrets.GetterSecrets.SecretType;
import com.mng.robotest.testslegacy.beans.AccesoEmpl;

public class Com001 extends TestBase {
	
	private CheckoutSteps checkoutSteps = new CheckoutSteps();
	
	public Com001() throws Exception {
		setDataTest();
	}
	
	private void setDataTest() {
		dataTest.setUserRegistered(true);
		dataTest.setUserConnected("test.performance11@mango.com");
		dataTest.setPasswordUser(GetterSecrets.factory()
			.getCredentials(SecretType.SHOP_PERFORMANCE_USER)
			.getPassword());
	}
	
	@Override
	public void execute() throws Exception {
		accessLoginAndClearBolsa();
		altaArticulosBolsaAndClickComprar(2);
		if (!isPRO()) {
			if (!isTarjetaVisaSaved()) {
				execPaymentSavingCardAndRepeatCheckout();
			}
			checkPromoEmployee();
			executeVisaPaymentSelectingSaveCard();
			checkMisCompras();
			//checkMisDatos();
		}
	}
	
	private boolean isTarjetaVisaSaved() {
		return checkoutSteps.isTarjetaGuardadaAvailable("VISA");
	}
	
	private void execPaymentSavingCardAndRepeatCheckout() throws Exception {
		executeVisaPaymentSavingCard();
		renewTestCase();
		access();
		altaArticulosBolsaAndClickComprar(2);
	}
	
	private void renewTestCase() {
		renewBrowser();
		checkoutSteps = new CheckoutSteps();
		setDataTest();		
		dataTest.getDataPago().setUseSavedCard(false);
	}
	
	private void executeVisaPaymentSelectingSaveCard() throws Exception {
        var dataPago = dataTest.getDataPago();
        dataPago.setPago(dataTest.getPais().getPago("VISA"));
        dataPago.setUseSavedCard(true);
        executePayment();
	}
	
	private void accessLoginAndClearBolsa() throws Exception {
		access();
		new SecBolsaSteps().clear();
	}
	
	private void checkPromoEmployee() {
		var accesoEmpl = AccesoEmpl.forSpain();
		checkoutSteps.inputTarjetaEmplEnCodPromo(dataTest.getPais(), accesoEmpl);
		checkoutSteps.inputDataEmplEnPromoAndAccept(accesoEmpl);
	}
	
	private void checkMisCompras() {
		var dataPago = dataTest.getDataPago();
		String codigoPedido = dataPago.getDataPedido().getCodpedido();
		new PageResultPagoSteps().selectMisCompras();
		new PageMisComprasSteps().checkIsCompraOnline(codigoPedido);
	}
	
	private void checkMisDatos() {
		var miCuentaSteps = new MiCuentaSteps();
		miCuentaSteps.goTo();
		miCuentaSteps.checkIsPedido(dataTest.getDataPago());
	}

}
