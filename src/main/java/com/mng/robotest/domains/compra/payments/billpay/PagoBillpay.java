package com.mng.robotest.domains.compra.payments.billpay;

import com.mng.robotest.domains.compra.payments.PagoSteps;
import com.mng.robotest.domains.compra.payments.PaymethodWithoutTestPayImplementedException;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;

public class PagoBillpay extends PagoSteps {
	
	public PagoBillpay(DataPago dataPago) throws Exception {
		super(dataPago);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		DataPedido dataPedido = this.dataPago.getDataPedido();
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago);
		pageCheckoutWrapperSteps.getSecBillpaySteps().validateIsSectionOk();
		String nombrePago = dataPedido.getPago().getNombre(channel, app);
		pageCheckoutWrapperSteps.getSecBillpaySteps().inputDiaNacAndCheckAcepto("23-04-1974", nombrePago);
		if (nombrePago.compareTo("Lastschrift")==0) {
			pageCheckoutWrapperSteps.getSecBillpaySteps().inputDataInLastschrift(dataPedido.getPago().getIban(), dataPedido.getPago().getBic(), dataPedido.getPago().getTitular());
		}
		
		if (execPay) {
			dataPago = checkoutFlow.checkout(From.METODOSPAGO);
			throw new PaymethodWithoutTestPayImplementedException(MsgNoPayImplemented);
		}
	}	
}
