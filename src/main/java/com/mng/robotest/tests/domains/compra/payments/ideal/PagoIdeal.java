package com.mng.robotest.tests.domains.compra.payments.ideal;

import com.mng.robotest.tests.domains.compra.payments.PagoSteps;
import com.mng.robotest.tests.domains.compra.payments.ideal.pageobjects.SecIdealCheckout.BancoSeleccionado;
import com.mng.robotest.tests.domains.compra.payments.ideal.steps.PageIdealSimuladorSteps;
import com.mng.robotest.tests.domains.compra.payments.ideal.steps.SecIdealCheckoutSteps;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.From;

public class PagoIdeal extends PagoSteps {
	
	private final SecIdealCheckoutSteps secIdealCheckoutSteps = new SecIdealCheckoutSteps();
	
	public PagoIdeal() {
		super();
		super.setAvaliableExecPay(true);
	}

	@Override
	public void startPayment(boolean execPay) throws Exception {
		checkoutSteps.selectDeliveryAndClickPaymentMethod();
		secIdealCheckoutSteps.checkIsSectionOk();
		if (execPay) {
			secIdealCheckoutSteps.clickBanco(BancoSeleccionado.TEST_ISSUER);
			checkoutFlow.checkout(From.METODOSPAGO);
			
			var pageIdealSimuladorSteps = new PageIdealSimuladorSteps();
			pageIdealSimuladorSteps.validateIsPage();
			pageIdealSimuladorSteps.clickContinueButton();
		}
	}
	
}
