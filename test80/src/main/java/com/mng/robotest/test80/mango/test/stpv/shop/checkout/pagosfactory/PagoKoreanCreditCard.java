package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.koreancreditcard.PageKoreanConfDesktopStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.koreancreditcard.PageKoCardAdyenStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.koreancreditcard.PageKoCardINIpay1MobilStpV2;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.koreancreditcard.PageKoCardINIpay2MobilStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.koreancreditcard.PageKoCardINIpay3MobilStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.koreancreditcard.PageKoCardINIpay4MobilStpV;

@SuppressWarnings("javadoc")
public class PagoKoreanCreditCard extends PagoStpV {
    
    public PagoKoreanCreditCard(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest) {
        super(dCtxSh, dCtxPago, dFTest);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public datosStep testPagoFromCheckout(boolean execPay) throws Exception {
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh, dFTest);
        datosStep datosStep = PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(dCtxPago, dCtxSh.channel, dFTest);
        PageKoCardAdyenStpV.validateIsPage(dCtxPago.getDataPedido().getImporteTotal(), dCtxSh.pais, dCtxSh.channel, datosStep, dFTest);
    	datosStep = PageKoCardAdyenStpV.clickIconForContinue(dCtxSh.channel, dFTest);
    	if (execPay) {
	        if (dCtxSh.channel == Channel.movil_web) {
	        	PageKoCardINIpay1MobilStpV2.checkTerminosBox(dFTest);
	        	PageKoCardINIpay1MobilStpV2.continuarConPagoCoreaMobile(dFTest);
	        	PageKoCardINIpay2MobilStpV.confirmMainPaymentCorea(dFTest);
	        	PageKoCardINIpay3MobilStpV.clickNextButton(dFTest);
	        	datosStep = PageKoCardINIpay4MobilStpV.clickConfirmarButton(dFTest);
	        }
	        
	        if (dCtxSh.channel == Channel.desktop) {
	        	datosStep = PageKoreanConfDesktopStpV.clickConfirmarButton(dFTest);
	        }
    	}
        return datosStep;
    }    
}
