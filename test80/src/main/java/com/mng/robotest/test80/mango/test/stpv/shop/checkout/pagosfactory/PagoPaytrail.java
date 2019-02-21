package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.paytrail.PagePaytrail1rstStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.paytrail.PagePaytrailEpaymentStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.paytrail.PagePaytrailIdConfirmStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.paytrail.PagePaytrailResultadoOkStpV;


public class PagoPaytrail extends PagoStpV {
    
    public PagoPaytrail(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest) {
        super(dCtxSh, dCtxPago, dFTest);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public DatosStep testPagoFromCheckout(boolean execPay) throws Exception {
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh, dFTest);
        PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(dCtxPago, dCtxSh.channel, dFTest);
        PagePaytrail1rstStpV.validateIsPage(dCtxPago.getDataPedido().getImporteTotal(), dCtxSh.pais.getCodigo_pais(), dCtxSh.channel, dFTest.driver);
        PagePaytrail1rstStpV.selectBancoAndContinue(dCtxSh.channel, dFTest.driver);
        
        if (execPay) {
            this.dCtxPago.getDataPedido().setCodtipopago("F");
            String codigoPais = this.dCtxPago.getDataPedido().getCodigoPais();
            PagePaytrailEpaymentStpV.clickCodeCardOK(dCtxPago.getDataPedido().getImporteTotal(), codigoPais, dFTest.driver);            
            PagePaytrailIdConfirmStpV.inputIDAndClickConfirmar("V2360A71", dCtxPago.getDataPedido().getImporteTotal(), codigoPais, dFTest.driver);
            PagePaytrailResultadoOkStpV.clickVolverAMangoButton(dFTest.driver);
        }
        
        return TestCaseData.getDatosLastStep();
    }
}
