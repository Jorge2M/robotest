package com.mng.robotest.domains.compra.payments.ideal;

import com.mng.robotest.domains.compra.payments.PagoSteps;
import com.mng.robotest.domains.compra.payments.ideal.pageobjects.SecIdeal.BancoSeleccionado;
import com.mng.robotest.domains.compra.payments.ideal.steps.PageIdealSimuladorSteps;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;

public class PagoIdeal extends PagoSteps {
	
	public PagoIdeal(DataPago dataPago) {
		super(dataPago);
		super.setAvaliableExecPay(true);
	}

	@Override
	public void startPayment(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago);
		pageCheckoutWrapperSteps.getSecIdealSteps().validateIsSectionOk();
		
		if (execPay) {
			pageCheckoutWrapperSteps.getSecIdealSteps().clickBanco(BancoSeleccionado.TEST_ISSUER);
			dataPago = checkoutFlow.checkout(From.METODOSPAGO);
			
			var pageIdealSimuladorSteps = new PageIdealSimuladorSteps();
			pageIdealSimuladorSteps.validateIsPage();
			pageIdealSimuladorSteps.clickContinueButton();
		}
	}
}
