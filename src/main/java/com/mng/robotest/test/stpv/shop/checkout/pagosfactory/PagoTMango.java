package com.mng.robotest.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.pageobject.shop.checkout.tmango.SecTMango;
import com.mng.robotest.test.stpv.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.stpv.shop.checkout.tmango.PageAmexInputTarjetaStpV;
import com.mng.robotest.test.stpv.shop.checkout.tmango.PageAmexResultStpV;
import com.mng.robotest.test.stpv.shop.checkout.tmango.PageRedsysSimStpV;

public class PagoTMango extends PagoStpV {

	public PagoTMango(DataCtxShop dCtxSh, DataCtxPago dataPago, WebDriver driver) throws Exception {
		super(dCtxSh, dataPago, driver);
		super.isAvailableExecPay = true;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		DataPedido dataPedido = this.dCtxPago.getDataPedido();
		pageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
		pageCheckoutWrapperStpV.getSecTMangoStpV().validateIsSectionOk();
		pageCheckoutWrapperStpV.getSecTMangoStpV().clickTipoPago(SecTMango.TipoPago.pagoHabitual);
		dCtxPago = checkoutFlow.checkout(From.MetodosPago);
		PageAmexInputTarjetaStpV.validateIsPageOk(dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais(), driver);
		
		if (execPay) {
			dataPedido.setCodtipopago("M");
			PageRedsysSimStpV pageRedsysSimStpV = 
				PageAmexInputTarjetaStpV.inputTarjetaAndPayButton(
					dataPedido.getPago().getNumtarj(), 
					dataPedido.getPago().getMescad(), 
					dataPedido.getPago().getAnycad(), 
					dataPedido.getPago().getCvc(), 
					dataPedido.getImporteTotal(), 
					dCtxSh.pais.getCodigo_pais(), driver);
			
			pageRedsysSimStpV.clickEnviar(dataPedido.getPago().getCip(), dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais(), driver);
			PageAmexResultStpV.clickContinuarButton(driver);
		}
	}
}
