package com.mng.robotest.test80.mango.test.stpv.shop;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageIniShopJapon;

@SuppressWarnings("javadoc")
public class PageIniShopJaponStpV {

    /**
     * Validaciones que comprueban que se trata de la página inicial de la shop de Japón
     */
    public static void validaPageIniJapon(DatosStep datosStep, DataFmwkTest dFTest) {
        //Validaciones
    	int maxSecondsToWait = 2;
        String descripValidac = 
            "1) Estamos en la página inicial de la shop de Japón (la esperamos hasta " + maxSecondsToWait + " segundos):<br>" +
            "   - El título es \"" + PageIniShopJapon.Title + "\"<br>" +        
            "   - La URL contiene \"" + PageIniShopJapon.URL + "\"";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);           
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageIniShopJapon.isPageUntil(maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
}
