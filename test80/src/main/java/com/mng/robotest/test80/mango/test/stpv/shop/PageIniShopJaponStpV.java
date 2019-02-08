package com.mng.robotest.test80.mango.test.stpv.shop;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
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
        datosStep.setStateIniValidations();           
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageIniShopJapon.isPageUntil(maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
            
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
}
