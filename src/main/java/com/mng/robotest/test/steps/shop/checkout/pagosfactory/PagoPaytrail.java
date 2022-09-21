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
	public void startPayment(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		
		PagePaytrail1rstSteps pagePaytrail1rstSteps = new PagePaytrail1rstSteps();
		pagePaytrail1rstSteps.validateIsPage(dataPago.getDataPedido().getImporteTotal());
		pagePaytrail1rstSteps.selectBancoAndContinue();
		if (execPay) {
			this.dataPago.getDataPedido().setCodtipopago("F");
			String codigoPais = this.dataPago.getDataPedido().getCodigoPais();
			new PagePaytrailEpaymentSteps().clickCodeCardOK(dataPago.getDataPedido().getImporteTotal(), codigoPais);
			new PagePaytrailIdConfirmSteps().inputIDAndClickConfirmar("V2360A71", dataPago.getDataPedido().getImporteTotal(), codigoPais);
			new PagePaytrailResultadoOkSteps().clickVolverAMangoButton();
		}
	}
}
