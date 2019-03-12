package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;

public class PagoStoreCredit extends PagoStpV {
    
    public PagoStoreCredit(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) {
        super(dCtxSh, dCtxPago, driver);
        super.isAvailableExecPay = true;
    }
    
    @SuppressWarnings("static-access")
    @Override
    public void testPagoFromCheckout(boolean execPay) throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        PageCheckoutWrapperStpV.secStoreCredit.validateInitialStateOk(dCtxSh.channel, dCtxPago, dFTest.driver);
        PageCheckoutWrapperStpV.secStoreCredit.selectSaldoEnCuentaBlock(dCtxSh.pais, dCtxPago, dCtxSh.appE, dCtxSh.channel, dFTest.driver);
        PageCheckoutWrapperStpV.secStoreCredit.selectSaldoEnCuentaBlock(dCtxSh.pais, dCtxPago, dCtxSh.appE, dCtxSh.channel, dFTest.driver);
        
        if (execPay) {
            PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(dCtxPago, dCtxSh.channel, driver);
            this.dCtxPago.getDataPedido().setCodtipopago("U");
        }
    }
}
