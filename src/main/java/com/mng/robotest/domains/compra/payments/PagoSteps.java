package com.mng.robotest.domains.compra.payments;

import static com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.BuilderCheckout;

import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.compra.steps.CheckoutSteps;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow;

public abstract class PagoSteps extends StepBase {
	
	protected final CheckoutFlow checkoutFlow;
	protected final CheckoutSteps pageCheckoutWrapperSteps = new CheckoutSteps();
	
	public DataPago dataPago;
	public static final String MSG_NO_PAY_IMPLEMENTED = "No está diponible la parte del test que permite completar/ejecutar el pago";

	private boolean availableExecPay = false;

	public PagoSteps(DataPago dataPago) {
		this.dataPago = dataPago;
		this.checkoutFlow = new BuilderCheckout(dataPago).build();
	}

	public boolean isAvailableExecPay() {
		return this.availableExecPay;
	}
	public void setAvaliableExecPay(boolean availableExecPay) {
		this.availableExecPay = availableExecPay;
	}

	public abstract void startPayment(boolean execPay) throws Exception;

	public void storePedidoForMantoAndResetData() {
		dataTest.setDataBag(new DataBag());
		this.dataPago.storePedidoForManto();
		this.dataPago.setDataPedido(new DataPedido(dataTest.getPais(), dataTest.getDataBag()));
	}
}
