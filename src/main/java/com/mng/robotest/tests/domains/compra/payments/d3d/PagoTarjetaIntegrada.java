package com.mng.robotest.tests.domains.compra.payments.d3d;

import com.mng.robotest.tests.domains.compra.payments.PagoSteps;
import com.mng.robotest.tests.domains.compra.payments.d3d.steps.PageD3DJPTestSelectOptionSteps;
import com.mng.robotest.tests.domains.compra.payments.d3d.steps.PageD3DLoginSteps;
import com.mng.robotest.testslegacy.datastored.DataPedido;

public class PagoTarjetaIntegrada extends PagoSteps {

	public PagoTarjetaIntegrada() {
		super();
		super.setAvaliableExecPay(true);
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		DataPedido dataPedido = this.dataPago.getDataPedido();
		checkoutSteps.fluxSelectEnvioAndClickPaymentMethod();
		if (dataPago.isSelectSaveCard()) {
			checkoutSteps.selectSaveCard();
		}
		
		if (execPay) {
			dataPedido.setCodtipopago("U");
			if (dataPago.isUseSavedCard()) {
				checkoutSteps.selectTrjGuardadaAndConfirmPago("747");
			} else {
				checkoutSteps.inputDataTrjAndConfirmPago();
			}
			switch (dataPedido.getPago().getTipotarjEnum()) {
			case VISAD3D:
				var pageD3DLoginSteps = new PageD3DLoginSteps();
				boolean isD3D = pageD3DLoginSteps.checkIsD3D(10);
				pageD3DLoginSteps.isImporteVisible(dataPedido.getImporteTotal());
				dataPedido.setCodtipopago("Y");
				if (isD3D) {
					var pago = dataPedido.getPago();
					pageD3DLoginSteps.loginAndClickSubmit(pago.getUsrd3d(), pago.getPassd3d());
				}
				
				break;
			case VISAD3D_JP:
				var pageD3DJPTestSelectOptionSteps = new PageD3DJPTestSelectOptionSteps();
				boolean isD3DJP = pageD3DJPTestSelectOptionSteps.validateIsD3D(1);
				pageD3DJPTestSelectOptionSteps.isImporteVisible(dataPedido.getImporteTotal(), dataTest.getCodigoPais());
				dataPedido.setCodtipopago("Y");
				if (isD3DJP) {
					pageD3DJPTestSelectOptionSteps.clickSubmitButton();
				}
				
				break;
			case VISAESTANDAR:
			default:
			}
		}
	}
}
