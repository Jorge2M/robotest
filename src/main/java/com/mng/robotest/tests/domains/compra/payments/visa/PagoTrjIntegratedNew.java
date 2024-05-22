package com.mng.robotest.tests.domains.compra.payments.visa;

import com.mng.robotest.tests.domains.compra.payments.PagoNewSteps;
import com.mng.robotest.tests.domains.compra.payments.visa.steps.PageD3DLoginSteps;
import com.mng.robotest.testslegacy.beans.Pago;
import com.mng.robotest.testslegacy.beans.Pago.TypeTarj;
import com.mng.robotest.testslegacy.datastored.DataPedido;

public class PagoTrjIntegratedNew extends PagoNewSteps {

	public PagoTrjIntegratedNew() {
		super();
		super.setAvaliableExecPay(true);
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		DataPedido dataPedido = this.dataPago.getDataPedido();
		if (dataPago.isSelectSaveCard()) {
			checkoutSteps.selectSaveCard();
		}
		
		if (execPay) {
			dataPedido.setCodtipopago("U");
			if (dataPago.isUseSavedCard()) {
				checkoutSteps.selectTrjGuardadaAndPayNow();
			} else {
				checkoutSteps.inputTrjAndPayNow(dataPedido.getPago());
			}
			checkD3DifNeeded(dataPedido);
		}
	}
	
	private void checkD3DifNeeded(DataPedido dataPedido) {
		if (isD3DCard(dataPedido.getPago())) {
			var pageD3DLoginSteps = new PageD3DLoginSteps();
			boolean isD3D = pageD3DLoginSteps.checkIsD3D(10);
			pageD3DLoginSteps.isImporteVisible(dataPedido.getImporteTotal());
			dataPedido.setCodtipopago("Y");
			if (isD3D) {
				var pago = dataPedido.getPago();
				pageD3DLoginSteps.loginAndClickSubmit(pago.getUsrd3d(), pago.getPassd3d());
			}
		}
	}
	
	private boolean isD3DCard(Pago pago) {
		return pago.getTipotarjEnum()==TypeTarj.VISAD3D;
	}
	
}
