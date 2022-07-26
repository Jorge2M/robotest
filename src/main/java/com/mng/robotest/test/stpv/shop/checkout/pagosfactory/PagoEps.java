package com.mng.robotest.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.generic.UtilsMangoTest;
import com.mng.robotest.test.pageobject.shop.checkout.eps.PageEpsSimulador.TypeDelay;
import com.mng.robotest.test.stpv.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.stpv.shop.checkout.eps.PageEpsSelBancoStpV;
import com.mng.robotest.test.stpv.shop.checkout.eps.PageEpsSimuladorStpV;

public class PagoEps extends PagoStpV {

	public PagoEps(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) throws Exception {
		super(dCtxSh, dCtxPago, driver);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		//TODO mantener hasta que se elimine el actual TestAB para la aparición o no del pago EPS
		//activateTestABforMethodEPS();
		driver.navigate().refresh();
		
		pageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
		pageCheckoutWrapperStpV.selectBancoEPS(dCtxSh);
		dCtxPago = checkoutFlow.checkout(From.METODOSPAGO);
		if (!UtilsMangoTest.isEntornoPRO(dCtxSh.appE, driver)) {
			PageEpsSimuladorStpV.validateIsPage(driver);
			PageEpsSimuladorStpV.selectDelay(TypeDelay.OneMinutes, driver);
		} else {
			PageEpsSelBancoStpV.validateIsPage(dCtxPago.getDataPedido().getImporteTotal(), dCtxSh.pais.getCodigo_pais(), dCtxSh.channel, driver);
		}
		
		if (execPay) {
			this.dCtxPago.getDataPedido().setCodtipopago("F");
			PageEpsSimuladorStpV.clickContinueButton(driver);
		}
	}
}
