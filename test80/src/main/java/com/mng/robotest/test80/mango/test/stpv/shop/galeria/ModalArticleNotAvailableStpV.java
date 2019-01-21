package com.mng.robotest.test80.mango.test.stpv.shop.galeria;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.ModalArticleNotAvailable;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.ModalArticleNotAvailable.StateModal;

@SuppressWarnings("javadoc")
public class ModalArticleNotAvailableStpV {
    
    public static boolean validateState(StateModal stateModal, datosStep datosStep, DataFmwkTest dFTest) {
        boolean isPresent = false;
        int maxSecondsToWait = 1;
        String descripValidac = 
            "1) El modal de \"Avísame\" por artículo no disponible está en estado " + stateModal + " (lo esperamos hasta " + maxSecondsToWait + " segundos)";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!ModalArticleNotAvailable.inStateUntil(stateModal, maxSecondsToWait, dFTest.driver)) {
                fmwkTest.addValidation(1, State.Info, listVals);
                isPresent = true;
            }
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        return isPresent;
    }
    
    public static void clickAspaForClose(DataFmwkTest dFTest) {
        datosStep datosStep = new datosStep       (
            "Seleccionamos el aspa del modal para cerrarlo", 
            "El modal queda en estado " + StateModal.notvisible);
        try {
            ModalArticleNotAvailable.clickAspaForClose(dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        validateState(StateModal.notvisible, datosStep, dFTest);
    }
}
