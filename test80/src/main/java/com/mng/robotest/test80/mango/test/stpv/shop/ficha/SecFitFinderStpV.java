package com.mng.robotest.test80.mango.test.stpv.shop.ficha;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecFitFinder;

@SuppressWarnings("javadoc")
public class SecFitFinderStpV {
    
    public static void validateIsOkAndClose(DatosStep datosStep, DataFmwkTest dFTest) {
        //Validaciones
        int maxSecondsToWait = 2;
        String descripValidac = 
            "1) Es visible el Wrapper con la guía de tallas (lo esperamos hasta " + maxSecondsToWait + " seconds)<br>" +
            "2) Es visible el input para la introducción de la altura<br>" +
            "3) Es visible el input para la introducción del peso";
        datosStep.setStateIniValidations();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!SecFitFinder.isVisibleUntil(maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if (!SecFitFinder.isVisibleInputAltura(dFTest.driver)) {
                listVals.add(2, State.Warn);
            }
            if (!SecFitFinder.isVisibleInputPeso(dFTest.driver)) {
                listVals.add(3, State.Warn);
            }

            datosStep.setListResultValidations(listVals);
        }
        finally { 
        	listVals.checkAndStoreValidations(descripValidac);
        	SecFitFinder.clickAspaForCloseAndWait(dFTest.driver);
        }
    }
}
