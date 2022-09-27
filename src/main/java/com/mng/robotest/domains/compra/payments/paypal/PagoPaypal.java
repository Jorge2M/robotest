package com.mng.robotest.domains.compra.payments.paypal;

import com.mng.robotest.domains.compra.payments.PagoSteps;
import com.mng.robotest.domains.compra.payments.paypal.pageobjects.PagePaypalConfirmacion;
import com.mng.robotest.domains.compra.payments.paypal.pageobjects.PagePaypalCreacionCuenta;
import com.mng.robotest.domains.compra.payments.paypal.pageobjects.PagePaypalLogin;
import com.mng.robotest.domains.compra.payments.paypal.pageobjects.PagePaypalSelectPago;
import com.mng.robotest.domains.compra.payments.paypal.steps.ModalPreloaderSppinerSteps;
import com.mng.robotest.domains.compra.payments.paypal.steps.PagePaypalConfirmacionSteps;
import com.mng.robotest.domains.compra.payments.paypal.steps.PagePaypalCreacionCuentaSteps;
import com.mng.robotest.domains.compra.payments.paypal.steps.PagePaypalLoginSteps;
import com.mng.robotest.domains.compra.payments.paypal.steps.PagePaypalSelectPagoSteps;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;

public class PagoPaypal extends PagoSteps {
	
	private final PagePaypalLoginSteps pagePaypalLoginSteps = new PagePaypalLoginSteps(); 
	private final ModalPreloaderSppinerSteps modalPreloaderSppinerSteps = new ModalPreloaderSppinerSteps();
	private final PagePaypalCreacionCuentaSteps pagePaypalCreacionCuentaSteps = new PagePaypalCreacionCuentaSteps();
	private final PagePaypalSelectPagoSteps pagePaypalSelectPagoSteps = new PagePaypalSelectPagoSteps();
	private final PagePaypalConfirmacionSteps pagePaypalConfirmacionSteps = new PagePaypalConfirmacionSteps();
	
	public PagoPaypal(DataPago dataPago) throws Exception {
		super(dataPago);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		modalPreloaderSppinerSteps.validateAppearsAndDisappears();
		switch (getInitPagePaypal()) {
		case Login:
			pagePaypalLoginSteps.validateIsPageUntil(0);
			break;
		case CreacionCuenta:
			pagePaypalCreacionCuentaSteps.clickButtonIniciarSesion();
			break;
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
			if (getPostLoginPagePaypal()==PostLoginPagePaypal.SelectPago) {
				pagePaypalSelectPagoSteps.validateIsPageUntil(0);
				pagePaypalSelectPagoSteps.clickContinuarButton();	  
			}
			
			seconds = 3;
			if (new PagePaypalConfirmacion().isPageUntil(seconds)) {
				pagePaypalConfirmacionSteps.validateIsPageUntil(0);
				pagePaypalConfirmacionSteps.clickContinuarButton();
			}
		}
	}
	
	private enum InitPagePaypal {Login, CreacionCuenta}
	private enum PostLoginPagePaypal {SelectPago, Confirmacion}
	
	private InitPagePaypal getInitPagePaypal() {
		if (new PagePaypalLogin().isPageUntil(5)) {
			return InitPagePaypal.Login;
		}
		
		if (new PagePaypalCreacionCuenta().isPageUntil(1)) {
			return InitPagePaypal.CreacionCuenta;
		}
		
		return InitPagePaypal.Login;
	}
	
	private PostLoginPagePaypal getPostLoginPagePaypal() {
		int seconds = 5;
		if (new PagePaypalSelectPago().isPageUntil(seconds)) {
			return PostLoginPagePaypal.SelectPago;
		}
		if (new PagePaypalConfirmacion().isPageUntil(0)) {
			return PostLoginPagePaypal.Confirmacion;
		}

		return PostLoginPagePaypal.Confirmacion;
	}
}