package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.assistqiwi.PageAssistQiwi1rstStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.assistqiwi.PageQiwiInputTlfnStpV;

@SuppressWarnings("javadoc")
public class PagoAssistQiwi extends PagoStpV {

    public PagoAssistQiwi(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest) {
        super(dCtxSh, dCtxPago, dFTest);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public datosStep testPagoFromCheckout(boolean execPay) throws Exception {
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(this.dCtxPago, this.dCtxSh, this.dFTest);
        datosStep datosStep = PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(this.dCtxPago, this.dCtxSh.channel, this.dFTest);
        PageAssistQiwi1rstStpV.validateIsPage(this.dCtxPago.getDataPedido().getImporteTotal(), this.dCtxSh.pais.getCodigo_pais(), this.dCtxSh.channel, datosStep, this.dFTest);
        PageAssistQiwi1rstStpV.clickIconPasarelaQiwi(this.dCtxSh.channel, this.dFTest);
        
        if (execPay) {
            String tlfQiwi = this.dCtxPago.getDataPedido().getPago().getTelefqiwi();
            PageQiwiInputTlfnStpV.inputTlfnAndAceptar(tlfQiwi, this.dFTest);
        }

        return datosStep;
    }    
}
