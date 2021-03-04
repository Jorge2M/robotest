package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.amazon.PageAmazonIdentStpV;

public class PagoAmazon extends PagoStpV {

    public PagoAmazon(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) {
        super(dCtxSh, dCtxPago, driver);
        super.isAvailableExecPay = false;
    }
    
    @Override
    public void testPagoFromCheckout(boolean execPay) throws Exception {
        pageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
        pagoNavigationsStpV.aceptarCompraDesdeMetodosPago();
        PageAmazonIdentStpV.validateIsPage(dCtxSh.pais, dCtxSh.channel, dCtxPago.getDataPedido(), driver);
        
        if (execPay) {
            throw new PaymethodWithoutTestPayImplemented(MsgNoPayImplemented);
        }
    }
    
}
