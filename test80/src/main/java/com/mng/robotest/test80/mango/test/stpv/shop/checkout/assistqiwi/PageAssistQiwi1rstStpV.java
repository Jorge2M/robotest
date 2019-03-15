package com.mng.robotest.test80.mango.test.stpv.shop.checkout.assistqiwi;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.assistqiwi.PageAssistQiwi1rst;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.assistqiwi.PageAssistQiwi1rst.pasarelasAssist;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class PageAssistQiwi1rstStpV {
    
	@Validation
    public static ChecksResult validateIsPage(String importeTotal, String codPais, Channel channel, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"Aparece el icono de Assist<br>",
			PageAssistQiwi1rst.isPresentIconoAssist(driver, channel), State.Warn);
	 	validations.add(
			"En la página resultante figura el importe total de la compra (" + importeTotal + ")<br>",
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), State.Warn);
	 	validations.add(
			"Aparece el icono de Qiwi",
			PageAssistQiwi1rst.isPresentIconPasarelas(driver, channel), State.Warn);
	 	return validations;         
    }
    
	@Step (
		description="Seleccionar la opción de Qiwi Kошелек", 
        expected="Aparece la página de introducción del número de teléfono")
    public static void clickIconPasarelaQiwi(Channel channel, WebDriver driver) throws Exception {
        PageAssistQiwi1rst.clickIconPasarela(driver, channel, pasarelasAssist.qiwikошелек);
        PageQiwiInputTlfnStpV.validateIsPage(driver);
    }
}
