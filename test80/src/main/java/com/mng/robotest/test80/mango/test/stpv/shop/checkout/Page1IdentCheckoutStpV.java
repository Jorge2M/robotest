package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;

@SuppressWarnings("javadoc")
public class Page1IdentCheckoutStpV {
    
    public static SecSoyNuevoStpV secSoyNuevo;
    
    @SuppressWarnings("static-access")
    public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) {
        //Validaciones
        int maxSecondsToWait = 5;
        String descripValidac = 
            "1) Aparece el formulario correspondiente a la identificación (lo esperamos hasta " + maxSecondsToWait + " segs)"; 
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!Page1IdentCheckout.secSoyNuevo.isFormIdentUntil(dFTest.driver, maxSecondsToWait))
                fmwkTest.addValidation(1, State.Defect, listVals);
        
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }

}
