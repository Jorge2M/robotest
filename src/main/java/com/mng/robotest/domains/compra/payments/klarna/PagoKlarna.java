package com.mng.robotest.domains.compra.payments.klarna;

import com.mng.robotest.domains.compra.payments.PagoSteps;
import com.mng.robotest.domains.compra.payments.klarna.pageobjects.DataKlarna;
import com.mng.robotest.domains.compra.payments.klarna.steps.PageKlarnaSteps;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.data.Constantes;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;

public class PagoKlarna extends PagoSteps {
	
	private final PageKlarnaSteps pageKlarnaSteps = new PageKlarnaSteps();

	public PagoKlarna(DataPago dataPago) throws Exception {
		super(dataPago);
		super.isAvailableExecPay = true;
	}

	@Override
	public void startPayment(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago);
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
				pageKlarnaSteps.inputDataPhoneAndConfirm(dataTest.getPais().getTelefono(), "123456");
				this.dataPago.getDataPedido().setCodtipopago("K");
			}
		}
	}

	private DataKlarna getDataKlarna() {
		Pago pago = this.dataPago.getDataPedido().getPago();
		
		DataKlarna dataKlarna = new DataKlarna();
		dataKlarna.setEmail(Constantes.MAIL_PERSONAL);
		dataKlarna.setCodPostal(dataTest.getPais().getCodpos());
		dataKlarna.setUserName("Jorge");
		dataKlarna.setApellidos("Muñoz Martínez");
		dataKlarna.setDireccion(dataTest.getPais().getAddress());
		dataKlarna.setPhone(dataTest.getPais().getTelefono());
		dataKlarna.setPersonnumber(pago.getNumPerKlarna());
		
		return dataKlarna; 
	}
}
