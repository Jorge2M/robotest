package com.mng.robotest.test80.mango.test.stpv.shop.checkout.paypal;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paypal.ModalPreloaderSpinner;

public class ModalPreloaderSppinerStpV {
	public static void validateAppearsAndDisappears(DatosStep datosStep, DataFmwkTest dFTest) {
		validateIsVisible(2, datosStep, dFTest);
		validateIsVanished(10, datosStep, dFTest);
	}
	
    public static void validateIsVisible(int maxSecondsWait, DatosStep datosStep, DataFmwkTest dFTest) {
        //Validaciones
        String descripValidac =
            "1) Aparece el icono del candado de \"Cargando\" (lo esperamos un máximo de " + maxSecondsWait + ")";   
        datosStep.setNOKstateByDefault(); 
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!ModalPreloaderSpinner.isVisibleUntil(maxSecondsWait, dFTest.driver)) {
                listVals.add(1, State.Info);
            }
                            
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
	
    public static void validateIsVanished(int maxSecondsWait, DatosStep datosStep, DataFmwkTest dFTest) {
        //Validaciones
        String descripValidac =
            "1) Desaparece el icono del candado de \"Cargando\" (lo esperamos un máximo de " + maxSecondsWait + ")";   
        datosStep.setNOKstateByDefault(); 
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {    
            if (!ModalPreloaderSpinner.isNotVisibleUntil(maxSecondsWait, dFTest.driver)) {
                listVals.add(1, State.Info);
            }
                            
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
}
