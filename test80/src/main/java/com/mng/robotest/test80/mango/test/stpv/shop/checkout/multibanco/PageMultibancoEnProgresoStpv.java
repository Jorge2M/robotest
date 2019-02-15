package com.mng.robotest.test80.mango.test.stpv.shop.checkout.multibanco;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.multibanco.PageMultibancoEnProgreso;


public class PageMultibancoEnProgresoStpv {
    
    public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) {
        int maxSecondsToWait = 3;
        String descripValidac = 
            "1) Aparece la cabecera <b>Pagamento em progreso</b> (la esperamos hasta " + maxSecondsToWait + " segundos<br>" +
            "2) Figura un botón para ir al siguiente paso";
        datosStep.setNOKstateByDefault();         
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageMultibancoEnProgreso.isPageUntil(maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
            if (!PageMultibancoEnProgreso.isButonNextStep(dFTest.driver)) {
                listVals.add(2, State.Defect);            
            }
                                                
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static DatosStep clickButtonNextStep(DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep       (
            "Seleccionar el botón \"Continuar\"", 
            "El pago se ejecuta correctamente y aparece la correspondiente página de resultado de Mango");
        try {
            PageMultibancoEnProgreso.clickButtonNextStep(dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
        
        return datosStep;
    }
}
