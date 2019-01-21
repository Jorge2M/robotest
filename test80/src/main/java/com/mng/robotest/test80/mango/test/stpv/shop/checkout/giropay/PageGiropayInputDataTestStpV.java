package com.mng.robotest.test80.mango.test.stpv.shop.checkout.giropay;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.giropay.PageGiropayInputDataTest;

@SuppressWarnings("javadoc")
public class PageGiropayInputDataTestStpV {
    
    public static void validateIsPage(datosStep datosStep, DataFmwkTest dFTest) {
        int maxSecondsToWait = 2;
        String descripValidac = 
            "1) Aparece la página de Test para la introducción de los datos de Giropay (la esperamos hasta " + maxSecondsToWait + ")";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageGiropayInputDataTest.isPageUntil(maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
                                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static datosStep inputDataAndClick(Pago pago, DataFmwkTest dFTest) throws Exception {
        //Step
        datosStep datosStep = new datosStep     (
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
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        return datosStep;
    }
}
