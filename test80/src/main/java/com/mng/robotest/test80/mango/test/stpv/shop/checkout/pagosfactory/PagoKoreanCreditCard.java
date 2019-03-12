package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

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
    
    public PagoKoreanCreditCard(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) {
        super(dCtxSh, dCtxPago, driver);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public void testPagoFromCheckout(boolean execPay) throws Exception {
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh, driver);
        PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(dCtxPago, dCtxSh.channel, driver);
        PageKoCardAdyenStpV.validateIsPage(dCtxPago.getDataPedido().getImporteTotal(), dCtxSh.pais, dCtxSh.channel, driver);
    	PageKoCardAdyenStpV.clickIconForContinue(dCtxSh.channel, driver);
    	if (execPay) {
	        if (dCtxSh.channel == Channel.movil_web) {
	        	PageKoCardINIpay1MobilStpV2.checkTerminosBox(driver);
	        	PageKoCardINIpay1MobilStpV2.continuarConPagoCoreaMobile(driver);
	        	PageKoCardINIpay2MobilStpV.confirmMainPaymentCorea(driver);
	        	PageKoCardINIpay3MobilStpV.clickNextButton(driver);
	        	PageKoCardINIpay4MobilStpV.clickConfirmarButton(driver);
	        }
	        
	        if (dCtxSh.channel == Channel.desktop) {
	        	PageKoreanConfDesktopStpV.clickConfirmarButton(driver);
	        }
    	}
    }    
}
