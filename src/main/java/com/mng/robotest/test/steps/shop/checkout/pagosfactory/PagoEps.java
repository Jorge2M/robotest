package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.generic.UtilsMangoTest;
import com.mng.robotest.test.pageobject.shop.checkout.eps.PageEpsSimulador.TypeDelay;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.eps.PageEpsSelBancoSteps;
import com.mng.robotest.test.steps.shop.checkout.eps.PageEpsSimuladorSteps;

public class PagoEps extends PagoSteps {

	PageEpsSimuladorSteps pageEpsSimuladorSteps = new PageEpsSimuladorSteps();
	
	public PagoEps(DataPago dataPago) throws Exception {
		super(dataPago);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		//TODO mantener hasta que se elimine el actual TestAB para la aparición o no del pago EPS
		//activateTestABforMethodEPS();
		driver.navigate().refresh();
		
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago, dataTest.pais);
		pageCheckoutWrapperSteps.selectBancoEPS();
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		if (!new UtilsMangoTest().isEntornoPRO()) {
			pageEpsSimuladorSteps.validateIsPage();
			pageEpsSimuladorSteps.selectDelay(TypeDelay.ONE_MINUTES);
		} else {
			new PageEpsSelBancoSteps().validateIsPage(dataPago.getDataPedido().getImporteTotal(), dataTest.pais.getCodigo_pais());
		}
		
		if (execPay) {
			this.dataPago.getDataPedido().setCodtipopago("F");
			pageEpsSimuladorSteps.clickContinueButton();
		}
	}
}
