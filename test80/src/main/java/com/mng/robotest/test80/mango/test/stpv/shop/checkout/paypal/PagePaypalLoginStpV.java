package com.mng.robotest.test80.mango.test.stpv.shop.checkout.paypal;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paypal.PagePaypalLogin;

@SuppressWarnings("javadoc")
public class PagePaypalLoginStpV {

    public static void validateIsPageUntil(int maxSecondsToWait, DatosStep datosStep, DataFmwkTest dFTest) {
        //Validaciones
        String descripValidac =
            "1) Aparece la página de login (la esperamos hasta un máximo de " + maxSecondsToWait + " segundos)";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok); 
        try {    
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PagePaypalLogin.isPageUntil(maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static DatosStep loginPaypal(String userMail, String password, DataFmwkTest dFTest) throws Exception { 
        //Step.
        DatosStep datosStep = new DatosStep     (
            "Introducimos las credenciales (" + userMail + " - " + password + ") y pulsamos el botón \"Iniciar sesión\"", 
            "Aparece la página de inicio de sesión en Paypal");
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);    
        datosStep.setGrabImage(true);
        datosStep.setGrabHTML(true);
        String paginaPadre = dFTest.driver.getWindowHandle();            
        try {                                          
            PagePaypalLogin.inputUserAndPassword(userMail, password, dFTest.driver);
            PagePaypalLogin.clickIniciarSesion(dFTest.driver);
                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally {
            dFTest.driver.switchTo().window(paginaPadre); //Salimos del iframe
            datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); 
        }
        
        //Validaciones
        int maxSecondsWait = 20;
        PagePaypalSelectPagoStpV.validateIsPageUntil(maxSecondsWait, datosStep, dFTest);
        
        return datosStep;
    }
}
