package com.mng.robotest.domains.compra.payments.sofort;

import com.mng.robotest.domains.compra.payments.PagoSteps;
import com.mng.robotest.domains.compra.payments.sofort.pageobjects.PageSofort1rst;
import com.mng.robotest.domains.compra.payments.sofort.steps.PageSofort2onSteps;
import com.mng.robotest.domains.compra.payments.sofort.steps.PageSofort4thSteps;
import com.mng.robotest.domains.compra.payments.sofort.steps.PageSofortIconosBancoSteps;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;

public class PagoSofort extends PagoSteps {
	
	public PagoSofort(DataPago dataPago) {
		super(dataPago);
		super.setAvaliableExecPay(true);
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		boolean isPageIconoSofort = new PageSofort1rst().isPageVisibleUntil(3);
		
		//En ocasiones se salta desde la página de Checkout-Mango hasta la página de selección del banco
		//saltándose la página de selección del icono de sofort
		if (isPageIconoSofort) {
			var pageSofortIconosBancoSteps = new PageSofortIconosBancoSteps();
			pageSofortIconosBancoSteps.validateIsPageUntil(3);
			pageSofortIconosBancoSteps.clickIconoSofort();
		}

		if (execPay) {
			Pago pago = this.dataPago.getDataPedido().getPago();
			var pageSofort2onSteps = new PageSofort2onSteps();
			pageSofort2onSteps.acceptCookies();
			pageSofort2onSteps.selectPaisYBanco(pago.getPaissofort(), pago.getBankcode());
			
			var pageSofort4thSteps = new PageSofort4thSteps();
			pageSofort4thSteps.inputCredencialesUsr(pago.getUsrsofort(), pago.getPasssofort());			
			pageSofort4thSteps.select1rstCtaAndAccept();
			pageSofort4thSteps.inputTANandAccept(pago.getTansofort());
			this.dataPago.getDataPedido().setCodtipopago("F");
		}
	}	
}
