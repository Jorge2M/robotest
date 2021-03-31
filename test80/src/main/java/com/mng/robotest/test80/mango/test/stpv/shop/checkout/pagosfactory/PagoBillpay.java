package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;


public class PagoBillpay extends PagoStpV {
    
    public PagoBillpay(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) {
        super(dCtxSh, dCtxPago, driver);
        super.isAvailableExecPay = true;
    }
    
    @SuppressWarnings("static-access")
    @Override
    public void testPagoFromCheckout(boolean execPay) throws Exception {
        DataPedido dataPedido = this.dCtxPago.getDataPedido();
        pageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
        pageCheckoutWrapperStpV.getSecBillpayStpV().validateIsSectionOk();
        String nombrePago = dataPedido.getPago().getNombre(dCtxSh.channel, dCtxSh.appE);
        pageCheckoutWrapperStpV.getSecBillpayStpV().inputDiaNacAndCheckAcepto("23-04-1974", nombrePago);
        if (nombrePago.compareTo("Lastschrift")==0) {
            pageCheckoutWrapperStpV.getSecBillpayStpV().inputDataInLastschrift(dataPedido.getPago().getIban(), dataPedido.getPago().getBic(), dataPedido.getPago().getTitular());
        }
        
        if (execPay) {
            pagoNavigationsStpV.aceptarCompraDesdeMetodosPago();
            throw new PaymethodWithoutTestPayImplemented(MsgNoPayImplemented);
        }
    }    
}
