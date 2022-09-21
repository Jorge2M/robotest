package com.mng.robotest.domains.compra.payments.d3d;

import com.mng.robotest.domains.compra.payments.PagoSteps;
import com.mng.robotest.domains.compra.payments.d3d.steps.PageD3DJPTestSelectOptionSteps;
import com.mng.robotest.domains.compra.payments.d3d.steps.PageD3DLoginSteps;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.datastored.DataPedido;

public class PagoTarjetaIntegrada extends PagoSteps {

	public PagoTarjetaIntegrada(DataPago dataPago) throws Exception {
		super(dataPago);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		DataPedido dataPedido = this.dataPago.getDataPedido();
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago);
		
		if (execPay) {
			dataPedido.setCodtipopago("U");
			String metodoPago = dataPago.getDataPedido().getPago().getNombre();
			if (dataPago.getFTCkout().checkSavedCard && 
				pageCheckoutWrapperSteps.isTarjetaGuardadaAvailable(metodoPago)) {
				pageCheckoutWrapperSteps.selectTrjGuardadaAndConfirmPago(dataPago, "737");
			} else {
				pageCheckoutWrapperSteps.inputDataTrjAndConfirmPago(dataPago);
			}
			switch (dataPedido.getPago().getTipotarjEnum()) {
			case VISAD3D:
				PageD3DLoginSteps pageD3DLoginSteps = new PageD3DLoginSteps();
				boolean isD3D = pageD3DLoginSteps.validateIsD3D(2);
				pageD3DLoginSteps.isImporteVisible(dataPedido.getImporteTotal());
				dataPedido.setCodtipopago("Y");
				if (isD3D) {
					pageD3DLoginSteps.loginAndClickSubmit(dataPedido.getPago().getUsrd3d(), dataPedido.getPago().getPassd3d());
				}
				
				break;
			case VISAD3D_JP:
				PageD3DJPTestSelectOptionSteps pageD3DJPTestSelectOptionSteps = new PageD3DJPTestSelectOptionSteps();
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
