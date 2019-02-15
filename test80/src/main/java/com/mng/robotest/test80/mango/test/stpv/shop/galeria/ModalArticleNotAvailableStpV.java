package com.mng.robotest.test80.mango.test.stpv.shop.galeria;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.ModalArticleNotAvailable;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.ModalArticleNotAvailable.StateModal;


public class ModalArticleNotAvailableStpV {
    
    public static boolean validateState(StateModal stateModal, DatosStep datosStep, DataFmwkTest dFTest) {
        boolean isPresent = false;
        int maxSecondsToWait = 1;
        String descripValidac = 
            "1) El modal de \"Avísame\" por artículo no disponible está en estado " + stateModal + " (lo esperamos hasta " + maxSecondsToWait + " segundos)";
        datosStep.setNOKstateByDefault();          
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!ModalArticleNotAvailable.inStateUntil(stateModal, maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Info);
                isPresent = true;
            }
            
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        return isPresent;
    }
    
    public static void clickAspaForClose(DataFmwkTest dFTest) {
        DatosStep datosStep = new DatosStep       (
            "Seleccionamos el aspa del modal para cerrarlo", 
            "El modal queda en estado " + StateModal.notvisible);
        try {
            ModalArticleNotAvailable.clickAspaForClose(dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
        
        validateState(StateModal.notvisible, datosStep, dFTest);
    }
}
