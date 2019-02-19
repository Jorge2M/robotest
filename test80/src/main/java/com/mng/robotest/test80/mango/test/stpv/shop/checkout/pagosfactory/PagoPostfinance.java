package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.postfinance.PagePostfCodSegStpV;


public class PagoPostfinance extends PagoStpV {
    
    public PagoPostfinance(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest) {
        super(dCtxSh, dCtxPago, dFTest);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public DatosStep testPagoFromCheckout(boolean execPay) throws Exception {
        DataPedido dataPedido = dCtxPago.getDataPedido();
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh, dFTest);
        PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(this.dCtxPago, dCtxSh.channel, dFTest);
        String nombrePago = dataPedido.getPago().getNombre(this.dCtxSh.channel);
        String importeTotal = dataPedido.getImporteTotal();
        String codPais = this.dCtxSh.pais.getCodigo_pais();
        PagePostfCodSegStpV.postfinanceValidate1rstPage(nombrePago, importeTotal, codPais, dFTest.driver);
        
        if (execPay) {
            dataPedido.setCodtipopago("P");
            PagePostfCodSegStpV.inputCodigoSeguridadAndAccept("11152", nombrePago, dFTest.driver);
        }
        	
        return TestCaseData.getDatosStepForValidation();
    }    
}
