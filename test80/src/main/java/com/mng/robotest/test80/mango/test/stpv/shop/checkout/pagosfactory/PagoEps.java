package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.eps.PageEpsSimulador.TypeDelay;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.eps.PageEpsSelBancoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.eps.PageEpsSimuladorStpV;
import com.mng.testmaker.conf.Log4jConfig;

public class PagoEps extends PagoStpV {
    static Logger pLogger = LogManager.getLogger(Log4jConfig.log4jLogger);
    
    public PagoEps(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) {
        super(dCtxSh, dCtxPago, driver);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public void testPagoFromCheckout(boolean execPay) throws Exception {
    	//TODO mantener hasta que se elimine el actual TestAB para la aparición o no del pago EPS
    	//activateTestABforMethodEPS();
    	driver.navigate().refresh();
    	
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh, driver);
        PageCheckoutWrapperStpV.selectBancoEPS(dCtxSh, driver);
        PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(dCtxPago, dCtxSh.channel, driver);
        if (!UtilsMangoTest.isEntornoPRO(dCtxSh.appE, driver)) {
        	PageEpsSimuladorStpV.validateIsPage(driver);
        	PageEpsSimuladorStpV.selectDelay(TypeDelay.OneMinutes, driver);
        } else {
        	PageEpsSelBancoStpV.validateIsPage(dCtxPago.getDataPedido().getImporteTotal(), dCtxSh.pais.getCodigo_pais(), dCtxSh.channel, driver);
        }
        
        if (execPay) {
            this.dCtxPago.getDataPedido().setCodtipopago("F");
            PageEpsSimuladorStpV.clickContinueButton(driver);
        }
    }
}
