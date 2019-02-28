package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
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
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh, dFTest);
        PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(dCtxPago, dCtxSh.channel, dFTest);
        DataPedido dataPedido = dCtxPago.getDataPedido(); 
        String nombrePago = dataPedido.getPago().getNombre(this.dCtxSh.channel);
        PageDotpay1rstStpV.validateIsPage(nombrePago, dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais(), dCtxSh.channel, dFTest.driver);
        
        if (execPay) {
            PageDotpay1rstStpV.clickToPay(dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais(), dCtxSh.channel, dFTest.driver);
            PageDotpayPaymentChannelStpV.selectPayment(1, dFTest.driver);
            PageDotpayPaymentChannelStpV.inputNameAndConfirm("Jorge", "Muñoz", dFTest.driver);
            PageDotpayAcceptSimulationStpV.clickRedButtonAceptar(dFTest.driver);
            PageDotpayResultadoStpV.clickNext(dFTest.driver);
            dataPedido.setCodtipopago("F");
        }
        
        return TestCaseData.getDatosLastStep();
    }
}
