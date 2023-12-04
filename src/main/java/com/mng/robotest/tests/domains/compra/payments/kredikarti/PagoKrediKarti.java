package com.mng.robotest.tests.domains.compra.payments.kredikarti;

import com.mng.robotest.tests.domains.compra.payments.PagoSteps;
import com.mng.robotest.tests.domains.compra.payments.kredikarti.steps.SecKrediKartiSteps;
import com.mng.robotest.testslegacy.datastored.DataPago;
import com.mng.robotest.testslegacy.datastored.DataPedido;

public class PagoKrediKarti extends PagoSteps {
	
	public PagoKrediKarti(DataPago dataPago) {
		super(dataPago);
		super.setAvaliableExecPay(true);
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		DataPedido dataPedido = dataPago.getDataPedido();
		checkoutSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago);
		
		SecKrediKartiSteps secKrediKartiSteps = checkoutSteps.getSecKrediKartiSteps();
		secKrediKartiSteps.inputNumTarjeta(dataPedido.getPago().getNumtarj());
		secKrediKartiSteps.clickOpcionPagoAPlazo(1);
		
		if (execPay) {
			dataPedido.setCodtipopago("O");
			checkoutSteps.inputDataTrjAndConfirmPago(dataPago);
		}
	}	
}
