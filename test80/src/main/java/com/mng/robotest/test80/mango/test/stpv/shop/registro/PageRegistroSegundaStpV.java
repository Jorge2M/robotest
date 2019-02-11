package com.mng.robotest.test80.mango.test.stpv.shop.registro;

import java.util.HashMap;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.otras.Constantes.ThreeState;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.PageRegistroSegunda;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;

@SuppressWarnings("javadoc")
public class PageRegistroSegundaStpV {
    
    public static void validaIsPageRegistroOK(Pais paisRegistro, AppEcom app, HashMap<String,String> dataRegistro, DatosStep datosStep, DataFmwkTest dFTest) 
    throws Exception {
        //Validaciones
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
        String descripValidac =
            "1) Aparece la 2ª página de introducción de datos (la esperamos hasta " + maxSecondsWait + " segs)<br>" +                
            "2) Se pueden seleccionar las colecciones " + lineasComaSeparated + "<br>" +
            "3) Aparece un número de colecciones coincidente con el número de líneas (" + numLineas + ")";                          
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageRegistroSegunda.isPageUntil(dFTest.driver, maxSecondsWait)) {
                listVals.add(1, State.Warn);         
            }
            if (!PageRegistroSegunda.isPresentInputForLineas(dFTest.driver, lineasComaSeparated)) {
                listVals.add(2, State.Info_NoHardcopy);
            }
            int numColecciones = PageRegistroSegunda.getNumColecciones(dFTest.driver);
            if (numColecciones!=numLineas) {
                listVals.add(3, State.Info_NoHardcopy);
            }

            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    /**
     * Introduce los datos en la página: una fecha de nacimiento + un número de niños + selección random de líneas 
     * @param fechaNacimiento fecha en formato "DD/MM/AAAA"
     */
    public static DatosStep setDataAndLineasRandom(String fechaNacimiento, boolean paisConNinos, int numNinos, Pais pais, HashMap<String,String> dataRegistroOK, DataFmwkTest dFTest) 
    throws Exception {
        //Step.
        String tagListaRandom = "@lineasRandom";
        String stepAction = 
        "Introducir datos adicionales y pulsar \"Continue\" si no existen niños: <br>" +
        "  1) Introducir la fecha de nacimiento: <b>" + fechaNacimiento + "</b><br>" + 
        "  2) Desmarcar una serie de líneas al azar: <b> " + tagListaRandom + "</b>";
        
        if (paisConNinos)
        stepAction+=
        "<br>" +
        "  3) Seleccionar el número de niños: <b>" + numNinos + "</b>";
            
        DatosStep datosStep = new DatosStep (
            stepAction,
            "Aparece la página de introducción de datos del niño o la de datos de la dirección (según se podían o no seleccionar niños)");    
        try {
            //Introducimos la fecha de nacimiento
            PageRegistroSegunda.setFechaNacimiento(dFTest.driver, fechaNacimiento);
                    
            //Seleccionar (desmarcar) una serie de líneas al azar
            String lineasDesmarcadas = PageRegistroSegunda.desmarcarLineasRandom(dFTest.driver, dataRegistroOK.get("lineascomaseparated"));
            datosStep.setDescripcion(datosStep.getDescripcion().replace(tagListaRandom, lineasDesmarcadas));
            dataRegistroOK.put("clicklineas", lineasDesmarcadas);
            
            if (paisConNinos)
                //Seleccionamos el número de niños
                PageRegistroSegunda.setNumeroNinos(numNinos, dFTest.driver);
            else
                PageRegistroSegunda.clickButtonContinuar(dFTest.driver);
                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }                 

        //Validaciones.
        if (paisConNinos)
            PageRegistroNinosStpV.validaIsPageWithNinos(numNinos, datosStep, dFTest);
        else
            PageRegistroDirecStpV.isPageFromPais(pais, datosStep, dFTest);
        
        //Validaciones estándar. 
        AllPagesStpV.validacionesEstandar(true/*validaSEO*/, false/*validaJS*/, false/*validaImgBroken*/, datosStep, dFTest);
        
        return datosStep;
    }
}
