package com.mng.robotest.tests.domains.compra.payments.postfinance;

import com.mng.robotest.tests.domains.compra.payments.PagoSteps;
import com.mng.robotest.tests.domains.compra.payments.postfinance.pageobjects.PagePostfSelectChannel;
import com.mng.robotest.tests.domains.compra.payments.postfinance.pageobjects.PagePostfSelectChannel.ChannelPF;
import com.mng.robotest.tests.domains.compra.payments.postfinance.steps.PagePostfCodSegSteps;
import com.mng.robotest.tests.domains.compra.payments.postfinance.steps.PagePostfSelectChannelSteps;
import com.mng.robotest.testslegacy.datastored.DataPedido;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.From;

public class PagoPostfinance extends PagoSteps {
	
	public PagoPostfinance() {
		super();
		super.setAvaliableExecPay(true);
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		DataPedido dataPedido = dataPago.getDataPedido();
		checkoutSteps.selectDeliveryAndClickPaymentMethod();
		checkoutFlow.checkout(From.METODOSPAGO);
		String nombrePago = dataPedido.getPago().getNombre(channel, app);
		String importeTotal = dataPedido.getImporteTotal();
		if (isPageSelectChannel(5)) {
			managePageSelectChannel();
		}
		
		var pagePostfCodSegSteps = new PagePostfCodSegSteps();
		if (pagePostfCodSegSteps.getPageObj().isPasarelaTest()) {
			pagePostfCodSegSteps.validateIsPageTest(nombrePago, importeTotal);
		} else {
			pagePostfCodSegSteps.validateIsPagePro(importeTotal);
		}
		
		if (execPay) {
			dataPedido.setCodtipopago("P");
			pagePostfCodSegSteps.inputCodigoSeguridadAndAccept("11152", nombrePago);
		}
	}	
	
	private boolean isPageSelectChannel(int seconds) {
		return (new PagePostfSelectChannel()).isPage(seconds);
	}
	
	private void managePageSelectChannel() {
		var pagePostfSelectChannelSteps = new PagePostfSelectChannelSteps();
		pagePostfSelectChannelSteps.checkIsPage(2);
		pagePostfSelectChannelSteps.selectChannel(ChannelPF.CARD);
	}
}
