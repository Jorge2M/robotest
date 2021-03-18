package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;

public class PagoKlarnaDeutsch extends PagoStpV {
    
    public PagoKlarnaDeutsch(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) {
        super(dCtxSh, dCtxPago, driver);
        super.isAvailableExecPay = true;
    }
    
    @SuppressWarnings("static-access")
    @Override
    public void testPagoFromCheckout(boolean execPay) throws Exception {
        pageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
        pageCheckoutWrapperStpV.getSecKlarnaDeutschStpV().validateIsSection(1);
        pageCheckoutWrapperStpV.getSecKlarnaDeutschStpV().inputData("23-04-1974");
        if (execPay) {
            this.dCtxPago.getDataPedido().setCodtipopago("?");
            pagoNavigationsStpV.aceptarCompraDesdeMetodosPago();
        }
    }    
}
