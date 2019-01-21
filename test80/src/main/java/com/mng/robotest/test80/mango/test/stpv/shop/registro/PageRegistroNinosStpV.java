package com.mng.robotest.test80.mango.test.stpv.shop.registro;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.ListDataNinos;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.PageRegistroNinos;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;

@SuppressWarnings("javadoc")
public class PageRegistroNinosStpV {
    
    public static void validaIsPageWithNinos(int numNinos, datosStep datosStep, DataFmwkTest dFTest) {
        int maxSecondsToWait = 5;
        String descripValidac = 
            "1) Aparece la página de introducción de datos del niño (la esperamos un máximo de " + maxSecondsToWait + " segundos)<br>" +
            "2) Aparecen inputs para introducir <b>" + numNinos + "</b>";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageRegistroNinos.isPageUntil(dFTest.driver, maxSecondsToWait))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2) 
            if (PageRegistroNinos.getNumInputsNameNino(dFTest.driver)!=numNinos)
                fmwkTest.addValidation(2, State.Defect, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static void sendNinoDataAndContinue(ListDataNinos listaNinos, Pais pais, DataFmwkTest dFTest) 
    throws Exception {
        //Step.
        datosStep datosStep = new datosStep (
            "Introducir datos de los niños: <br>" + listaNinos.getFormattedHTMLData() + "<br>" + "y finalmente pulsar el botón \"Continuar\"", 
            "Aparece la página de introducción de datos de la dirección");
        try {
            PageRegistroNinos.setDataNinoIfNotExists(listaNinos, 2/*nTimes*/, dFTest.driver);
            PageRegistroNinos.clickContinuar(dFTest.driver);
                                                                        
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }                 
                
        //Validaciones
        PageRegistroDirecStpV.isPageFromPais(pais, datosStep, dFTest);
        
        //Validaciones estándar. 
        AllPagesStpV.validacionesEstandar(true/*validaSEO*/, false/*validaJS*/, false/*validaImgBroken*/, datosStep, dFTest);
    }
}
