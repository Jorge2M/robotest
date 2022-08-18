package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.data.Constantes;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.pageobject.shop.checkout.klarna.DataKlarna;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.klarna.PageKlarnaSteps;


public class PagoKlarna extends PagoSteps {
	
	private final PageKlarnaSteps pageKlarnaSteps;

	public PagoKlarna(DataCtxShop dCtxSh, DataCtxPago dCtxPago) throws Exception {
		super(dCtxSh, dCtxPago);
		super.isAvailableExecPay = true;
		pageKlarnaSteps = new PageKlarnaSteps(driver);
	}

	@SuppressWarnings("static-access")
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh.pais);
		dCtxPago = checkoutFlow.checkout(From.METODOSPAGO);
		
		if (pageKlarnaSteps.getPageObject().isPage(10)) {
			pageKlarnaSteps.checkIsPage(0);
			pageKlarnaSteps.clickComprar();
			if (execPay) {
				DataKlarna dataKlarna = getDataKlarna();
				pageKlarnaSteps.inputPersonNumberAndConfirm(dataKlarna.getPersonnumber());
				this.dCtxPago.getDataPedido().setCodtipopago("K");
			}
		} else {
			pageKlarnaSteps.checkModalInputPhoneNumber(15);
			if (execPay) {
				pageKlarnaSteps.inputDataPhoneAndConfirm(dCtxSh.pais.getTelefono(), "123456");
				this.dCtxPago.getDataPedido().setCodtipopago("K");
			}
		}
	}

	private DataKlarna getDataKlarna() {
		Pago pago = this.dCtxPago.getDataPedido().getPago();
		
		DataKlarna dataKlarna = new DataKlarna();
		dataKlarna.setEmail(Constantes.MAIL_PERSONAL);
		dataKlarna.setCodPostal(dCtxSh.pais.getCodpos());
		dataKlarna.setUserName("Jorge");
		dataKlarna.setApellidos("Muñoz Martínez");
		dataKlarna.setDireccion(dCtxSh.pais.getAddress());
		dataKlarna.setPhone(dCtxSh.pais.getTelefono());
		dataKlarna.setPersonnumber(pago.getNumPerKlarna());
		
		return dataKlarna; 
	}
}
