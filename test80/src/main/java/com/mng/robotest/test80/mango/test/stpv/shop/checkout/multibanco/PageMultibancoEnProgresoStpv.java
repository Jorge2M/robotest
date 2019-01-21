package com.mng.robotest.test80.mango.test.stpv.shop.checkout.multibanco;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.multibanco.PageMultibancoEnProgreso;

@SuppressWarnings("javadoc")
public class PageMultibancoEnProgresoStpv {
    
    public static void validateIsPage(datosStep datosStep, DataFmwkTest dFTest) {
        int maxSecondsToWait = 3;
        String descripValidac = 
            "1) Aparece la cabecera <b>Pagamento em progreso</b> (la esperamos hasta " + maxSecondsToWait + " segundos<br>" +
            "2) Figura un botón para ir al siguiente paso";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageMultibancoEnProgreso.isPageUntil(maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            if (!PageMultibancoEnProgreso.isButonNextStep(dFTest.driver)) 
                fmwkTest.addValidation(2, State.Defect, listVals);            
                                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static datosStep clickButtonNextStep(DataFmwkTest dFTest) throws Exception {
        //Step
        datosStep datosStep = new datosStep       (
            "Seleccionar el botón \"Continuar\"", 
            "El pago se ejecuta correctamente y aparece la correspondiente página de resultado de Mango");
        try {
            PageMultibancoEnProgreso.clickButtonNextStep(dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        return datosStep;
    }
}
