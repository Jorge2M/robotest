package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;

public class PagoBillpay extends PagoStpV {
    
    public PagoBillpay(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) {
        super(dCtxSh, dCtxPago, driver);
        super.isAvailableExecPay = true;
    }
    
    @SuppressWarnings("static-access")
    @Override
    public void testPagoFromCheckout(boolean execPay) throws Exception {
        DataPedido dataPedido = this.dCtxPago.getDataPedido();
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh, driver);
        PageCheckoutWrapperStpV.secBillpay.validateIsSectionOk(dCtxSh.channel, driver);
        String nombrePago = dataPedido.getPago().getNombre(dCtxSh.channel);
        PageCheckoutWrapperStpV.secBillpay.inputDiaNacAndCheckAcepto("23-04-1974", nombrePago, dCtxSh.channel, driver);
        if (nombrePago.compareTo("Lastschrift")==0) {
            PageCheckoutWrapperStpV.secBillpay.inputDataInLastschrift(dataPedido.getPago().getIban(), dataPedido.getPago().getBic(), dataPedido.getPago().getTitular(), driver);
        }
        
        if (execPay) {
            PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(dCtxPago, dCtxSh.channel, driver);
            throw new PaymethodWithoutTestPayImplemented(MsgNoPayImplemented);
        }
    }    
}
