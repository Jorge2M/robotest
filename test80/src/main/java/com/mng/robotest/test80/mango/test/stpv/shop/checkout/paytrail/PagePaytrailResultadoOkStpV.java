package com.mng.robotest.test80.mango.test.stpv.shop.checkout.paytrail;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paytrail.PagePaytrailResultadoOk;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

@SuppressWarnings("javadoc")
public class PagePaytrailResultadoOkStpV {
    
    public static void validateIsPage(String importeTotal, String codPais, DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac =
            "1) Aparece la página de resultado Ok de Paytrail<br>" +
            "2) Aparece el importe de la compra: " + importeTotal;
        datosStep.setNOKstateByDefault();       
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PagePaytrailResultadoOk.isPage(dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, dFTest.driver)) {
                listVals.add(2, State.Warn);            
            }
                                                
            datosStep.setListResultValidations(listVals);
        }
        catch (Exception e) {
            //
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static DatosStep clickVolverAMangoButton(DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep       (
            "Click el botón para volver a Mango", 
            "Aparece la página de resultado Ok de Mango");
        try {
            PagePaytrailResultadoOk.clickVolverAMangoButton(dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        return datosStep;
    }
}
