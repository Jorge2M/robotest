package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.paymaya.PageIdentPaymayaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.paymaya.PageInitPaymayaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.paymaya.PageOtpPaymayaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.paymaya.PageResultPaymayaStpV;

public class PagoPayMaya extends PagoStpV {
	
    public PagoPayMaya(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) {
        super(dCtxSh, dCtxPago, driver);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public void testPagoFromCheckout(boolean execPay) throws Exception {
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh, driver);
        PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(this.dCtxPago, dCtxSh.channel, driver);
        PageInitPaymayaStpV pageInitPaymayaStpV = new PageInitPaymayaStpV(driver);
        pageInitPaymayaStpV.checkPage();
        PageIdentPaymayaStpV pageIdentPaymayaStpV =  
        	pageInitPaymayaStpV.clickPaymayaButton();
        if (execPay) {
            Pago pago = dCtxPago.getDataPedido().getPago();
        	PageOtpPaymayaStpV pageOtpPaymayaStpV = 
        		pageIdentPaymayaStpV.login(pago.getUsrpaymaya(), pago.getPasswordpaymaya());
        	PageResultPaymayaStpV pageResultPaymanaStpV = 
        		pageOtpPaymayaStpV.proceed(pago.getOtpdpaymaya());
        	pageResultPaymanaStpV.confirmPayment();
        	
            dCtxPago.getDataPedido().setCodtipopago("F");
        }
    }    
}
