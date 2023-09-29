package com.mng.robotest.tests.domains.compra.payments.d3d;

import com.mng.robotest.tests.domains.compra.payments.PagoSteps;
import com.mng.robotest.tests.domains.compra.payments.d3d.steps.PageD3DLoginSteps;
import com.mng.robotest.testslegacy.beans.Pago;
import com.mng.robotest.testslegacy.beans.Pago.TypeTarj;
import com.mng.robotest.testslegacy.datastored.DataPago;
import com.mng.robotest.testslegacy.datastored.DataPedido;

public class PagoBancontact extends PagoSteps {
	
	public PagoBancontact(DataPago dataPago) {
		super(dataPago);
		super.setAvaliableExecPay(true);
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		boolean isD3D = true;
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago);
		
		if (execPay) {
			pageCheckoutWrapperSteps.inputDataTrjAndConfirmPago(dataPago);
			DataPedido dataPedido = this.dataPago.getDataPedido(); 
			dataPedido.setCodtipopago("U");
			Pago pago = dataPedido.getPago();
			//En el caso de Bancontact siempre saltará el D3D
			if (dataPedido.getPago().getTipotarjEnum()==TypeTarj.VISAD3D) {
				var pageD3DLoginSteps = new PageD3DLoginSteps();
				isD3D = pageD3DLoginSteps.validateIsD3D(2);
				pageD3DLoginSteps.isImporteVisible(dataPedido.getImporteTotal());
				if (isD3D) {
					dataPedido.setCodtipopago("Y");
					pageD3DLoginSteps.loginAndClickSubmit(pago.getUsrd3d(), pago.getPassd3d());
				}
			}
		}
	}	
}
