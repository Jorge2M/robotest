package com.mng.robotest.test80.mango.test.stpv.shop.checkout.postfinance;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.postfinance.PagePostfRedirect;


public class PagePostfRedirectStpV {

    public static void isPageAndFinallyDisappears(DatosStep datosStep, DataFmwkTest dFTest) throws Exception {
        int maxSecondsToWait = 10;
        String descripValidac = 
            "1) Aparece una página de redirección con un botón OK<br>" +
            "2) La página de redirección acaba desapareciendo (esperamos hasta " + maxSecondsToWait + " segundos)";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try { 
            if (!PagePostfRedirect.isPresentButtonOk(dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if (!PagePostfRedirect.isInvisibleButtonOkUntil(dFTest.driver, maxSecondsToWait)) {
                listVals.add(2, State.Defect);
            }
    
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
}
