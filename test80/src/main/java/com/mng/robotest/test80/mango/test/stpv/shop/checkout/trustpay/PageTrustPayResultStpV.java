package com.mng.robotest.test80.mango.test.stpv.shop.checkout.trustpay;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.trustpay.PageTrustPayResult;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;


public class PageTrustPayResultStpV {
    
    public static void validateIsPage(String importeTotal, String codPais, DatosStep datosStep, DataFmwkTest dFTest) {
        String textHeader = "Payment In Progress";
        String descripValidac = 
            "1) Figura el encabezamiento \"" + textHeader + "<br>" +
            "2) Figura el importe total de la compra (" + importeTotal + ")<br>" +
            "3) Figura el botón \"continue\"";
        datosStep.setNOKstateByDefault();    
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageTrustPayResult.headerContains(textHeader, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, dFTest.driver)) {
                listVals.add(2, State.Warn);
            }
            if (!PageTrustPayResult.isPresentButtonContinue(dFTest.driver)) {
                listVals.add(3, State.Defect);
            }
                                                
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static DatosStep clickButtonContinue(DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep       (
            "Seleccionar el botón para continuar con el pago", 
            "El pago se completa correctamente");
        try {
            PageTrustPayResult.clickButtonContinue(dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
        
        return datosStep;
    }
}
