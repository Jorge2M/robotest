package com.mng.robotest.test80.mango.test.stpv.shop.checkout.paypal;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paypal.PagePaypalConfirmacion;

@SuppressWarnings("javadoc")
public class PagePaypalConfirmacionStpV {
    
    public static void validateIsPageUntil(int maxSecondsWait, DatosStep datosStep, DataFmwkTest dFTest) {
        //Validaciones
        String descripValidac =
            "1) Aparece la página de Confirmación (la esperamos hasta " + maxSecondsWait + " segundos)";      
        datosStep.setNOKstateByDefault(); 
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {    
            if (!PagePaypalConfirmacion.isPageUntil(maxSecondsWait, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
                            
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static DatosStep clickContinuarButton(DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep     (
            "Seleccionar el botón \"Continuar\"", 
            "Aparece la página de Mango de resultado OK del pago");
        datosStep.setNOKstateByDefault();    
        try {       
            PagePaypalConfirmacion.clickContinuarButton(dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { fmwkTest.grabStep(datosStep, dFTest); }

        return datosStep;
    }
}
