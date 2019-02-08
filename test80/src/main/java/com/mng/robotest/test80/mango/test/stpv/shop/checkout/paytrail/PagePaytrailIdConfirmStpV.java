package com.mng.robotest.test80.mango.test.stpv.shop.checkout.paytrail;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paytrail.PagePaytrailIdConfirm;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

@SuppressWarnings("javadoc")
public class PagePaytrailIdConfirmStpV {
    
    public static void validateIsPage(String importeTotal, String codPais, DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac =
            "1) Aparece la página de introducción del ID de confirmación<br>" +
            "2) Aparece el importe de la compra: " + importeTotal;
        datosStep.setStateIniValidations();    
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PagePaytrailIdConfirm.isPage(dFTest.driver)) {
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
    
    public static DatosStep inputIDAndClickConfirmar(String idConfirm, String importeTotal, String codPais, DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep       (
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
