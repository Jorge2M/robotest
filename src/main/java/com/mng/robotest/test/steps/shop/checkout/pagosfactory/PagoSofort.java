package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.pageobject.shop.checkout.sofort.PageSofort1rst;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.sofort.PageSofort2onSteps;
import com.mng.robotest.test.steps.shop.checkout.sofort.PageSofort4thSteps;
import com.mng.robotest.test.steps.shop.checkout.sofort.PageSofortIconosBancoSteps;

public class PagoSofort extends PagoSteps {
	
	public PagoSofort(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) throws Exception {
		super(dCtxSh, dCtxPago, driver);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
		dCtxPago = checkoutFlow.checkout(From.METODOSPAGO);
		boolean isPageIconoSofort = new PageSofort1rst(dCtxSh.channel, driver).isPageVisibleUntil(3);
		
		//En ocasiones se salta desde la página de Checkout-Mango hasta la página de selección del banco
		//saltándose la página de selección del icono de sofort
		if (isPageIconoSofort) {
			PageSofortIconosBancoSteps pageSofortIconosBancoSteps = new PageSofortIconosBancoSteps(dCtxSh.channel, driver);
			pageSofortIconosBancoSteps.validateIsPageUntil(3);
			pageSofortIconosBancoSteps.clickIconoSofort();
		}

		if (execPay) {
			Pago pago = this.dCtxPago.getDataPedido().getPago();
			PageSofort2onSteps pageSofort2onSteps = new PageSofort2onSteps(driver);
			pageSofort2onSteps.acceptCookies();
			pageSofort2onSteps.selectPaisYBanco(pago.getPaissofort(), pago.getBankcode());
			
			PageSofort4thSteps pageSofort4thSteps = new PageSofort4thSteps(driver);
			pageSofort4thSteps.inputCredencialesUsr(pago.getUsrsofort(), pago.getPasssofort());			
			pageSofort4thSteps.select1rstCtaAndAccept();
			pageSofort4thSteps.inputTANandAccept(pago.getTansofort());
			this.dCtxPago.getDataPedido().setCodtipopago("F");
		}
	}	
}
