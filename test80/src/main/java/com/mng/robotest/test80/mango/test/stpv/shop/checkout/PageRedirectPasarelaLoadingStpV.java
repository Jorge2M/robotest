package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageRedirectPasarelaLoading;

@SuppressWarnings("javadoc")
public class PageRedirectPasarelaLoadingStpV {
    
    public static void validateDisappeared(DatosStep datosStep, DataFmwkTest dFTest) { 
    	int maxSecondsWait = 5;
        String descripValidac = 
            "1) Acaba desapareciendo la página de \"Por favor espere. Este proceso puede tardar...\" (esperamos hasta " + maxSecondsWait + " segundos)";
        datosStep.setStateIniValidations();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try { 
            if (!PageRedirectPasarelaLoading.isPageNotVisibleUntil(5/*maxSecondsToWait*/, dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
                                            
            datosStep.setListResultValidations(listVals); 
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
}
