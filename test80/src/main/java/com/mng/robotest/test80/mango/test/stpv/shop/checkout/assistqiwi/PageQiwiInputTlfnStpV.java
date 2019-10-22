package com.mng.robotest.test80.mango.test.stpv.shop.checkout.assistqiwi;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.assistqiwi.PageQiwiInputTlfn;

public class PageQiwiInputTlfnStpV {
                 
	@Validation (
		description="Aparece una página con el campo de introducción del Qiwi Mobile Phone",
		level=State.Warn)
    public static boolean validateIsPage(WebDriver driver) { 
        return (PageQiwiInputTlfn.isPresentInputPhone(driver));
    }
    
	@Step (
		description="Introducimos el Qiwi Mobile Phone #{tlfnQiwi} y pulsamos el botón \"Aceptar\"", 
        expected="Aparece la página de confirmación de Qiwi o la de resultado del pago de Mango")
	public static void inputTelefono(String tlfnQiwi, WebDriver driver) throws Exception {
        PageQiwiInputTlfn.inputQiwiPhone(driver, tlfnQiwi);
        checkIsVisibleAceptarButton(driver);
	}
	
	@Validation (
		description="Aparece el link de Aceptar")
	private static boolean checkIsVisibleAceptarButton(WebDriver driver) {
		return (PageQiwiInputTlfn.isVisibleLinkAceptar(1, driver));
	}	
	
	@Step (
		description="Seleccionar el link <b>Aceptar</b>",
		expected="Aparece la página de confirmación del pago-Qiwi")
    public static void clickConfirmarButton(WebDriver driver) throws Exception { 
    	PageQiwiInputTlfn.clickLinkAceptar(driver);
    }
}
