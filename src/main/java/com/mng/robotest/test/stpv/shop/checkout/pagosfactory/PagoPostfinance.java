package com.mng.robotest.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.pageobject.shop.checkout.postfinance.PagePostfSelectChannel;
import com.mng.robotest.test.pageobject.shop.checkout.postfinance.PagePostfSelectChannel.ChannelPF;
import com.mng.robotest.test.stpv.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.stpv.shop.checkout.postfinance.PagePostfCodSegStpV;
import com.mng.robotest.test.stpv.shop.checkout.postfinance.PagePostfSelectChannelStpV;

public class PagoPostfinance extends PagoStpV {
	
	public PagoPostfinance(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) throws Exception {
		super(dCtxSh, dCtxPago, driver);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		DataPedido dataPedido = dCtxPago.getDataPedido();
		pageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
		dCtxPago = checkoutFlow.checkout(From.MetodosPago);
		String nombrePago = dataPedido.getPago().getNombre(dCtxSh.channel, dCtxSh.appE);
		String importeTotal = dataPedido.getImporteTotal();
		String codPais = this.dCtxSh.pais.getCodigo_pais();
		if (isPageSelectChannel(5)) {
			managePageSelectChannel();
		}
		
		PagePostfCodSegStpV pagePostfCodSegStpV = new PagePostfCodSegStpV(driver);
		if (pagePostfCodSegStpV.getPageObj().isPasarelaTest()) {
			pagePostfCodSegStpV.validateIsPageTest(nombrePago, importeTotal);
		} else {
			pagePostfCodSegStpV.validateIsPagePro(importeTotal, codPais);
		}
		
		if (execPay) {
			dataPedido.setCodtipopago("P");
			pagePostfCodSegStpV.inputCodigoSeguridadAndAccept("11152", nombrePago);
		}
	}	
	
	private boolean isPageSelectChannel(int maxSeconds) {
		return (new PagePostfSelectChannel(driver)).isPage(maxSeconds);
	}
	
	private void managePageSelectChannel() {
		PagePostfSelectChannelStpV pagePostfSelectChannelStpV = new PagePostfSelectChannelStpV(driver);
		pagePostfSelectChannelStpV.checkIsPage(2);
		pagePostfSelectChannelStpV.selectChannel(ChannelPF.Card);
	}
}
