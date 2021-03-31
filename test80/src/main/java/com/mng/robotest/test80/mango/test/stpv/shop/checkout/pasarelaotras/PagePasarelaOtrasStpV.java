package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pasarelaotras;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;


public class PagePasarelaOtrasStpV {
    
    @Validation
    public static ChecksTM validateIsPage(String importeTotal, Pais pais, Channel channel, AppEcom app, WebDriver driver) {
    	ChecksTM validations = ChecksTM.getNew();
        if (channel==Channel.desktop) {
    	   	validations.add(
	    		"En la página resultante figura el importe total de la compra (" + importeTotal + ")",
	    		ImporteScreen.isPresentImporteInScreen(importeTotal, pais.getCodigo_pais(), driver), State.Warn);
        }
	   	validations.add(
    		"No se trata de la página de precompra (no aparece los logos de formas de pago)",
    		new PageCheckoutWrapper(channel, app, driver).isPresentMetodosPago(), State.Defect);
	   	
	   	return validations;
    }
}
