package com.mng.robotest.test80.mango.test.stpv.votf;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.pageobject.votf.PageAlertaVOTF;
import com.mng.robotest.test80.mango.test.pageobject.votf.PageSelectIdiomaVOTF;

@SuppressWarnings("javadoc")
public class PageSelectIdiomaVOTFStpV {

    public static DatosStep selectIdiomaAndContinue(IdiomaPais idioma, DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep(
            "Seleccionar el idioma <b>" + idioma.getCodigo().getLiteral() + "</b> y pulsar \"Aceptar\" (si aparece una página de alerta la aceptamos)",
            "Aparece la página de selección de la línea");
        try {
            PageSelectIdiomaVOTF.selectIdioma(idioma.getCodigo(), dFTest.driver);
            PageSelectIdiomaVOTF.clickButtonAceptar(dFTest.driver);
            if (PageAlertaVOTF.isPage(dFTest.driver))
                PageAlertaVOTF.clickButtonContinuar(dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        return datosStep;
    }
}
