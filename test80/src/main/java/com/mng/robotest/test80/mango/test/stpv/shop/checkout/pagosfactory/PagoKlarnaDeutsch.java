package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;

@SuppressWarnings("javadoc")
public class PagoKlarnaDeutsch extends PagoStpV {
    
    public PagoKlarnaDeutsch(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest) {
        super(dCtxSh, dCtxPago, dFTest);
        super.isAvailableExecPay = true;
    }
    
    @SuppressWarnings("static-access")
    @Override
    public DatosStep testPagoFromCheckout(boolean execPay) throws Exception {
        DatosStep datosStep = PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(this.dCtxPago, this.dCtxSh, this.dFTest);
        PageCheckoutWrapperStpV.secKlarnaDeutsch.validateIsSection(datosStep, this.dFTest);
        datosStep = PageCheckoutWrapperStpV.secKlarnaDeutsch.inputData("23-04-1974", this.dCtxSh.channel, this.dFTest);
        if (execPay) {
            this.dCtxPago.getDataPedido().setCodtipopago("?");
            datosStep = PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(this.dCtxPago, this.dCtxSh.channel, this.dFTest);
        }
                
        return datosStep;
    }    
}
