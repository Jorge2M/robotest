package com.mng.robotest.test80.mango.test.stpv.shop.checkout.Dotpay;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.dotpay.PageDotpayResultado;


public class PageDotpayResultadoStpV {
    
    public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Aparece la página de resultado del pago OK de Dotpay";
        datosStep.setNOKstateByDefault();         
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageDotpayResultado.isPageResultadoOk(dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
                                                
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static DatosStep clickNext(DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep (
            "Seleccionar el botón <b>Next</b>", 
            "Aparece la página de pago OK de Mango");
        try {
            PageDotpayResultado.clickButtonNext(dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
        
        return datosStep;
    }
}
