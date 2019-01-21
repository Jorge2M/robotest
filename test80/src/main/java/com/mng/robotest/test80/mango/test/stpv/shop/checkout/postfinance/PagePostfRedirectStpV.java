package com.mng.robotest.test80.mango.test.stpv.shop.checkout.postfinance;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.postfinance.PagePostfRedirect;

@SuppressWarnings("javadoc")
public class PagePostfRedirectStpV {

    public static void isPageAndFinallyDisappears(datosStep datosStep, DataFmwkTest dFTest) throws Exception {
        int maxSecondsToWait = 10;
        String descripValidac = 
            "1) Aparece una página de redirección con un botón OK<br>" +
            "2) La página de redirección acaba desapareciendo (esperamos hasta " + maxSecondsToWait + " segundos)";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try { 
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PagePostfRedirect.isPresentButtonOk(dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (!PagePostfRedirect.isInvisibleButtonOkUntil(dFTest.driver, maxSecondsToWait))
                fmwkTest.addValidation(2, State.Defect, listVals);
    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
}
