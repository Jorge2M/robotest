package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;

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
        PageCheckoutWrapperStpV.secKrediKarti.inputNumTarjeta(dataPedido.getPago().getNumtarj(), dCtxSh.channel, driver);
        PageCheckoutWrapperStpV.secKrediKarti.clickOpcionPagoAPlazo(1, dCtxSh.channel, driver);
        
        if (execPay) {
            dataPedido.setCodtipopago("O");
            this.dCtxPago.getFTCkout().trjGuardada = false;
            PageCheckoutWrapperStpV.inputDataTrjAndConfirmPago(dCtxPago, dCtxSh.channel, driver);
        }
    }    
}
