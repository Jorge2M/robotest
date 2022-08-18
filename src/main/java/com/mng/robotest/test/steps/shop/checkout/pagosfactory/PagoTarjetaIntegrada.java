package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.steps.shop.checkout.d3d.PageD3DJPTestSelectOptionSteps;
import com.mng.robotest.test.steps.shop.checkout.d3d.PageD3DLoginSteps;


public class PagoTarjetaIntegrada extends PagoSteps {

	public PagoTarjetaIntegrada(DataCtxShop dCtxSh, DataCtxPago dCtxPago) throws Exception {
		super(dCtxSh, dCtxPago);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		DataPedido dataPedido = this.dCtxPago.getDataPedido();
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh.pais);
		
		if (execPay) {
			dataPedido.setCodtipopago("U");
			String metodoPago = dCtxPago.getDataPedido().getPago().getNombre();
			if (dCtxPago.getFTCkout().checkSavedCard && 
				pageCheckoutWrapperSteps.isTarjetaGuardadaAvailable(metodoPago)) {
				pageCheckoutWrapperSteps.selectTrjGuardadaAndConfirmPago(dCtxPago, "737");
			} else {
				pageCheckoutWrapperSteps.inputDataTrjAndConfirmPago(dCtxPago);
			}
			switch (dataPedido.getPago().getTipotarjEnum()) {
			case VISAD3D:
				PageD3DLoginSteps pageD3DLoginSteps = new PageD3DLoginSteps(driver);
				boolean isD3D = pageD3DLoginSteps.validateIsD3D(2);
				pageD3DLoginSteps.isImporteVisible(dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais());
				dataPedido.setCodtipopago("Y");
				if (isD3D) {
					pageD3DLoginSteps.loginAndClickSubmit(dataPedido.getPago().getUsrd3d(), dataPedido.getPago().getPassd3d());
				}
				
				break;
			case VISAD3D_JP:
				PageD3DJPTestSelectOptionSteps pageD3DJPTestSelectOptionSteps = new PageD3DJPTestSelectOptionSteps();
				boolean isD3DJP = pageD3DJPTestSelectOptionSteps.validateIsD3D(1);
				pageD3DJPTestSelectOptionSteps.isImporteVisible(dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais());
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
