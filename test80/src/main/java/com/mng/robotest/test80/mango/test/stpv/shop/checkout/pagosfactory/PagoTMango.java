package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

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

    public PagoTMango(DataCtxShop dCtxSh, DataCtxPago dataPago, WebDriver driver) {
        super(dCtxSh, dataPago, driver);
        super.isAvailableExecPay = true;
    }
    
    @SuppressWarnings("static-access")
    @Override
    public void testPagoFromCheckout(boolean execPay) throws Exception {
        DataPedido dataPedido = this.dCtxPago.getDataPedido();
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh, driver);
        PageCheckoutWrapperStpV.secTMango.validateIsSectionOk(dCtxSh.channel, driver);
        PageCheckoutWrapperStpV.secTMango.clickTipoPago(SecTMango.TipoPago.pagoHabitual, dCtxSh.channel, driver);
        PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(dCtxPago, dCtxSh.channel, driver);
        PageAmexInputTarjetaStpV.validateIsPageOk(dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais(), driver);
        
        if (execPay) {
            dataPedido.setCodtipopago("M");
            PageAmexInputTarjetaStpV.inputTarjetaAndPayButton(dataPedido.getPago().getNumtarj(), dataPedido.getPago().getMescad(), dataPedido.getPago().getAnycad(), dataPedido.getPago().getCvc(), dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais(), driver);
            PageAmexInputCipStpV.inputCipAndAcceptButton(dataPedido.getPago().getCip(), dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais(), driver);
            PageAmexResultStpV.clickContinuarButton(driver);
        }
    }
}
