package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
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

public class PagoKoreanCreditCard extends PagoStpV {
    
    public PagoKoreanCreditCard(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest) {
        super(dCtxSh, dCtxPago, dFTest);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public DatosStep testPagoFromCheckout(boolean execPay) throws Exception {
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh, dFTest);
        PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(dCtxPago, dCtxSh.channel, dFTest);
        PageKoCardAdyenStpV.validateIsPage(dCtxPago.getDataPedido().getImporteTotal(), dCtxSh.pais, dCtxSh.channel, dFTest.driver);
    	PageKoCardAdyenStpV.clickIconForContinue(dCtxSh.channel, dFTest.driver);
    	if (execPay) {
	        if (dCtxSh.channel == Channel.movil_web) {
	        	PageKoCardINIpay1MobilStpV2.checkTerminosBox(dFTest.driver);
	        	PageKoCardINIpay1MobilStpV2.continuarConPagoCoreaMobile(dFTest.driver);
	        	PageKoCardINIpay2MobilStpV.confirmMainPaymentCorea(dFTest.driver);
	        	PageKoCardINIpay3MobilStpV.clickNextButton(dFTest.driver);
	        	PageKoCardINIpay4MobilStpV.clickConfirmarButton(dFTest.driver);
	        }
	        
	        if (dCtxSh.channel == Channel.desktop) {
	        	PageKoreanConfDesktopStpV.clickConfirmarButton(dFTest);
	        }
    	}

        return TestCaseData.getDatosLastStep();
    }    
}
