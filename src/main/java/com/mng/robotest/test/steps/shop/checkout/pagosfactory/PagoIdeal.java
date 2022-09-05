package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.pageobject.shop.checkout.ideal.SecIdeal.BancoSeleccionado;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.ideal.PageIdealSimuladorSteps;

public class PagoIdeal extends PagoSteps {
	
	public PagoIdeal(DataPago dataPago) throws Exception {
		super(dataPago);
		super.isAvailableExecPay = true;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(this.dataPago, dataTest.pais);
		pageCheckoutWrapperSteps.getSecIdealSteps().validateIsSectionOk();
		
		if (execPay) {
			pageCheckoutWrapperSteps.getSecIdealSteps().clickBanco(BancoSeleccionado.TestIssuer);
			dataPago = checkoutFlow.checkout(From.METODOSPAGO);
			PageIdealSimuladorSteps.validateIsPage(driver);
			PageIdealSimuladorSteps.clickContinueButton(driver);
		}
	}
}
