package com.mng.robotest.test80.mango.test.stpv.shop.checkout.ideal;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.ideal.PageIdealSimulador;


public class PageIdealSimuladorStpV {
    
    public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) { 
        String descripValidac = 
            "1) Aparece la página de simulación de Ideal"; 
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Ok);  
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try { 
            if (!PageIdealSimulador.isPage(dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
                    
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static DatosStep clickContinueButton(DataFmwkTest dFTest) throws Exception {
        DatosStep datosStep = new DatosStep       (
            "Seleccionar el botón \"Continuar\"", 
            "El pago se realiza correctamente");
        try {
            //Selecionamos el botón "Continuar"
            PageIdealSimulador.clickButtonContinue(dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
        
        return datosStep;
    }
}
