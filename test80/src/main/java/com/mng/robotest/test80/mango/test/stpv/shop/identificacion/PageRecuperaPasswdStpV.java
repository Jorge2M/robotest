package com.mng.robotest.test80.mango.test.stpv.shop.identificacion;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.identificacion.PageRecuperaPasswd;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;

@SuppressWarnings("javadoc")
public class PageRecuperaPasswdStpV {
    
    public static void isPage(DatosStep datosStep, DataFmwkTest dFTest) throws Exception {
    	int maxSecondsToWait = 2;
        String descripValidac = 
            "1) Aparece la pantalla de recuperación de la contraseña (la esperamos hasta " + maxSecondsToWait + " segundos)<br>" +
            "2) Aparece el campo para la introducción del correo";                         
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageRecuperaPasswd.isPageUntil(maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (!PageRecuperaPasswd.isPresentInputCorreo(dFTest.driver))
                fmwkTest.addValidation(2, State.Defect, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        //Validaciones estándar. 
        AllPagesStpV.validacionesEstandar(true/*validaSEO*/, false/*validaJS*/, false/*validaImgBroken*/, datosStep, dFTest);
    }
    
    public static void inputMailAndClickEnviar(String email, DataFmwkTest dFTest) throws Exception {
        //Step.
        DatosStep datosStep = new DatosStep       (
            "Introducir el email <b>" + email + "</b> y pulsar el botón \"Enviar\"", 
            "Aparece la página de cambio de contraseña");
        try {
            PageRecuperaPasswd.inputEmail(email, dFTest.driver);
            PageRecuperaPasswd.clickEnviar(dFTest.driver);
                                                                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validaciones
        int maxSecondsToWait = 2;
        String descripValidac = 
            "1) Aparece el mensaje de \"Revisa tu email\" (lo esperamos hasta " + maxSecondsToWait + " segundos)<br>" +
            "2) Aparece el botón \"Ir de Shopping\"";                         
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageRecuperaPasswd.isVisibleRevisaTuEmailUntil(maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (!PageRecuperaPasswd.isVisibleButtonIrDeShopping(dFTest.driver))
                fmwkTest.addValidation(2, State.Defect, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        //Validaciones estándar
        AllPagesStpV.validacionesEstandar(false/*validaSEO*/, true/*validaJS*/, false/*validaImgBroken*/, datosStep, dFTest);
    }
}
