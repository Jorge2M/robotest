package com.mng.robotest.test80.mango.test.stpv.shop.modales;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
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
        datosStep.setNOKstateByDefault();  
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!ModalCambioPais.isVisibleModalUntil(dFTest.driver, maxSecondsToWait)) {
                listVals.add(1, State.Defect);
            }

            datosStep.setListResultValidations(listVals);
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }
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
        finally { fmwkTest.grabStep(datosStep, dFTest);}
        
        //Validation. Aparece el país destino con todas las líneas de dicho país
        PageHomeMarcasStpV.validateIsPageWithCorrectLineas(dCtxSh.pais, dCtxSh.channel, dCtxSh.appE, datosStep, dFTest);
        
        return datosStep;
    }
}
