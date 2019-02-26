package com.mng.robotest.test80.mango.test.stpv.ayuda;

import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.mango.test.pageobject.ElementPageFunctions;
import com.mng.robotest.test80.mango.test.pageobject.ayuda.PageAyuda;
import org.json.simple.JSONArray;
import org.openqa.selenium.WebDriver;

public class AyudaStpV {
    public static void selectTypeValidaciones (DataFmwkTest dFTest) throws Exception {
        //provisional
        JSONArray sections = PageAyuda.getSectionFromJSON("Apartados");
        for(Object section : sections){
            accesAndValidationSection(section.toString(), dFTest);
        }
    }

    @Step(
            description = "Seleccionamos la seccion de <b>#{section}</b>",
            expected = "Aparecen sus secciones internas")
    private static void accesAndValidationSection (String section, DataFmwkTest dFTest) throws Exception {
        JSONArray sectionToValidate = PageAyuda.getSectionFromJSON(section);
        PageAyuda.selectElement(PageAyuda.getXPath(sectionToValidate.get(0).toString()), dFTest.driver);
        for (Object textToCheck : sectionToValidate) {
            validateSectionsAyuda(textToCheck.toString(), dFTest.driver);
        }
    }

    //Validacion genérica
    @Validation(
            description="1) Está presente el apartado de <b>#{validation}</b>",
            level= State.Defect)
    private static boolean validateSectionsAyuda(String validation, WebDriver driver) {
        return (PageAyuda.isElementInStateUntil(PageAyuda.getXPath(validation), ElementPageFunctions.StateElem.Visible, 2, driver));
    }

}
