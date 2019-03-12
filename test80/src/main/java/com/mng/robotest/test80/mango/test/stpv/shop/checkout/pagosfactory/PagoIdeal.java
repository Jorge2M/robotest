package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.ideal.SecIdeal.BancoSeleccionado;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.ideal.PageIdealSimuladorStpV;

public class PagoIdeal extends PagoStpV {
    
    public PagoIdeal(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) {
        super(dCtxSh, dCtxPago, driver);
        super.isAvailableExecPay = true;
    }
    
    @SuppressWarnings("static-access")
	@Override
    public void testPagoFromCheckout(boolean execPay) throws Exception {
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(this.dCtxPago, dCtxSh, driver);
        PageCheckoutWrapperStpV.secIdeal.validateIsSectionOk(this.dCtxSh.channel, driver);
        
        if (execPay) {
            PageCheckoutWrapperStpV.secIdeal.clickBanco(BancoSeleccionado.TestIssuer, dCtxSh.channel, driver);
            PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(dCtxPago, dCtxSh.channel, driver);
            PageIdealSimuladorStpV.validateIsPage(driver);
            PageIdealSimuladorStpV.clickContinueButton(driver);
        }
    }
}
