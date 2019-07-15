package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.SecKrediKartiStpV;

public class PagoKrediKarti extends PagoStpV {
    
    public PagoKrediKarti(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) {
        super(dCtxSh, dCtxPago, driver);
        super.isAvailableExecPay = true;
    }
    
    @SuppressWarnings("static-access")
    @Override
    public void testPagoFromCheckout(boolean execPay) throws Exception {
        DataPedido dataPedido = dCtxPago.getDataPedido();
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh, driver);
        
        SecKrediKartiStpV secKrediKartiStpV = PageCheckoutWrapperStpV.getSecKrediKartiStpV(dCtxSh.channel, driver);
        secKrediKartiStpV.inputNumTarjeta(dataPedido.getPago().getNumtarj());
        secKrediKartiStpV.clickOpcionPagoAPlazo(1);
        
        if (execPay) {
            dataPedido.setCodtipopago("O");
            this.dCtxPago.getFTCkout().trjGuardada = false;
            PageCheckoutWrapperStpV.inputDataTrjAndConfirmPago(dCtxPago, dCtxSh.channel, driver);
        }
    }    
}
