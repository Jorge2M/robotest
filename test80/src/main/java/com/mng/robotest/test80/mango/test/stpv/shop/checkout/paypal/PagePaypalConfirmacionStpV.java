package com.mng.robotest.test80.mango.test.stpv.shop.checkout.paypal;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paypal.PagePaypalConfirmacion;

@SuppressWarnings("javadoc")
public class PagePaypalConfirmacionStpV {
    
    public static void validateIsPageUntil(int maxSecondsWait, datosStep datosStep, DataFmwkTest dFTest) {
        //Validaciones
        String descripValidac =
            "1) Aparece la página de Confirmación (la esperamos hasta " + maxSecondsWait + " segundos)";
        datosStep.setGrabImage(true);        
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok); 
        try {    
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PagePaypalConfirmacion.isPageUntil(maxSecondsWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static datosStep clickContinuarButton(DataFmwkTest dFTest) throws Exception {
        //Step
        datosStep datosStep = new datosStep     (
            "Seleccionar el botón \"Continuar\"", 
            "Aparece la página de Mango de resultado OK del pago");
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);    
        try {       
            PagePaypalConfirmacion.clickContinuarButton(dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        return datosStep;
    }
}
