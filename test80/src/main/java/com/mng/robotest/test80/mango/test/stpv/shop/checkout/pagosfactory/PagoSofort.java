package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
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
    
    public PagoSofort(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest) {
        super(dCtxSh, dCtxPago, dFTest);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public DatosStep testPagoFromCheckout(boolean execPay) throws Exception {
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(this.dCtxPago, this.dCtxSh, this.dFTest);
        DatosStep datosStep = 
        	PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(this.dCtxPago, this.dCtxSh.channel, this.dFTest);
        boolean isPageIconoSofort = PageSofort1rst.isPageVisibleUntil(3, this.dCtxSh.channel, this.dFTest.driver);
        
        //En ocasiones se salta desde la página de Checkout-Mango hasta la página de selección del banco
        //saltándose la página de selección del icono de sofort
        if (isPageIconoSofort) {
            PageSofortIconosBancoStpV.validateIsPage(this.dCtxSh.channel, datosStep, dFTest);
            datosStep = PageSofortIconosBancoStpV.clickIconoSofort(this.dCtxSh.channel, this.dFTest);
        }

        if (execPay) {
            Pago pago = this.dCtxPago.getDataPedido().getPago();
            PageSofort2onStpV.selectPaisYBanco(pago.getPaissofort(), pago.getBankcode(), this.dFTest);
            PageSofort4thStpV.inputCredencialesUsr(pago.getUsrsofort(), pago.getPasssofort(), this.dFTest);            
            PageSofort4thStpV.select1rstCtaAndAccept(this.dFTest);
            datosStep = PageSofort4thStpV.inputTANandAccept(pago.getTansofort(), this.dFTest);
            this.dCtxPago.getDataPedido().setCodtipopago("F");
        }
        
        return datosStep;
    }    
}
