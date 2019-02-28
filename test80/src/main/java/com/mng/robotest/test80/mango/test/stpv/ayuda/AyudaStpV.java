package com.mng.robotest.test80.mango.test.stpv.ayuda;

import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.ElementPageFunctions;
import com.mng.robotest.test80.mango.test.pageobject.ayuda.PageAyuda;
import org.json.simple.JSONArray;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;

public class AyudaStpV {
    public static void selectTypeValidaciones (Channel channel, DataFmwkTest dFTest) throws Exception {
        ArrayList<String> sections = PageAyuda.getKeysFromJSON(PageAyuda.getFileJSON());
        for(String section : sections){
             if (section.equals("Buscar una tienda") && channel!=Channel.movil_web){
                helpToBuscarTienda(section, dFTest.driver);
             } else {
                 accesAndValidationSection(section, dFTest);
             }
        }
    }

    @Step(
            description = "Seleccionamos la seccion de <b>#{section}</b>",
            expected = "Aparecen sus secciones internas")
    private static void accesAndValidationSection (String section, DataFmwkTest dFTest) throws Exception {
        JSONArray sectionToValidate = PageAyuda.getSectionFromJSON(section);
        PageAyuda.selectElement(PageAyuda.getXPath(section), dFTest.driver);
        for (Object textToCheck : sectionToValidate) {
            validateSectionsAyuda(textToCheck.toString(), dFTest.driver);
            if (textToCheck.toString().equals("Tarjeta Regalo Mango")) {
                helpToChequeRegalo(textToCheck.toString(), dFTest.driver);
            }
        }
    }

    @Validation(
            description="1) Está presente el apartado de <b>#{validation}</b>",
            level= State.Defect)
    private static boolean validateSectionsAyuda(String validation, WebDriver driver) {
        return (PageAyuda.isElementInStateUntil(PageAyuda.getXPath(validation), ElementPageFunctions.StateElem.Visible, 2, driver));
    }

    @Step(
            description = "Seleccionamos el enlace a la tarjeta regalo",
            expected = "Aparece una nueva página que contiene la información de cheque regalo")
    private static void helpToChequeRegalo(String textToCheck, WebDriver driver) throws Exception {
        PageAyuda.selectElement(PageAyuda.getXPath(textToCheck), driver);
        PageAyuda.selectElement(PageAyuda.getXPath("COMPRAR TARJETA REGALO"), driver);
        validatePageTarjetaRegalo(driver);
        driver.navigate().back();
    }

    @Validation(
            description = "1) Estamos en la página de <b>Tarjeta Regalo</b>",
            level = State.Defect)
    private static boolean validatePageTarjetaRegalo(WebDriver driver) {
        return (PageAyuda.currentURLContains("giftVoucher", 5, driver));
    }

    @Step(
            description = "Seleccionamos el enlace de \"Buscar tu Tienda\"",
            expected = "Aparece el modal de busqueda de tiendas")
    private static void helpToBuscarTienda(String textToCheck, WebDriver driver) throws Exception {
        PageAyuda.selectElement(PageAyuda.getXPath(textToCheck), driver);
        validateBuscarTienda(driver);
        PageAyuda.selectElement(PageAyuda.xPathCloseBuscar, driver);
    }

    @Validation(
            description = "1) Es visible la cabecera de <b>Encuentra tu tienda</b>",
            level = State.Defect)
    private static boolean validateBuscarTienda(WebDriver driver) {
        return (PageAyuda.isElementInStateUntil(PageAyuda.getXPath("Encuentra tu tienda"), ElementPageFunctions.StateElem.Visible, 4, driver));
    }
}