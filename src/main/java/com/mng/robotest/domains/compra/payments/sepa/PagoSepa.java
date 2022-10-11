package com.mng.robotest.domains.compra.payments.sepa;

import com.mng.robotest.domains.compra.payments.PagoSteps;
import com.mng.robotest.domains.compra.payments.sepa.steps.PageSepa1rstSteps;
import com.mng.robotest.domains.compra.payments.sepa.steps.PageSepaResultMobilSteps;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;

public class PagoSepa extends PagoSteps {
	
	PageSepa1rstSteps pageSepa1rstSteps = new PageSepa1rstSteps();
	
	public PagoSepa(DataPago dataPago) throws Exception {
		super(dataPago);
		super.setAvaliableExecPay(true);
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		Pago pago = this.dataPago.getDataPedido().getPago();
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		String importeTotal = dataPago.getDataPedido().getImporteTotal();
		pageSepa1rstSteps.validateIsPage(pago.getNombre(channel, app), importeTotal, dataTest.getCodigoPais(), channel);
		
		if (execPay) {
			pageSepa1rstSteps.inputDataAndclickPay(pago.getNumtarj(), pago.getTitular(), importeTotal, dataTest.getCodigoPais(), channel);
			if (channel.isDevice()) {
				new PageSepaResultMobilSteps().clickButtonPagar();
			}
		}
	}
}
