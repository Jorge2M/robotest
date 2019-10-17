package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pasarelaotras;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.utils.State;
import com.mng.testmaker.utils.conf.Log4jConfig;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.utils.otras.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;


public class PagePasarelaOtrasStpV {
    static Logger pLogger = LogManager.getLogger(Log4jConfig.log4jLogger);
    
    @Validation
    public static ChecksResult validateIsPage(String importeTotal, Pais pais, Channel channel, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
        if (channel==Channel.desktop) {
    	   	validations.add(
	    		"En la página resultante figura el importe total de la compra (" + importeTotal + ")",
	    		ImporteScreen.isPresentImporteInScreen(importeTotal, pais.getCodigo_pais(), driver), State.Warn);
        }
	   	validations.add(
    		"No se trata de la página de precompra (no aparece los logos de formas de pago)",
    		PageCheckoutWrapper.isPresentMetodosPago(channel, driver), State.Defect);
	   	
	   	return validations;
    }
}
