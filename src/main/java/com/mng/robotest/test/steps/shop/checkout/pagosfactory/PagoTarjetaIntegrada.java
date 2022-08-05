package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.steps.shop.checkout.d3d.PageD3DJPTestSelectOptionSteps;
import com.mng.robotest.test.steps.shop.checkout.d3d.PageD3DLoginSteps;

public class PagoTarjetaIntegrada extends PagoSteps {

	public PagoTarjetaIntegrada(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) throws Exception {
		super(dCtxSh, dCtxPago, driver);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		DataPedido dataPedido = this.dCtxPago.getDataPedido();
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
		
		if (execPay) {
			dataPedido.setCodtipopago("U");
			String metodoPago = dCtxPago.getDataPedido().getPago().getNombre();
			if (dCtxPago.getFTCkout().trjGuardada && 
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
				boolean isD3DJP = PageD3DJPTestSelectOptionSteps.validateIsD3D(1, driver);
				PageD3DJPTestSelectOptionSteps.isImporteVisible(dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais(), driver);
				dataPedido.setCodtipopago("Y");
				if (isD3DJP) {
					PageD3DJPTestSelectOptionSteps.clickSubmitButton(driver);
				}
				
				break;
			case VISAESTANDAR:
			default:
			}
		}
	}
}
