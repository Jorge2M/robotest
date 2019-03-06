package com.mng.robotest.test80.mango.test.stpv.shop.checkout.koreancreditcard;

import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard.PageKoCardAdyen;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class PageKoCardAdyenStpV {
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
    
    @Validation
    public static ListResultValidation validateIsPage(String importeTotal, Pais pais, Channel channel, WebDriver driver) {
    	ListResultValidation validations = ListResultValidation.getNew();
    	if (channel==Channel.desktop) {
	      	validations.add(
	    		"En la página resultante figura el importe total de la compra (" + importeTotal + ")<br>",
	    		ImporteScreen.isPresentImporteInScreen(importeTotal, pais.getCodigo_pais(), driver), State.Warn);
    	}
      	validations.add(
    		"No se trata de la página de precompra (no aparece los logos de formas de pago <br>",
    		!PageCheckoutWrapper.isPresentMetodosPago(pais, channel, driver), State.Defect);
      	validations.add(
    		"Aparece la página de Adyen / Korean Kredit Cards",
    		PageKoCardAdyen.isPage(driver), State.Defect);
      	return validations;
    }

    @Step (
    	description="Seleccionar el icono de Korean Credit Card para continuar",
        expected="Aparece la páinga de INIpay")
    public static void clickIconForContinue (Channel channel, WebDriver driver) throws Exception {
        PageKoCardAdyen.clickForContinue(channel, driver);
        
        //Validations
        switch (channel) {
        case movil_web:
        	PageKoCardINIpay1MobilStpV2.validateIsPage(driver);
        	break;
        case desktop:
        	PageKoreanConfDesktopStpV.validateIsPage(driver);
        	break;
        }
    }
}