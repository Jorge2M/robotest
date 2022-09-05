package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.paytrail.PagePaytrail1rstSteps;
import com.mng.robotest.test.steps.shop.checkout.paytrail.PagePaytrailEpaymentSteps;
import com.mng.robotest.test.steps.shop.checkout.paytrail.PagePaytrailIdConfirmSteps;
import com.mng.robotest.test.steps.shop.checkout.paytrail.PagePaytrailResultadoOkSteps;

public class PagoPaytrail extends PagoSteps {
	
	public PagoPaytrail(DataPago dataPago) throws Exception {
		super(dataPago);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago, dataTest.pais);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		PagePaytrail1rstSteps.validateIsPage(dataPago.getDataPedido().getImporteTotal(), dataTest.pais.getCodigo_pais(), channel, driver);
		PagePaytrail1rstSteps.selectBancoAndContinue(channel, driver);
		
		if (execPay) {
			this.dataPago.getDataPedido().setCodtipopago("F");
			String codigoPais = this.dataPago.getDataPedido().getCodigoPais();
			new PagePaytrailEpaymentSteps(driver).clickCodeCardOK(dataPago.getDataPedido().getImporteTotal(), codigoPais);
			PagePaytrailIdConfirmSteps.inputIDAndClickConfirmar("V2360A71", dataPago.getDataPedido().getImporteTotal(), codigoPais, driver);
			PagePaytrailResultadoOkSteps.clickVolverAMangoButton(driver);
		}
	}
}
