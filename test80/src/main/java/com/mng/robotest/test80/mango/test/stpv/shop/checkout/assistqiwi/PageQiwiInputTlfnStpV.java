package com.mng.robotest.test80.mango.test.stpv.shop.checkout.assistqiwi;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.assistqiwi.PageQiwiConfirm;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.assistqiwi.PageQiwiInputTlfn;

@SuppressWarnings("javadoc")
public class PageQiwiInputTlfnStpV {
                 
    public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) { 
        String descripValidac = 
            "1) Aparece una página con el campo de introducción del Qiwi Mobile Phone"; 
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try { 
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageQiwiInputTlfn.isPresentInputPhone(dFTest.driver))
                fmwkTest.addValidation(1,State.Warn, listVals);
    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static DatosStep inputTlfnAndAceptar(String tlfnQiwi, DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep     (
            "Introducimos el Qiwi Mobile Phone " + tlfnQiwi + " y pulsamos el botón \"Aceptar\"", 
            "Aparece la página de confirmación de Qiwi o la de resultado del pago de Mango");
        try {
            PageQiwiInputTlfn.inputQiwiPhone(dFTest.driver, tlfnQiwi);
            PageQiwiInputTlfn.waitAndClickAceptar(dFTest.driver, 1/*seconds*/);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
            
        //En caso de que aparezca la página de confirmación...
        if (PageQiwiConfirm.isPage(dFTest.driver)) {
            datosStep = new DatosStep     (
                "Seleccionar el botón \"Confirmar\" de la página de confirmación de Qiwi", 
                "Aparece la página de resultado del pago de Mango");
            try {
                PageQiwiConfirm.clickConfirmar(dFTest.driver);
                            
                datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
            }
            finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }            
        }
        
        return datosStep;
    }
}
