package com.mng.robotest.test80.mango.test.stpv.shop.checkout.koreancreditcard;

import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.conf.Log4jConfig;
import com.mng.testmaker.conf.State;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard.PageKoCardAdyen;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class PageKoCardAdyenStpV {
	
    static Logger pLogger = LogManager.getLogger(Log4jConfig.log4jLogger);
    
    @Validation
    public static ChecksResult validateIsPage(String importeTotal, Pais pais, Channel channel, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
    	if (channel==Channel.desktop) {
	      	validations.add(
	    		"En  la página resultante figura el importe total de la compra (" + importeTotal + ")",
	    		ImporteScreen.isPresentImporteInScreen(importeTotal, pais.getCodigo_pais(), driver), State.Warn);
    	}
      	validations.add(
    		"No se trata de la página de precompra (no aparece los logos de formas de pago",
    		!PageCheckoutWrapper.isPresentMetodosPago(channel, driver), State.Defect);
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
