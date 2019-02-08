package com.mng.robotest.test80.mango.test.stpv.shop.registro;

import java.util.HashMap;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageRegistroAddressData;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.ListDataRegistro;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.PageRegistroDirec;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.ListDataRegistro.PageData;

@SuppressWarnings("javadoc")
public class PageRegistroDirecStpV {
    
    public static void isPageFromPais(Pais pais, DatosStep datosStep, DataFmwkTest dFTest) {
        //Validaciones.
    	int maxSecondsWait = 3;
        String descripValidac = 
            "1) Aparece la página de introducción de datos de la dirección (la esperamos un máximo de " + maxSecondsWait + " segundos)<br>" +
            "2) Si existe el desplebagle de países, en él aparece el país con código " + pais.getCodigo_pais() + " (" + pais.getNombre_pais() + ")";
        datosStep.setStateIniValidations();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageRegistroAddressData.isPageUntil(dFTest.driver, maxSecondsWait)) {
                listVals.add(1, State.Warn);
            }
            if (PageRegistroAddressData.existsDesplegablePaises(dFTest.driver)) {
                if (!PageRegistroAddressData.isOptionPaisSelected(dFTest.driver, pais.getCodigo_pais())) {
                    listVals.add(2, State.Warn);
                }
            }
    
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static DatosStep sendDataAccordingCountryToInputs(HashMap<String,String> dataRegistro, Pais pais, DataFmwkTest dFTest) throws Exception {
        //Step. Introducir datos en el registro según el país
        DatosStep datosStep = new DatosStep       (
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
    
    public static DatosStep sendDataOkToInputs(ListDataRegistro dataToSend, DataFmwkTest dFTest) throws Exception {
        //Step. Introducir datos en el registro
        DatosStep datosStep = new DatosStep       (
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
    
    private static void validateInputDataOk(DatosStep datosStep, DataFmwkTest dFTest) {
        //Validaciones
        String descripValidac = 
            "1) No aparece ningún mensaje de error asociado a los campos de entrada"; 
        datosStep.setStateIniValidations();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (PageRegistroDirec.getNumberMsgInputInvalid(dFTest.driver) > 0) {
                listVals.add(1, State.Defect);
            }
            
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static DatosStep clickFinalizarButton(DataFmwkTest dFTest) 
    throws Exception {
        //Step. 
        DatosStep datosStep = new DatosStep       (
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
