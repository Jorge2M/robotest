package com.mng.robotest.test80.mango.test.stpv.shop.checkout.d3d;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.d3d.PageD3DLogin;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

@SuppressWarnings("javadoc")
public class PageD3DLoginStpV {
    
    public static boolean validateIsPage(String importeTotal, String codPais, DatosStep datosStep, DataFmwkTest dFTest) {
        boolean isD3D = true;
        int maxSecondsToWait = 1;
        String descripValidac = 
            "1) Aparece la página de identificación D3D (la esperamos hasta " + maxSecondsToWait + " segundos)<br>" + 
            "2) Aparece el importe " + importeTotal + " de la operación";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageD3DLogin.isPageUntil(maxSecondsToWait, dFTest.driver)) {
                fmwkTest.addValidation(1, State.Info_NoHardcopy, listVals);
                isD3D = false;
            }
            //2)
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, dFTest.driver))
                fmwkTest.addValidation(2,State.Warn, listVals);
    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        return isD3D;
    }
    
    public static DatosStep loginAndClickSubmit(String user, String password, DataFmwkTest dFTest) throws Exception {
        DatosStep datosStep = new DatosStep       (
            "Autenticarse en D3D con " + user + "/" + password, 
            "Aparece la página de resultado OK");
        try {
            PageD3DLogin.inputUserPassword(user, password, dFTest.driver);
            PageD3DLogin.clickButtonSubmit(dFTest.driver);
                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        return datosStep;
    }
}
