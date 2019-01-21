package com.mng.robotest.test80.mango.test.stpv.shop.registro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageRegistroAddressData;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.ListDataRegistro;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.PageRegistroDirec;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.ListDataRegistro.PageData;

@SuppressWarnings("javadoc")
public class PageRegistroDirecStpV {
    
    public static void isPageFromPais(Pais pais, datosStep datosStep, DataFmwkTest dFTest) {
        //Validaciones.
        String descripValidac = 
            "1) Aparece la página de introducción de datos de la dirección (la esperamos un máximo de 3 segundos)<br>" +
            "2) Si existe el desplebagle de países, en él aparece el país con código " + pais.getCodigo_pais() + " (" + pais.getNombre_pais() + ")";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageRegistroAddressData.isPageUntil(dFTest.driver, 3/*secondsMaxToWait*/))
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            if (PageRegistroAddressData.existsDesplegablePaises(dFTest.driver)) {
                if (!PageRegistroAddressData.isOptionPaisSelected(dFTest.driver, pais.getCodigo_pais()))
                    fmwkTest.addValidation(2, State.Warn, listVals);
            }
    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static datosStep sendDataAccordingCountryToInputs(HashMap<String,String> dataRegistro, Pais pais, DataFmwkTest dFTest) throws Exception {
        //Step. Introducir datos en el registro según el país
        datosStep datosStep = new datosStep       (
            "Introducir los datos correctos para el país " + pais.getNombre_pais(), 
            "No aparece ningún mensaje de error");
        try {
            PageRegistroDirec.sendDataAccordingCountryToInputs(dataRegistro, pais, dFTest.driver);
    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validaciones
        validateInputDataOk(datosStep, dFTest);
        
        return datosStep;
    }
    
    public static datosStep sendDataOkToInputs(ListDataRegistro dataToSend, DataFmwkTest dFTest) throws Exception {
        //Step. Introducir datos en el registro
        datosStep datosStep = new datosStep       (
            "Introducir los datos correctos:<br>" + dataToSend.getFormattedHTMLData(PageData.pageDireccion), 
            "No aparece ningún mensaje de error");
        try {
            PageRegistroDirec.sendDataToInputs(dataToSend, dFTest.driver, 3/*repeat*/);
    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }           
            
        //Validaciones
        validateInputDataOk(datosStep, dFTest);
        
        return datosStep;
    }
    
    private static void validateInputDataOk(datosStep datosStep, DataFmwkTest dFTest) {
        //Validaciones
        String descripValidac = 
            "1) No aparece ningún mensaje de error asociado a los campos de entrada"; 
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (PageRegistroDirec.getNumberMsgInputInvalid(dFTest.driver) > 0)
                fmwkTest.addValidation(1, State.Defect, listVals);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static datosStep clickFinalizarButton(DataFmwkTest dFTest) 
    throws Exception {
        //Step. 
        datosStep datosStep = new datosStep       (
            "Seleccionar el botón \"<b>Finalizar</b>\"", 
            "Aparece la página final del proceso de registro");
        try {
            PageRegistroDirec.clickFinalizarButton(dFTest.driver);
                                                                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        //Validaciones 
        PageRegistroFinStpV.isPage(datosStep, dFTest);
        
        return datosStep;
    }
}
