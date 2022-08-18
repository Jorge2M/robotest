package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.beans.Pago.TypeTarj;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.steps.shop.checkout.d3d.PageD3DLoginSteps;


public class PagoBancontact extends PagoSteps {
	
	public PagoBancontact(DataCtxShop dCtxSh, DataCtxPago dCtxPago) throws Exception {
		super(dCtxSh, dCtxPago);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		boolean isD3D = true;
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh.pais);
		
		if (execPay) {
			pageCheckoutWrapperSteps.inputDataTrjAndConfirmPago(this.dCtxPago);
			DataPedido dataPedido = this.dCtxPago.getDataPedido(); 
			dataPedido.setCodtipopago("U");
			Pago pago = dataPedido.getPago();
			//En el caso de Bancontact siempre saltará el D3D
			if (dataPedido.getPago().getTipotarjEnum()==TypeTarj.VISAD3D) {
				PageD3DLoginSteps pageD3DLoginSteps = new PageD3DLoginSteps(driver);
				isD3D = pageD3DLoginSteps.validateIsD3D(2);
				pageD3DLoginSteps.isImporteVisible(dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais());
				if (isD3D) {
					dataPedido.setCodtipopago("Y");
					pageD3DLoginSteps.loginAndClickSubmit(pago.getUsrd3d(), pago.getPassd3d());
				}
			}
		}
	}	
}
