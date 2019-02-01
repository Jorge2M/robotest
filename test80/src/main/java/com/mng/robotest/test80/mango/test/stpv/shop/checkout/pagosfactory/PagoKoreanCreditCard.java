package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.koreancreditcard.PageKoreanConfDesktopStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.koreancreditcard.PageKoCardAdyenMobilStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.koreancreditcard.PageKoCardINIpay1MobilStpV2;

@SuppressWarnings("javadoc")
public class PagoKoreanCreditCard extends PagoStpV {
    
    public PagoKoreanCreditCard(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest) {
        super(dCtxSh, dCtxPago, dFTest);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public datosStep testPagoFromCheckout(boolean execPay) throws Exception {
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(this.dCtxPago, this.dCtxSh, this.dFTest);
        datosStep datosStep = PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(this.dCtxPago, this.dCtxSh.channel, this.dFTest);
        PageKoCardAdyenMobilStpV.validateIsPage(this.dCtxPago.getDataPedido().getImporteTotal(), this.dCtxSh.pais, this.dCtxSh.channel, datosStep, this.dFTest);
        if (dCtxSh.channel == Channel.movil_web){
        	PageKoCardAdyenMobilStpV.clickIconForContinue(dFTest);
        	PageKoCardINIpay1MobilStpV2.preparePaymentMobile(dFTest);
        	PageKoCardINIpay1MobilStpV2.continuarConPagoCoreaMobile(dFTest);
        	PageKoCardINIpay1MobilStpV2.confirmMainPaymentCorea(dFTest);
        	datosStep = PageKoCardINIpay1MobilStpV2.endPayment(dFTest);
        }
        if (execPay && dCtxSh.channel == Channel.desktop) {
        	PageKoCardINIpay1MobilStpV2.clickConfirmarButton(dFTest);
        	datosStep = PageKoreanConfDesktopStpV.clickConfirmarButton(dFTest);
        }
        return datosStep;
    }    
}
