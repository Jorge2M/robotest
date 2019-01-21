package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.koreancreditcard.PageKoreanConfirmatioStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.koreancreditcard.PageKoreanCreditCardStpV;

@SuppressWarnings("javadoc")
public class PagoKoreanCreditCard extends PagoStpV {
    
    public PagoKoreanCreditCard(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest) {
        super(dCtxSh, dCtxPago, dFTest);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public datosStep testPagoFromCheckout(boolean execPay) throws Exception {
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(this.dCtxPago, this.dCtxSh, this.dFTest);
        datosStep datosStep = PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(this.dCtxPago, this.dCtxSh.channel, this.dFTest);
        PageKoreanCreditCardStpV.validateIsPage(this.dCtxPago.getDataPedido().getImporteTotal(), this.dCtxSh.pais, this.dCtxSh.channel, datosStep, this.dFTest);
        
        if (execPay) {
        	PageKoreanCreditCardStpV.clickConfirmarButton(dFTest);
        	datosStep = PageKoreanConfirmatioStpV.clickConfirmarButton(dFTest);
        }
        
        return datosStep;
    }    
}
