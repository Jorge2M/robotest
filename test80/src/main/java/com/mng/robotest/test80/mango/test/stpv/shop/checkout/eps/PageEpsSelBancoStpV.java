package com.mng.robotest.test80.mango.test.stpv.shop.checkout.eps;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.eps.PageEpsSelBanco;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class PageEpsSelBancoStpV {

	@Validation
    public static ListResultValidation validateIsPage(String importeTotal, String codPais, Channel channel, WebDriver driver) {
		ListResultValidation validations = ListResultValidation.getNew();
    	validations.add(
    		"Figura el icono correspondiente al pago <b>EPS</b><br>",
    		PageEpsSelBanco.isPresentIconoEps(driver), State.Warn);
    	
    	State stateVal = State.Warn;
        if (channel==Channel.movil_web) {
        	stateVal=State.Info;
        }
    	validations.add(
    		"Aparece el importe de la compra: " + importeTotal + "<br>",
    		ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), stateVal);
    	validations.add(
    		"Aparece el logo del banco seleccionado",
    		PageEpsSelBanco.isVisibleIconoBanco(driver), State.Warn);
    	return validations;
    }
}
    