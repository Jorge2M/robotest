package com.mng.robotest.test80.mango.test.stpv.shop.checkout.tmango;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.tmango.PageAmexInputCip;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;


public class PageAmexInputCipStpV {
    
    public static void validateIsPageOk(String importeTotal, String codigoPais, DatosStep datosStep, DataFmwkTest dFTest) {
    	int maxSecondsToWait = 5;
        String descripValidac = 
            "1) Aparece la página de introducción del CIP (la esperamos hasta " + maxSecondsToWait + " segundos)<br>" + 
            "2) Aparece el importe de la operación " + importeTotal;
        datosStep.setExcepExists(true);
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try { 
            if (!PageAmexInputCip.isPageUntil(maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codigoPais, dFTest.driver)) {
                listVals.add(2, State.Warn);
            }
    
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static DatosStep inputCipAndAcceptButton(String CIP, String importeTotal, String codigoPais, DataFmwkTest dFTest) 
    throws Exception {
        //Step
        DatosStep datosStep = new DatosStep (
            "Introducimos el CIP " + CIP + " y pulsamos el botón \"Aceptar\"", 
            "Aparece una página de la pasarela de resultado OK");
        try {
            PageAmexInputCip.inputCIP(CIP, dFTest.driver);
            PageAmexInputCip.clickAceptarButton(dFTest.driver);
                               
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
                    
        //Validaciones
        PageAmexResultStpV.validateIsPageOk(datosStep, importeTotal, codigoPais, dFTest);
        
        return datosStep;
    }
}
