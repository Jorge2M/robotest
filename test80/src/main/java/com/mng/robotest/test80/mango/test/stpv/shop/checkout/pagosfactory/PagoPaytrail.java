package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.paytrail.PagePaytrail1rstStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.paytrail.PagePaytrailEpaymentStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.paytrail.PagePaytrailIdConfirmStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.paytrail.PagePaytrailResultadoOkStpV;

public class PagoPaytrail extends PagoStpV {
    
    public PagoPaytrail(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) throws Exception {
        super(dCtxSh, dCtxPago, driver);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public void testPagoFromCheckout(boolean execPay) throws Exception {
        pageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
        dCtxPago = checkoutFlow.checkout(From.MetodosPago);
        PagePaytrail1rstStpV.validateIsPage(dCtxPago.getDataPedido().getImporteTotal(), dCtxSh.pais.getCodigo_pais(), dCtxSh.channel, driver);
        PagePaytrail1rstStpV.selectBancoAndContinue(dCtxSh.channel, driver);
        
        if (execPay) {
            this.dCtxPago.getDataPedido().setCodtipopago("F");
            String codigoPais = this.dCtxPago.getDataPedido().getCodigoPais();
            new PagePaytrailEpaymentStpV(driver).clickCodeCardOK(dCtxPago.getDataPedido().getImporteTotal(), codigoPais);
            PagePaytrailIdConfirmStpV.inputIDAndClickConfirmar("V2360A71", dCtxPago.getDataPedido().getImporteTotal(), codigoPais, driver);
            PagePaytrailResultadoOkStpV.clickVolverAMangoButton(driver);
        }
    }
}
