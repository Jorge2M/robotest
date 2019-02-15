package com.mng.robotest.test80.mango.test.stpv.shop.checkout.d3d;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.d3d.PageD3DJPTestSelectOption;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.d3d.PageD3DJPTestSelectOption.OptionD3D;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;


public class PageD3DJPTestSelectOptionStpV {
    
    public static boolean validateIsPage(String importeTotal, String codPais, DatosStep datosStep, DataFmwkTest dFTest) {
        boolean isD3DJP = true;
        int maxSecondsToWait = 1;
        String descripValidac = 
            "1) Aparece la página de Test correspondiente al D3D de JPMorgan (la esperamos hasta " + maxSecondsToWait + " segundos)<br>" + 
            "2) Aparece el importe " + importeTotal + " de la operación";
        datosStep.setNOKstateByDefault(); 
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageD3DJPTestSelectOption.isPageUntil(maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Warn);
                isD3DJP = false;
            }
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, dFTest.driver)) {
                listVals.add(2, State.Warn);
            }
    
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        return isD3DJP;
    }
    
    public static DatosStep clickSubmitButton(DataFmwkTest dFTest) throws Exception {
        DatosStep datosStep = new DatosStep       (
            "Seleccionamos la opción \"Successful\" y clickamos en el botón \"Submit\"", 
            "Aparece la página de resultado OK");
        try {
            PageD3DJPTestSelectOption.selectOption(OptionD3D.Successful, dFTest.driver);
            PageD3DJPTestSelectOption.clickSubmitButton(dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
        
        return datosStep;
    }
}
