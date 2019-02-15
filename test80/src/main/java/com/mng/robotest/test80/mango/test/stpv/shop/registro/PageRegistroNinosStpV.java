package com.mng.robotest.test80.mango.test.stpv.shop.registro;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.ListDataNinos;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.PageRegistroNinos;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;


public class PageRegistroNinosStpV {
    
    public static void validaIsPageWithNinos(int numNinos, DatosStep datosStep, DataFmwkTest dFTest) {
        int maxSecondsToWait = 5;
        String descripValidac = 
            "1) Aparece la página de introducción de datos del niño (la esperamos un máximo de " + maxSecondsToWait + " segundos)<br>" +
            "2) Aparecen inputs para introducir <b>" + numNinos + "</b>";
        datosStep.setNOKstateByDefault();   
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageRegistroNinos.isPageUntil(dFTest.driver, maxSecondsToWait)) {
                listVals.add(1, State.Defect);
            }
            if (PageRegistroNinos.getNumInputsNameNino(dFTest.driver)!=numNinos) {
                listVals.add(2, State.Defect);
            }

            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static void sendNinoDataAndContinue(ListDataNinos listaNinos, Pais pais, DataFmwkTest dFTest) 
    throws Exception {
        //Step.
        DatosStep datosStep = new DatosStep (
            "Introducir datos de los niños: <br>" + listaNinos.getFormattedHTMLData() + "<br>" + "y finalmente pulsar el botón \"Continuar\"", 
            "Aparece la página de introducción de datos de la dirección");
        try {
            PageRegistroNinos.setDataNinoIfNotExists(listaNinos, 2/*nTimes*/, dFTest.driver);
            PageRegistroNinos.clickContinuar(dFTest.driver);
                                                                        
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }                 
                
        //Validaciones
        PageRegistroDirecStpV.isPageFromPais(pais, datosStep, dFTest);
        
        //Validaciones estándar. 
        AllPagesStpV.validacionesEstandar(true/*validaSEO*/, false/*validaJS*/, false/*validaImgBroken*/, datosStep, dFTest);
    }
}
