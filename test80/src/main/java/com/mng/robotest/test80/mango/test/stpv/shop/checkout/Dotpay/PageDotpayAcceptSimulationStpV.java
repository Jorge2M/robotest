package com.mng.robotest.test80.mango.test.stpv.shop.checkout.Dotpay;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.dotpay.PageDotpayAcceptSimulation;

@SuppressWarnings("javadoc")
public class PageDotpayAcceptSimulationStpV {
    
    public static void validateIsPage(datosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Aparece la página de Dotpay para la introducción de los datos del pagador<br>" +
            "2) Figura un botón de aceptar rojo";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageDotpayAcceptSimulation.isPage(dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            if (!PageDotpayAcceptSimulation.isPresentRedButtonAceptar(dFTest.driver)) 
                fmwkTest.addValidation(2, State.Defect, listVals); 
                                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static datosStep clickRedButtonAceptar(DataFmwkTest dFTest) throws Exception {
        //Step
        datosStep datosStep = new datosStep (
            "Seleccionar el botón rojo para aceptar", 
            "Aparece la página resultado");
        try {
            PageDotpayAcceptSimulation.clickRedButtonAceptar(dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validation
        PageDotpayResultadoStpV.validateIsPage(datosStep, dFTest);
        
        return datosStep;
    }
}
