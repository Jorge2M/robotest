package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
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
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh, dFTest);
        PageCheckoutWrapperStpV.secBillpay.validateIsSectionOk(dCtxSh.channel, dFTest.driver);
        String nombrePago = dataPedido.getPago().getNombre(dCtxSh.channel);
        PageCheckoutWrapperStpV.secBillpay.inputDiaNacAndCheckAcepto("23-04-1974", nombrePago, dCtxSh.channel, dFTest);
        if (nombrePago.compareTo("Lastschrift")==0) {
            PageCheckoutWrapperStpV.secBillpay.inputDataInLastschrift(dataPedido.getPago().getIban(), dataPedido.getPago().getBic(), dataPedido.getPago().getTitular(), dFTest);
        }
        
        if (execPay) {
            PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(dCtxPago, dCtxSh.channel, dFTest);
            throw new PaymethodWithoutTestPayImplemented(MsgNoPayImplemented);
        }
        
        return TestCaseData.getDatosLastStep();
    }    
}
