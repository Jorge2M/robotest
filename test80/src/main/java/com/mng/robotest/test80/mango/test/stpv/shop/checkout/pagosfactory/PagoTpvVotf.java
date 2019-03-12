package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;

public class PagoTpvVotf extends PagoStpV {
    
    public PagoTpvVotf(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) {
        super(dCtxSh, dCtxPago, driver);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public void testPagoFromCheckout(boolean execPay) throws Exception {
        String nombrePago = this.dCtxPago.getDataPedido().getPago().getNombre(this.dCtxSh.channel);
        PageCheckoutWrapperStpV.noClickIconoVotf(nombrePago);
        if (execPay) {
            PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(this.dCtxPago, dCtxSh.channel, driver);
        }
    }    
}
