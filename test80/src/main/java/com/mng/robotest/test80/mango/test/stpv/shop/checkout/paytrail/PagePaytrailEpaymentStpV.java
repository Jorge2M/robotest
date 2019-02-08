package com.mng.robotest.test80.mango.test.stpv.shop.checkout.paytrail;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.epayment.PageEpaymentIdent;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paytrail.PagePaytrailEpayment;

@SuppressWarnings("javadoc")
public class PagePaytrailEpaymentStpV {
    
    public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) { 
        String descripValidac = 
            "1) Aparece la página inicial de E-Payment<br>" +
            "2) Figuran el input correspondientes al \"User ID\"";
        datosStep.setStateIniValidations();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageEpaymentIdent.isPage(dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
            if (!PageEpaymentIdent.isPresentInputUserTypePassword(dFTest.driver)) {
                listVals.add(2, State.Warn);
            }
                                                
            datosStep.setListResultValidations(listVals);
        }
        catch (Exception e) {
            //
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static DatosStep clickCodeCardOK(String importeTotal, String codPais, DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep (
            "Click en el botón \"OK\"del apartado \"Code card\"", 
            "Aparece la página de introducción del <b>ID de confirmación</b>");
        try {
            PagePaytrailEpayment.clickOkFromCodeCard(dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validation
        PagePaytrailIdConfirmStpV.validateIsPage(importeTotal, codPais, datosStep, dFTest);
        
        return datosStep;
    }
}
