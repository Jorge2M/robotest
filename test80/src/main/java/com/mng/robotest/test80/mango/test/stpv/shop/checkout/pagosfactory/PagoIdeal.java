package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.ideal.SecIdeal.BancoSeleccionado;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.ideal.PageIdealSimuladorStpV;

public class PagoIdeal extends PagoStpV {
    
    public PagoIdeal(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) throws Exception {
        super(dCtxSh, dCtxPago, driver);
        super.isAvailableExecPay = true;
    }
    
    @SuppressWarnings("static-access")
	@Override
    public void testPagoFromCheckout(boolean execPay) throws Exception {
        pageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(this.dCtxPago, dCtxSh);
        pageCheckoutWrapperStpV.getSecIdealStpV().validateIsSectionOk();
        
        if (execPay) {
            pageCheckoutWrapperStpV.getSecIdealStpV().clickBanco(BancoSeleccionado.TestIssuer);
            dCtxPago = checkoutFlow.checkout(From.MetodosPago);
            PageIdealSimuladorStpV.validateIsPage(driver);
            PageIdealSimuladorStpV.clickContinueButton(driver);
        }
    }
}
