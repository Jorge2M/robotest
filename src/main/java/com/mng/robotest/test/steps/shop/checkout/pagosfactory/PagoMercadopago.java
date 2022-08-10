package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.pageobject.shop.checkout.mercadopago.PageMercpagoDatosTrj.TypePant;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.mercadopago.PageMercpago1rstSteps;
import com.mng.robotest.test.steps.shop.checkout.mercadopago.PageMercpagoConfSteps;
import com.mng.robotest.test.steps.shop.checkout.mercadopago.PageMercpagoDatosTrjSteps;
import com.mng.robotest.test.steps.shop.checkout.mercadopago.PageMercpagoLoginSteps;


public class PagoMercadopago extends PagoSteps {

	private static final String codigoSeguridad = "123";
	
	public PagoMercadopago(DataCtxShop dCtxSh, DataCtxPago dCtxPago) throws Exception {
		super(dCtxSh, dCtxPago);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		DataPedido dataPedido = this.dCtxPago.getDataPedido();
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
		dCtxPago = checkoutFlow.checkout(From.METODOSPAGO);
		int maxSeconds = 5;
		PageMercpago1rstSteps.validateIsPageUntil(maxSeconds, driver);
		PageMercpago1rstSteps.clickLinkRegistration(driver);
		if (execPay) {
			PageMercpagoLoginSteps.loginMercadopago(dataPedido.getPago(), dCtxSh.channel, driver);
			PageMercpagoDatosTrjSteps pageMercpagoDatosTrjSteps = PageMercpagoDatosTrjSteps.newInstance(dCtxSh.channel, driver);
			if (pageMercpagoDatosTrjSteps.getPageObject().getTypeInput()==TypePant.inputDataTrjNew) {
				fluxFromInputDataTrj(dataPedido, pageMercpagoDatosTrjSteps);
			}
			else {
				pageMercpagoDatosTrjSteps.inputCvcAndPay(codigoSeguridad);
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
		inputData.setCodigoSeguridad(codigoSeguridad);
		pageMercpagoDatosTrjSteps.inputDataAndPay(inputData);
		PageMercpagoConfSteps.clickPagar(dCtxSh.channel, driver);
	}
}
