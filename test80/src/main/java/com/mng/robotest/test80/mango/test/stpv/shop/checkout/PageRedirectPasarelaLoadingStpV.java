package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageRedirectPasarelaLoading;

@SuppressWarnings("javadoc")
public class PageRedirectPasarelaLoadingStpV {
    
    public static void validateDisappeared(datosStep datosStep, DataFmwkTest dFTest) { 
        String descripValidac = 
            "1) Acaba desapareciendo la página de \"Por favor espere. Este proceso puede tardar...\" (esperamos hasta 5 segundos)";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);                 
        try { 
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageRedirectPasarelaLoading.isPageNotVisibleUntil(5/*maxSecondsToWait*/, dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
                                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals); 
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest);  }
    }
}
