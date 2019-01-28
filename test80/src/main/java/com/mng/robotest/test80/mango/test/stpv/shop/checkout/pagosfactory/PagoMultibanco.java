package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.multibanco.PageMultibanco1rstStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.multibanco.PageMultibancoEnProgresoStpv;

@SuppressWarnings("javadoc")
public class PagoMultibanco extends PagoStpV {

    public PagoMultibanco(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest) {
        super(dCtxSh, dCtxPago, dFTest);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public DatosStep testPagoFromCheckout(boolean execPay) throws Exception {
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(this.dCtxPago, this.dCtxSh, this.dFTest);
        DatosStep datosStep = PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(this.dCtxPago, this.dCtxSh.channel, this.dFTest);
        DataPedido dataPedido = this.dCtxPago.getDataPedido(); 
        String nombrePago = dataPedido.getPago().getNombre(this.dCtxSh.channel);
        PageMultibanco1rstStpV.validateIsPage(nombrePago, dataPedido.getImporteTotal(), dataPedido.getEmailCheckout(), this.dCtxSh.pais.getCodigo_pais(), this.dCtxSh.channel, datosStep, this.dFTest);
        datosStep = PageMultibanco1rstStpV.continueToNextPage(this.dCtxSh.channel, this.dFTest);
        
        if (execPay)
            datosStep = PageMultibancoEnProgresoStpv.clickButtonNextStep(this.dFTest);
        
        return datosStep;
    }    
}
