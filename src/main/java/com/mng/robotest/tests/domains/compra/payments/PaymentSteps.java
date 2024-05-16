package com.mng.robotest.tests.domains.compra.payments;

import com.mng.robotest.testslegacy.datastored.DataPago;

public interface PaymentSteps {

	public boolean isAvailableExecPay();
	public void setAvaliableExecPay(boolean availableExecPay);
	public void startPayment(boolean execPay) throws Exception;
	public void storePedidoForMantoAndResetData();
	public DataPago getDataPago();
	
}
