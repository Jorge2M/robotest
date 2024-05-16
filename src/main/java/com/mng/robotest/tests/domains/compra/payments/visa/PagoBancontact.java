package com.mng.robotest.tests.domains.compra.payments.visa;

import com.mng.robotest.tests.domains.compra.payments.PagoSteps;
import com.mng.robotest.tests.domains.compra.payments.visa.steps.PageD3DLoginSteps;
import com.mng.robotest.testslegacy.beans.Pago;
import com.mng.robotest.testslegacy.beans.Pago.TypeTarj;
import com.mng.robotest.testslegacy.datastored.DataPedido;

public class PagoBancontact extends PagoSteps {
	
	public PagoBancontact() {
		super();
		super.setAvaliableExecPay(true);
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		boolean isD3D = true;
		checkoutSteps.selectDeliveryAndClickPaymentMethod();
		
		if (execPay) {
			checkoutSteps.inputDataTrjAndConfirmPago();
			DataPedido dataPedido = this.dataPago.getDataPedido(); 
			dataPedido.setCodtipopago("U");
			Pago pago = dataPedido.getPago();
			//En el caso de Bancontact siempre saltará el D3D
			if (dataPedido.getPago().getTipotarjEnum()==TypeTarj.VISAD3D) {
				var pageD3DLoginSteps = new PageD3DLoginSteps();
				isD3D = pageD3DLoginSteps.checkIsD3D(2);
				pageD3DLoginSteps.isImporteVisible(dataPedido.getImporteTotal());
				if (isD3D) {
					dataPedido.setCodtipopago("Y");
					pageD3DLoginSteps.loginAndClickSubmit(pago.getUsrd3d(), pago.getPassd3d());
				}
			}
		}
	}	
}
