package com.mng.robotest.test80.mango.test.stpv.shop.checkout.tmango;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
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
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try { 
            if (!PageAmexResult.isResultOkUntil(maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codigoPais, dFTest.driver)) {
                listVals.add(2,State.Warn);
            }
            if (!PageAmexResult.isPresentContinueButton(dFTest.driver)) {
                listVals.add(1, State.Defect);
            }

            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
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