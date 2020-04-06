package com.mng.robotest.test80.mango.test.stpv.ayuda;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.test80.mango.test.pageobject.ayuda.PageAyuda;
import com.mng.robotest.test80.mango.test.pageobject.ayuda.PageAyuda.StateApartado;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import org.json.simple.JSONArray;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class AyudaStpV {
	
	private final WebDriver driver;
	private final PageAyuda pageAyuda; 
	
	private AyudaStpV(WebDriver driver) {
		this.driver = driver;
		this.pageAyuda = new PageAyuda(driver);
	}
	public static AyudaStpV getNew(WebDriver driver) {
		return new AyudaStpV(driver);
	}
	
    public void selectTypeValidaciones (Channel channel) throws Exception {
        List<String> sections = pageAyuda.getKeysFromJSON();
        for(String section : sections){
             if (section.equals("Buscar una tienda") && channel!=Channel.movil_web) {
                helpToBuscarTienda(section);
             } else {
                 accesAndValidationSection(section);
             }
        }
    }

    @Step(
    	description = "Seleccionamos la seccion de <b>#{section}</b>",
        expected = "Aparecen sus secciones internas")
    private void accesAndValidationSection (String section) throws Exception {
        JSONArray sectionToValidate = pageAyuda.getSectionFromJSON(section);
        pageAyuda.selectSection(section);
        for (Object textToCheck : sectionToValidate) {
            validateSectionsAyuda(textToCheck.toString());
            if (textToCheck.toString().equals("Tarjeta Regalo Mango")) {
                helpToChequeRegalo(textToCheck.toString());
            }
        }
    }

    @Validation(
    	description="Está presente el apartado de <b>#{validation}</b>",
        level= State.Defect)
    private boolean validateSectionsAyuda(String validation) {
    	return pageAyuda.sectionInState(validation, Visible);
    }

    @Step(
        description="Seleccionamos el enlace a la tarjeta regalo",
        expected="Aparece una nueva página que contiene la información de cheque regalo")
    private void helpToChequeRegalo(String textToCheck) throws Exception {
        pageAyuda.selectSection(textToCheck);
        checkIsApartadoInState(textToCheck, StateApartado.expanded);	
        pageAyuda.selectSection("COMPRAR TARJETA REGALO");
        validatePageTarjetaRegalo();
        driver.navigate().back();
    }

    @Validation(
    	description="Se despliega el bloque asociado a <b>#{textSection}</b>",
    	level=State.Warn)
    private boolean checkIsApartadoInState(String textSection, StateApartado stateApartado) {
    	return (pageAyuda.isApartadoInStateUntil(textSection, stateApartado, 1));
    }
    
    @Validation(
        description = "1) Estamos en la página de <b>Tarjeta Regalo</b>",
        level = State.Defect)
    private boolean validatePageTarjetaRegalo() {
        return (PageAyuda.currentURLContains("giftVoucher", 5, driver));
    }

    @Step(
        description = "Seleccionamos el enlace de \"Buscar tu Tienda\"",
        expected = "Aparece el modal de busqueda de tiendas")
    private void helpToBuscarTienda(String textToCheck) throws Exception {
        pageAyuda.selectSection(textToCheck);
        validateBuscarTienda();
        pageAyuda.selectCloseBuscar();
    }

    @Validation(
        description = "1) Es visible la cabecera de <b>Encuentra tu tienda</b>",
        level = State.Defect)
    private boolean validateBuscarTienda() {
        return (pageAyuda.sectionInState("Encuentra tu tienda", Visible));
    }
}