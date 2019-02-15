package com.mng.robotest.test80.mango.test.stpv.shop.checkout.giropay;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.giropay.PageGiropayInputDataTest;


public class PageGiropayInputDataTestStpV {
    
    public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) {
        int maxSecondsToWait = 2;
        String descripValidac = 
            "1) Aparece la página de Test para la introducción de los datos de Giropay (la esperamos hasta " + maxSecondsToWait + ")";
        datosStep.setNOKstateByDefault();    
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageGiropayInputDataTest.isPageUntil(maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
                                                
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static DatosStep inputDataAndClick(Pago pago, DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep     (
            "Introducimos los datos del pago:<br>" +
            "  - sc: <b>" + pago.getScgiropay() + "</b><br>" +
            "  - extensionSc: <b>" + pago.getExtscgiropay() + "</b><br>" +
            "  - customerName: <b>" + pago.getTitular() + "</b><br>" +
            "  - customerIBAN: <b>" + pago.getIban() + "</b><br>" +
            "Y pulsamos <b>Abseden</b>", 
            "Aparece la página de Test de introducción de datos de Giropay");
        try {
            PageGiropayInputDataTest.inputSc(pago.getScgiropay(), dFTest.driver);
            PageGiropayInputDataTest.inputExtensionSc(pago.getExtscgiropay(), dFTest.driver);
            PageGiropayInputDataTest.inputCustomerName(pago.getTitular(), dFTest.driver);
            PageGiropayInputDataTest.inputIBAN(pago.getIban(), dFTest.driver);
            PageGiropayInputDataTest.clickButtonAbseden(dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
        
        return datosStep;
    }
}
