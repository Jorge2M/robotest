package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.pageobject.shop.checkout.postfinance.PagePostfSelectChannel;
import com.mng.robotest.test.pageobject.shop.checkout.postfinance.PagePostfSelectChannel.ChannelPF;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.postfinance.PagePostfCodSegSteps;
import com.mng.robotest.test.steps.shop.checkout.postfinance.PagePostfSelectChannelSteps;

public class PagoPostfinance extends PagoSteps {
	
	public PagoPostfinance(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) throws Exception {
		super(dCtxSh, dCtxPago, driver);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		DataPedido dataPedido = dCtxPago.getDataPedido();
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
		dCtxPago = checkoutFlow.checkout(From.METODOSPAGO);
		String nombrePago = dataPedido.getPago().getNombre(dCtxSh.channel, dCtxSh.appE);
		String importeTotal = dataPedido.getImporteTotal();
		String codPais = this.dCtxSh.pais.getCodigo_pais();
		if (isPageSelectChannel(5)) {
			managePageSelectChannel();
		}
		
		PagePostfCodSegSteps pagePostfCodSegSteps = new PagePostfCodSegSteps(driver);
		if (pagePostfCodSegSteps.getPageObj().isPasarelaTest()) {
			pagePostfCodSegSteps.validateIsPageTest(nombrePago, importeTotal);
		} else {
			pagePostfCodSegSteps.validateIsPagePro(importeTotal, codPais);
		}
		
		if (execPay) {
			dataPedido.setCodtipopago("P");
			pagePostfCodSegSteps.inputCodigoSeguridadAndAccept("11152", nombrePago);
		}
	}	
	
	private boolean isPageSelectChannel(int maxSeconds) {
		return (new PagePostfSelectChannel(driver)).isPage(maxSeconds);
	}
	
	private void managePageSelectChannel() {
		PagePostfSelectChannelSteps pagePostfSelectChannelSteps = new PagePostfSelectChannelSteps(driver);
		pagePostfSelectChannelSteps.checkIsPage(2);
		pagePostfSelectChannelSteps.selectChannel(ChannelPF.Card);
	}
}
