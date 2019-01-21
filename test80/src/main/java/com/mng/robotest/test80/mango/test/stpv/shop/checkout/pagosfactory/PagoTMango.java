package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.tmango.SecTMango;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.tmango.PageAmexInputCipStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.tmango.PageAmexInputTarjetaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.tmango.PageAmexResultStpV;

@SuppressWarnings("javadoc")
public class PagoTMango extends PagoStpV {

    public PagoTMango(DataCtxShop dCtxSh, DataCtxPago dataPago, DataFmwkTest dFTest) {
        super(dCtxSh, dataPago, dFTest);
        super.isAvailableExecPay = true;
    }
    
    @SuppressWarnings("static-access")
    @Override
    public datosStep testPagoFromCheckout(boolean execPay) throws Exception {
        DataPedido dataPedido = this.dCtxPago.getDataPedido();
        datosStep datosStep = PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(this.dCtxPago, this.dCtxSh, this.dFTest);
        PageCheckoutWrapperStpV.secTMango.validateIsSectionOk(this.dCtxSh.channel, datosStep, this.dFTest);
        PageCheckoutWrapperStpV.secTMango.clickTipoPago(SecTMango.TipoPago.pagoHabitual, this.dCtxSh.channel, this.dFTest);
        datosStep = PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(this.dCtxPago, this.dCtxSh.channel, this.dFTest);
        PageAmexInputTarjetaStpV.validateIsPageOk(dataPedido.getImporteTotal(), this.dCtxSh.pais.getCodigo_pais(), datosStep, this.dFTest);
        
        if (execPay) {
            dataPedido.setCodtipopago("M");
            PageAmexInputTarjetaStpV.inputTarjetaAndPayButton(dataPedido.getPago().getNumtarj(), dataPedido.getPago().getMescad(), dataPedido.getPago().getAnycad(), dataPedido.getPago().getCvc(), dataPedido.getImporteTotal(), this.dCtxSh.pais.getCodigo_pais(), this.dFTest);
            PageAmexInputCipStpV.inputCipAndAcceptButton(dataPedido.getPago().getCip(), dataPedido.getImporteTotal(), this.dCtxSh.pais.getCodigo_pais(), this.dFTest);
            datosStep = PageAmexResultStpV.clickContinuarButton(this.dFTest);
        }
        
        return datosStep;
    }
}
