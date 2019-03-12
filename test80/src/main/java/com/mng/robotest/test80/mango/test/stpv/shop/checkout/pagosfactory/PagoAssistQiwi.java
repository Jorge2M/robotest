package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.assistqiwi.PageQiwiConfirm;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.assistqiwi.PageAssistQiwi1rstStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.assistqiwi.PageQiwiConfirmStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.assistqiwi.PageQiwiInputTlfnStpV;

public class PagoAssistQiwi extends PagoStpV {

    public PagoAssistQiwi(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) {
        super(dCtxSh, dCtxPago, driver);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public void testPagoFromCheckout(boolean execPay) throws Exception {
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh, driver);
        PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(dCtxPago, dCtxSh.channel, driver);
        PageAssistQiwi1rstStpV.validateIsPage(dCtxPago.getDataPedido().getImporteTotal(), dCtxSh.pais.getCodigo_pais(), dCtxSh.channel, driver);
        PageAssistQiwi1rstStpV.clickIconPasarelaQiwi(dCtxSh.channel, driver);
        
        if (execPay) {
            String tlfQiwi = dCtxPago.getDataPedido().getPago().getTelefqiwi();
            PageQiwiInputTlfnStpV.inputTelefono(tlfQiwi, driver);
            PageQiwiInputTlfnStpV.clickConfirmarButton(driver);
            if (PageQiwiConfirm.isPage(driver)) {
            	PageQiwiConfirmStpV.selectConfirmButton(driver);
            }
        }
    }    
}
