package com.mng.robotest.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.data.Constantes;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.pageobject.shop.checkout.klarna.DataKlarna;
import com.mng.robotest.test.stpv.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.stpv.shop.checkout.klarna.PageKlarnaStpV;


public class PagoKlarna extends PagoStpV {
	
	private final PageKlarnaStpV pageKlarnaStpV;

	public PagoKlarna(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) throws Exception {
		super(dCtxSh, dCtxPago, driver);
		super.isAvailableExecPay = true;
		pageKlarnaStpV = new PageKlarnaStpV(driver);
	}

	@SuppressWarnings("static-access")
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
		dCtxPago = checkoutFlow.checkout(From.MetodosPago);
		pageKlarnaStpV.checkIsPage(5);
		pageKlarnaStpV.clickComprar();
		pageKlarnaStpV.checkIsModalInputUserData(5);
		
		if (execPay) {
			DataKlarna dataKlarna = getDataKlarna();
			pageKlarnaStpV.inputUserDataAndConfirm(dataKlarna);
			if (pageKlarnaStpV.checkModalInputPersonNumber(2)) {
				pageKlarnaStpV.inputPersonNumberAndConfirm(dataKlarna.getPersonnumber());
			}
			this.dCtxPago.getDataPedido().setCodtipopago("K");
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
