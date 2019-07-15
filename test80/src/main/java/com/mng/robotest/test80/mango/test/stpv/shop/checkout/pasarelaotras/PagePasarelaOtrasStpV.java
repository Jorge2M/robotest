package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pasarelaotras;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;


public class PagePasarelaOtrasStpV {
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
    
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
