package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.giropay.PageGiropay1rstStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.giropay.PageGiropayInputDataTestStpV;


public class PagoGiropay extends PagoStpV {
    
    public PagoGiropay(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest) {
        super(dCtxSh, dCtxPago, dFTest);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public DatosStep testPagoFromCheckout(boolean execPay) throws Exception {
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh, dFTest);
        PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(dCtxPago, dCtxSh.channel, dFTest);
        DataPedido dataPedido = dCtxPago.getDataPedido(); 
        String nombrePago = dataPedido.getPago().getNombre(dCtxSh.channel);
        PageGiropay1rstStpV.validateIsPage(nombrePago, dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais(), dCtxSh.channel, dFTest.driver);
        
        if (execPay) {
            dataPedido.setCodtipopago("F");
            Pago pago = dataPedido.getPago();
            String bankIdGiropay = pago.getBankidgiropay();
            PageGiropay1rstStpV.inputBankFluxSteps(bankIdGiropay, dCtxSh.channel, dFTest.driver);
            PageGiropay1rstStpV.clickButtonContinuePay(dCtxSh.channel, dFTest.driver);            
            PageGiropayInputDataTestStpV.inputDataAndClick(pago, dFTest.driver);
        }
        
        return TestCaseData.getDatosLastStep();
    }
}
