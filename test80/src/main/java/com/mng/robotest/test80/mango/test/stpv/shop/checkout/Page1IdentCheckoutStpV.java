package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;

@SuppressWarnings("javadoc")
public class Page1IdentCheckoutStpV {
    
    public static SecSoyNuevoStpV secSoyNuevo;
    
    @SuppressWarnings("static-access")
    public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) {
        //Validaciones
        int maxSecondsToWait = 5;
        String descripValidac = 
            "1) Aparece el formulario correspondiente a la identificación (lo esperamos hasta " + maxSecondsToWait + " segs)"; 
        datosStep.setNOKstateByDefault();  
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!Page1IdentCheckout.secSoyNuevo.isFormIdentUntil(dFTest.driver, maxSecondsToWait)) {
                listVals.add(1, State.Defect);
            }
        
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }

}
