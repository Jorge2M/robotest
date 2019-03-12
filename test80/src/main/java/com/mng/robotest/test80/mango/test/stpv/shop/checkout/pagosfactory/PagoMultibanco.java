package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.multibanco.PageMultibanco1rstStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.multibanco.PageMultibancoEnProgresoStpv;

public class PagoMultibanco extends PagoStpV {

    public PagoMultibanco(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) {
        super(dCtxSh, dCtxPago, driver);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public void testPagoFromCheckout(boolean execPay) throws Exception {
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh, driver);
        PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(dCtxPago, dCtxSh.channel, driver);
        DataPedido dataPedido = this.dCtxPago.getDataPedido(); 
        String nombrePago = dataPedido.getPago().getNombre(dCtxSh.channel);
        PageMultibanco1rstStpV.validateIsPage(nombrePago, dataPedido.getImporteTotal(), dataPedido.getEmailCheckout(), dCtxSh.pais.getCodigo_pais(), dCtxSh.channel, driver);
        PageMultibanco1rstStpV.continueToNextPage(dCtxSh.channel, driver);
        
        if (execPay) {
            PageMultibancoEnProgresoStpv.clickButtonNextStep(driver);
        }
    }    
}
