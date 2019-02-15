package com.mng.robotest.test80.mango.test.stpv.shop.checkout.mercadopago;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.mercadopago.PageMercpago1rst;


public class PageMercpago1rstStpV {
		
    public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) {
        int maxSecondsToWait = 5;
        String descripValidac = 
            "1) Aparece la página inicial de Mercado para la introducción de datos";
        datosStep.setNOKstateByDefault();     
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if ((!PageMercpago1rst.isPageUntil(maxSecondsToWait, dFTest.driver))) {
                listVals.add(1, State.Warn);
            }

            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
	
    public static DatosStep clickLinkRegistration(DataFmwkTest dFTest) throws Exception {
        DatosStep datosStep = new DatosStep       (
            "Accedemos a la página de identificación", 
            "Aparece la página de identificación");
        try {
            PageMercpago1rst.clickLinkRegistro(dFTest.driver);
                                                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
        
        //Validaciones
        PageMercpagoLoginStpV.validateIsPage(datosStep, dFTest);
        
        return datosStep;
    }
}
