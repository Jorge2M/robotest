package com.mng.robotest.test80.mango.test.stpv.votf;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.pageobject.votf.PageLoginVOTF;

@SuppressWarnings("javadoc")
public class PageLoginVOTFStpV {

    public static DatosStep goToAndLogin(String urlAcceso, DataCtxShop dCtxSh, DataFmwkTest dFTest) throws Exception {
        String usuarioVOTF = dCtxSh.pais.getAccesoVOTF().getUsuario();
        String passwordVOTF = dCtxSh.pais.getAccesoVOTF().getPassword();
        int numIdiomas = dCtxSh.pais.getListIdiomas().size();
        
        //Step
        String resultadoEsperado = "Aparece la página de selección de la línea";
        if (numIdiomas > 1)
            resultadoEsperado = "Aparece la página de selección del idioma";
        
        DatosStep datosStep = new DatosStep(
            "Acceder a la página de Login e identificarnos con un usuario de " + 
            dCtxSh.pais.getNombre_pais() + " (" + usuarioVOTF + " / " + passwordVOTF + ")",
            resultadoEsperado);
        try {
            PageLoginVOTF.goToFromUrlAndSetTestABs(urlAcceso, dCtxSh, dFTest);
            PageLoginVOTF.inputUsuario(usuarioVOTF, dFTest.driver);
            PageLoginVOTF.inputPassword(passwordVOTF, dFTest.driver);
            PageLoginVOTF.clickButtonContinue(dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        return datosStep;
    }
}
