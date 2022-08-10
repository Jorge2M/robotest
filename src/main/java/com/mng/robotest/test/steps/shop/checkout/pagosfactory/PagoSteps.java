package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import static com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.BuilderCheckout;

import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow;
import com.mng.robotest.test.steps.shop.checkout.PageCheckoutWrapperSteps;


public abstract class PagoSteps extends StepBase {
	
	final CheckoutFlow checkoutFlow;
	final PageCheckoutWrapperSteps pageCheckoutWrapperSteps;
	
	public DataCtxShop dCtxSh;
	public DataCtxPago dCtxPago;
	public static String MsgNoPayImplemented = "No está diponible la parte del test que permite completar/ejecutar el pago";

	public boolean isAvailableExecPay = false;

	public PagoSteps(DataCtxShop dCtxSh, DataCtxPago dCtxPago) throws Exception {
		this.dCtxSh = dCtxSh;
		this.dCtxPago = dCtxPago;
		this.checkoutFlow = new BuilderCheckout(dCtxSh, dCtxPago, driver).build();
		this.pageCheckoutWrapperSteps = new PageCheckoutWrapperSteps(dCtxSh.channel, dCtxSh.appE, driver);
	}

	public boolean isAvailableExecPay() {
		return this.isAvailableExecPay;
	}

	public abstract void testPagoFromCheckout(boolean execPay) throws Exception;

	public void storePedidoForMantoAndResetData() {
		this.dCtxPago.storePedidoForManto();
		this.dCtxPago.setDataPedido(new DataPedido(this.dCtxSh.pais));
	}
}

class PaymethodWithoutTestPayImplemented extends Exception {
	private static final long serialVersionUID = 1L;
	public PaymethodWithoutTestPayImplemented(String msg){
		super(msg);
	}
 }
