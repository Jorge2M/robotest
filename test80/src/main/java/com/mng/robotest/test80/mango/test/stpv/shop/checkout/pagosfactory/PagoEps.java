package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.eps.PageEpsSimulador.TypeDelay;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.eps.PageEpsSelBancoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.eps.PageEpsSimuladorStpV;

public class PagoEps extends PagoStpV {
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
    
    public PagoEps(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest) {
        super(dCtxSh, dCtxPago, dFTest);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public DatosStep testPagoFromCheckout(boolean execPay) throws Exception {
    	//TODO mantener hasta que se elimine el actual TestAB para la aparición o no del pago EPS
    	//activateTestABforMethodEPS();
    	this.dFTest.driver.navigate().refresh();
    	
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh, dFTest);
        PageCheckoutWrapperStpV.selectBancoEPS(dCtxSh, dFTest);
        PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(dCtxPago, dCtxSh.channel, dFTest);
        if (!UtilsMangoTest.isEntornoPRO(dCtxSh.appE, dFTest)) {
        	PageEpsSimuladorStpV.validateIsPage(dFTest.driver);
        	PageEpsSimuladorStpV.selectDelay(TypeDelay.OneMinutes, dFTest.driver);
        }
        else {
        	PageEpsSelBancoStpV.validateIsPage(dCtxPago.getDataPedido().getImporteTotal(), dCtxSh.pais.getCodigo_pais(), dCtxSh.channel, dFTest.driver);
        }
        
        if (execPay) {
            this.dCtxPago.getDataPedido().setCodtipopago("F");
            PageEpsSimuladorStpV.clickContinueButton(dFTest.driver);
        }
        
        return TestCaseData.getDatosStepForValidation();
    }
    
//    private void activateTestABforMethodEPS() {
//    	TestAB testAB = TestAB.getInstance(TestABid.PagoAustriaEPS, dCtxSh.appE);
//	    try {
//	    	testAB.activateTestAB(1, dFTest.driver);
//    	}
//    	catch (Exception e) {
//    		pLogger.warn("Problem activating TestAB for show EPS payment method", e);
//    	}
//	    
//    	this.dFTest.driver.navigate().refresh();
//    }
}
