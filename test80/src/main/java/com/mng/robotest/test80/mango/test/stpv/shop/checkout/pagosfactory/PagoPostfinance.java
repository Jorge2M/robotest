package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.postfinance.PagePostfCodSegStpV;

public class PagoPostfinance extends PagoStpV {
    
    public PagoPostfinance(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) {
        super(dCtxSh, dCtxPago, driver);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public void testPagoFromCheckout(boolean execPay) throws Exception {
        DataPedido dataPedido = dCtxPago.getDataPedido();
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh, driver);
        PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(this.dCtxPago, dCtxSh.channel, driver);
        String nombrePago = dataPedido.getPago().getNombre(this.dCtxSh.channel);
        String importeTotal = dataPedido.getImporteTotal();
        String codPais = this.dCtxSh.pais.getCodigo_pais();
        PagePostfCodSegStpV.postfinanceValidate1rstPage(nombrePago, importeTotal, codPais, driver);
        
        if (execPay) {
            dataPedido.setCodtipopago("P");
            PagePostfCodSegStpV.inputCodigoSeguridadAndAccept("11152", nombrePago, driver);
        }
    }    
}
