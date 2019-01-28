package com.mng.robotest.test80.mango.test.stpv.shop.checkout.tmango;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.tmango.PageAmexResult;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

@SuppressWarnings("javadoc")
public class PageAmexResultStpV {

    public static void validateIsPageOk(DatosStep datosStep, String importeTotal, String codigoPais, DataFmwkTest dFTest) {
    	int maxSecondsToWait = 2;
        String descripValidac = 
            "1) Aparece una página con un mensaje de OK (lo esperamos hasta " + maxSecondsToWait + " segundos)<br>" + 
            "2) Aparece el importe de la operación " + importeTotal + "<br>" +
            "3) Aparece un botón \"CONTINUAR\"";            
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Ok); 
        try { 
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageAmexResult.isResultOkUntil(maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codigoPais, dFTest.driver))
                fmwkTest.addValidation(2,State.Warn, listVals);
            //3)
            if (!PageAmexResult.isPresentContinueButton(dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    
    public static DatosStep clickContinuarButton(DataFmwkTest dFTest) throws Exception {
        DatosStep datosStep = new DatosStep     (
            "Seleccionamos el botón \"Continuar\"", 
            "Aparece la página de Mango de resultado OK del pago");
        try {
            PageAmexResult.clickContinuarButton(dFTest.driver);
                               
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        return datosStep;
    }
}