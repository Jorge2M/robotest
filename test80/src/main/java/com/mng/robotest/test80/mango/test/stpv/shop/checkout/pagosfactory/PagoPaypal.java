package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paypal.PagePaypalConfirmacion;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paypal.PagePaypalCreacionCuenta;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paypal.PagePaypalLogin;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paypal.PagePaypalSelectPago;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.paypal.ModalPreloaderSppinerStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.paypal.PagePaypalConfirmacionStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.paypal.PagePaypalCreacionCuentaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.paypal.PagePaypalLoginStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.paypal.PagePaypalSelectPagoStpV;


public class PagoPaypal extends PagoStpV {
	
    public PagoPaypal(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest) {
        super(dCtxSh, dCtxPago, dFTest);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public DatosStep testPagoFromCheckout(boolean execPay) throws Exception {
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(this.dCtxPago, this.dCtxSh, this.dFTest);
        DatosStep datosStep = PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(this.dCtxPago, this.dCtxSh.channel, this.dFTest);
        int maxSecondsWait = 10;
        ModalPreloaderSppinerStpV.validateAppearsAndDisappears(datosStep, dFTest);
        switch (getInitPagePaypal(dFTest.driver)) {
        case Login:
            PagePaypalLoginStpV.validateIsPageUntil(0, datosStep, this.dFTest);
            break;
        case CreacionCuenta:
        	datosStep = PagePaypalCreacionCuentaStpV.clickButtonIniciarSesion(this.dFTest);
        	break;
        }
        
        if (execPay) {
            DataPedido dataPedido = this.dCtxPago.getDataPedido();
            dataPedido.setCodtipopago("P");
        	ModalPreloaderSppinerStpV.validateIsVanished(maxSecondsWait, datosStep, dFTest);
            if (PagePaypalLogin.isPageUntil(0, this.dFTest.driver)) {
                PagePaypalLoginStpV.loginPaypal(dataPedido.getPago().getUseremail(), dataPedido.getPago().getPasswordemail(), this.dFTest);
            }
            
            ModalPreloaderSppinerStpV.validateIsVanished(maxSecondsWait, datosStep, dFTest);
            if (getPostLoginPagePaypal()==PostLoginPagePaypal.SelectPago) {
            	PagePaypalSelectPagoStpV.validateIsPageUntil(0, datosStep, dFTest);
            	datosStep = PagePaypalSelectPagoStpV.clickContinuarButton(this.dFTest);      
            }
            
            maxSecondsWait = 3;
            if (PagePaypalConfirmacion.isPageUntil(maxSecondsWait, dFTest.driver)) {
	            PagePaypalConfirmacionStpV.validateIsPageUntil(0, datosStep, dFTest);
	            datosStep = PagePaypalConfirmacionStpV.clickContinuarButton(dFTest);
            }
        }
        
        return datosStep;
    }
    
    private enum InitPagePaypal {Login, CreacionCuenta}
    private enum PostLoginPagePaypal {SelectPago, Confirmacion}
    
    private InitPagePaypal getInitPagePaypal(WebDriver driver) {
    	int maxSecondsWait = 5;
        if (PagePaypalLogin.isPageUntil(maxSecondsWait, driver)) {
        	return InitPagePaypal.Login;
        }
        
        if (PagePaypalCreacionCuenta.isPageUntil(1, driver)) {
        	return InitPagePaypal.CreacionCuenta;
        }
        
        return InitPagePaypal.Login;
    }
    
    private PostLoginPagePaypal getPostLoginPagePaypal() {
    	int maxSecondsWait = 5;
    	if (PagePaypalSelectPago.isPageUntil(maxSecondsWait, dFTest.driver)) {
    		return PostLoginPagePaypal.SelectPago;
    	}
    	if (PagePaypalConfirmacion.isPageUntil(0, dFTest.driver)) {
    		return PostLoginPagePaypal.Confirmacion;
    	}

    	return PostLoginPagePaypal.Confirmacion;
    }
}
