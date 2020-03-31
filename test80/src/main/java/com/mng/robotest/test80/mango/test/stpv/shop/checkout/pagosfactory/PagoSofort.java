package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.sofort.PageSofort1rst;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.sofort.PageSofort2onStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.sofort.PageSofort4thStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.sofort.PageSofortIconosBancoStpV;

public class PagoSofort extends PagoStpV {
    
    public PagoSofort(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) {
        super(dCtxSh, dCtxPago, driver);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public void testPagoFromCheckout(boolean execPay) throws Exception {
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh, driver);
        PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(this.dCtxPago, dCtxSh.channel, driver);
        boolean isPageIconoSofort = PageSofort1rst.isPageVisibleUntil(3, dCtxSh.channel, driver);
        
        //En ocasiones se salta desde la página de Checkout-Mango hasta la página de selección del banco
        //saltándose la página de selección del icono de sofort
        if (isPageIconoSofort) {
        	int maxSeconds = 3;
            PageSofortIconosBancoStpV.validateIsPageUntil(maxSeconds, dCtxSh.channel, driver);
            PageSofortIconosBancoStpV.clickIconoSofort(dCtxSh.channel, driver);
        }

        if (execPay) {
            Pago pago = this.dCtxPago.getDataPedido().getPago();
            PageSofort2onStpV.selectPaisYBanco(pago.getPaissofort(), pago.getBankcode(), driver);
            PageSofort4thStpV.inputCredencialesUsr(pago.getUsrsofort(), pago.getPasssofort(), driver);            
            PageSofort4thStpV.select1rstCtaAndAccept(driver);
            PageSofort4thStpV.inputTANandAccept(pago.getTansofort(), driver);
            this.dCtxPago.getDataPedido().setCodtipopago("F");
        }
    }    
}
