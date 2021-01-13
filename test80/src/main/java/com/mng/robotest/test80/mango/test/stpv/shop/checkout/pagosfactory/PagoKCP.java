package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.kcp.PageKcpMainStpV;

public class PagoKCP extends PagoStpV {
    
    public PagoKCP(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) {
        super(dCtxSh, dCtxPago, driver);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public void testPagoFromCheckout(boolean execPay) throws Exception {
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh, driver);
        PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(dCtxPago, dCtxSh.channel, driver);
        PageKcpMainStpV pageKcpMainStpV = new PageKcpMainStpV(driver);
        pageKcpMainStpV.isPage(30);
        pageKcpMainStpV.isPresentTermAndConditions(30);
    }    
}
