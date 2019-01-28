package com.mng.robotest.test80.mango.test.stpv.shop.checkout.sofort;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.sofort.PageSofort4th;

/**
 * Page4: la página de entrada usuario/password
 * @author jorge.munoz
 *
 */
@SuppressWarnings("javadoc")
public class PageSofort4thStpV {
    
    public static void validaIsPage(DatosStep datosStep, DataFmwkTest dFTest) { 
        String descripValidac = 
            "1) Aparece la página de introducción del Usuario/Password de \"SOFORT\"";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);           
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageSofort4th.isPage(dFTest.driver)) 
                fmwkTest.addValidation(1,State.Warn, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static DatosStep inputCredencialesUsr(String usrSofort, String passSofort, DataFmwkTest dFTest) throws Exception {
        DatosStep datosStep = new DatosStep       (
            "Introducir el usuario/password de DEMO: " + usrSofort + " / " + passSofort, 
            "Aparece la página de selección de cuenta");
        try {
            PageSofort4th.inputUserPass(dFTest.driver, usrSofort, passSofort);
            PageSofort4th.clickSubmitButton(dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        //Validaciones
        String descripValidac = 
            "1) Aparece un formulario para la selección de la cuenta"; 
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);           
        try {
            List<SimpleValidation> listVals = new ArrayList<>(); 
            //1)
            if (!PageSofort4th.isVisibleFormSelCta(dFTest.driver)) 
                fmwkTest.addValidation(1,State.Warn, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        return datosStep;
    }
    
    public static DatosStep select1rstCtaAndAccept(DataFmwkTest dFTest) throws Exception { 
        DatosStep datosStep = new DatosStep       (
            "Seleccionamos la 1a cuenta y pulsamos aceptar", 
            "Aparece la página de confirmación de la transacción");
        try {
            //Seleccionamos el radio correspondiente a la 1a cuenta + submit
            PageSofort4th.selectRadioCta(dFTest.driver, 1);
            PageSofort4th.clickSubmitButton(dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validaciones
        String descripValidac = 
            "1) Aparece un campo para la introducción del TAN"; 
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);           
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageSofort4th.isVisibleInputTAN(dFTest.driver))
                fmwkTest.addValidation(1,State.Warn, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        return datosStep;
    }
    
    public static DatosStep inputTANandAccept(String TANSofort, DataFmwkTest dFTest) throws Exception {
        DatosStep datosStep = new DatosStep       (
            "Introducción del TAN: " + TANSofort + " y pulsamos aceptar", 
            "El pago se realiza correctamente");
        try {
            PageSofort4th.inputTAN(dFTest.driver, TANSofort);
            PageSofort4th.clickSubmitButton(dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        return datosStep;
    }
}