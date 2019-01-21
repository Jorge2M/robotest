package com.mng.robotest.test80.mango.test.stpv.shop.checkout.paytrail;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paytrail.PagePaytrailIdConfirm;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

@SuppressWarnings("javadoc")
public class PagePaytrailIdConfirmStpV {
    
    public static void validateIsPage(String importeTotal, String codPais, datosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac =
            "1) Aparece la página de introducción del ID de confirmación<br>" +
            "2) Aparece el importe de la compra: " + importeTotal;
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1) 
            if (!PagePaytrailIdConfirm.isPage(dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, dFTest.driver)) 
                fmwkTest.addValidation(2, State.Warn, listVals);            
                                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        catch (Exception e) {
            //
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static datosStep inputIDAndClickConfirmar(String idConfirm, String importeTotal, String codPais, DataFmwkTest dFTest) throws Exception {
        //Step
        datosStep datosStep = new datosStep       (
            "Introducir el ID <b>idConfirm</b> y seleccionar el botón \"Confirmar\"", 
            "Aparece la página de introducción del <b>ID de confirmación</b>");
        try {
            PagePaytrailIdConfirm.inputIdConfirm(idConfirm, dFTest.driver);
            PagePaytrailIdConfirm.clickConfirmar(dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validations
        PagePaytrailResultadoOkStpV.validateIsPage(importeTotal, codPais, datosStep, dFTest);
        
        return datosStep;
    }
}
