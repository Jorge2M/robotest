package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paypal.PagePaypalConfirmacion;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paypal.PagePaypalCreacionCuenta;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paypal.PagePaypalLogin;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paypal.PagePaypalSelectPago;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.paypal.ModalPreloaderSppinerStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.paypal.PagePaypalConfirmacionStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.paypal.PagePaypalCreacionCuentaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.paypal.PagePaypalLoginStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.paypal.PagePaypalSelectPagoStpV;

public class PagoPaypal extends PagoStpV {
	
    public PagoPaypal(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) throws Exception {
        super(dCtxSh, dCtxPago, driver);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public void testPagoFromCheckout(boolean execPay) throws Exception {
        pageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
        dCtxPago = checkoutFlow.checkout(From.MetodosPago);
        int maxSeconds = 10;
        ModalPreloaderSppinerStpV.validateAppearsAndDisappears(driver);
        switch (getInitPagePaypal(driver)) {
        case Login:
            PagePaypalLoginStpV.validateIsPageUntil(0, driver);
            break;
        case CreacionCuenta:
        	PagePaypalCreacionCuentaStpV.clickButtonIniciarSesion(driver);
        	break;
        }
        
        if (execPay) {
            DataPedido dataPedido = this.dCtxPago.getDataPedido();
            dataPedido.setCodtipopago("P");
        	ModalPreloaderSppinerStpV.validateIsVanished(maxSeconds, driver);
            if (PagePaypalLogin.isPageUntil(0, driver)) {
                PagePaypalLoginStpV.loginPaypal(dataPedido.getPago().getUseremail(), dataPedido.getPago().getPasswordemail(), driver);
            }
            
            ModalPreloaderSppinerStpV.validateIsVanished(maxSeconds, driver);
            if (getPostLoginPagePaypal()==PostLoginPagePaypal.SelectPago) {
            	PagePaypalSelectPagoStpV.validateIsPageUntil(0, driver);
            	PagePaypalSelectPagoStpV.clickContinuarButton(driver);      
            }
            
            maxSeconds = 3;
            if (PagePaypalConfirmacion.isPageUntil(maxSeconds, driver)) {
	            PagePaypalConfirmacionStpV.validateIsPageUntil(0, driver);
	            PagePaypalConfirmacionStpV.clickContinuarButton(driver);
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
