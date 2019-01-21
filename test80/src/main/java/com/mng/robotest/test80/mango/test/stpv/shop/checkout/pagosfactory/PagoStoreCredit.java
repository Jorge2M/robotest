package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;

@SuppressWarnings("javadoc")
public class PagoStoreCredit extends PagoStpV {
    
    public PagoStoreCredit(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest) {
        super(dCtxSh, dCtxPago, dFTest);
        super.isAvailableExecPay = true;
    }
    
    @SuppressWarnings("static-access")
    @Override
    public datosStep testPagoFromCheckout(boolean execPay) throws Exception {
        PageCheckoutWrapperStpV.secStoreCredit.validateInitialStateOk(this.dCtxSh.channel, this.dCtxPago, this.dFTest);
        PageCheckoutWrapperStpV.secStoreCredit.selectSaldoEnCuentaBlock(this.dCtxSh.pais, this.dCtxPago, this.dCtxSh.appE, this.dCtxSh.channel, this.dFTest);
        datosStep datosStep = PageCheckoutWrapperStpV.secStoreCredit.selectSaldoEnCuentaBlock(this.dCtxSh.pais, this.dCtxPago, this.dCtxSh.appE, this.dCtxSh.channel, this.dFTest);
        
        if (execPay) {
            datosStep = PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(this.dCtxPago, this.dCtxSh.channel, this.dFTest);
            this.dCtxPago.getDataPedido().setCodtipopago("U");
        }

        return datosStep;
    }
}
