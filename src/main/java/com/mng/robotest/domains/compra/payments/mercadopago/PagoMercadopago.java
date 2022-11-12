package com.mng.robotest.domains.compra.payments.mercadopago;

import com.mng.robotest.domains.compra.payments.PagoSteps;
import com.mng.robotest.domains.compra.payments.mercadopago.pageobjects.PageMercpagoDatosTrj.TypePant;
import com.mng.robotest.domains.compra.payments.mercadopago.steps.PageMercpago1rstSteps;
import com.mng.robotest.domains.compra.payments.mercadopago.steps.PageMercpagoConfSteps;
import com.mng.robotest.domains.compra.payments.mercadopago.steps.PageMercpagoDatosTrjSteps;
import com.mng.robotest.domains.compra.payments.mercadopago.steps.PageMercpagoLoginSteps;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;

public class PagoMercadopago extends PagoSteps {

	private static final String CODIGO_SEGURIDAD = "123";
	
	public PagoMercadopago(DataPago dataPago) {
		super(dataPago);
		super.setAvaliableExecPay(true);
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		DataPedido dataPedido = this.dataPago.getDataPedido();
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		
		PageMercpago1rstSteps pageMercpago1rstSteps = new PageMercpago1rstSteps();
		pageMercpago1rstSteps.validateIsPageUntil(5);
		pageMercpago1rstSteps.clickLinkRegistration();
		if (execPay) {
			new PageMercpagoLoginSteps().loginMercadopago(dataPedido.getPago());
			PageMercpagoDatosTrjSteps pageMercpagoDatosTrjSteps = new PageMercpagoDatosTrjSteps();
			if (pageMercpagoDatosTrjSteps.getPageObject().getTypeInput()==TypePant.INPUT_DATA_TRJ_NEW) {
				fluxFromInputDataTrj(dataPedido, pageMercpagoDatosTrjSteps);
			}
			else {
				pageMercpagoDatosTrjSteps.inputCvcAndPay(CODIGO_SEGURIDAD);
			}
			
			dataPedido.setCodtipopago("D");
		}
	}
	
	private void fluxFromInputDataTrj(
			DataPedido dataPedido, PageMercpagoDatosTrjSteps pageMercpagoDatosTrjSteps) {
		pageMercpagoDatosTrjSteps.inputNumTarjeta(dataPedido.getPago().getNumtarj());
		PageMercpagoDatosTrjSteps.InputData inputData = new PageMercpagoDatosTrjSteps.InputData();
		inputData.setMesVencimiento(dataPedido.getPago().getMescad());
		inputData.setAnyVencimiento(dataPedido.getPago().getAnycad());
		inputData.setCodigoSeguridad(CODIGO_SEGURIDAD);
		pageMercpagoDatosTrjSteps.inputDataAndPay(inputData);
		new PageMercpagoConfSteps().clickPagar();
	}
}
