package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.data.Constantes;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.pageobject.shop.checkout.klarna.DataKlarna;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.klarna.PageKlarnaSteps;

public class PagoKlarna extends PagoSteps {
	
	private final PageKlarnaSteps pageKlarnaSteps;

	public PagoKlarna(DataPago dataPago) throws Exception {
		super(dataPago);
		super.isAvailableExecPay = true;
		pageKlarnaSteps = new PageKlarnaSteps(driver);
	}

	@SuppressWarnings("static-access")
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago, dataTest.pais);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		
		if (pageKlarnaSteps.getPageObject().isPage(10)) {
			pageKlarnaSteps.checkIsPage(0);
			pageKlarnaSteps.clickComprar();
			if (execPay) {
				DataKlarna dataKlarna = getDataKlarna();
				pageKlarnaSteps.inputPersonNumberAndConfirm(dataKlarna.getPersonnumber());
				this.dataPago.getDataPedido().setCodtipopago("K");
			}
		} else {
			pageKlarnaSteps.checkModalInputPhoneNumber(15);
			if (execPay) {
				pageKlarnaSteps.inputDataPhoneAndConfirm(dataTest.pais.getTelefono(), "123456");
				this.dataPago.getDataPedido().setCodtipopago("K");
			}
		}
	}

	private DataKlarna getDataKlarna() {
		Pago pago = this.dataPago.getDataPedido().getPago();
		
		DataKlarna dataKlarna = new DataKlarna();
		dataKlarna.setEmail(Constantes.MAIL_PERSONAL);
		dataKlarna.setCodPostal(dataTest.pais.getCodpos());
		dataKlarna.setUserName("Jorge");
		dataKlarna.setApellidos("Muñoz Martínez");
		dataKlarna.setDireccion(dataTest.pais.getAddress());
		dataKlarna.setPhone(dataTest.pais.getTelefono());
		dataKlarna.setPersonnumber(pago.getNumPerKlarna());
		
		return dataKlarna; 
	}
}
