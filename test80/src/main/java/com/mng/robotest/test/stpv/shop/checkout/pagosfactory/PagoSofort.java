package com.mng.robotest.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.pageobject.shop.checkout.sofort.PageSofort1rst;
import com.mng.robotest.test.stpv.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.stpv.shop.checkout.sofort.PageSofort2onStpV;
import com.mng.robotest.test.stpv.shop.checkout.sofort.PageSofort4thStpV;
import com.mng.robotest.test.stpv.shop.checkout.sofort.PageSofortIconosBancoStpV;

public class PagoSofort extends PagoStpV {
	
	public PagoSofort(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) throws Exception {
		super(dCtxSh, dCtxPago, driver);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
		dCtxPago = checkoutFlow.checkout(From.MetodosPago);
		boolean isPageIconoSofort = new PageSofort1rst(dCtxSh.channel, driver).isPageVisibleUntil(3);
		
		//En ocasiones se salta desde la página de Checkout-Mango hasta la página de selección del banco
		//saltándose la página de selección del icono de sofort
		if (isPageIconoSofort) {
			PageSofortIconosBancoStpV pageSofortIconosBancoStpV = new PageSofortIconosBancoStpV(dCtxSh.channel, driver);
			pageSofortIconosBancoStpV.validateIsPageUntil(3);
			pageSofortIconosBancoStpV.clickIconoSofort();
		}

		if (execPay) {
			Pago pago = this.dCtxPago.getDataPedido().getPago();
			PageSofort2onStpV pageSofort2onStpV = new PageSofort2onStpV(driver);
			pageSofort2onStpV.acceptCookies();
			pageSofort2onStpV.selectPaisYBanco(pago.getPaissofort(), pago.getBankcode());
			
			PageSofort4thStpV pageSofort4thStpV = new PageSofort4thStpV(driver);
			pageSofort4thStpV.inputCredencialesUsr(pago.getUsrsofort(), pago.getPasssofort());			
			pageSofort4thStpV.select1rstCtaAndAccept();
			pageSofort4thStpV.inputTANandAccept(pago.getTansofort());
			this.dCtxPago.getDataPedido().setCodtipopago("F");
		}
	}	
}