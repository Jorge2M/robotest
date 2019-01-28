package com.mng.robotest.test80.mango.test.stpv.shop.ficha;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
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
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!SecFitFinder.isVisibleUntil(maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (!SecFitFinder.isVisibleInputAltura(dFTest.driver))
                fmwkTest.addValidation(2, State.Warn, listVals);
            //3)
            if (!SecFitFinder.isVisibleInputPeso(dFTest.driver))
                fmwkTest.addValidation(3, State.Warn, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { 
        	fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest);
        	SecFitFinder.clickAspaForCloseAndWait(dFTest.driver);
        }
    }
}
