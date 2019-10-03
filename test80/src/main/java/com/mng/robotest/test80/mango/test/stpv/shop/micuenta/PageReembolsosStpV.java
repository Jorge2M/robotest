package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import com.mng.testmaker.annotations.validation.Validation;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.annotations.validation.ChecksResult;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageReembolsos;
import org.openqa.selenium.WebDriver;


public class PageReembolsosStpV {

    @Validation
    public static ChecksResult validateIsPage (WebDriver driver) {
        ChecksResult validations = ChecksResult.getNew();
        validations.add(
        	"Aparece la página de Reembolsos",
        	PageReembolsos.isPage(driver), State.Defect);
        validations.add(
        	"Aparecen los inputs de BANCO, TITULAR e IBAN",
            (PageReembolsos.existsInputBanco(driver) && PageReembolsos.existsInputTitular(driver) && PageReembolsos.existsInputIBAN(driver)), 
            State.Warn);
        return validations;
    }
}
