package com.mng.robotest.test80.mango.test.stpv.shop.checkout.Dotpay;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.dotpay.PageDotpayResultado;

@SuppressWarnings("javadoc")
public class PageDotpayResultadoStpV {
    
    public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Aparece la página de resultado del pago OK de Dotpay";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageDotpayResultado.isPageResultadoOk(dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
                                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
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
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        return datosStep;
    }
}
