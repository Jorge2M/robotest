package com.mng.robotest.test80.mango.test.stpv.shop.checkout.mercadopago;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.mercadopago.PageMercpago1rst;

@SuppressWarnings("javadoc")
public class PageMercpago1rstStpV {
		
    public static void validateIsPage(datosStep datosStep, DataFmwkTest dFTest) {
        int maxSecondsToWait = 5;
        String descripValidac = 
            "1) Aparece la página inicial de Mercado para la introducción de datos";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if ((!PageMercpago1rst.isPageUntil(maxSecondsToWait, dFTest.driver)))
                fmwkTest.addValidation(1, State.Warn, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
	
    public static datosStep clickLinkRegistration(DataFmwkTest dFTest) throws Exception {
        datosStep datosStep = new datosStep       (
            "Accedemos a la página de identificación", 
            "Aparece la página de identificación");
        try {
            PageMercpago1rst.clickLinkRegistro(dFTest.driver);
                                                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validaciones
        PageMercpagoLoginStpV.validateIsPage(datosStep, dFTest);
        
        return datosStep;
    }
}
