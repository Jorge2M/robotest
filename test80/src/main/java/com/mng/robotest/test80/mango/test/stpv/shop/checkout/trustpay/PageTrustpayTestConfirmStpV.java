package com.mng.robotest.test80.mango.test.stpv.shop.checkout.trustpay;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.trustpay.PageTrustpayTestConfirm;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.trustpay.PageTrustpayTestConfirm.typeButtons;

@SuppressWarnings("javadoc")
public class PageTrustpayTestConfirmStpV {
    
    public static void validateIsPage(datosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Figura el botón \"OK\"<br>" +
            "2) Figura el botón \"ANNOUNCED\"<br>" +
            "3) Figura el botón \"FAIL\"<br>" +
            "4) Figura el botón \"PENDING\"";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageTrustpayTestConfirm.isPresentButton(typeButtons.OK, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (!PageTrustpayTestConfirm.isPresentButton(typeButtons.ANNOUNCED, dFTest.driver))
                fmwkTest.addValidation(2, State.Warn, listVals);
            //3)
            if (!PageTrustpayTestConfirm.isPresentButton(typeButtons.FAIL, dFTest.driver))
                fmwkTest.addValidation(3, State.Warn, listVals);
            //4)
            if (!PageTrustpayTestConfirm.isPresentButton(typeButtons.PENDING, dFTest.driver))
                fmwkTest.addValidation(4, State.Warn, listVals);
                                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static datosStep clickButtonOK(DataFmwkTest dFTest) throws Exception {
        //Step
        datosStep datosStep = new datosStep       (
            "Seleccionar el botón para continuar con el pago", 
            "El pago se completa correctamente");
        try {
            PageTrustpayTestConfirm.clickButton(typeButtons.OK, dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        return datosStep;
    }
}
