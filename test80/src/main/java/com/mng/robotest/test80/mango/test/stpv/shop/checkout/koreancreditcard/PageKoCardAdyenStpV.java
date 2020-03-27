package com.mng.robotest.test80.mango.test.stpv.shop.checkout.koreancreditcard;

import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.conf.Log4jConfig;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard.PageKoCardAdyen;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class PageKoCardAdyenStpV {
	
    static Logger pLogger = LogManager.getLogger(Log4jConfig.log4jLogger);
    
    private final WebDriver driver;
    private final Channel channel;
    private final PageKoCardAdyen pageKoCardAdyen;
    
    public PageKoCardAdyenStpV(Channel channel, WebDriver driver) {
    	this.driver = driver;
    	this.channel = channel;
    	this.pageKoCardAdyen = new PageKoCardAdyen(driver);
    }
    
    @Validation
    public ChecksTM validateIsPage(String importeTotal, Pais pais) {
    	ChecksTM validations = ChecksTM.getNew();
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
    		pageKoCardAdyen.isPage(), State.Defect);
      	return validations;
    }

    @Step (
    	description="Seleccionar el icono de Korean Credit Card para continuar",
        expected="Aparece la páinga de INIpay")
    public void clickIconForContinue () throws Exception {
        pageKoCardAdyen.clickForContinue(channel);
        
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
