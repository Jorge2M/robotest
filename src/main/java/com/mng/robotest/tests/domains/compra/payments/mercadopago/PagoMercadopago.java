package com.mng.robotest.tests.domains.compra.payments.mercadopago;

import com.mng.robotest.tests.domains.compra.payments.PagoSteps;
import com.mng.robotest.tests.domains.compra.payments.mercadopago.pageobjects.PageMercpagoDatosTrj.TypePant;
import com.mng.robotest.tests.domains.compra.payments.mercadopago.steps.PageMercpago1rstSteps;
import com.mng.robotest.tests.domains.compra.payments.mercadopago.steps.PageMercpagoConfSteps;
import com.mng.robotest.tests.domains.compra.payments.mercadopago.steps.PageMercpagoDatosTrjSteps;
import com.mng.robotest.tests.domains.compra.payments.mercadopago.steps.PageMercpagoLoginSteps;
import com.mng.robotest.testslegacy.datastored.DataPago;
import com.mng.robotest.testslegacy.datastored.DataPedido;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.From;

public class PagoMercadopago extends PagoSteps {

	private static final String CODIGO_SEGURIDAD = "123";
	
	public PagoMercadopago(DataPago dataPago) {
		super(dataPago);
		super.setAvaliableExecPay(true);
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		var dataPedido = this.dataPago.getDataPedido();
		checkoutSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		
		var pageMercpago1rstSteps = new PageMercpago1rstSteps();
		pageMercpago1rstSteps.validateisPage(5);
		pageMercpago1rstSteps.clickLinkRegistration();
		if (execPay) {
			new PageMercpagoLoginSteps().loginMercadopago(dataPedido.getPago());
			var pageMercpagoDatosTrjSteps = new PageMercpagoDatosTrjSteps();
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
		var inputData = new PageMercpagoDatosTrjSteps.InputData();
		inputData.setMesVencimiento(dataPedido.getPago().getMescad());
		inputData.setAnyVencimiento(dataPedido.getPago().getAnycad());
		inputData.setCodigoSeguridad(CODIGO_SEGURIDAD);
		pageMercpagoDatosTrjSteps.inputDataAndPay(inputData);
		new PageMercpagoConfSteps().clickPagar();
	}
}
