package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
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

    public PagoMercadopago(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest) {
        super(dCtxSh, dCtxPago, dFTest);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public DatosStep testPagoFromCheckout(boolean execPay) throws Exception {
        DataPedido dataPedido = this.dCtxPago.getDataPedido();
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh, dFTest);
        PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(dCtxPago, dCtxSh.channel, dFTest);
        int maxSecondsWait = 5;
        PageMercpago1rstStpV.validateIsPageUntil(maxSecondsWait, dFTest.driver);
        PageMercpago1rstStpV.clickLinkRegistration(dFTest.driver);
        if (execPay) {
            PageMercpagoLoginStpV.loginMercadopago(dataPedido.getPago(), dCtxSh.channel, dFTest.driver);
            PageMercpagoDatosTrjStpV.inputNumTarjeta(dataPedido.getPago().getNumtarj(), dCtxSh.channel, dFTest.driver);
            
            PageMercpagoDatosTrjStpV.InputData inputData = new PageMercpagoDatosTrjStpV.InputData();
            inputData.setBanco("Visa");
            if (!UtilsMangoTest.isEntornoPRO(dCtxSh.appE, dFTest)) {
                inputData.setBanco("Bancomer");
            }
            inputData.setMesVencimiento(dataPedido.getPago().getMescad());
            inputData.setAnyVencimiento(dataPedido.getPago().getAnycad());
            inputData.setCodigoSeguridad("123");
            PageMercpagoDatosTrjStpV.inputDataAndPay(inputData, dCtxSh.channel, dFTest.driver);
            PageMercpagoConfStpV.clickPagar(dCtxSh.channel, dFTest.driver);
            dataPedido.setCodtipopago("D");
        }

        return TestCaseData.getDatosStepForValidation();
    }
}
