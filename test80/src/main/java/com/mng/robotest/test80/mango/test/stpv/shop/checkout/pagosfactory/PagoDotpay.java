package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.Dotpay.PageDotpay1rstStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.Dotpay.PageDotpayAcceptSimulationStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.Dotpay.PageDotpayPaymentChannelStpV;

public class PagoDotpay extends PagoStpV {
    
    public PagoDotpay(DataCtxShop dCtxSh, DataCtxPago dataPago, WebDriver driver) {
        super(dCtxSh, dataPago, driver);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public void testPagoFromCheckout(boolean execPay) throws Exception {
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh, driver);
        PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(dCtxPago, dCtxSh.channel, driver);
        DataPedido dataPedido = dCtxPago.getDataPedido(); 
        String nombrePago = dataPedido.getPago().getNombre(this.dCtxSh.channel);
        PageDotpay1rstStpV.validateIsPage(nombrePago, dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais(), dCtxSh.channel, driver);
        
        if (execPay) {
            PageDotpay1rstStpV.clickToPay(dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais(), dCtxSh.channel, driver);
            PageDotpayPaymentChannelStpV.selectPayment(1, driver);
            PageDotpayPaymentChannelStpV.inputNameAndConfirm("Jorge", "Muñoz", driver);
            PageDotpayAcceptSimulationStpV.clickRedButtonAceptar(driver);
            dataPedido.setCodtipopago("F");
        }
    }
}
