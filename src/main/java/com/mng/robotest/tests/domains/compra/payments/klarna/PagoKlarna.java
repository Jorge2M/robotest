package com.mng.robotest.tests.domains.compra.payments.klarna;

import com.mng.robotest.tests.domains.compra.payments.PagoSteps;

public class PagoKlarna extends PagoSteps {
	
	public PagoKlarna() {
		super();
		super.setAvaliableExecPay(true);
	}

	@Override
	public void startPayment(boolean execPay) throws Exception {
		checkoutSteps.selectDeliveryAndClickPaymentMethod();
		//TODO ajustar
//		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
//		if (pageKlarnaSteps.getPageObject().isPage(10)) {
//			pageKlarnaSteps.checkIsPage(0);
//			pageKlarnaSteps.clickComprar();
//			if (execPay) {
//				DataKlarna dataKlarna = getDataKlarna();
//				pageKlarnaSteps.inputPersonNumberAndConfirm(dataKlarna.getPersonnumber());
//				this.dataPago.getDataPedido().setCodtipopago("K");
//			}
//		} else {
//			pageKlarnaSteps.checkModalInputPhoneNumber(15);
//			if (execPay) {
//				pageKlarnaSteps.inputDataPhoneAndConfirm(dataTest.getPais().getTelefono(), "123456");
//				this.dataPago.getDataPedido().setCodtipopago("K");
//			}
//		}
	}

//	private DataKlarna getDataKlarna() {
//		Pago pago = this.dataPago.getDataPedido().getPago();
//		
//		var dataKlarna = new DataKlarna();
//		dataKlarna.setEmail(Constantes.MAIL_PERSONAL);
//		dataKlarna.setCodPostal(dataTest.getPais().getCodpos());
//		dataKlarna.setUserName("Jorge");
//		dataKlarna.setApellidos("Muñoz Martínez");
//		dataKlarna.setDireccion(dataTest.getPais().getAddress());
//		dataKlarna.setPhone(dataTest.getPais().getTelefono());
//		dataKlarna.setPersonnumber(pago.getNumPerKlarna());
//		
//		return dataKlarna; 
//	}
}
