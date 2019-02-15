package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
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
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(this.dCtxPago, this.dCtxSh, this.dFTest);
        DatosStep datosStep = PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(this.dCtxPago, this.dCtxSh.channel, this.dFTest);
        PagePaytrail1rstStpV.validateIsPage(this.dCtxPago.getDataPedido().getImporteTotal(), this.dCtxSh.pais.getCodigo_pais(), this.dCtxSh.channel, datosStep, this.dFTest);
        datosStep = PagePaytrail1rstStpV.selectBancoAndContinue(this.dCtxSh.channel, this.dFTest);
        
        if (execPay) {
            this.dCtxPago.getDataPedido().setCodtipopago("F");
            String codigoPais = this.dCtxPago.getDataPedido().getCodigoPais();
            PagePaytrailEpaymentStpV.clickCodeCardOK(this.dCtxPago.getDataPedido().getImporteTotal(), codigoPais, this.dFTest);            
            PagePaytrailIdConfirmStpV.inputIDAndClickConfirmar("V2360A71", this.dCtxPago.getDataPedido().getImporteTotal(), codigoPais, this.dFTest);
            datosStep = PagePaytrailResultadoOkStpV.clickVolverAMangoButton(this.dFTest);
        }
        
        return datosStep;
    }
}
