package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import static com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.BuilderCheckout;

import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow;
import com.mng.robotest.test.steps.shop.checkout.CheckoutSteps;

public abstract class PagoSteps extends StepBase {
	
	final CheckoutFlow checkoutFlow;
	final CheckoutSteps pageCheckoutWrapperSteps = new CheckoutSteps();
	
	public DataPago dataPago;
	public static String MsgNoPayImplemented = "No está diponible la parte del test que permite completar/ejecutar el pago";

	public boolean isAvailableExecPay = false;

	public PagoSteps(DataPago dataPago) throws Exception {
		this.dataPago = dataPago;
		this.checkoutFlow = new BuilderCheckout(dataPago).build();
	}

	public boolean isAvailableExecPay() {
		return this.isAvailableExecPay;
	}

	public abstract void startPayment(boolean execPay) throws Exception;

	public void storePedidoForMantoAndResetData() {
		dataTest.dataBag = new DataBag();
		this.dataPago.storePedidoForManto();
		this.dataPago.setDataPedido(new DataPedido(dataTest.pais, dataTest.dataBag));
	}
}

class PaymethodWithoutTestPayImplemented extends Exception {
	private static final long serialVersionUID = 1L;
	public PaymethodWithoutTestPayImplemented(String msg){
		super(msg);
	}
 }
