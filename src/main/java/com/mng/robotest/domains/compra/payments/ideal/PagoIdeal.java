package com.mng.robotest.domains.compra.payments.ideal;

import com.mng.robotest.domains.compra.payments.PagoSteps;
import com.mng.robotest.domains.compra.payments.ideal.pageobjects.SecIdeal.BancoSeleccionado;
import com.mng.robotest.domains.compra.payments.ideal.steps.PageIdealSimuladorSteps;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;

public class PagoIdeal extends PagoSteps {
	
	public PagoIdeal(DataPago dataPago) throws Exception {
		super(dataPago);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago);
		pageCheckoutWrapperSteps.getSecIdealSteps().validateIsSectionOk();
		
		if (execPay) {
			pageCheckoutWrapperSteps.getSecIdealSteps().clickBanco(BancoSeleccionado.TestIssuer);
			dataPago = checkoutFlow.checkout(From.METODOSPAGO);
			
			PageIdealSimuladorSteps pageIdealSimuladorSteps = new PageIdealSimuladorSteps();
			pageIdealSimuladorSteps.validateIsPage();
			pageIdealSimuladorSteps.clickContinueButton();
		}
	}
}
