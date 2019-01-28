package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.assist.PageAssist1rstStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.assist.PageAssistLastStpV;

@SuppressWarnings("javadoc")
public class PagoAssist extends PagoStpV {
    
    public PagoAssist(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest) {
        super(dCtxSh, dCtxPago, dFTest);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public DatosStep testPagoFromCheckout(boolean execPay) throws Exception {
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(this.dCtxPago, this.dCtxSh, this.dFTest);
        DatosStep datosStep = PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(this.dCtxPago, this.dCtxSh.channel, this.dFTest);
        PageAssist1rstStpV.validateIsPage(this.dCtxPago.getDataPedido().getImporteTotal(), this.dCtxSh.pais, this.dCtxSh.channel, datosStep, this.dFTest);
        
        if (execPay) {
            PageAssist1rstStpV.inputDataTarjAndPay(this.dCtxPago.getDataPedido().getPago(), this.dCtxSh.channel, this.dFTest);            
            datosStep = PageAssistLastStpV.clickSubmit(this.dFTest);
        }
        
        return datosStep;
    }
}
