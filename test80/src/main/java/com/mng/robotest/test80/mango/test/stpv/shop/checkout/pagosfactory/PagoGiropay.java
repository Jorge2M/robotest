package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.giropay.PageGiropay1rstStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.giropay.PageGiropayInputDataTestStpV;


public class PagoGiropay extends PagoStpV {
    
    public PagoGiropay(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest) {
        super(dCtxSh, dCtxPago, dFTest);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public DatosStep testPagoFromCheckout(boolean execPay) throws Exception {
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(this.dCtxPago, this.dCtxSh, this.dFTest);
        DatosStep datosStep = PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(this.dCtxPago, this.dCtxSh.channel, this.dFTest);
        DataPedido dataPedido = this.dCtxPago.getDataPedido(); 
        String nombrePago = dataPedido.getPago().getNombre(this.dCtxSh.channel);
        PageGiropay1rstStpV.validateIsPage(nombrePago, dataPedido.getImporteTotal(), this.dCtxSh.pais.getCodigo_pais(), this.dCtxSh.channel, datosStep, this.dFTest);
        
        if (execPay) {
            dataPedido.setCodtipopago("F");
            Pago pago = dataPedido.getPago();
            String bankIdGiropay = pago.getBankidgiropay();
            PageGiropay1rstStpV.inputBank(bankIdGiropay, this.dCtxSh.channel, this.dFTest);
            PageGiropay1rstStpV.clickButtonContinuePay(this.dCtxSh.channel, this.dFTest);            
            datosStep = PageGiropayInputDataTestStpV.inputDataAndClick(pago, this.dFTest);
        }
        
        return datosStep;
    }
}
