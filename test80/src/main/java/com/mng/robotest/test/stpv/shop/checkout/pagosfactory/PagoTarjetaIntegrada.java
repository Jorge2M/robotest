package com.mng.robotest.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.stpv.shop.checkout.d3d.PageD3DJPTestSelectOptionStpV;
import com.mng.robotest.test.stpv.shop.checkout.d3d.PageD3DLoginStpV;

public class PagoTarjetaIntegrada extends PagoStpV {

	public PagoTarjetaIntegrada(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) throws Exception {
		super(dCtxSh, dCtxPago, driver);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		DataPedido dataPedido = this.dCtxPago.getDataPedido();
		pageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
		
		if (execPay) {
			dataPedido.setCodtipopago("U");
			String metodoPago = dCtxPago.getDataPedido().getPago().getNombre();
			if (dCtxPago.getFTCkout().trjGuardada && 
				pageCheckoutWrapperStpV.isTarjetaGuardadaAvailable(metodoPago)) {
				pageCheckoutWrapperStpV.selectTrjGuardadaAndConfirmPago(dCtxPago, "737");
			} else {
				pageCheckoutWrapperStpV.inputDataTrjAndConfirmPago(dCtxPago);
			}
			switch (dataPedido.getPago().getTipotarjEnum()) {
			case VISAD3D:
				PageD3DLoginStpV pageD3DLoginStpV = new PageD3DLoginStpV(driver);
				boolean isD3D = pageD3DLoginStpV.validateIsD3D(2);
				pageD3DLoginStpV.isImporteVisible(dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais());
				dataPedido.setCodtipopago("Y");
				if (isD3D) {
					pageD3DLoginStpV.loginAndClickSubmit(dataPedido.getPago().getUsrd3d(), dataPedido.getPago().getPassd3d());
				}
				
				break;
			case VISAD3D_JP:
				boolean isD3DJP = PageD3DJPTestSelectOptionStpV.validateIsD3D(1, driver);
				PageD3DJPTestSelectOptionStpV.isImporteVisible(dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais(), driver);
				dataPedido.setCodtipopago("Y");
				if (isD3DJP) {
					PageD3DJPTestSelectOptionStpV.clickSubmitButton(driver);
				}
				
				break;
			case VISAESTANDAR:
			default:
			}
		}
	}
}