package com.mng.robotest.test80.mango.test.stpv.shop.registro;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.PageRegistroFin;

@SuppressWarnings({"javadoc", "static-access"})
public class PageRegistroFinStpV {
    
    public static void isPage(datosStep datosStep, DataFmwkTest dFTest) {
        //Validaciones.
        int maxSecondsToWait = 5;
        String descripValidac = 
            "1) Aparece la página final del proceso de registro (la esperamos hasta " + maxSecondsToWait + " segundos)";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageRegistroFin.isPageUntil(maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static datosStep clickIrDeShoppingButton(DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
        //Step.
        datosStep datosStep = new datosStep (
            "Seleccionar el botón \"Ir de shopping\" y finalmente el icono de Mango", 
            "Se accede a la shop correctamente");
        try {
            PageRegistroFin.clickIrDeShopping(dFTest.driver);
            SecCabecera.getNew(dCtxSh.channel, dCtxSh.appE, dFTest.driver)
            	.clickLogoMango();
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
            
        //Validaciones.
        String descripValidac = 
            "1) El logo de Mango redirige al país/idioma origen: /" + dCtxSh.idioma.getAcceso() + "<br>" +
            "2) Aparece el link superior de \"Cerrar Sesión\" (estamos loginados)";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!SecCabecera.getNew(dCtxSh.channel, dCtxSh.appE, dFTest.driver)
            		.validaLogoMangoGoesToIdioma(dCtxSh.idioma))
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            if (!SecMenusWrap.secMenusUser.isPresentCerrarSesion(dCtxSh.channel, dFTest.driver))
                fmwkTest.addValidation(2, State.Defect, listVals);
                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        return datosStep;
    }
}