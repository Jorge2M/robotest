package com.mng.robotest.domains.compra.payments.tmango;

import com.mng.robotest.domains.compra.payments.PagoSteps;
import com.mng.robotest.domains.compra.payments.tmango.pageobjects.SecTMango;
import com.mng.robotest.domains.compra.payments.tmango.steps.PageAmexInputTarjetaSteps;
import com.mng.robotest.domains.compra.payments.tmango.steps.PageAmexResultSteps;
import com.mng.robotest.domains.compra.payments.tmango.steps.PageRedsysSimSteps;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;

public class PagoTMango extends PagoSteps {

	public PagoTMango(DataPago dataPago) {
		super(dataPago);
		super.setAvaliableExecPay(true);
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		DataPedido dataPedido = this.dataPago.getDataPedido();
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago);
		pageCheckoutWrapperSteps.getSecTMangoSteps().validateIsSectionOk();
		pageCheckoutWrapperSteps.getSecTMangoSteps().clickTipoPago(SecTMango.TipoPago.PAGO_HABITUAL);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		
		PageAmexInputTarjetaSteps pageAmexInputTarjetaSteps = new PageAmexInputTarjetaSteps();
		pageAmexInputTarjetaSteps.validateIsPageOk(dataPedido.getImporteTotal());
		
		if (execPay) {
			dataPedido.setCodtipopago("M");
			PageRedsysSimSteps pageRedsysSimSteps = 
				pageAmexInputTarjetaSteps.inputTarjetaAndPayButton(
					dataPedido.getPago().getNumtarj(), 
					dataPedido.getPago().getMescad(), 
					dataPedido.getPago().getAnycad(), 
					dataPedido.getPago().getCvc(), 
					dataPedido.getImporteTotal());
			
			pageRedsysSimSteps.clickEnviar(dataPedido.getPago().getCip(), dataPedido.getImporteTotal());
			new PageAmexResultSteps().clickContinuarButton();
		}
	}
}
