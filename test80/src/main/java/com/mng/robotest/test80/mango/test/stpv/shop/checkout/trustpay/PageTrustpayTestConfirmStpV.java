package com.mng.robotest.test80.mango.test.stpv.shop.checkout.trustpay;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.trustpay.PageTrustpayTestConfirm;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.trustpay.PageTrustpayTestConfirm.typeButtons;

@SuppressWarnings("javadoc")
public class PageTrustpayTestConfirmStpV {
    
    public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Figura el botón \"OK\"<br>" +
            "2) Figura el botón \"ANNOUNCED\"<br>" +
            "3) Figura el botón \"FAIL\"<br>" +
            "4) Figura el botón \"PENDING\"";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageTrustpayTestConfirm.isPresentButton(typeButtons.OK, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if (!PageTrustpayTestConfirm.isPresentButton(typeButtons.ANNOUNCED, dFTest.driver)) {
                listVals.add(2, State.Warn);
            }
            if (!PageTrustpayTestConfirm.isPresentButton(typeButtons.FAIL, dFTest.driver)) {
                listVals.add(3, State.Warn);
            }
            if (!PageTrustpayTestConfirm.isPresentButton(typeButtons.PENDING, dFTest.driver)) {
                listVals.add(4, State.Warn);
            }
                                                
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static DatosStep clickButtonOK(DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep       (
            "Seleccionar el botón para continuar con el pago", 
            "El pago se completa correctamente");
        try {
            PageTrustpayTestConfirm.clickButton(typeButtons.OK, dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { fmwkTest.grabStep(datosStep, dFTest); }
        
        return datosStep;
    }
}
