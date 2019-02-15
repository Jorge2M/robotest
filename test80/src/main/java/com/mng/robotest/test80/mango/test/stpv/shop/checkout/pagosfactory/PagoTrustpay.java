package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.trustpay.PageTrustPayResultStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.trustpay.PageTrustpaySelectBankStpV;


public class PagoTrustpay extends PagoStpV {

    public PagoTrustpay(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest) {
        super(dCtxSh, dCtxPago, dFTest);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public DatosStep testPagoFromCheckout(boolean execPay) throws Exception {
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(this.dCtxPago, this.dCtxSh, this.dFTest);
        DatosStep datosStep = PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(this.dCtxPago, this.dCtxSh.channel, this.dFTest);
        String importeTotal = this.dCtxPago.getDataPedido().getImporteTotal();
        PageTrustpaySelectBankStpV.validateIsPage(this.dCtxPago.getDataPedido().getPago().getNombre(this.dCtxSh.channel), importeTotal, this.dCtxSh.pais.getCodigo_pais(), this.dCtxSh.channel, datosStep, this.dFTest);
        
        if (execPay) {
            PageTrustpaySelectBankStpV.selectTestBankAndPay(importeTotal, this.dCtxSh.pais.getCodigo_pais(), this.dCtxSh.channel, this.dFTest);
            datosStep = PageTrustPayResultStpV.clickButtonContinue(this.dFTest);
        }
        
        return datosStep;
    }    
}
