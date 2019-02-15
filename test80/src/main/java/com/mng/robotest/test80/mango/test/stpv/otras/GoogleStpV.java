package com.mng.robotest.test80.mango.test.stpv.otras;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.pageobject.otras.PageGoogle;
import com.mng.robotest.test80.mango.test.pageobject.shop.PagePrehome;
import com.mng.robotest.test80.mango.test.pageobject.shop.landing.PageLanding;


public class GoogleStpV {

    public static void accessGoogleAndSearchMango(DataFmwkTest dFTest) throws Exception {
        //Step. 
        DatosStep datosStep = new DatosStep       (
            "Accedemos a la URL <b> " + PageGoogle.URLacceso + "</b> y buscamos \"MANGO\"", 
            "Aparecen los links de Mango con contenido correcto");
        try {
            PageGoogle.accessViaURL(dFTest.driver);
            PageGoogle.searchTextAndWait(dFTest.driver, "MANGO");
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }           
    
        //Validaciones
        int maxSecondsWait = 5;
        String descripValidac = 
            "1) El 1er link no-anuncio contiene \"MANGO\" (lo esperamos " + maxSecondsWait + " segundos)<br>" +
            "2) El 1er link no-anuncion no contiene \"robots.txt\""; 
        datosStep.setExcepExists(false); datosStep.setResultSteps(State.Warn); 
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageGoogle.validaFirstLinkContainsUntil("MANGO", maxSecondsWait, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if (PageGoogle.validaFirstLinkContainsUntil("robots.txt", 0, dFTest.driver)) {
                listVals.add(2, State.Warn);
            }
                
            datosStep.setListResultValidations(listVals);
        }  
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }

    public static void selectFirstLinkSinPublicidad(DataFmwkTest dFTest) throws Exception { 
        //Step.
        DatosStep datosStep = new DatosStep       (
            "Seleccionamos el 1er link normal (sin publicidad)", 
            "Aparece la página inicial de la shop de Mango");
        try {
            PageGoogle.clickFirstLinkNoPubli(dFTest.driver);
    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
    
        //Validaciones.
        String descripValidac = 
            "1) Aparece la página de <b>Landing</b> o <b>Prehome</b> (estamos en la URL " + dFTest.driver.getCurrentUrl() + ")<br>";// +
            //"2) Si estamos en la Landing: aparece visible el modal de selección del país o el de provincia";
        datosStep.setExcepExists(false); datosStep.setResultSteps(State.Warn);  
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);         
        try {
            boolean isPageLanding = PageLanding.isPage(dFTest.driver);
            boolean isPagePrehome = PagePrehome.isPage(dFTest.driver);
            if (!isPageLanding && !isPagePrehome) {
                listVals.add(1, State.Defect);
            }
            /*
            if (isPageLanding) {
                if (!ModalCambioPais.isVisibleModalUntil(dFTest.driver, 0/) &&
                    !ModalCambioPais.isVisibleModalSelecProvincia(dFTest.driver)) {
                    listVals.add(2, State.Warn);                         
                }
            }*/
                
            datosStep.setListResultValidations(listVals);
        }  
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
}
