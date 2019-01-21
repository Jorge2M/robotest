package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;

@SuppressWarnings("javadoc")
public class PagoKlarna extends PagoStpV {
    
    public PagoKlarna(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest) {
        super(dCtxSh, dCtxPago, dFTest);
        super.isAvailableExecPay = true;
    }
    
    @SuppressWarnings("static-access")
    @Override
    public datosStep testPagoFromCheckout(boolean execPay) throws Exception {
        datosStep datosStep = PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(this.dCtxPago, this.dCtxSh, this.dFTest);
        Pago pago = this.dCtxPago.getDataPedido().getPago();
        PageCheckoutWrapperStpV.secKlarna.inputNumPersonal(pago.getNumperklarna(), this.dCtxSh.channel, this.dFTest);
        if (pago.getSearchAddklarna().compareTo("s")==0) {
            PageCheckoutWrapperStpV.secKlarna.searchAddress(pago, this.dFTest);
            datosStep = PageCheckoutWrapperStpV.secKlarna.confirmAddress(pago, this.dCtxSh.channel, this.dFTest);
        }
        
        if (execPay) {
            this.dCtxPago.getDataPedido().setCodtipopago("K");
            datosStep = PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(this.dCtxPago, this.dCtxSh.channel, this.dFTest);
        }
                
        return datosStep;
    }    
}
