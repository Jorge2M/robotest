package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.ideal.SecIdeal.BancoSeleccionado;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.ideal.PageIdealSimuladorStpV;


@SuppressWarnings("javadoc")
public class PagoIdeal extends PagoStpV {
    
    public PagoIdeal(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest) {
        super(dCtxSh, dCtxPago, dFTest);
        super.isAvailableExecPay = true;
    }
    
    @SuppressWarnings("static-access")
	@Override
    public datosStep testPagoFromCheckout(boolean execPay) throws Exception {
        datosStep datosStep = PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(this.dCtxPago, this.dCtxSh, this.dFTest);
        PageCheckoutWrapperStpV.secIdeal.validateIsSectionOk(this.dCtxSh.channel, datosStep, this.dFTest);
        
        if (execPay) {
            PageCheckoutWrapperStpV.secIdeal.clickBanco(BancoSeleccionado.TestIssuer, this.dCtxSh.channel, this.dFTest);
            
            datosStep = PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(this.dCtxPago, this.dCtxSh.channel, this.dFTest);
            PageIdealSimuladorStpV.validateIsPage(datosStep, dFTest);
            datosStep = PageIdealSimuladorStpV.clickContinueButton(this.dFTest);
        }
        
        return datosStep;
    }
}
