package com.mng.robotest.test80.mango.test.stpv.shop.checkout.paypal;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paypal.ModalPreloaderSpinner;

public class ModalPreloaderSppinerStpV {
	public static void validateAppearsAndDisappears(datosStep datosStep, DataFmwkTest dFTest) {
		validateIsVisible(2, datosStep, dFTest);
		validateIsVanished(10, datosStep, dFTest);
	}
	
    public static void validateIsVisible(int maxSecondsWait, datosStep datosStep, DataFmwkTest dFTest) {
        //Validaciones
        String descripValidac =
            "1) Aparece el icono del candado de \"Cargando\" (lo esperamos un máximo de " + maxSecondsWait + ")";   
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok); 
        try {    
            List<SimpleValidation> listVals = new ArrayList<>();
            //1) 
            if (!ModalPreloaderSpinner.isVisibleUntil(maxSecondsWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Info, listVals);
                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
	
    public static void validateIsVanished(int maxSecondsWait, datosStep datosStep, DataFmwkTest dFTest) {
        //Validaciones
        String descripValidac =
            "1) Desaparece el icono del candado de \"Cargando\" (lo esperamos un máximo de " + maxSecondsWait + ")";   
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok); 
        try {    
            List<SimpleValidation> listVals = new ArrayList<>();
            //1) 
            if (!ModalPreloaderSpinner.isNotVisibleUntil(maxSecondsWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Info, listVals);
                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
}
