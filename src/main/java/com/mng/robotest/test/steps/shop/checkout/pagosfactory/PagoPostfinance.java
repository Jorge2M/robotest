package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.pageobject.shop.checkout.postfinance.PagePostfSelectChannel;
import com.mng.robotest.test.pageobject.shop.checkout.postfinance.PagePostfSelectChannel.ChannelPF;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.postfinance.PagePostfCodSegSteps;
import com.mng.robotest.test.steps.shop.checkout.postfinance.PagePostfSelectChannelSteps;

public class PagoPostfinance extends PagoSteps {
	
	public PagoPostfinance(DataPago dataPago) throws Exception {
		super(dataPago);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		DataPedido dataPedido = dataPago.getDataPedido();
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago, dataTest.pais);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		String nombrePago = dataPedido.getPago().getNombre(channel, app);
		String importeTotal = dataPedido.getImporteTotal();
		String codPais = this.dataTest.pais.getCodigo_pais();
		if (isPageSelectChannel(5)) {
			managePageSelectChannel();
		}
		
		PagePostfCodSegSteps pagePostfCodSegSteps = new PagePostfCodSegSteps();
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
	
	private boolean isPageSelectChannel(int seconds) {
		return (new PagePostfSelectChannel(driver)).isPage(seconds);
	}
	
	private void managePageSelectChannel() {
		PagePostfSelectChannelSteps pagePostfSelectChannelSteps = new PagePostfSelectChannelSteps(driver);
		pagePostfSelectChannelSteps.checkIsPage(2);
		pagePostfSelectChannelSteps.selectChannel(ChannelPF.Card);
	}
}
