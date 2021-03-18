package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paysecureqiwi.PagePaysecureConfirm;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.paysecureqiwi.PagePaysecureQiwi1rstStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.paysecureqiwi.PageQiwiConfirmStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.paysecureqiwi.PageQiwiInputTlfnStpV;

public class PagoPaysecureQiwi extends PagoStpV {

    public PagoPaysecureQiwi(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) {
        super(dCtxSh, dCtxPago, driver);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public void testPagoFromCheckout(boolean execPay) throws Exception {
        pageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
        pagoNavigationsStpV.aceptarCompraDesdeMetodosPago();
        
        PagePaysecureQiwi1rstStpV pagePaysecureQiwi1rstStpV = new PagePaysecureQiwi1rstStpV(dCtxSh.appE, driver);
        pagePaysecureQiwi1rstStpV.validateIsPage(dCtxPago.getDataPedido().getImporteTotal(), dCtxSh.pais.getCodigo_pais(), dCtxSh.channel);
        pagePaysecureQiwi1rstStpV.clickIconPasarelaQiwi(dCtxSh.channel);
        
        if (execPay) {
            String tlfQiwi = dCtxPago.getDataPedido().getPago().getTelefqiwi();
            PageQiwiInputTlfnStpV.inputTelefono(tlfQiwi, driver);
            PageQiwiInputTlfnStpV.clickConfirmarButton(driver);
            if (PagePaysecureConfirm.isPage(driver)) {
            	PageQiwiConfirmStpV.selectConfirmButton(driver);
            }
        }
    }    
}
