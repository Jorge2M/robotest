package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.Dotpay.PageDotpay1rstStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.Dotpay.PageDotpayAcceptSimulationStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.Dotpay.PageDotpayPaymentChannelStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.Dotpay.PageDotpayResultadoStpV;


public class PagoDotpay extends PagoStpV {
    
    public PagoDotpay(DataCtxShop dCtxSh, DataCtxPago dataPago, DataFmwkTest dFTest) {
        super(dCtxSh, dataPago, dFTest);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public DatosStep testPagoFromCheckout(boolean execPay) throws Exception {
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(this.dCtxPago, this.dCtxSh, this.dFTest);
        DatosStep datosStep = PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(this.dCtxPago, this.dCtxSh.channel, this.dFTest);
        DataPedido dataPedido = this.dCtxPago.getDataPedido(); 
        String nombrePago = dataPedido.getPago().getNombre(this.dCtxSh.channel);
        PageDotpay1rstStpV.validateIsPage(nombrePago, dataPedido.getImporteTotal(), this.dCtxSh.pais.getCodigo_pais(), this.dCtxSh.channel, datosStep, this.dFTest);
        
        if (execPay) {
            PageDotpay1rstStpV.clickToPay(dataPedido.getImporteTotal(), this.dCtxSh.pais.getCodigo_pais(), this.dCtxSh.channel, this.dFTest);
            PageDotpayPaymentChannelStpV.selectPayment(1, this.dFTest);
            PageDotpayPaymentChannelStpV.inputNameAndConfirm("Jorge", "Muñoz", this.dFTest);
            PageDotpayAcceptSimulationStpV.clickRedButtonAceptar(this.dFTest);
            PageDotpayResultadoStpV.clickNext(this.dFTest);
            dataPedido.setCodtipopago("F");
        }
        
        return datosStep;
    }
}
