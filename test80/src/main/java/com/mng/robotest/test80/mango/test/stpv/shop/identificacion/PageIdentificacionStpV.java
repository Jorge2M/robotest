package com.mng.robotest.test80.mango.test.stpv.shop.identificacion;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.identificacion.PageIdentificacion;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;

@SuppressWarnings("javadoc")
public class PageIdentificacionStpV {

    public static DatosStep inicioSesionDatosKO(String usrExistente, String password, Channel channel, AppEcom appE, DataFmwkTest dFTest) throws Exception {
        //Step.
        DatosStep datosStep = new DatosStep(
            "Seleccionar el link 'Iniciar Sesión' e introducir credenciales incorrectas: <b>" + usrExistente + ", " + password + "</b>",
            "Aparece el correspondiente mensaje de error");
        try {
            PageIdentificacion.iniciarSesion(usrExistente, password, channel, appE, dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        //Validaciones
        String descripValidac = 
            "1) Aparece el texto \"" + PageIdentificacion.avisoCredencialesKO + "\"";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageIdentificacion.isErrorEmailoPasswordKO(dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals); 

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        } 
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        //Validaciones estándar. 
        AllPagesStpV.validacionesEstandar(true/*validaSEO*/, false/*validaJS*/, false/*validaImgBroken*/, datosStep, dFTest);        

        return datosStep;
    }
    
    public static DatosStep selectHasOlvidadoTuContrasenya(DataFmwkTest dFTest) 
    throws Exception {
        //Step.
        DatosStep datosStep = new DatosStep       (
            "Seleccionar el link \"¿Has olvidado tu contraseña?\"", 
            "Aparece la página de cambio de contraseña");
        try {
            PageIdentificacion.clickHasOlvidadoContrasenya(dFTest.driver);
                                                                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }           

        //Validaciones
        PageRecuperaPasswdStpV.isPage(datosStep, dFTest);
        
        return datosStep;
    }
}
