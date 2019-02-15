package com.mng.robotest.test80.mango.test.stpv.shop.checkout.Dotpay;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.dotpay.PageDotpayAcceptSimulation;


public class PageDotpayAcceptSimulationStpV {
    
    public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Aparece la página de Dotpay para la introducción de los datos del pagador<br>" +
            "2) Figura un botón de aceptar rojo";
        datosStep.setNOKstateByDefault();    
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageDotpayAcceptSimulation.isPage(dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
            if (!PageDotpayAcceptSimulation.isPresentRedButtonAceptar(dFTest.driver)) {
                listVals.add(2, State.Defect); 
            }
                                                
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static DatosStep clickRedButtonAceptar(DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep (
            "Seleccionar el botón rojo para aceptar", 
            "Aparece la página resultado");
        try {
            PageDotpayAcceptSimulation.clickRedButtonAceptar(dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
        
        //Validation
        PageDotpayResultadoStpV.validateIsPage(datosStep, dFTest);
        
        return datosStep;
    }
}
