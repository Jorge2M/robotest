package com.mng.robotest.test80.mango.test.stpv.shop.checkout.koreancreditcard;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard.PageKoreanConfDesktop;


public class PageKoreanConfDesktopStpV {
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
    
    public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) {    
        String descripValidac = 
    		"1) Aparece la página para la confirmación de la compra";
        datosStep.setNOKstateByDefault();    
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageKoreanConfDesktop.isPage(dFTest.driver)) {
            	listVals.add(1, State.Defect);
            }
            
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static DatosStep clickConfirmarButton(DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep     (
            "Seleccionar el botón para Confirmar", 
            "Aparece la página de Mango de resultado OK del pago");
        datosStep.setNOKstateByDefault();    
        try {       
        	PageKoreanConfDesktop.clickButtonSubmit(dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }

        return datosStep;
    }
}
