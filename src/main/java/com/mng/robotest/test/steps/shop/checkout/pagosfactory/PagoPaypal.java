package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
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
	
	public PagoPaypal(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) throws Exception {
		super(dCtxSh, dCtxPago, driver);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
		dCtxPago = checkoutFlow.checkout(From.METODOSPAGO);
		int maxSeconds = 10;
		ModalPreloaderSppinerSteps.validateAppearsAndDisappears(driver);
		switch (getInitPagePaypal(driver)) {
		case Login:
			PagePaypalLoginSteps.validateIsPageUntil(0, driver);
			break;
		case CreacionCuenta:
			PagePaypalCreacionCuentaSteps.clickButtonIniciarSesion(driver);
			break;
		}
		
		if (execPay) {
			DataPedido dataPedido = this.dCtxPago.getDataPedido();
			dataPedido.setCodtipopago("P");
			ModalPreloaderSppinerSteps.validateIsVanished(maxSeconds, driver);
			if (PagePaypalLogin.isPageUntil(0, driver)) {
				PagePaypalLoginSteps.loginPaypal(dataPedido.getPago().getUseremail(), dataPedido.getPago().getPasswordemail(), driver);
			}
			
			ModalPreloaderSppinerSteps.validateIsVanished(maxSeconds, driver);
			if (getPostLoginPagePaypal()==PostLoginPagePaypal.SelectPago) {
				PagePaypalSelectPagoSteps.validateIsPageUntil(0, driver);
				PagePaypalSelectPagoSteps.clickContinuarButton(driver);	  
			}
			
			maxSeconds = 3;
			if (PagePaypalConfirmacion.isPageUntil(maxSeconds, driver)) {
				PagePaypalConfirmacionSteps.validateIsPageUntil(0, driver);
				PagePaypalConfirmacionSteps.clickContinuarButton(driver);
			}
		}
	}
	
	private enum InitPagePaypal {Login, CreacionCuenta}
	private enum PostLoginPagePaypal {SelectPago, Confirmacion}
	
	private InitPagePaypal getInitPagePaypal(WebDriver driver) {
		int maxSeconds = 5;
		if (PagePaypalLogin.isPageUntil(maxSeconds, driver)) {
			return InitPagePaypal.Login;
		}
		
		if (PagePaypalCreacionCuenta.isPageUntil(1, driver)) {
			return InitPagePaypal.CreacionCuenta;
		}
		
		return InitPagePaypal.Login;
	}
	
	private PostLoginPagePaypal getPostLoginPagePaypal() {
		int maxSeconds = 5;
		if (PagePaypalSelectPago.isPageUntil(maxSeconds, driver)) {
			return PostLoginPagePaypal.SelectPago;
		}
		if (PagePaypalConfirmacion.isPageUntil(0, driver)) {
			return PostLoginPagePaypal.Confirmacion;
		}

		return PostLoginPagePaypal.Confirmacion;
	}
}
