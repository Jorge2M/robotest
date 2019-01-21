package com.mng.robotest.test80.mango.test.stpv.shop.checkout.trustpay;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.trustpay.PageTrustPayResult;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

@SuppressWarnings("javadoc")
public class PageTrustPayResultStpV {
    
    public static void validateIsPage(String importeTotal, String codPais, datosStep datosStep, DataFmwkTest dFTest) {
        String textHeader = "Payment In Progress";
        String descripValidac = 
            "1) Figura el encabezamiento \"" + textHeader + "<br>" +
            "2) Figura el importe total de la compra (" + importeTotal + ")<br>" +
            "3) Figura el botón \"continue\"";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageTrustPayResult.headerContains(textHeader, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, dFTest.driver))
                fmwkTest.addValidation(2, State.Warn, listVals);
            //3)
            if (!PageTrustPayResult.isPresentButtonContinue(dFTest.driver))
                fmwkTest.addValidation(3, State.Defect, listVals);
                                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static datosStep clickButtonContinue(DataFmwkTest dFTest) throws Exception {
        //Step
        datosStep datosStep = new datosStep       (
            "Seleccionar el botón para continuar con el pago", 
            "El pago se completa correctamente");
        try {
            PageTrustPayResult.clickButtonContinue(dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        return datosStep;
    }
}
