package com.mng.robotest.test80.mango.test.stpv.shop.checkout.assist;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.assist.PageAssistLast;

@SuppressWarnings("javadoc")
public class PageAssistLastStpV {

    public static datosStep clickSubmit(DataFmwkTest dFTest) throws Exception {
        //Step
        datosStep datosStep = new datosStep     (
            "Seleccionar el botón de Submit", 
            "Aparece la página de resultado de Mango");
        try {
            PageAssistLast.clickButtonSubmit(dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        return datosStep;
    }
}
