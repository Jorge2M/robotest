package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.tmango.SecTMango;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.tmango.PageAmexInputCipStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.tmango.PageAmexInputTarjetaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.tmango.PageAmexResultStpV;


public class PagoTMango extends PagoStpV {

    public PagoTMango(DataCtxShop dCtxSh, DataCtxPago dataPago, DataFmwkTest dFTest) {
        super(dCtxSh, dataPago, dFTest);
        super.isAvailableExecPay = true;
    }
    
    @SuppressWarnings("static-access")
    @Override
    public DatosStep testPagoFromCheckout(boolean execPay) throws Exception {
        DataPedido dataPedido = this.dCtxPago.getDataPedido();
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh, dFTest);
        PageCheckoutWrapperStpV.secTMango.validateIsSectionOk(dCtxSh.channel, dFTest.driver);
        PageCheckoutWrapperStpV.secTMango.clickTipoPago(SecTMango.TipoPago.pagoHabitual, dCtxSh.channel, dFTest.driver);
        PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(dCtxPago, dCtxSh.channel, dFTest);
        PageAmexInputTarjetaStpV.validateIsPageOk(dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais(), dFTest.driver);
        
        if (execPay) {
            dataPedido.setCodtipopago("M");
            PageAmexInputTarjetaStpV.inputTarjetaAndPayButton(dataPedido.getPago().getNumtarj(), dataPedido.getPago().getMescad(), dataPedido.getPago().getAnycad(), dataPedido.getPago().getCvc(), dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais(), dFTest.driver);
            PageAmexInputCipStpV.inputCipAndAcceptButton(dataPedido.getPago().getCip(), dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais(), dFTest.driver);
            PageAmexResultStpV.clickContinuarButton(dFTest.driver);
        }
        
        return TestCaseData.getDatosLastStep();
    }
}
