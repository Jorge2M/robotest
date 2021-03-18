package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;


public class PagoKlarna extends PagoStpV {
    
    public PagoKlarna(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) {
        super(dCtxSh, dCtxPago, driver);
        super.isAvailableExecPay = true;
    }
    
    @SuppressWarnings("static-access")
    @Override
    public void testPagoFromCheckout(boolean execPay) throws Exception {
        pageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
        Pago pago = this.dCtxPago.getDataPedido().getPago();
        pageCheckoutWrapperStpV.getSecKlarnaStpV().inputNumPersonal(pago.getNumperklarna());
        if (pago.getSearchAddklarna().compareTo("s")==0) {
            pageCheckoutWrapperStpV.getSecKlarnaStpV().searchAddress(pago);
            pageCheckoutWrapperStpV.getSecKlarnaStpV().confirmAddress(pago);
        }
        
        if (execPay) {
            this.dCtxPago.getDataPedido().setCodtipopago("K");
            pagoNavigationsStpV.aceptarCompraDesdeMetodosPago();
        }
    }    
}
