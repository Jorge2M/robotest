package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.mercadopago.PageMercpago1rstStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.mercadopago.PageMercpagoConfStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.mercadopago.PageMercpagoDatosTrjStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.mercadopago.PageMercpagoLoginStpV;

public class PagoMercadopago extends PagoStpV {

    public PagoMercadopago(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) {
        super(dCtxSh, dCtxPago, driver);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public void testPagoFromCheckout(boolean execPay) throws Exception {
        DataPedido dataPedido = this.dCtxPago.getDataPedido();
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh, driver);
        PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(dCtxPago, dCtxSh.channel, driver);
        int maxSecondsWait = 5;
        PageMercpago1rstStpV.validateIsPageUntil(maxSecondsWait, driver);
        PageMercpago1rstStpV.clickLinkRegistration(driver);
        if (execPay) {
            PageMercpagoLoginStpV.loginMercadopago(dataPedido.getPago(), dCtxSh.channel, driver);
            PageMercpagoDatosTrjStpV.inputNumTarjeta(dataPedido.getPago().getNumtarj(), dCtxSh.channel, driver);
            
            PageMercpagoDatosTrjStpV.InputData inputData = new PageMercpagoDatosTrjStpV.InputData();
            inputData.setBanco("Visa");
            if (!UtilsMangoTest.isEntornoPRO(dCtxSh.appE, driver)) {
                inputData.setBanco("Bancomer");
            }
            inputData.setMesVencimiento(dataPedido.getPago().getMescad());
            inputData.setAnyVencimiento(dataPedido.getPago().getAnycad());
            inputData.setCodigoSeguridad("123");
            PageMercpagoDatosTrjStpV.inputDataAndPay(inputData, dCtxSh.channel, driver);
            PageMercpagoConfStpV.clickPagar(dCtxSh.channel, driver);
            dataPedido.setCodtipopago("D");
        }
    }
}
