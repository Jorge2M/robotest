package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageInfoNewMisComprasMovil;

@SuppressWarnings("javadoc")
public class PageInfoNewMisComprasMovilStpV {
    
    public static void validateIsPage(datosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Aparece la página \"New!\" informativa a nivel de \"Mis Compras\"";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageInfoNewMisComprasMovil.isPage(dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }        
    }
    
    public static datosStep clickButtonToMisComprasAndNoValidate(DataFmwkTest dFTest) throws Exception {
        //Step.
        datosStep datosStep = new datosStep     (
            "Seleccionar el botón \"Ver mis compras\"", 
            "Aparece la página de \"Mis Compras\"");
        try {
            PageInfoNewMisComprasMovil.clickButtonToMisCompras(dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        return datosStep;
    }
}
