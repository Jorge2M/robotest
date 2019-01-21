package com.mng.robotest.test80.mango.test.stpv.shop.checkout.d3d;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.d3d.PageD3DJPTestSelectOption;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.d3d.PageD3DJPTestSelectOption.OptionD3D;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

@SuppressWarnings("javadoc")
public class PageD3DJPTestSelectOptionStpV {
    
    public static boolean validateIsPage(String importeTotal, String codPais, datosStep datosStep, DataFmwkTest dFTest) {
        boolean isD3DJP = true;
        int maxSecondsToWait = 1;
        String descripValidac = 
            "1) Aparece la página de Test correspondiente al D3D de JPMorgan (la esperamos hasta " + maxSecondsToWait + " segundos)<br>" + 
            "2) Aparece el importe " + importeTotal + " de la operación";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageD3DJPTestSelectOption.isPageUntil(maxSecondsToWait, dFTest.driver)) {
                fmwkTest.addValidation(1, State.Warn, listVals);
                isD3DJP = false;
            }
            //2)
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, dFTest.driver))
                fmwkTest.addValidation(2,State.Warn, listVals);
    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        return isD3DJP;
    }
    
    public static datosStep clickSubmitButton(DataFmwkTest dFTest) throws Exception {
        datosStep datosStep = new datosStep       (
            "Seleccionamos la opción \"Successful\" y clickamos en el botón \"Submit\"", 
            "Aparece la página de resultado OK");
        try {
            PageD3DJPTestSelectOption.selectOption(OptionD3D.Successful, dFTest.driver);
            PageD3DJPTestSelectOption.clickSubmitButton(dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        return datosStep;
    }
}
