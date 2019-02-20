package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.multibanco.PageMultibanco1rstStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.multibanco.PageMultibancoEnProgresoStpv;


public class PagoMultibanco extends PagoStpV {

    public PagoMultibanco(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest) {
        super(dCtxSh, dCtxPago, dFTest);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public DatosStep testPagoFromCheckout(boolean execPay) throws Exception {
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh, dFTest);
        PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(dCtxPago, dCtxSh.channel, dFTest);
        DataPedido dataPedido = this.dCtxPago.getDataPedido(); 
        String nombrePago = dataPedido.getPago().getNombre(dCtxSh.channel);
        PageMultibanco1rstStpV.validateIsPage(nombrePago, dataPedido.getImporteTotal(), dataPedido.getEmailCheckout(), dCtxSh.pais.getCodigo_pais(), dCtxSh.channel, dFTest.driver);
        PageMultibanco1rstStpV.continueToNextPage(dCtxSh.channel, dFTest.driver);
        
        if (execPay) {
            PageMultibancoEnProgresoStpv.clickButtonNextStep(this.dFTest);
        }
        
        return TestCaseData.getDatosStepForValidation();
    }    
}
