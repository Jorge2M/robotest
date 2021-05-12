package com.mng.robotest.test80.mango.test.stpv.shop.registro;

import java.util.Arrays;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.test80.mango.test.data.Constantes.ThreeState;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.PageRegistroSegunda;
import com.mng.robotest.test80.mango.test.stpv.shop.genericchecks.GenericChecks;
import com.mng.robotest.test80.mango.test.stpv.shop.genericchecks.GenericChecks.GenericCheck;


public class PageRegistroSegundaStpV {
    
	@Validation
    public static ChecksTM validaIsPageRegistroOK(Pais paisRegistro, AppEcom app, Map<String,String> dataRegistro, WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
        String lineasComaSeparated = "";
        int numLineas = 0;
        if (paisRegistro.getShoponline().stateLinea(LineaType.she, app)==ThreeState.TRUE) {
            lineasComaSeparated = LineaType.she.name();
            numLineas+=1;
        }
        
        if (paisRegistro.getShoponline().stateLinea(LineaType.he, app)==ThreeState.TRUE) {
            lineasComaSeparated+="," + LineaType.he.name();
            numLineas+=1;
        }
        
        if (paisRegistro.getShoponline().stateLinea(LineaType.nina, app)==ThreeState.TRUE ||
        	paisRegistro.getShoponline().stateLinea(LineaType.nino, app)==ThreeState.TRUE) {
            lineasComaSeparated+="," + LineaType.kids.name();
            numLineas+=1;
        }
        
        if (paisRegistro.getShoponline().stateLinea(LineaType.violeta, app)==ThreeState.TRUE) {
            lineasComaSeparated+="," + LineaType.violeta.name();
            numLineas+=1;
        }
        
        if (app==AppEcom.outlet) {
            lineasComaSeparated+=",outlet";
            numLineas+=1;
        }

        dataRegistro.put("numlineas", String.valueOf(numLineas));
        dataRegistro.put("lineascomaseparated", lineasComaSeparated);
        int maxSeconds = 5;
    	validations.add(
    		"Aparece la 2ª página de introducción de datos (la esperamos hasta " + maxSeconds + " segs)",
    		PageRegistroSegunda.isPageUntil(driver, maxSeconds), State.Warn);
    	validations.add(
    		"Se pueden seleccionar las colecciones " + lineasComaSeparated,
    		PageRegistroSegunda.isPresentInputForLineas(driver, lineasComaSeparated), State.Info, true);
    	
        int numColecciones = PageRegistroSegunda.getNumColecciones(driver);
    	validations.add(
    		"Aparece un número de colecciones coincidente con el número de líneas (" + numLineas + ")",
    		numColecciones==numLineas, State.Info, true);
    	
    	return validations;
    }
    
    /**
     * Introduce los datos en la página: una fecha de nacimiento + un número de niños + selección random de líneas 
     * @param fechaNacimiento fecha en formato "DD/MM/AAAA"
     */
	@Step (
		description="@rewritable",
		expected="Aparece la página de introducción de datos del niño o la de datos de la dirección (según se podían o no seleccionar niños)")
    public static void setDataAndLineasRandom(String fechaNacimiento, boolean paisConNinos, int numNinos, Pais pais, Map<String,String> dataRegistroOK, WebDriver driver) 
    throws Exception {
        String tagListaRandom = "@lineasRandom";
        String stepDescription = 
	        "Introducir datos adicionales y pulsar \"Continue\" si no existen niños: <br>" +
	        "  1) Introducir la fecha de nacimiento: <b>" + fechaNacimiento + "</b><br>" + 
	        "  2) Desmarcar una serie de líneas al azar: <b> " + tagListaRandom + "</b>";
        if (paisConNinos) {
	        stepDescription+=
	        "<br>" +
	        "  3) Seleccionar el número de niños: <b>" + numNinos + "</b>";
        }
        
        //Rewrite description step
        StepTM step = TestMaker.getCurrentStepInExecution();
        step.setDescripcion(stepDescription);

        PageRegistroSegunda.setFechaNacimiento(driver, fechaNacimiento);
        String lineasDesmarcadas = PageRegistroSegunda.desmarcarLineasRandom(driver, dataRegistroOK.get("lineascomaseparated"));
        step.setDescripcion(step.getDescripcion().replace(tagListaRandom, lineasDesmarcadas));
        dataRegistroOK.put("clicklineas", lineasDesmarcadas);
        if (paisConNinos) {
            PageRegistroSegunda.setNumeroNinos(numNinos, driver);
        } else {
            PageRegistroSegunda.clickButtonContinuar(driver);
        }                

        //Validaciones.
        if (paisConNinos) {
            PageRegistroNinosStpV.validaIsPageWithNinos(numNinos, driver);
        } else {
            PageRegistroDirecStpV.isPageFromPais(pais, driver);
        }
        
		GenericChecks.from(Arrays.asList(
				GenericCheck.SEO, 
				GenericCheck.Analitica)).checks(driver);
    }
}
