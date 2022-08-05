package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.generic.UtilsMangoTest;
import com.mng.robotest.test.pageobject.shop.checkout.eps.PageEpsSimulador.TypeDelay;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.eps.PageEpsSelBancoSteps;
import com.mng.robotest.test.steps.shop.checkout.eps.PageEpsSimuladorSteps;

public class PagoEps extends PagoSteps {

	public PagoEps(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) throws Exception {
		super(dCtxSh, dCtxPago, driver);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		//TODO mantener hasta que se elimine el actual TestAB para la aparición o no del pago EPS
		//activateTestABforMethodEPS();
		driver.navigate().refresh();
		
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
		pageCheckoutWrapperSteps.selectBancoEPS(dCtxSh);
		dCtxPago = checkoutFlow.checkout(From.METODOSPAGO);
		if (!UtilsMangoTest.isEntornoPRO(dCtxSh.appE, driver)) {
			PageEpsSimuladorSteps.validateIsPage(driver);
			PageEpsSimuladorSteps.selectDelay(TypeDelay.OneMinutes, driver);
		} else {
			PageEpsSelBancoSteps.validateIsPage(dCtxPago.getDataPedido().getImporteTotal(), dCtxSh.pais.getCodigo_pais(), dCtxSh.channel, driver);
		}
		
		if (execPay) {
			this.dCtxPago.getDataPedido().setCodtipopago("F");
			PageEpsSimuladorSteps.clickContinueButton(driver);
		}
	}
}
