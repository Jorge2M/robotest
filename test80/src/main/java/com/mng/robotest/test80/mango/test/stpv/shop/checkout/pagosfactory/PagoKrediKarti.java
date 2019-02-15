package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;


public class PagoKrediKarti extends PagoStpV {
    
    public PagoKrediKarti(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest) {
        super(dCtxSh, dCtxPago, dFTest);
        super.isAvailableExecPay = true;
    }
    
    @SuppressWarnings("static-access")
    @Override
    public DatosStep testPagoFromCheckout(boolean execPay) throws Exception {
        DataPedido dataPedido = this.dCtxPago.getDataPedido();
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(this.dCtxPago, this.dCtxSh, this.dFTest);
        PageCheckoutWrapperStpV.secKrediKarti.inputNumTarjeta(dataPedido.getPago().getNumtarj(), this.dCtxSh.channel, this.dFTest);
        DatosStep datosStep = PageCheckoutWrapperStpV.secKrediKarti.clickOpcionPagoAPlazo(1/*numOpcion*/, this.dCtxSh.channel, this.dFTest);
        
        if (execPay) {
            dataPedido.setCodtipopago("O");
            this.dCtxPago.getFTCkout().trjGuardada = false;
            datosStep = PageCheckoutWrapperStpV.inputDataTrjAndConfirmPago(this.dCtxPago, this.dCtxSh.channel, this.dFTest);
        }
            
        return datosStep;
    }    
}
