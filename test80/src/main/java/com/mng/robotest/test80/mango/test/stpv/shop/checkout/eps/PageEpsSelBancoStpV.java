package com.mng.robotest.test80.mango.test.stpv.shop.checkout.eps;

import org.openqa.selenium.WebDriver;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.utils.otras.Channel;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.eps.PageEpsSelBanco;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class PageEpsSelBancoStpV {

	@Validation
    public static ChecksResult validateIsPage(String importeTotal, String codPais, Channel channel, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
    	validations.add(
    		"Figura el icono correspondiente al pago <b>EPS</b>",
    		PageEpsSelBanco.isPresentIconoEps(driver), State.Warn);
    	
    	State stateVal = State.Warn;
        if (channel==Channel.movil_web) {
        	stateVal=State.Info;
        }
    	validations.add(
    		"Aparece el importe de la compra: " + importeTotal,
    		ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), stateVal);
    	validations.add(
    		"Aparece el logo del banco seleccionado",
    		PageEpsSelBanco.isVisibleIconoBanco(driver), State.Warn);
    	return validations;
    }
}
    