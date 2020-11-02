package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.kcp.PageKcpMain;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;

public class PagoKCP extends PagoStpV {
    
    public PagoKCP(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) {
        super(dCtxSh, dCtxPago, driver);
        super.isAvailableExecPay = true;
    }
    
    @Override
    public void testPagoFromCheckout(boolean execPay) throws Exception {
        PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh, driver);
        PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(dCtxPago, dCtxSh.channel, driver);
        
        PageKcpMain pageKcpMain = new PageKcpMain(driver);
        pageKcpMain.isPage(30);
        pageKcpMain.acceptTermsAndConditions();
        pageKcpMain.selectHyundai();
        pageKcpMain.selectInstallement("12Months");
        pageKcpMain.isSelectInstallmentVisible(5);
        pageKcpMain.clickNext();
        
        pageKcpMain.getModalHundayCard1().isPage(10); 
        pageKcpMain.getModalHundayCard1().clickPagoGeneral(); 
        
        pageKcpMain.getModalHundayCard2().isPage(5);
        pageKcpMain.getModalHundayCard2().inputCardNumber("9490220006611406");
        pageKcpMain.getModalHundayCard2().accept();
        
        pageKcpMain.getModalHundayCard3().isPage(5);
        pageKcpMain.getModalHundayCard3().inputPassword("kcptest10$$");
        pageKcpMain.getModalHundayCard3().inputCVC("637");
        pageKcpMain.getModalHundayCard3().accept();
        
        pageKcpMain.getModalHundayCard4().isPage(5);
        pageKcpMain.getModalHundayCard4().submitPayment();
        
        
//        PageKoCardAdyenStpV pageKoCardAdyenStpV = new PageKoCardAdyenStpV(dCtxSh.channel, driver);
//        pageKoCardAdyenStpV.validateIsPage(dCtxPago.getDataPedido().getImporteTotal(), dCtxSh.pais);
//        pageKoCardAdyenStpV.clickIconForContinue();
//    	if (execPay) {
//	        if (dCtxSh.channel == Channel.mobile) {
//	        	PageKoCardINIpay1MobilStpV2.checkTerminosBox(driver);
//	        	PageKoCardINIpay1MobilStpV2.continuarConPagoCoreaMobile(driver);
//	        	PageKoCardINIpay2MobilStpV.confirmMainPaymentCorea(driver);
//	        	PageKoCardINIpay3MobilStpV.clickNextButton(driver);
//	        	PageKoCardINIpay4MobilStpV.clickConfirmarButton(driver);
//	        }
//	        
//	        if (dCtxSh.channel == Channel.desktop) {
//	        	PageKoreanConfDesktopStpV.clickConfirmarButton(driver);
//	        }
//    	}
    }    
}
