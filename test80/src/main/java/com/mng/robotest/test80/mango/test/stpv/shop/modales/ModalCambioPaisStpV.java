package com.mng.robotest.test80.mango.test.stpv.shop.modales;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.pageobject.shop.PagePrehome;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalCambioPais;
import com.mng.robotest.test80.mango.test.stpv.shop.PageHomeMarcasStpV;

@SuppressWarnings("javadoc")
public class ModalCambioPaisStpV {
    
    public static void validateIsVisible(DatosStep datosStep, DataFmwkTest dFTest) {
        int maxSecondsToWait = 5;
        String descripValidac = 
            "1) Aparece el modal de selección de país (lo esperamos hasta " + maxSecondsToWait + " segundos)"; 
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);  
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!ModalCambioPais.isVisibleModalUntil(dFTest.driver, maxSecondsToWait))
                fmwkTest.addValidation(1, State.Defect, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        } 
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static DatosStep cambioPais(DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
        //Step
        DatosStep datosStep = new DatosStep(
            "Cambiamos al país <b>" + dCtxSh.pais.getNombre_pais() + "</b> (" + dCtxSh.pais.getCodigo_pais() + "), idioma <b>" + dCtxSh.idioma.getCodigo().getLiteral() + "</b>", 
            "Se accede a la shop de " + dCtxSh.pais.getNombre_pais() + " en " + dCtxSh.idioma.getCodigo().getLiteral());
        try {
            PagePrehome.selecPaisIdiomaYAccede(dCtxSh, dFTest);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest));}
        
        //Validation. Aparece el país destino con todas las líneas de dicho país
        PageHomeMarcasStpV.validateIsPageWithCorrectLineas(dCtxSh.pais, dCtxSh.channel, dCtxSh.appE, datosStep, dFTest);
        
        return datosStep;
    }
}
