package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.sepa.PageSepa1rstSteps;
import com.mng.robotest.test.steps.shop.checkout.sepa.PageSepaResultMobilSteps;

public class PagoSepa extends PagoSteps {
	
	PageSepa1rstSteps pageSepa1rstSteps = new PageSepa1rstSteps();
	
	public PagoSepa(DataPago dataPago) throws Exception {
		super(dataPago);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		Pago pago = this.dataPago.getDataPedido().getPago();
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(this.dataPago, dataTest.pais);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		String importeTotal = dataPago.getDataPedido().getImporteTotal();
		pageSepa1rstSteps.validateIsPage(pago.getNombre(channel, app), importeTotal, dataTest.pais.getCodigo_pais(), channel);
		
		if (execPay) {
			pageSepa1rstSteps.inputDataAndclickPay(pago.getNumtarj(), pago.getTitular(), importeTotal, dataTest.pais.getCodigo_pais(), channel);
			if (channel.isDevice()) {
				new PageSepaResultMobilSteps().clickButtonPagar();
			}
		}
	}
}
