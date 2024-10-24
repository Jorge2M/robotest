package com.mng.robotest.tests.domains.compra.payments;

import static com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.BuilderCheckout;

import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.steps.CheckoutSteps;
import com.mng.robotest.testslegacy.datastored.DataBag;
import com.mng.robotest.testslegacy.datastored.DataPago;
import com.mng.robotest.testslegacy.datastored.DataPedido;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow;

public abstract class PagoSteps extends StepBase implements PaymentSteps {
	
	protected final DataPago dataPago;
	protected final CheckoutFlow checkoutFlow;
	protected final CheckoutSteps checkoutSteps = new CheckoutSteps();
	
	public static final String MSG_NO_PAY_IMPLEMENTED = "No está diponible la parte del test que permite completar/ejecutar el pago";

	private boolean availableExecPay = false;

	protected PagoSteps() {
		this.dataPago = dataTest.getDataPago();
		this.checkoutFlow = new BuilderCheckout(dataTest.getDataPago()).build();
	}

	@Override
	public boolean isAvailableExecPay() {
		return this.availableExecPay;
	}
	
	@Override
	public void setAvaliableExecPay(boolean availableExecPay) {
		this.availableExecPay = availableExecPay;
	}

	@Override
	public void storePedidoForMantoAndResetData() {
		dataTest.setDataBag(new DataBag());
		this.dataPago.storePedidoForManto();
		this.dataPago.setDataPedido(new DataPedido(dataTest.getPais(), dataTest.getDataBag()));
	}

	@Override
	public DataPago getDataPago() {
		return dataPago;
	}
	
}
