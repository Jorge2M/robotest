package com.mng.robotest.test80.mango.test.stpv.shop.registro;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.otras.Constantes.ThreeState;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageIniShopJapon;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.PageRegistroSegunda;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;

public class PageRegistroSegundaStpV {
    
	@Validation
    public static ListResultValidation validaIsPageRegistroOK(Pais paisRegistro, AppEcom app, HashMap<String,String> dataRegistro, WebDriver driver) 
    throws Exception {
		ListResultValidation validations = ListResultValidation.getNew();
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
    		"Aparece la 2ª página de introducción de datos (la esperamos hasta " + maxSecondsWait + " segs)<br>",
    		PageRegistroSegunda.isPageUntil(driver, maxSecondsWait), State.Warn);
    	validations.add(
    		"Se pueden seleccionar las colecciones " + lineasComaSeparated + "<br>",
    		PageRegistroSegunda.isPresentInputForLineas(driver, lineasComaSeparated), State.Info_NoHardcopy);
    	
        int numColecciones = PageRegistroSegunda.getNumColecciones(driver);
    	validations.add(
    		"Aparece un número de colecciones coincidente con el número de líneas (" + numLineas + ")",
    		numColecciones==numLineas, State.Info_NoHardcopy);
    	
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
        DatosStep datosStep = TestCaseData.peekDatosStepForStep();
        datosStep.setDescripcion(stepDescription);

        PageRegistroSegunda.setFechaNacimiento(dFTest.driver, fechaNacimiento);
        String lineasDesmarcadas = PageRegistroSegunda.desmarcarLineasRandom(dFTest.driver, dataRegistroOK.get("lineascomaseparated"));
        datosStep.setDescripcion(datosStep.getDescripcion().replace(tagListaRandom, lineasDesmarcadas));
        dataRegistroOK.put("clicklineas", lineasDesmarcadas);
        if (paisConNinos) {
            PageRegistroSegunda.setNumeroNinos(numNinos, dFTest.driver);
        }
        else {
            PageRegistroSegunda.clickButtonContinuar(dFTest.driver);
        }                

        //Validaciones.
        if (paisConNinos) {
            PageRegistroNinosStpV.validaIsPageWithNinos(numNinos, dFTest);
        }
        else {
            PageRegistroDirecStpV.isPageFromPais(pais, dFTest);
        }
        
        AllPagesStpV.validacionesEstandar(true/*validaSEO*/, false/*validaJS*/, false/*validaImgBroken*/, dFTest);
    }
}
