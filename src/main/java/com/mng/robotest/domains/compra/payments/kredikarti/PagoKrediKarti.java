package com.mng.robotest.domains.compra.payments.kredikarti;

import com.mng.robotest.domains.compra.payments.PagoSteps;
import com.mng.robotest.domains.compra.payments.kredikarti.steps.SecKrediKartiSteps;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.datastored.DataPedido;

public class PagoKrediKarti extends PagoSteps {
	
	public PagoKrediKarti(DataPago dataPago) throws Exception {
		super(dataPago);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		DataPedido dataPedido = dataPago.getDataPedido();
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago);
		
		SecKrediKartiSteps secKrediKartiSteps = pageCheckoutWrapperSteps.getSecKrediKartiSteps();
		secKrediKartiSteps.inputNumTarjeta(dataPedido.getPago().getNumtarj());
		secKrediKartiSteps.clickOpcionPagoAPlazo(1);
		
		if (execPay) {
			dataPedido.setCodtipopago("O");
			pageCheckoutWrapperSteps.inputDataTrjAndConfirmPago(dataPago);
		}
	}	
}
