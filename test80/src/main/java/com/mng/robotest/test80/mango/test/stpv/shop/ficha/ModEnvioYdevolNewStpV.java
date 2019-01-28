package com.mng.robotest.test80.mango.test.stpv.shop.ficha;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.ModEnvioYdevolNew;

@SuppressWarnings("javadoc")
public class ModEnvioYdevolNewStpV {

    public static void validateIsVisible(DatosStep datosStep, DataFmwkTest dFTest) {
        int maxSecondsToWait = 1;
        String descripValidac = 
            "1) Aparece el modal con los datos a nivel de envío y devolución";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!ModEnvioYdevolNew.isVisibleUntil(maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }  
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static void clickAspaForClose(DataFmwkTest dFTest) throws Exception {
        //Step.
        DatosStep datosStep = new DatosStep (
            "Seleccionar el aspa para cerrar el modal de \"Envío y devolución\"",
            "Desaparece el modal");
        try {
            ModEnvioYdevolNew.clickAspaForClose(dFTest.driver);
                        
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }        
        
        int maxSecondsToWait = 1;
        String descripValidac = 
            "1) No es visible el modal con los datos a nivel de envío y devolución";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (ModEnvioYdevolNew.isVisibleUntil(maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }  
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
}
