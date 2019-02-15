package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;


public class PagoBillpay extends PagoStpV {
    
    public PagoBillpay(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest) {
        super(dCtxSh, dCtxPago, dFTest);
        super.isAvailableExecPay = true;
    }
    
    @SuppressWarnings("static-access")
    @Override
    public DatosStep testPagoFromCheckout(boolean execPay) throws Exception {
        DataPedido dataPedido = this.dCtxPago.getDataPedido();
        DatosStep datosStep = PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(this.dCtxPago, this.dCtxSh, this.dFTest);
        PageCheckoutWrapperStpV.secBillpay.validateIsSectionOk(this.dCtxSh.channel, datosStep, this.dFTest);
        String nombrePago = dataPedido.getPago().getNombre(this.dCtxSh.channel);
        datosStep = PageCheckoutWrapperStpV.secBillpay.inputDiaNacAndCheckAcepto("23-04-1974", nombrePago, this.dCtxSh.channel, this.dFTest);
        if (nombrePago.compareTo("Lastschrift")==0)
            datosStep = PageCheckoutWrapperStpV.secBillpay.inputDataInLastschrift(dataPedido.getPago().getIban(), dataPedido.getPago().getBic(), dataPedido.getPago().getTitular(), this.dFTest);
        
        if (execPay) {
            datosStep = PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(this.dCtxPago, this.dCtxSh.channel, this.dFTest);
            throw new PaymethodWithoutTestPayImplemented(MsgNoPayImplemented);
        }
        
        return datosStep;
    }    
}
