package com.mng.robotest.tests.domains.compra.payments.billpay;

import com.mng.robotest.tests.domains.compra.payments.PagoSteps;
import com.mng.robotest.tests.domains.compra.payments.PaymethodWithoutTestPayImplementedException;
import com.mng.robotest.testslegacy.datastored.DataPedido;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.From;

public class PagoBillpay extends PagoSteps {
	
	public PagoBillpay() {
		super();
		super.setAvaliableExecPay(true);
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		DataPedido dataPedido = this.dataPago.getDataPedido();
		checkoutSteps.fluxSelectEnvioAndClickPaymentMethod();
		checkoutSteps.getSecBillpaySteps().validateIsSectionOk();
		String nombrePago = dataPedido.getPago().getNombre(channel, app);
		checkoutSteps.getSecBillpaySteps().inputDiaNacAndCheckAcepto("23-04-1974", nombrePago);
		if (nombrePago.compareTo("Lastschrift")==0) {
			checkoutSteps.getSecBillpaySteps().inputDataInLastschrift(dataPedido.getPago().getIban(), dataPedido.getPago().getBic(), dataPedido.getPago().getTitular());
		}
		
		if (execPay) {
			checkoutFlow.checkout(From.METODOSPAGO);
			throw new PaymethodWithoutTestPayImplementedException(MSG_NO_PAY_IMPLEMENTED);
		}
	}	
}
