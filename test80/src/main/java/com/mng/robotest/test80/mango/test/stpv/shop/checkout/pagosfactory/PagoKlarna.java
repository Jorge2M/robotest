package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;


public class PagoKlarna extends PagoStpV {
    
    public PagoKlarna(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest) {
        super(dCtxSh, dCtxPago, dFTest);
        super.isAvailableExecPay = true;
    }
    
    @SuppressWarnings("static-access")
    @Override
    public DatosStep testPagoFromCheckout(boolean execPay) throws Exception {
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh, dFTest);
        Pago pago = this.dCtxPago.getDataPedido().getPago();
        PageCheckoutWrapperStpV.secKlarna.inputNumPersonal(pago.getNumperklarna(), dCtxSh.channel, dFTest);
        if (pago.getSearchAddklarna().compareTo("s")==0) {
            PageCheckoutWrapperStpV.secKlarna.searchAddress(pago, dFTest);
            PageCheckoutWrapperStpV.secKlarna.confirmAddress(pago, dCtxSh.channel, dFTest);
        }
        
        if (execPay) {
            this.dCtxPago.getDataPedido().setCodtipopago("K");
            PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(dCtxPago, dCtxSh.channel, dFTest);
        }
                
        return TestCaseData.getDatosLastStep();
    }    
}
