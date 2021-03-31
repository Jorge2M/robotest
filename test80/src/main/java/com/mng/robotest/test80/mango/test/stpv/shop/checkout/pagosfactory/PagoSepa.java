package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.sepa.PageSepa1rstStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.sepa.PageSepaResultMobilStpV;

public class PagoSepa extends PagoStpV {
    
    public PagoSepa(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) {
        super(dCtxSh, dCtxPago, driver);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public void testPagoFromCheckout(boolean execPay) throws Exception {
        Pago pago = this.dCtxPago.getDataPedido().getPago();
        pageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(this.dCtxPago, dCtxSh);
        pagoNavigationsStpV.aceptarCompraDesdeMetodosPago();
        String importeTotal = dCtxPago.getDataPedido().getImporteTotal();
        PageSepa1rstStpV.validateIsPage(pago.getNombre(dCtxSh.channel, dCtxSh.appE), importeTotal, dCtxSh.pais.getCodigo_pais(), dCtxSh.channel, driver);
        
        if (execPay) {
            PageSepa1rstStpV.inputDataAndclickPay(pago.getNumtarj(), pago.getTitular(), importeTotal, dCtxSh.pais.getCodigo_pais(), dCtxSh.channel, driver);
            if (dCtxSh.channel.isDevice()) {
                PageSepaResultMobilStpV.clickButtonPagar(driver);
            }
        }
    }
}
