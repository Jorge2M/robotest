package com.mng.robotest.test80.mango.test.stpv.shop.checkout.paytrail;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.epayment.PageEpaymentIdent;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paytrail.PagePaytrailEpayment;

@SuppressWarnings("javadoc")
public class PagePaytrailEpaymentStpV {
    
    public static void validateIsPage(datosStep datosStep, DataFmwkTest dFTest) { 
        String descripValidac = 
            "1) Aparece la página inicial de E-Payment<br>" +
            "2) Figuran el input correspondientes al \"User ID\"";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageEpaymentIdent.isPage(dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            if (!PageEpaymentIdent.isPresentInputUserTypePassword(dFTest.driver))
                fmwkTest.addValidation(2, State.Warn, listVals);            
                                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        catch (Exception e) {
            //
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static datosStep clickCodeCardOK(String importeTotal, String codPais, DataFmwkTest dFTest) throws Exception {
        //Step
        datosStep datosStep = new datosStep (
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
