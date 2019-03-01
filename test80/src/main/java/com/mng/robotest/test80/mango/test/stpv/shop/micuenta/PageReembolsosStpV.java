package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageReembolsos;
import org.openqa.selenium.WebDriver;


public class PageReembolsosStpV {

    @Validation
    public static ListResultValidation validateIsPage (WebDriver driver) {
        ListResultValidation validations = ListResultValidation.getNew();
        validations.add(
                "Aparece la página de Reembolsos<br>",
                PageReembolsos.isPage(driver), State.Defect);
        validations.add(
                "Aparecen los inputs de BANCO, TITULAR e IBAN",
                (PageReembolsos.existsInputBanco(driver) && PageReembolsos.existsInputTitular(driver) && PageReembolsos.existsInputIBAN(driver)), State.Warn);
        return validations;
    }
}
