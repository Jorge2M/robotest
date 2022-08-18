package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;


public class PagoBillpay extends PagoSteps {
	
	public PagoBillpay(DataCtxShop dCtxSh, DataCtxPago dCtxPago) throws Exception {
		super(dCtxSh, dCtxPago);
		super.isAvailableExecPay = true;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		DataPedido dataPedido = this.dCtxPago.getDataPedido();
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh.pais);
		pageCheckoutWrapperSteps.getSecBillpaySteps().validateIsSectionOk();
		String nombrePago = dataPedido.getPago().getNombre(channel, app);
		pageCheckoutWrapperSteps.getSecBillpaySteps().inputDiaNacAndCheckAcepto("23-04-1974", nombrePago);
		if (nombrePago.compareTo("Lastschrift")==0) {
			pageCheckoutWrapperSteps.getSecBillpaySteps().inputDataInLastschrift(dataPedido.getPago().getIban(), dataPedido.getPago().getBic(), dataPedido.getPago().getTitular());
		}
		
		if (execPay) {
			dCtxPago = checkoutFlow.checkout(From.METODOSPAGO);
			throw new PaymethodWithoutTestPayImplemented(MsgNoPayImplemented);
		}
	}	
}
