package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageInfoNewMisComprasMovil;


public class PageInfoNewMisComprasMovilStpV {
    
    public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Aparece la página \"New!\" informativa a nivel de \"Mis Compras\"";
        datosStep.setNOKstateByDefault();    
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageInfoNewMisComprasMovil.isPage(dFTest.driver)) {
                listVals.add(1, State.Defect);
            }

            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }        
    }
    
    public static DatosStep clickButtonToMisComprasAndNoValidate(DataFmwkTest dFTest) throws Exception {
        //Step.
        DatosStep datosStep = new DatosStep     (
            "Seleccionar el botón \"Ver mis compras\"", 
            "Aparece la página de \"Mis Compras\"");
        try {
            PageInfoNewMisComprasMovil.clickButtonToMisCompras(dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
        
        return datosStep;
    }
}
