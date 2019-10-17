package com.mng.robotest.test80.mango.test.stpv.ayuda;

import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.utils.otras.Channel;
import com.mng.robotest.test80.mango.test.pageobject.ayuda.PageAyuda;
import com.mng.robotest.test80.mango.test.pageobject.ayuda.PageAyuda.StateApartado;
import com.mng.testmaker.service.TestMaker;
import com.mng.testmaker.service.webdriver.wrapper.ElementPageFunctions;

import org.json.simple.JSONArray;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;

public class AyudaStpV {
	
    public static void selectTypeValidaciones (Channel channel) throws Exception {
        ArrayList<String> sections = PageAyuda.getKeysFromJSON(PageAyuda.getFileJSON());
        WebDriver driver = TestMaker.getDriverTestCase();
        for(String section : sections){
             if (section.equals("Buscar una tienda") && channel!=Channel.movil_web) {
                helpToBuscarTienda(section, driver);
             } else {
                 accesAndValidationSection(section, driver);
             }
        }
    }

    @Step(
    	description = "Seleccionamos la seccion de <b>#{section}</b>",
        expected = "Aparecen sus secciones internas")
    private static void accesAndValidationSection (String section, WebDriver driver) throws Exception {
        JSONArray sectionToValidate = PageAyuda.getSectionFromJSON(section);
        PageAyuda.selectElement(PageAyuda.getXPath(section), driver);
        for (Object textToCheck : sectionToValidate) {
            validateSectionsAyuda(textToCheck.toString(), driver);
            if (textToCheck.toString().equals("Tarjeta Regalo Mango")) {
                helpToChequeRegalo(textToCheck.toString(), driver);
            }
        }
    }

    @Validation(
    	description="Está presente el apartado de <b>#{validation}</b>",
        level= State.Defect)
    private static boolean validateSectionsAyuda(String validation, WebDriver driver) {
        return (PageAyuda.isElementInStateUntil(PageAyuda.getXPath(validation), ElementPageFunctions.StateElem.Visible, 2, driver));
    }

    @Step(
        description="Seleccionamos el enlace a la tarjeta regalo",
        expected="Aparece una nueva página que contiene la información de cheque regalo")
    private static void helpToChequeRegalo(String textToCheck, WebDriver driver) throws Exception {
        PageAyuda.selectElement(PageAyuda.getXPath(textToCheck), driver);
        checkIsApartadoInState(textToCheck, StateApartado.expanded, driver);	
        PageAyuda.selectElement(PageAyuda.getXPath("COMPRAR TARJETA REGALO"), driver);
        validatePageTarjetaRegalo(driver);
        driver.navigate().back();
    }

    @Validation(
    	description="Se despliega el bloque asociado a <b>#{textSection}</b>",
    	level=State.Warn)
    private static boolean checkIsApartadoInState(String textSection, StateApartado stateApartado, WebDriver driver) {
    	return (PageAyuda.isApartadoInStateUntil(textSection, stateApartado, 1, driver));
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