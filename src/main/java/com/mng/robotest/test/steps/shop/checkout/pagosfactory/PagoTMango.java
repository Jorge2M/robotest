package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.pageobject.shop.checkout.tmango.SecTMango;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.tmango.PageAmexInputTarjetaSteps;
import com.mng.robotest.test.steps.shop.checkout.tmango.PageAmexResultSteps;
import com.mng.robotest.test.steps.shop.checkout.tmango.PageRedsysSimSteps;

public class PagoTMango extends PagoSteps {

	public PagoTMango(DataPago dataPago) throws Exception {
		super(dataPago);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		DataPedido dataPedido = this.dataPago.getDataPedido();
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago, dataTest.pais);
		pageCheckoutWrapperSteps.getSecTMangoSteps().validateIsSectionOk();
		pageCheckoutWrapperSteps.getSecTMangoSteps().clickTipoPago(SecTMango.TipoPago.PAGO_HABITUAL);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		
		PageAmexInputTarjetaSteps pageAmexInputTarjetaSteps = new PageAmexInputTarjetaSteps();
		pageAmexInputTarjetaSteps.validateIsPageOk(dataPedido.getImporteTotal(), dataTest.pais.getCodigo_pais());
		
		if (execPay) {
			dataPedido.setCodtipopago("M");
			PageRedsysSimSteps pageRedsysSimSteps = 
				pageAmexInputTarjetaSteps.inputTarjetaAndPayButton(
					dataPedido.getPago().getNumtarj(), 
					dataPedido.getPago().getMescad(), 
					dataPedido.getPago().getAnycad(), 
					dataPedido.getPago().getCvc(), 
					dataPedido.getImporteTotal(), 
					dataTest.pais.getCodigo_pais());
			
			pageRedsysSimSteps.clickEnviar(dataPedido.getPago().getCip(), dataPedido.getImporteTotal(), dataTest.pais.getCodigo_pais());
			new PageAmexResultSteps(driver).clickContinuarButton();
		}
	}
}
