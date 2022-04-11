package com.mng.robotest.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.beans.Pago.TypeTarj;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.stpv.shop.checkout.d3d.PageD3DLoginStpV;

public class PagoBancontact extends PagoStpV {
	
	public PagoBancontact(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) throws Exception {
		super(dCtxSh, dCtxPago, driver);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		boolean isD3D = true;
		pageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
		this.dCtxPago.getFTCkout().trjGuardada = false;
		
		if (execPay) {
			pageCheckoutWrapperStpV.inputDataTrjAndConfirmPago(this.dCtxPago);
			DataPedido dataPedido = this.dCtxPago.getDataPedido(); 
			dataPedido.setCodtipopago("U");
			Pago pago = dataPedido.getPago();
			//En el caso de Bancontact siempre saltará el D3D
			if (dataPedido.getPago().getTipotarjEnum()==TypeTarj.VISAD3D) {
				PageD3DLoginStpV pageD3DLoginStpV = new PageD3DLoginStpV(driver);
				isD3D = pageD3DLoginStpV.validateIsD3D(2);
				pageD3DLoginStpV.isImporteVisible(dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais());
				if (isD3D) {
					dataPedido.setCodtipopago("Y");
					pageD3DLoginStpV.loginAndClickSubmit(pago.getUsrd3d(), pago.getPassd3d());
				}
			}
		}
	}	
}