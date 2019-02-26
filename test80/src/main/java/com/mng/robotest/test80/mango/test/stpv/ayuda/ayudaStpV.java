package com.mng.robotest.test80.mango.test.stpv.ayuda;

import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.mango.test.pageobject.ElementPageFunctions;
import com.mng.robotest.test80.mango.test.pageobject.ayuda.pageAyuda;
import org.json.simple.JSONArray;
import org.openqa.selenium.WebDriver;

import javax.xml.crypto.Data;

public class ayudaStpV {
    public static void selectTypeValidaciones (DataFmwkTest dFTest) throws Exception {
        //provisional
        for(int i = 0; i < 14; i++){
            switch (i) {
                case 1:
                    accesAndValidationSection("Preguntas Frecuentes", dFTest);
                    break;
                case 2:
                    accesAndValidationSection("Como comprar", dFTest);
                    break;
                case 3:
                    accesAndValidationSection("Formas de pago", dFTest);
                    break;
                case 4:
                    accesAndValidationSection("Envio", dFTest);
                    break;
                case 5:
                    accesAndValidationSection("Cambios y devoluciones", dFTest);
                    break;
                case 6:
                    accesAndValidationSection("Registro", dFTest);
                    break;
                case 7:
                    accesAndValidationSection("Tarjetas regalo", dFTest);
                    break;
                case 8:
                    accesAndValidationSection("Cuida tus prendas", dFTest);
                    break;
                case 9:
                    accesAndValidationSection("Contacto", dFTest);
                    break;
                case 10:
                    accesAndValidationSection("Buscar una tienda", dFTest);
                    break;
                case 11:
                    accesAndValidationSection("Guia de tallas", dFTest);
                    break;
                case 12:
                    accesAndValidationSection("Mango Card", dFTest);
                    break;
                case 13:
                    accesAndValidationSection("Contactanos", dFTest);
                    break;
            }
        }
    }

    @Step(
            description = "Seleccionamos la seccion de <b>#{section}</b>",
            expected = "Aparecen sus secciones internas")
    private static void accesAndValidationSection (String section, DataFmwkTest dFTest) throws Exception {
        JSONArray sectionToValidate = pageAyuda.getSectionFromJSON(section);
        pageAyuda.selectElement(pageAyuda.getXPath(sectionToValidate.get(0).toString()), dFTest.driver);
        if (sectionToValidate.size()<=1){
            validateSectionsAyuda(section, dFTest.driver);
        }
        for (Object textToCheck : sectionToValidate.subList(1, sectionToValidate.size())) {
            validateSectionsAyuda(textToCheck.toString(), dFTest.driver);
        }
    }

    //Validacion genérica
    @Validation(
            description="1) Está presente el apartado de <b>#{validation}</b>",
            level= State.Defect)
    private static boolean validateSectionsAyuda(String validation, WebDriver driver) {
        return (pageAyuda.isElementInStateUntil(pageAyuda.getXPath(validation), ElementPageFunctions.StateElem.Visible, 2, driver));
    }

}
