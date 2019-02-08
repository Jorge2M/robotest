package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageSuscripciones;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageSuscripciones.idNewsletters;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;

@SuppressWarnings("javadoc")
public class PageSuscripcionesStpV {
    
    public static DatosStep validaIsPage(DatosStep datosStep, DataFmwkTest dFTest) throws Exception {
        //Validaciones
        String descripValidac = 
            "1) Aparece la página de \"Suscripciones\"";
        datosStep.setStateIniValidations();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageSuscripciones.isPage(dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        //Validaciones estándar. 
        AllPagesStpV.validacionesEstandar(true/*validaSEO*/, true/*validaJS*/, false/*validaImgBroken*/, datosStep, dFTest);
        
        return datosStep;
    }    
    
    public static void validaIsDataAssociatedToRegister(HashMap<String,String> datosRegOk, DatosStep datosStep, DataFmwkTest dFTest) {
        int numLineasTotales = Integer.valueOf(datosRegOk.get("numlineas")).intValue();
        String lineasUnchecked = datosRegOk.get("clicklineas");
        StringTokenizer tokensLinDesmarcadas = new StringTokenizer(lineasUnchecked, ",");
        int numLinDesmarcadas = tokensLinDesmarcadas.countTokens();
        String descripValidac = 
            "1) Aparecen "  + numLineasTotales + " Newsletter<br>" +
            "2) Aparecen "  + numLinDesmarcadas + " suscripciones desmarcadas<br>" +
            "3) Aparecen desmarcadas las suscripciones de: " + lineasUnchecked;
        datosStep.setStateIniValidations();   
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (PageSuscripciones.getNumNewsletters(dFTest.driver)!=numLineasTotales) {
                listVals.add(1, State.Warn);
            }
            if (PageSuscripciones.getNumNewslettersDesmarcadas(dFTest.driver)!=numLinDesmarcadas) {
                listVals.add(2, State.Warn);
            }
            while (tokensLinDesmarcadas.hasMoreElements()) {
                String lineaStr=tokensLinDesmarcadas.nextToken();
                if (!PageSuscripciones.isNewsletterDesmarcada(lineaStr, dFTest.driver)) {
                    listVals.add(3, State.Warn);
                }
            }
            
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static DatosStep selectNewslettersAndGuarda(ArrayList<idNewsletters> listNewsletters, DataFmwkTest dFTest) throws Exception {
        DatosStep datosStep = new DatosStep (
            "Seleccionar los checkbox de las Newsletter <b>" + listNewsletters.toString() + "</b> + Botón \"Guardar Cambios\"", 
            "Aparece la confirmación que los datos se han modificado");
        try {
            //Seleccionamos los checkbox asociados a las newsletters
            for (idNewsletters idNewsletter  :listNewsletters)
                PageSuscripciones.clickRadioNewsletter(dFTest.driver, idNewsletter);
                    
            PageSuscripciones.clickGuardarCambios(dFTest.driver);
                                                                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }           
            
        //Validaciones.
        int maxSecondsToWait = 5;
        String descripValidac = 
            "1) Aparece una pantalla de resultado OK (la esperamos hasta " + maxSecondsToWait + " segundos)";
        datosStep.setStateIniValidations();     
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageSuscripciones.isPageResOKUntil(maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
                                    
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        return datosStep;
    }
}
