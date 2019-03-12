package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;

public class PagoKlarna extends PagoStpV {
    
    public PagoKlarna(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) {
        super(dCtxSh, dCtxPago, driver);
        super.isAvailableExecPay = true;
    }
    
    @SuppressWarnings("static-access")
    @Override
    public void testPagoFromCheckout(boolean execPay) throws Exception {
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh, driver);
        Pago pago = this.dCtxPago.getDataPedido().getPago();
        PageCheckoutWrapperStpV.secKlarna.inputNumPersonal(pago.getNumperklarna(), dCtxSh.channel, driver);
        if (pago.getSearchAddklarna().compareTo("s")==0) {
            PageCheckoutWrapperStpV.secKlarna.searchAddress(pago, driver);
            PageCheckoutWrapperStpV.secKlarna.confirmAddress(pago, dCtxSh.channel, driver);
        }
        
        if (execPay) {
            this.dCtxPago.getDataPedido().setCodtipopago("K");
            PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(dCtxPago, dCtxSh.channel, driver);
        }
    }    
}
