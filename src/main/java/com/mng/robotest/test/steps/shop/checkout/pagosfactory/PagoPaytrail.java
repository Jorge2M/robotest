package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.paytrail.PagePaytrail1rstSteps;
import com.mng.robotest.test.steps.shop.checkout.paytrail.PagePaytrailEpaymentSteps;
import com.mng.robotest.test.steps.shop.checkout.paytrail.PagePaytrailIdConfirmSteps;
import com.mng.robotest.test.steps.shop.checkout.paytrail.PagePaytrailResultadoOkSteps;


public class PagoPaytrail extends PagoSteps {
	
	public PagoPaytrail(DataCtxShop dCtxSh, DataCtxPago dCtxPago) throws Exception {
		super(dCtxSh, dCtxPago);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh.pais);
		dCtxPago = checkoutFlow.checkout(From.METODOSPAGO);
		PagePaytrail1rstSteps.validateIsPage(dCtxPago.getDataPedido().getImporteTotal(), dCtxSh.pais.getCodigo_pais(), dCtxSh.channel, driver);
		PagePaytrail1rstSteps.selectBancoAndContinue(dCtxSh.channel, driver);
		
		if (execPay) {
			this.dCtxPago.getDataPedido().setCodtipopago("F");
			String codigoPais = this.dCtxPago.getDataPedido().getCodigoPais();
			new PagePaytrailEpaymentSteps(driver).clickCodeCardOK(dCtxPago.getDataPedido().getImporteTotal(), codigoPais);
			PagePaytrailIdConfirmSteps.inputIDAndClickConfirmar("V2360A71", dCtxPago.getDataPedido().getImporteTotal(), codigoPais, driver);
			PagePaytrailResultadoOkSteps.clickVolverAMangoButton(driver);
		}
	}
}
