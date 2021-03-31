package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.assist.PageAssist1rstStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.assist.PageAssistLastStpV;

public class PagoAssist extends PagoStpV {
    
    public PagoAssist(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) {
        super(dCtxSh, dCtxPago, driver);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public void testPagoFromCheckout(boolean execPay) throws Exception {
        pageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
        pagoNavigationsStpV.aceptarCompraDesdeMetodosPago();
        PageAssist1rstStpV.validateIsPage(dCtxPago.getDataPedido().getImporteTotal(), dCtxSh.pais, dCtxSh.channel, dCtxSh.appE, driver);
        
        if (execPay) {
            PageAssist1rstStpV.inputDataTarjAndPay(this.dCtxPago.getDataPedido().getPago(), dCtxSh.channel, driver);            
            PageAssistLastStpV.clickSubmit(driver);
        }
    }
}
