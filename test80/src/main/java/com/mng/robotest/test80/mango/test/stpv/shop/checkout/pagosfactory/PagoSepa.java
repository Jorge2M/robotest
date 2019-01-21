package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.sepa.PageSepa1rstStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.sepa.PageSepaResultMobilStpV;

@SuppressWarnings("javadoc")
public class PagoSepa extends PagoStpV {
    
    public PagoSepa(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest) {
        super(dCtxSh, dCtxPago, dFTest);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public datosStep testPagoFromCheckout(boolean execPay) throws Exception {
        Pago pago = this.dCtxPago.getDataPedido().getPago();
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(this.dCtxPago, this.dCtxSh, this.dFTest);
        datosStep datosStep = PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(this.dCtxPago, this.dCtxSh.channel, this.dFTest);
        String importeTotal = this.dCtxPago.getDataPedido().getImporteTotal();
        PageSepa1rstStpV.validateIsPage(pago.getNombre(this.dCtxSh.channel), importeTotal, this.dCtxSh.pais.getCodigo_pais(), this.dCtxSh.channel, datosStep, this.dFTest);
        
        if (execPay) {
            datosStep = PageSepa1rstStpV.inputDataAndclickPay(pago.getNumtarj(), pago.getTitular(), importeTotal, this.dCtxSh.pais.getCodigo_pais(), this.dCtxSh.channel, this.dFTest);
            if (this.dCtxSh.channel==Channel.movil_web)
                datosStep = PageSepaResultMobilStpV.clickButtonPagar(this.dFTest);
        }
        
        return datosStep;
    }
}
