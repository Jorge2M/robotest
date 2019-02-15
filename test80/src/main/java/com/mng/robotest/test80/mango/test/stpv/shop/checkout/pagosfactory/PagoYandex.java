package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.yandex.PageYandex1rstStpv;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.yandex.PageYandexMoneyStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.yandex.PageYandexPayingByCodeStpV;


public class PagoYandex extends PagoStpV {
    
    public PagoYandex(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest) {
        super(dCtxSh, dCtxPago, dFTest);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public DatosStep testPagoFromCheckout(boolean execPay) throws Exception {
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(this.dCtxPago, this.dCtxSh, this.dFTest);
        DatosStep datosStep = PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(this.dCtxPago, this.dCtxSh.channel, this.dFTest);
        DataPedido dataPedido = this.dCtxPago.getDataPedido();
        PageYandex1rstStpv.validateIsPage(dataPedido.getEmailCheckout(), dataPedido.getImporteTotal(), this.dCtxSh.pais.getCodigo_pais(), datosStep, this.dFTest);
        if (execPay) {
            this.dCtxPago.getDataPedido().setCodtipopago("?");
            String telefono = "+7 900 000 00 00";
            String paymentCode = PageYandex1rstStpv.inputTlfnAndclickContinuar(telefono, dataPedido.getImporteTotal(), 
            																   this.dCtxSh.pais.getCodigo_pais(), this.dFTest);
            String windowHandlePageYandex1rst = this.dFTest.driver.getWindowHandle();

            if (PageYandex1rstStpv.hasFailed(dFTest.driver)){
                paymentCode = PageYandex1rstStpv.retry(dataPedido.getImporteTotal(), this.dCtxSh.pais.getCodigo_pais(), datosStep, this.dFTest);
            }

            String tabNameYandexMoney = "yandexMoney";
            PageYandexMoneyStpV.accessInNewTab(tabNameYandexMoney, this.dFTest);
            PageYandexMoneyStpV.inputDataAndPay(paymentCode, dataPedido.getImporteTotal(), this.dFTest);
            PageYandexMoneyStpV.closeTabByTitle(tabNameYandexMoney, windowHandlePageYandex1rst, this.dFTest);
            datosStep = PageYandexPayingByCodeStpV.clickBackToMango(this.dCtxSh.channel, this.dFTest);
        }
        
        return datosStep;
    }    
}
