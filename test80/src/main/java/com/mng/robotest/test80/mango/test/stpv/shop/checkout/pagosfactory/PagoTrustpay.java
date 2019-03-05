package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
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
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh, dFTest);
        PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(dCtxPago, dCtxSh.channel, dFTest);
        String importeTotal = this.dCtxPago.getDataPedido().getImporteTotal();
        PageTrustpaySelectBankStpV.validateIsPage(dCtxPago.getDataPedido().getPago().getNombre(dCtxSh.channel), importeTotal, dCtxSh.pais.getCodigo_pais(), dCtxSh.channel, dFTest.driver);
        
        if (execPay) {
            PageTrustpaySelectBankStpV.selectTestBankAndPay(importeTotal, dCtxSh.pais.getCodigo_pais(), dCtxSh.channel, dFTest.driver);
            PageTrustPayResultStpV.clickButtonContinue(dFTest.driver);
        }
        
        return (TestCaseData.getDatosLastStep());
    }    
}
