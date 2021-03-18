package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.trustpay.PageTrustPayResultStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.trustpay.PageTrustpaySelectBankStpV;

public class PagoTrustpay extends PagoStpV {

    public PagoTrustpay(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) {
        super(dCtxSh, dCtxPago, driver);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public void testPagoFromCheckout(boolean execPay) throws Exception {
        pageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
        pagoNavigationsStpV.aceptarCompraDesdeMetodosPago();
        String importeTotal = this.dCtxPago.getDataPedido().getImporteTotal();
        PageTrustpaySelectBankStpV.validateIsPage(dCtxPago.getDataPedido().getPago().getNombre(dCtxSh.channel), importeTotal, dCtxSh.pais.getCodigo_pais(), dCtxSh.channel, driver);
        
        if (execPay) {
            PageTrustpaySelectBankStpV.selectTestBankAndPay(importeTotal, dCtxSh.pais.getCodigo_pais(), dCtxSh.channel, driver);
            PageTrustPayResultStpV.clickButtonContinue(driver);
        }
    }    
}
