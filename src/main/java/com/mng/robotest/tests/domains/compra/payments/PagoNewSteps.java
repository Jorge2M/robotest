package com.mng.robotest.tests.domains.compra.payments;

import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compranew.steps.CheckoutNewSteps;
import com.mng.robotest.testslegacy.datastored.DataBag;
import com.mng.robotest.testslegacy.datastored.DataPago;
import com.mng.robotest.testslegacy.datastored.DataPedido;

public abstract class PagoNewSteps extends StepBase implements PaymentSteps {
	
	protected final DataPago dataPago;
	protected final CheckoutNewSteps checkoutSteps = new CheckoutNewSteps();
	
	public static final String MSG_NO_PAY_IMPLEMENTED = "No está diponible la parte del test que permite completar/ejecutar el pago";

	private boolean availableExecPay = false;

	protected PagoNewSteps() {
		this.dataPago = dataTest.getDataPago();
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
