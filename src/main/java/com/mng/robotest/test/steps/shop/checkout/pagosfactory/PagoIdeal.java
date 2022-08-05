package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.pageobject.shop.checkout.ideal.SecIdeal.BancoSeleccionado;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.ideal.PageIdealSimuladorSteps;

public class PagoIdeal extends PagoSteps {
	
	public PagoIdeal(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) throws Exception {
		super(dCtxSh, dCtxPago, driver);
		super.isAvailableExecPay = true;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(this.dCtxPago, dCtxSh);
		pageCheckoutWrapperSteps.getSecIdealSteps().validateIsSectionOk();
		
		if (execPay) {
			pageCheckoutWrapperSteps.getSecIdealSteps().clickBanco(BancoSeleccionado.TestIssuer);
			dCtxPago = checkoutFlow.checkout(From.METODOSPAGO);
			PageIdealSimuladorSteps.validateIsPage(driver);
			PageIdealSimuladorSteps.clickContinueButton(driver);
		}
	}
}
