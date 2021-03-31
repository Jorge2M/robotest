package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;


public class PagoTpvVotf extends PagoStpV {
    
    public PagoTpvVotf(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) {
        super(dCtxSh, dCtxPago, driver);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public void testPagoFromCheckout(boolean execPay) throws Exception {
        String nombrePago = this.dCtxPago.getDataPedido().getPago().getNombre(dCtxSh.channel, dCtxSh.appE);
        pageCheckoutWrapperStpV.noClickIconoVotf(nombrePago);
        if (execPay) {
            pagoNavigationsStpV.aceptarCompraDesdeMetodosPago();
        }
    }    
}
