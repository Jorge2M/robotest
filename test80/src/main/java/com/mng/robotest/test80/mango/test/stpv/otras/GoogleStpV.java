package com.mng.robotest.test80.mango.test.stpv.otras;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.otras.PageGoogle;
import com.mng.robotest.test80.mango.test.pageobject.shop.PagePrehome;
import com.mng.robotest.test80.mango.test.pageobject.shop.landing.PageLanding;

@SuppressWarnings("javadoc")
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
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }           
    
        //Validaciones.
        String descripValidac = 
            "1) El 1er link no-anuncio contiene \"MANGO\" (lo esperamos 5 segundos)<br>" +
            "2) El 1er link no-anuncion no contiene \"robots.txt\""; 
        datosStep.setExcepExists(false); datosStep.setResultSteps(State.Warn);            
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageGoogle.validaFirstLinkContainsUntil("MANGO", 5/*maxSecondsToWait*/, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (PageGoogle.validaFirstLinkContainsUntil("robots.txt", 0/*maxSecondsToWait*/, dFTest.driver))
                fmwkTest.addValidation(2, State.Warn, listVals);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }  
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
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
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
    
        //Validaciones.
        String descripValidac = 
            "1) Aparece la página de <b>Landing</b> o <b>Prehome</b> (estamos en la URL " + dFTest.driver.getCurrentUrl() + ")<br>";// +
            //"2) Si estamos en la Landing: aparece visible el modal de selección del país o el de provincia";
        datosStep.setExcepExists(false); datosStep.setResultSteps(State.Warn);            
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            boolean isPageLanding = PageLanding.isPage(dFTest.driver);
            boolean isPagePrehome = PagePrehome.isPage(dFTest.driver);
            if (!isPageLanding && !isPagePrehome)
                fmwkTest.addValidation(1, State.Defect, listVals);
            /*
            //2)
            if (isPageLanding) {
                if (!ModalCambioPais.isVisibleModalUntil(dFTest.driver, 0/) &&
                    !ModalCambioPais.isVisibleModalSelecProvincia(dFTest.driver))
                    fmwkTest.addValidation(2, State.Warn, listVals);                         
            }*/
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }  
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
}
