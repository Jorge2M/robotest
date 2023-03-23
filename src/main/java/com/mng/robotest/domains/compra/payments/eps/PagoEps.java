package com.mng.robotest.domains.compra.payments.eps;

import com.mng.robotest.domains.compra.payments.PagoSteps;
import com.mng.robotest.domains.compra.payments.eps.pageobjects.PageEpsSimulador.TypeDelay;
import com.mng.robotest.domains.compra.payments.eps.steps.PageEpsSelBancoSteps;
import com.mng.robotest.domains.compra.payments.eps.steps.PageEpsSimuladorSteps;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;

public class PagoEps extends PagoSteps {

	private final PageEpsSimuladorSteps pageEpsSimuladorSteps = new PageEpsSimuladorSteps();
	
	public PagoEps(DataPago dataPago) {
		super(dataPago);
		super.setAvaliableExecPay(true);
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		//TODO mantener hasta que se elimine el actual TestAB para la aparición o no del pago EPS
		//activateTestABforMethodEPS();
		driver.navigate().refresh();
		
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago);
		pageCheckoutWrapperSteps.selectBancoEPS();
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		if (!isPRO()) {
			pageEpsSimuladorSteps.validateIsPage();
			pageEpsSimuladorSteps.selectDelay(TypeDelay.ONE_MINUTES);
		} else {
			new PageEpsSelBancoSteps().validateIsPage(dataPago.getDataPedido().getImporteTotal());
		}
		
		if (execPay) {
			this.dataPago.getDataPedido().setCodtipopago("F");
			pageEpsSimuladorSteps.clickContinueButton();
		}
	}
}
