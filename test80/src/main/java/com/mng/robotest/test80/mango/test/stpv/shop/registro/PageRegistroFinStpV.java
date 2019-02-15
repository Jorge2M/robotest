package com.mng.robotest.test80.mango.test.stpv.shop.registro;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.PageRegistroFin;

@SuppressWarnings({"static-access"})
public class PageRegistroFinStpV {
    
    public static void isPage(DatosStep datosStep, DataFmwkTest dFTest) {
        //Validaciones.
        int maxSecondsToWait = 5;
        String descripValidac = 
            "1) Aparece la página final del proceso de registro (la esperamos hasta " + maxSecondsToWait + " segundos)";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageRegistroFin.isPageUntil(maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
    
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static DatosStep clickIrDeShoppingButton(DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
        //Step.
        DatosStep datosStep = new DatosStep (
            "Seleccionar el botón \"Ir de shopping\" y finalmente el icono de Mango", 
            "Se accede a la shop correctamente");
        try {
            PageRegistroFin.clickIrDeShopping(dFTest.driver);
            SecCabecera.getNew(dCtxSh.channel, dCtxSh.appE, dFTest.driver)
            	.clickLogoMango();
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
            
        //Validaciones.
        String descripValidac = 
            "1) El logo de Mango redirige al país/idioma origen: /" + dCtxSh.idioma.getAcceso() + "<br>" +
            "2) Aparece el link superior de \"Cerrar Sesión\" (estamos loginados)";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!SecCabecera.getNew(dCtxSh.channel, dCtxSh.appE, dFTest.driver)
            	.validaLogoMangoGoesToIdioma(dCtxSh.idioma)) {
                listVals.add(1, State.Warn);
            }
            if (!SecMenusWrap.secMenusUser.isPresentCerrarSesion(dCtxSh.channel, dFTest.driver)) {
                listVals.add(2, State.Defect);
            }
                            
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        return datosStep;
    }
}