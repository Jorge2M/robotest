package com.mng.robotest.test80.mango.test.stpv.shop.checkout.koreancreditcard;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Log4jConfig;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard.PageKoreanConfDesktop;

public class PageKoreanConfDesktopStpV {
    static Logger pLogger = LogManager.getLogger(Log4jConfig.log4jLogger);
    
    @Validation (
    	description="Aparece la página para la confirmación de la compra",
    	level=State.Defect)
    public static boolean validateIsPage(WebDriver driver) {    
        return (PageKoreanConfDesktop.isPage(driver));
    }
    
    @Step (
    	description="Seleccionar el botón para Confirmar", 
        expected="Aparece la página de Mango de resultado OK del pago")
    public static void clickConfirmarButton(WebDriver driver) {
    	PageKoreanConfDesktop.clickButtonSubmit(driver);
    }
}
