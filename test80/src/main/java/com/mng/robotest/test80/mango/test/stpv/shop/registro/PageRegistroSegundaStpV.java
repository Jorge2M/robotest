package com.mng.robotest.test80.mango.test.stpv.shop.registro;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.utils.DataFmwkTest;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.utils.TestCaseData;
import com.mng.testmaker.annotations.step.Step;
import com.mng.testmaker.annotations.validation.ChecksResult;
import com.mng.testmaker.annotations.validation.Validation;
import com.mng.testmaker.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.Constantes.ThreeState;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.PageRegistroSegunda;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.StdValidationFlags;

public class PageRegistroSegundaStpV {
    
	@Validation
    public static ChecksResult validaIsPageRegistroOK(Pais paisRegistro, AppEcom app, HashMap<String,String> dataRegistro, WebDriver driver) 
    throws Exception {
		ChecksResult validations = ChecksResult.getNew();
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
        int maxSecondsWait = 5;
    	validations.add(
    		"Aparece la 2ª página de introducción de datos (la esperamos hasta " + maxSecondsWait + " segs)",
    		PageRegistroSegunda.isPageUntil(driver, maxSecondsWait), State.Warn);
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
    public static void setDataAndLineasRandom(String fechaNacimiento, boolean paisConNinos, int numNinos, Pais pais, HashMap<String,String> dataRegistroOK, DataFmwkTest dFTest) 
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
        DatosStep datosStep = TestCaseData.getDatosCurrentStep();
        datosStep.setDescripcion(stepDescription);

        PageRegistroSegunda.setFechaNacimiento(dFTest.driver, fechaNacimiento);
        String lineasDesmarcadas = PageRegistroSegunda.desmarcarLineasRandom(dFTest.driver, dataRegistroOK.get("lineascomaseparated"));
        datosStep.setDescripcion(datosStep.getDescripcion().replace(tagListaRandom, lineasDesmarcadas));
        dataRegistroOK.put("clicklineas", lineasDesmarcadas);
        if (paisConNinos) {
            PageRegistroSegunda.setNumeroNinos(numNinos, dFTest.driver);
        } else {
            PageRegistroSegunda.clickButtonContinuar(dFTest.driver);
        }                

        //Validaciones.
        if (paisConNinos) {
            PageRegistroNinosStpV.validaIsPageWithNinos(numNinos, dFTest);
        } else {
            PageRegistroDirecStpV.isPageFromPais(pais, dFTest);
        }
        
        StdValidationFlags flagsVal = StdValidationFlags.newOne();
        flagsVal.validaSEO = true;
        flagsVal.validaJS = false;
        flagsVal.validaImgBroken = false;
        AllPagesStpV.validacionesEstandar(flagsVal, dFTest.driver);
    }
}
