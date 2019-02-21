package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.ideal.SecIdeal.BancoSeleccionado;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.ideal.PageIdealSimuladorStpV;



public class PagoIdeal extends PagoStpV {
    
    public PagoIdeal(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest) {
        super(dCtxSh, dCtxPago, dFTest);
        super.isAvailableExecPay = true;
    }
    
    @SuppressWarnings("static-access")
	@Override
    public DatosStep testPagoFromCheckout(boolean execPay) throws Exception {
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(this.dCtxPago, dCtxSh, dFTest);
        PageCheckoutWrapperStpV.secIdeal.validateIsSectionOk(this.dCtxSh.channel, dFTest.driver);
        
        if (execPay) {
            PageCheckoutWrapperStpV.secIdeal.clickBanco(BancoSeleccionado.TestIssuer, dCtxSh.channel, dFTest.driver);
            PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(dCtxPago, dCtxSh.channel, dFTest);
            PageIdealSimuladorStpV.validateIsPage(dFTest.driver);
            PageIdealSimuladorStpV.clickContinueButton(dFTest.driver);
        }
        
        return TestCaseData.getDatosLastStep();
    }
}
