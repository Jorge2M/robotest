package com.mng.robotest.tests.domains.compra.payments.paypal;

import com.mng.robotest.tests.domains.compra.payments.PagoSteps;
import com.mng.robotest.tests.domains.compra.payments.paypal.pageobjects.PagePaypalConfirmacion;
import com.mng.robotest.tests.domains.compra.payments.paypal.pageobjects.PagePaypalCreacionCuenta;
import com.mng.robotest.tests.domains.compra.payments.paypal.pageobjects.PagePaypalLogin;
import com.mng.robotest.tests.domains.compra.payments.paypal.pageobjects.PagePaypalSelectPago;
import com.mng.robotest.tests.domains.compra.payments.paypal.steps.ModalPreloaderSppinerSteps;
import com.mng.robotest.tests.domains.compra.payments.paypal.steps.PagePaypalConfirmacionSteps;
import com.mng.robotest.tests.domains.compra.payments.paypal.steps.PagePaypalCreacionCuentaSteps;
import com.mng.robotest.tests.domains.compra.payments.paypal.steps.PagePaypalLoginSteps;
import com.mng.robotest.tests.domains.compra.payments.paypal.steps.PagePaypalSelectPagoSteps;
import com.mng.robotest.testslegacy.datastored.DataPago;
import com.mng.robotest.testslegacy.datastored.DataPedido;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.From;

public class PagoPaypal extends PagoSteps {
	
	private final PagePaypalLoginSteps pagePaypalLoginSteps = new PagePaypalLoginSteps(); 
	private final ModalPreloaderSppinerSteps modalPreloaderSppinerSteps = new ModalPreloaderSppinerSteps();
	private final PagePaypalCreacionCuentaSteps pagePaypalCreacionCuentaSteps = new PagePaypalCreacionCuentaSteps();
	private final PagePaypalSelectPagoSteps pagePaypalSelectPagoSteps = new PagePaypalSelectPagoSteps();
	private final PagePaypalConfirmacionSteps pagePaypalConfirmacionSteps = new PagePaypalConfirmacionSteps();
	
	public PagoPaypal(DataPago dataPago) {
		super(dataPago);
		super.setAvaliableExecPay(true);
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		checkoutSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		modalPreloaderSppinerSteps.validateAppearsAndDisappears();
		if (getInitPagePaypal() == InitPagePaypal.LOGIN) {
			pagePaypalLoginSteps.validateisPage(0);
		}
		else {
			pagePaypalCreacionCuentaSteps.clickButtonIniciarSesion();
		}
		
		if (execPay) {
			int seconds = 10;
			DataPedido dataPedido = this.dataPago.getDataPedido();
			dataPedido.setCodtipopago("P");
			modalPreloaderSppinerSteps.validateIsVanished(seconds);
			if (new PagePaypalLogin().isPage()) {
				pagePaypalLoginSteps.loginPaypal(dataPedido.getPago().getUseremail(), dataPedido.getPago().getPasswordemail());
			}
			
			modalPreloaderSppinerSteps.validateIsVanished(seconds);
			if (getPostLoginPagePaypal()==PostLoginPagePaypal.SELECT_PAGO) {
				pagePaypalSelectPagoSteps.checkIsPage(0);
				pagePaypalSelectPagoSteps.clickContinuarButton();	  
			}
			
			seconds = 3;
			if (new PagePaypalConfirmacion().isPage(seconds)) {
				pagePaypalConfirmacionSteps.validateisPage(0);
				pagePaypalConfirmacionSteps.clickContinuarButton();
			}
		}
	}
	
	private enum InitPagePaypal { LOGIN, CREACION_CUENTA }
	private enum PostLoginPagePaypal { SELECT_PAGO, CONFIRMACION }
	
	private InitPagePaypal getInitPagePaypal() {
		if (new PagePaypalLogin().isPage(5)) {
			return InitPagePaypal.LOGIN;
		}
		
		if (new PagePaypalCreacionCuenta().isPage(1)) {
			return InitPagePaypal.CREACION_CUENTA;
		}
		
		return InitPagePaypal.LOGIN;
	}
	
	private PostLoginPagePaypal getPostLoginPagePaypal() {
		int seconds = 5;
		if (new PagePaypalSelectPago().isPage(seconds)) {
			return PostLoginPagePaypal.SELECT_PAGO;
		}
		if (new PagePaypalConfirmacion().isPage(0)) {
			return PostLoginPagePaypal.CONFIRMACION;
		}

		return PostLoginPagePaypal.CONFIRMACION;
	}
}
