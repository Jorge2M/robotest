package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.pageobject.shop.checkout.paypal.PagePaypalConfirmacion;
import com.mng.robotest.test.pageobject.shop.checkout.paypal.PagePaypalCreacionCuenta;
import com.mng.robotest.test.pageobject.shop.checkout.paypal.PagePaypalLogin;
import com.mng.robotest.test.pageobject.shop.checkout.paypal.PagePaypalSelectPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.paypal.ModalPreloaderSppinerSteps;
import com.mng.robotest.test.steps.shop.checkout.paypal.PagePaypalConfirmacionSteps;
import com.mng.robotest.test.steps.shop.checkout.paypal.PagePaypalCreacionCuentaSteps;
import com.mng.robotest.test.steps.shop.checkout.paypal.PagePaypalLoginSteps;
import com.mng.robotest.test.steps.shop.checkout.paypal.PagePaypalSelectPagoSteps;

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
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago, dataTest.pais);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		int seconds = 10;
		modalPreloaderSppinerSteps.validateAppearsAndDisappears();
		switch (getInitPagePaypal(driver)) {
		case Login:
			pagePaypalLoginSteps.validateIsPageUntil(0);
			break;
		case CreacionCuenta:
			pagePaypalCreacionCuentaSteps.clickButtonIniciarSesion();
			break;
		}
		
		if (execPay) {
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
	
	private InitPagePaypal getInitPagePaypal(WebDriver driver) {
		int seconds = 5;
		if (new PagePaypalLogin().isPageUntil(seconds)) {
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
